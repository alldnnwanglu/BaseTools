/*
* ÍøÂç²ã ÏûÏ¢
* @author rodking
*/
public abstract class Message implements Serializable
{
	private int sn = -1;
	private int module;
	private int cmd;
	private long time = System.currentTimeMillis();
	
	private int messageType = 0;
	private Object value;
	
	public int getSn()
	{
		return this.sn;
	}
	
	public int getModule()
	{
		return this.module;
	}
	
	public int getCmd()
	{
		return this.cmd;
	}
	
	public long getTime()
	{
		return this.time;
	}
	
	public Object getValue()
	{
		return this.value;
	}
	
	public int getMessageType()
	{
		return this.messageType;
	}
	
	
	public void setModule(int module)
	{
		this.module = module;
	}
	
	public void setCmd(int cmd)
	{
		this.cmd = cmd;
	}
	
	public void setTime(long time)
	{
		this.time = time;
	}
	
	public void setValue(Object value)
	{
		this.value = value;
	}
	
	public void setMessageType(int messageType)
	{
		this.messageType = messageType;
	}
	
	public String toString()
	{
		return "[module=" + this.module+", cmd=" + this.cmd+", messageType=" + this.messageType+", sn="+this.sn+", value"+this.value+"]";
	}
}