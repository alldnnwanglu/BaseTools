/*
* mina 网络层入口
* @author rodking
*/
public class BootMinaStart
{
	public static void main(string[] args)
	{
		ProtocolEncoder encoder = new ResponseEncoder();   // 编码器
		ProtocolDecoder decoder = new RequestDecoder() ;   // 解析器
		
		// 协议编解码器的工厂
		ProtocolCodecFactory protocolCodecFactory = new CommonCodecFactory(encoder,decoder);
		
		// 通讯监控器
		IoHandler ioHandler = new MyServerHandler();
		// 字节攻击过滤
		IoFilter byteFilter = new ByteAttackFilter();
		
		final SocketServer socketServer = new SocketServer(protocolCodecFactory,ioHandler,byteFilter,cmdFilter);
		
		try
		{
			// 启动 网络套接字
			socketServer.start();
		}
		catch(Exception e)
		{
		}
		finally
		{
			// // 关闭 网络套接字
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				public void run() {
					socketServer.stop();
				}
			}));
		}
	}
}

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
		// 连接异常调用 do something ...
	}
	
	@Override
	public void messageReceived(IoSession session,Object message)throws Exception
	{
		 // 回复用户
		 // if 用户连接成功，session 是有效的
		 if(isOK(session,message))
		 {
			ioHandler.messageReceived(session,message);
			return;
		}
	}
	
	@Override
	public void messageSent(IoSession session,Object message)throws Exception
	{
		 // 发送消息
		 ioHandler.messageSent(session,message);
	}
	
	@Override
	public void sessionClosed(IoSession session)throws Exception
	{
		// 断开连接 do something
	}
	
	@Override
	public void sessionIdle(IoSession session,IdleStatus idleStatus)throws Exception
	{
		// 空闲状态 do something
	}
	
	@Override
	public void sessionOpened(IoSession session)throws Exception
	{
		// 打开连接
	}
	
	/*
	*	
	*/
	private bool isOK(IoSession session,Object message)
	{
		// 1. 如果是一个空包 或者 不是一个请求 返回
		if(message ==null || !(message instanceof Request)) return;
		
		// 2. 其他过滤(IP 过滤 ...)
		
		// 3. 解析请求
		Request request = (Request) message;
		int sn = request.getSn();
		int cmd = request.getCmd();
		int module = request.getModule();
		
		// 4 区分请求  (user/GM)
	
		// a. 用户相关验证
		if(true)
		{
			// 反馈消息
			Response response = Response.valueOf(sn,module,cmd);
			response.setMessageType(request.getMessageType());
			response.setStatus(ResonseCode.RESPONSE_CODE_NO_RIGHT);
			
			// 写网络流
			sessionMessage.write(session,response);
			return false;
		}
		
		// b. GM
		if(false)
		{
			// 反馈消息
			Response response = Response.valueOf(sn,module,cmd);
			response.setMessageType(request.getMessageType());
			response.setStatus(ResonseCode.RESPONSE_CODE_NO_RIGHT);
			
			// 写网络流
			sessionMessage.write(session,response);
			return true;
		}
		
		return true;
		
	}
	
	
}