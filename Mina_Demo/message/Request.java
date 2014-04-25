/*
* ÍøÂç²ã ÇëÇó
* @author rodking
*/
public class Request extends Message implements Serializable
{
	public static Request valueOf(int sn,int module,int cmd,int messageType)
	{
		Request request = valueOf(sn,module,cmd);
		request.setMessageType(messageType);
		return request;
	}
	
	public static Request valueOf(int sn,int module,int cmd,int messageType)
	{
		Request request = valueOf(sn,module,cmd);
		request.sentMessageType(messageType);
		return request;
	}
	
	public static Request valueOf(int sn,int module,int cmd,int messageType,Object value)
	{
		Request request = valueOf(sn,module,cmd,messageType);
		request.setValue(value);
		return request;
	}
	
	public String toString()
	{
		return "Request" + super.toString();
	}
}