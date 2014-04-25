using System.Collections;
using System.Text.RegularExpressions;
using StageEditor;
using System;

namespace rodking.tools
{
    ////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// <para>����ת������</para>
    /// <para>�ṩһЩ����������ת��,�ַ����и�</para>
    ///
    /// <para>ʱ��:2014-4-25 10:13:18</para>
    /// <para>����:rodking</para>
    /// <para>�汾:0.0.1</para>
    /// </summary>
    ////////////////////////////////////////////////////////////////////////
    public class ConvertExt
    {
        #region ����ת��

        public static int getInt(T o)
        {
            try
            {
                string str = getString(o);
                return string.IsNullOrEmpty(str) ? 0 : int.Parse(o);
            }
            catch (Exception e)
            {
                return 0;
            }
        }

        public static string getString(T o)
        {
            try
            {
                string str = getString(o);
                return string.IsNullOrEmpty(str) ? "" : o;
            }
            catch (Exception e)
            {
                return "";
            }
        }

        public static float getFloat(T o)
        {
            try
            {
                string str = getString(o);
                return string.IsNullOrEmpty(str) ? 0 : float.Parse(o);
            }
            catch (Exception e)
            {
                return 0.0f;
            }
        }

        public static float getFloat2(T o)
        {
            try
            {
                o = getFloat(o).ToString("F2");
                return getFloat(o);
            }
            catch (Exception e)
            {
                return 0.0f;
            }
        }

        public static int getMaxInt(int def, int value)
        {
            return value < def ? def : value;
        }

        #endregion

        #region �����и����

        /// <summary>
        /// ��� tab �ָ��Ӵ�
        /// </summary>
        /// <param name="str"></param>
        /// <returns></returns>
        public static string[] getTabSubStrings(string str)
        {
            str = str.Replace("\n", "");
            return Regex.Split(str, Global.TAB, RegexOptions.IgnoreCase);
        }

        public static string[] getCommaSubStrings(string str)
        {
            str = str.Replace("\n", "");
            return Regex.Split(str, ",", RegexOptions.IgnoreCase);
        }

        public static string[] getDownLineSubStrings(string str)
        {
            return str.Split(new char[] { '_' });
        }

        public static string[] getSpaceSubString(string str)
        {
            return str.Split(new char[] { ' ' });
        }

        public static string[] getUSDSubStrings(string str)
        {
            return str.Split(new char[] { '$' });
        }

        /// <summary>
        /// ȥ��str ������ ����
        /// </summary>
        /// <param name="str"></param>
        /// <returns></returns>
        public static string getSubStringNotLastWord(string str)
        {
            if (str.Length > 1)
                return str.Substring(0, str.Length - 1);
            return str;
        }

        public static string getString(string t, string max)
        {
            if (max != null && max.Length > 0)
                return max.Substring(0, max.Length);
            return t;
        }

        public static string getString(string t, Texture _tex)
        {
            if (_tex != null && _tex.name.Length > 0)
                return _tex.name.Substring(0, _tex.name.Length);
            return t;
        }

        #endregion
    }
}