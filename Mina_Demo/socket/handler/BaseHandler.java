public abstract class BaseHandler implements Handler
{
	protected Dispatcher dispatcher;
	protected SessionManager sessionManager;
	protected final Map<Integer,Invoker>CMD_INVOKERS = new HashMap();
	
	protected abstract int getModule();
	protected abstract void inititialize();
	
	public final void init()
	{
		this.dispatcher.put(getModule(),this);
		inititialize();
	}
	
	public void putInvoker(int cmd,Invoke invoker)
	{
		if(this.CMD_INVOKERS.containsKey(Integer.valueOf(cmd)))
		{
			// log cmd;
		}
		
		this.CMD_INVOKERS.put(Integer.valueOf(cmd),invoker);
	}
	
	// ÅÉ·¢Âß¼­
	public void dispatch(IoSession session,Request request)
	{
		if(request != null)
		{
			int cmd = request.getCmd();
			int sn = request.getSn();
			int module = request.getModule();
			Request request = Response.valueOf(sn,module,cmd);
			request.setMessageType(request.getMessageType());
			Invoker invoker = (Invoker) this.CMD_INVOKERS.get(Integer.valueOf(cmd));
			
			if(invoker !=null)
			{
				invoker.invoke(session,request,response);
			}
			else
			{
				response.setStatus(-1);
				if(session.isConnected())
					session.write(response);
			}
		}
	}
}