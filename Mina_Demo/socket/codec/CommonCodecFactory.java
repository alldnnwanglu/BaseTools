/*
* 网络层 协议编码解密工厂
* @author:rodking
*/
public class CommonCodecFactory implements ProtocolCodecFactory
{
	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;
	
	public CommonCodecFactory(ProtocolEncoder encoder,ProtocolDecoder decoder)
	{
		this.encoder = encoder;
		this.decoder = decoder;
	}
	
	public ProtocolDecoder getDecoder(IoSession session) throws Exception
	{
		return this.decoder;
	}
	
	public ProtocolEncoder getEncoder(IoSession session) throws Exception
	{
		return this.encoder;
	}
}