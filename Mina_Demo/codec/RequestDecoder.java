/*
* ����� ��Ϣ����
* @author rodking
*/
public enum DecoderState
{
  WAITING_DATA, 
  READY;
}

public class RequestDecoder extends CumulativeProtocolDecoder
{
	private SessionManager sessionManager;
	public static final Charset charset = Charset.forName("UTF-8");
	public static final int HEADER_LEN = 20;
	public static final int IGNORE_BYES = 28;
	public static final int PACKAGE_HEADER_LEN = 8;
	public static final int PACKAGE_HADER_ID = -1860168940;
	private final byte[] POICY_REQUEST = "<policy-file-request/>".getBytes(chrset);
	
	protected boolean doDecode(IoSession session,IoBuffer input,ProtocolDecoderOutPut out)throws Exce[tion
	{
		// ...ʡ�� ���ִ���
		while(true)
		{
			if(input.remaining < 8) return false;
			input.mark();
			if(input.getInt() == -1860168940) break;
			
			input.reset();
			input.get();
		}
		
		int len = input.getInt();
		if((len <= 0)||(len >= 65536)) return true;
		
		if(input.remaining() < len)
		{
			return false;
		}
		byte[] buffer = new byte[len];
		Request request = decodeBuffer(buffer,session);
		if(request != null)
			out.write(request);
			
		return true;
	}
	
	// ���� ������ 
	public Request decodeBuffer(byte[] buffer,IoSession session)
	{
		if(buffer ==null) return null;
		int bufferSize = buffer.length;
		if(buffer <20) return null;
		
		int skipBytes = 12;
		DataInputStream dataInputStream = null;
		ByteArrayInputStream byteArrayInputStream = null;
		byte[] authData = Arrays.copyOfRange(buffer,12,bufferSize);
		
		try
		{
			byteArrayInputStream = new ByteArrayInputStream(buffer);
			dataInputStream =new DataInputStream(byteArrayInputStream);
			
			// �������ݰ�
			int sn = dataInputStream.readInt();
			int messageType = dataInputStream.readInt();
			int authCode = dataInputStream.readInt();
			int module = dataInputStream.readInt();
			int cmd = dataInputStream.readInt();
			int calcAuthCode = HashAlgorithms.fnvHash(authData);
			
			// ���������֤
			if(authCode != calcAuthCode)
			{
				session.write(Response.valueOf(sn,module,cmd,messageType,4);
				// ����ǽ��ش��� ��...
				return null;
			}
			
			Request request = Request.valueOf(sn,module,cmd,messageType);
			byte[] byteArray;
			if(bufferSize > 20)
			{
				byteArray = new byte[bufferSize - 20];
				dataInputStream.read(byteArray);
				// ����Ϊ��Ϸ�е��߼�����
				Object value = transferObject(module,cmd,messageType,byteArray);
				
				if(value == null)
				{
					session.write(Response.valueOf(sn,module,cmd,messageType,2);
					return null;
				}
				
				request.setValue(value);
			}
			return requset;
		}
		catch(Exception ex)
		{
			
		}
		finally
		{
			// �ر��� ��д ȡ�� �����쳣��֤
			if(dataInputStream !=null) dataInputStream.close();
			if(byteArrayStream !=null) byteArrayStream.close();
			dataInputStream = null;
			byteArrayStream = null;
		}
		
	}
	
	/// �������
	protected Object transferObject(int module,int cmd,int messageType,byte[] array)
	{
		if(messageType == MessageType.AMF3.ordinal())
			return ObjectConvert.byteArray2ASObject(array);
		if(messageType == MessageType.JAVA.ordinal())
			return ObjectConvert.byteArray2Object(array);
		return null;
	}
}