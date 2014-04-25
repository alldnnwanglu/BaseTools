/*
* mina ��������Handler
* @author rodking
*/

public class MyServerHandler implements IoHandler
{
	@Autowired
	private IoHandler ioHandler;
	
	public MyServerHandler()
	{
		ioHandler = new IoHandler();
	}
	
	@Override
	public void exceptionCaught(IoSession session,Throwable throwable)throws Exception
	{
		// �����쳣���� do something ...
	}
	
	@Override
	public void messageReceived(IoSession session,Object message)throws Exception
	{
		 // �ظ��û�
		 // if �û����ӳɹ���session ����Ч��
		 if(isOK(session,message))
		 {
			ioHandler.messageReceived(session,message);
			return;
		}
	}
	
	@Override
	public void messageSent(IoSession session,Object message)throws Exception
	{
		 // ������Ϣ
		 ioHandler.messageSent(session,message);
	}
	
	@Override
	public void sessionClosed(IoSession session)throws Exception
	{
		// �Ͽ����� do something
	}
	
	@Override
	public void sessionIdle(IoSession session,IdleStatus idleStatus)throws Exception
	{
		// ����״̬ do something
	}
	
	@Override
	public void sessionOpened(IoSession session)throws Exception
	{
		// ������
	}
	
	/*
	*	
	*/
	private bool isOK(IoSession session,Object message)
	{
		// 1. �����һ���հ� ���� ����һ������ ����
		if(message ==null || !(message instanceof Request)) return;
		
		// 2. ��������(IP ���� ...)
		
		// 3. ��������
		Request request = (Request) message;
		int sn = request.getSn();
		int cmd = request.getCmd();
		int module = request.getModule();
		
		// 4 ��������  (user/GM)
	
		// a. �û������֤
		if(true)
		{
			// ������Ϣ
			Response response = Response.valueOf(sn,module,cmd);
			response.setMessageType(request.getMessageType());
			response.setStatus(ResonseCode.RESPONSE_CODE_NO_RIGHT);
			
			// д������
			sessionMessage.write(session,response);
			return false;
		}
		
		// b. GM
		if(false)
		{
			// ������Ϣ
			Response response = Response.valueOf(sn,module,cmd);
			response.setMessageType(request.getMessageType());
			response.setStatus(ResonseCode.RESPONSE_CODE_NO_RIGHT);
			
			// д������
			sessionMessage.write(session,response);
			return true;
		}
		
		return true;
		
	}
}