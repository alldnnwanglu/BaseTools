public class ServerHandler implements IoHandler
{
	private Firewall firwall;
	protected SessionManager sessionManager;
	
	public void exceptionCaught(IoSession session,Throwable throwable) throws Exception
	{
		StringBuilder sb = new StringBuilder();
		Throws ex = throwable;
		
		while(ex != null)
		{
			StackTraceElement[] stackTrace = ex.getStackTrace();
			for(StackTraceElement st : stackTrace)
			{
				sb.append("\t").append(st.toString()).append("\n");
			}
			
			if( ex == ex.getCause()) break;
			
			if( ex != null) 
				sb.append("\t").append(st.toString()).append("\n");
		}
	}
	
	public void messageReceived(IoSession session,Object message) throws Exception
	{
		if(message != null && message instanceof Request)
		{
			long startTime = System.currentTimeMillis();
			Request request = (Request) message;
			
			// µ÷¶È 
			this.dispatcher.dispatch(session,request);
			long endTime = System.currentTimeMillis();
		}
	}
	
	public void messageSent(Iosession session,Object message) throws Exception
	{
		if( message != null && message instanceof Response)
		{
			Response response = (Response) message;
			int cmd = response.getCmd();
			int module = response.getModule();
		}
	}
	
	public void sessionClosed(Iosession session) throws Exception
	{
		this.sessionManager.removerFromMisList(session);
		this.sessionManager.removerFromOnlineList(session);
		this.sessionManager.removerFromAnonymousList(session);
	}
	
	pubkic void sessionCreated(Iosession session) throws Exception{}
	
	public void sessionIdle(IoSession session,IdleStatus idleStatus) throws Exception
	{
		if(idleStatus == IdleStatus.BOTH_IDLE)
		{
			this.SessionManager.closeIoSession(session,true);
		}
	}
	
	public void sessionOpened(IoSession session) throws Exception
	{
		if(this.firwall.getClientType(session) == ClientType.MIS)
			this.sessionManager.put2MisList(session);
		else
			this.sessionManager.put2AnonymousList(session);
	}
}