/*
* 网络层 编码
* @author:rodking
*/
public class ResponseEncoder extends ProtocolEncoderAdapter
{
	public void encode(IoSession session,Object message,ProtocolEncoderOutPut encoderOutput) throws Exception
	{
		if(message == null) return;
		
		if(message instanceof IoBuffer)
		{
			encoderOutput.write(message);
			encoderOutput.flush();
		}
		else if(message instanceof byte[])
		{
			IoBuffer buffer = IoBuffer.allocate(bytes.length);
			buffer.setAutoExpand(true);
			buffer.put(bytes);
			buffer.flip();
			buffer.free();
			encoderOutput.write(buffer);
			encoderOutput.flush();
		}
		else
		{
			IoBuffer buf = transform(message);
			if(buf != null)
			{	
				encoderOutput.write(buf);
				encoderOutput.flush();
			}
		}
	}
	
	public IoBuffer transform(Object message)
	{
		return transformByteArray(encodeResponse(message));
	}
	
	public IoBuffer transformByteArray(byte[] bytes)
	{
		if(bytes == null || bytes.length ==0) return;
	}
	
	public byte[] encodeResponse(Object message)
	{
		if(message instanceof Response)
		{
			Response response = (Response) message;
			response.setTime(System.currentTimeMillis());
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream();
			try
			{
				// 包装对象
				int sn = response.getSn();
				int cmd = response.getCmd();
				int module = response.getModule();
				int messageType = response.getMessageType();
				
				dataOutputStream.writeInt(sn);
				dataOutputStream.writeInt(messageType);
				dataOutputStream.writeInt(module);
				dataOutputStream.writeInt(cmd);
				dataOutputStream.writeDouble(response.getTime());
				
				Object value = response.getValue();
				byte[] bytes;
				
				if(value !=null)
				{
					bytes = transferByteArray(module,cmd,messageType,value);
					if(byte == null)
					{
						response.setStatus(-1);
						dataOutputStream.writeInt(response.getStatus());
					}
					else
					{
						dataOutputStream.writeInt(response.getStatus());
					}
				}
				return byteArrayOutStream.toByteArray();
			}
			catch(Exception e)
			{
				
			}
			finally
			{
				// 去掉关闭异常判断
				dataOutputStream.close();
				byteArrayOutputStream.close();
				
				dataOutputStream = null;
				byteArrayOutputStream = null;
			}
		}
		
		return null;
	}
	
	protected byte[] transferByteArray(int module,int cmd,int messageType,Object obj)
	{
		if(messageType == MessageType.AMF3.ordinal())
			return ObjectConverter.asObject2ByteArray(obj);
		if(messageType == MessageType.JAVA.ordinal())
			return ObjectConverter.object2ByteArray(obj);
		return null;
	}
	
}