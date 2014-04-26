/*
* mina 网络层入口 (启动入口)
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
		
		// 各种攻击过滤模块 省略...
		// IoFilter cmdFilter = new CmdAttackFilter();
		// 字节攻击过滤
		// IoFilter byteFilter = new ByteAttackFilter();
		
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
