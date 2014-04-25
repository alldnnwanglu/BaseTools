/* 
 * 解析TAB分隔符文件 
 * 同时支持编辑器和运行时引擎
 * 来源于 http://svn.stellman-greene.com/CSVReader 
 */

using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using UnityEngine;

namespace client.core.common.util
{
    public class TableFileReader : IDisposable
    {
        public const string NEWLINE = "\r\n";
        public const string TAB = "\t";
        public const string TAB_QUOTE = "\"\t";

        
        private BinaryReader reader;

        /// <summary>
        /// 读取时跳过多少行
        /// </summary>
        public int scanRows  { get; set; }

        #region Constructors

        public TableFileReader(string data)
        {
            if ( String.IsNullOrEmpty( data ) )
                throw new ArgumentNullException("Null string passed to TableFileReader");

            this.reader = new BinaryReader(new MemoryStream(System.Text.Encoding.UTF8.GetBytes( data) ));
        }

        #endregion


        string currentLine = "";
        /// <summary>
        /// Read the next row from the Table data
        /// </summary>
        /// <returns>A list of objects read from the row, or null if there is no next row</returns>
        public ListEx<object> ReadRow()
        {
            // ReadLine() will return null if there's no next line
            if (reader.BaseStream.Position >= reader.BaseStream.Length)
                return null;

            StringBuilder builder = new StringBuilder();

            // Read the next line
            while ((reader.BaseStream.Position < reader.BaseStream.Length) && (!builder.ToString().EndsWith(NEWLINE)))
            {
                char c = reader.ReadChar();
                builder.Append(c);
            }

            currentLine = builder.ToString();
            if (currentLine.EndsWith(NEWLINE))
                currentLine = currentLine.Remove(currentLine.IndexOf(NEWLINE), NEWLINE.Length);

            // Build the list of objects in the line
            ListEx<object> objects = new ListEx<object>();
            while (currentLine != "")
                objects.Add(ReadNextObject());
            return objects;
        }

        /// <summary>
        /// Read the next object from the currentLine string
        /// </summary>
        /// <returns>The next object in the currentLine string</returns>
        private object ReadNextObject()
        {
            if (currentLine == null)
                return null;

            // Check to see if the next value is quoted
            bool quoted = false;
            if (currentLine.StartsWith("\""))
                quoted = true;

            // Find the end of the next value
            string nextObjectString = "";
            int i = 0;
            int len = currentLine.Length;
            bool foundEnd = false;
            while (!foundEnd && i <= len)
            {
                // Check if we've hit the end of the string
                if ((!quoted && i == len) // non-quoted strings end with a comma or end of line
                    || (!quoted && currentLine.Substring(i, 1) == TAB )
                    // quoted strings end with a quote followed by a comma or end of line
                    || (quoted && i == len - 1 && currentLine.EndsWith("\""))
                    || (quoted && currentLine.Substring(i, 2) == TAB_QUOTE ))
                    foundEnd = true;
                else
                    i++;
            }
            if (quoted)
            {
                if (i > len || !currentLine.Substring(i, 1).StartsWith("\""))
                    throw new FormatException("Invalid Table file format: " + currentLine.Substring(0, i));
                i++;
            }
            nextObjectString = currentLine.Substring(0, i).Replace("\"\"", "\"");

            if (i < len)
                currentLine = currentLine.Substring(i + 1);
            else
                currentLine = "";

            if (quoted)
            {
                if (nextObjectString.StartsWith("\""))
                    nextObjectString = nextObjectString.Substring(1);
                if (nextObjectString.EndsWith("\""))
                    nextObjectString = nextObjectString.Substring(0, nextObjectString.Length - 1);
                return nextObjectString;
            }
            else
            {
                object convertedValue;
                StringConverter.ConvertString(nextObjectString, out convertedValue);
                return convertedValue;
            }
        }

        /// <summary>
        /// Read the row data read using repeated ReadRow() calls and build a DataColumnCollection with types and column names
        /// </summary>
        /// <param name="headerRow">True if the first row contains headers</param>
        /// <returns>System.Data.DataTable object populated with the row data</returns>
        private DataTable CreateDataTable(bool headerRow)
        {
            // Read the CSV data into rows
            ListEx<ListEx<object>> rows = new ListEx<ListEx<object>>();
            ListEx<object> readRow = null;
            int rowCount = 0;
            while ((readRow = ReadRow()) != null)
            {
                if ( rowCount >= scanRows )
                {
                    rows.Add(readRow);
                }
                rowCount++;
            }

            // The types and names (if headerRow is true) will be stored in these lists
            List<Type> columnTypes = new List<Type>();
            ListEx<string> columnNames = new ListEx<string>();

            // Read the column names from the header row (if there is one)
            if (headerRow)
                foreach (object name in rows[0])
                    columnNames.Add(name.ToString());

            // Read the column types from each row in the list of rows
            bool headerRead = false;
            foreach (ListEx<object> row in rows)
                if (headerRead || !headerRow)
                    for (int i = 0; i < row.Count; i++)
                        // If we're adding a new column to the columnTypes list, use its type.
                        // Otherwise, find the common type between the one that's there and the new row.
                        if (columnTypes.Count < i + 1)
                            columnTypes.Add(row[i].GetType());
                        else
                            columnTypes[i] = StringConverter.FindCommonType(columnTypes[i], row[i].GetType());
                else
                    headerRead = true;

            DataTable dt = new DataTable();
            dt.rows = rows;
            dt.columnNames = columnNames;
            dt.columnNumber = rows[0].size;
            return dt;
        }


        /// <summary>
        /// 从Unity的Resources目录读取表格文件
        /// </summary>
        /// <param name="fullName">相对于Resources目录的路径</param>
        /// <param name="headerRow">第一行是否为列的名称</param>
        public static DataTable ReadTableFileFromRes( string fullName, bool headerRow )
        {
            TextAsset txt = (TextAsset)Resources.Load( fullName );
            if (!txt)
            {
                Debug.LogError( "资源路径错误:" + fullName );
                return null;
            }
            using ( TableFileReader reader = new TableFileReader( txt.text ) )
            {
                return reader.CreateDataTable(headerRow);
            }
        }

        public static DataTable ReadTableFileFromRes(string fullName, bool headerRow, int scanRows )
        {
            TextAsset txt = (TextAsset)Resources.Load(fullName);
            if (!txt)
            {
                Debug.LogError("资源路径错误:" + fullName);
                return null;
            }
            using (TableFileReader reader = new TableFileReader(txt.text))
            {
                reader.scanRows = scanRows;
                return reader.CreateDataTable(headerRow);
            }
        }

        #region IDisposable Members

        public void Dispose()
        {
            if (reader != null)
            {
                try
                {
                    // Can't call BinaryReader.Dispose due to its protection level
                    reader.Close();
                }
                catch { }
            }
        }

        #endregion
    }
    
}
