/*
* ÍøÂç²ã ÏìÓ¦
* @author rodking
*/

public class Response extends Message implement Serializable
{
	public static final int DEFAULT_SN = -1;
	private int status = 0;
	
	public static Request defaultResponse(int module,int cmd)
	{
		Response response = new Response();
		response.setCmd(cmd);
		response.setModule(module);
		response.setSn(-1);
		return response;
	}
	
	public static Response defaultResponse(int module,int cmd,Object value)
	{
		Response response = new Response();
		response.setCmd(cmd);
		response.setValue(value);
		response.setModule(module);
		response.setSn(-1);
		return response;
	}
	
	public static Response valueOf(int sn,int module, int cmd)
	{
		Response response = new Response();
		response.setSn(sn);
		response.setCmd(cmd);
		response.setModule(module);
		return response;
	}
	
	public static Response valueOf(int sn,int module,int cmd,Object value)
	{
		Response response = new Response();
		response.setSn(sn);
		response.setCmd(cmd);
		response.setValue(module);
		response.setModule(module);
		return response;
	}
	
	public static Response valueOf(int sn,int module,int cmd,int messageType,int status)
	{
		Response response = valueOf(sn,module,cmd);
		response.setStatus(status);
		response.setMessageType(messageType);
		return response;
	}
	
	public static Response valueOf(int sn,int module,int cmd,int messageType,int status,Object value)
	{
		Response response = valueOf(sn,module,cmd,messageType,status);
		response.setValue(value);
		return response;
	}
	
	public int getStatus()
	{
		return this.status;
	}
	
	public Response setStatus(int status)
	{
		this.status = status;
		return this;
	}
	
	public String toString()
	{
		return "Response" + super.toString();
	}
}