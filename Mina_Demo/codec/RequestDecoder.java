/*
* ÍøÂç²ã ÏûÏ¢½âÎö
* @author rodking
*/

public class RequestDecoder extends CumulativeProtocolDecoder
{
	private SessionManager sessionManager;
	public static final Charset charset = Charset.forName("UTF-8");
	public static final int HEADER_LEN = 20;
	public static final int IGNORE_BYES = 28;
	public static final int PACKAGE_HEADER_LEN = 8;
	public static final int PACKAGE_HADER_ID = -1860168940;
	private final byte[] POICY_REQUEST = "<policy-file-request/>".getBytes(chrset);
	
	protected boolean doDecode(IoSession session,IoBuffer input,ProtocolDecoderOutPut out)throws Exce[tion
	{
		CodeContext ctx = (CodeContext)session.getAttribute(SessionType.CONTEXT_KEY);
		if((ctx!=null)&&(ctx.isSamState(DecoderState.WAITING_DATA))
		{
			{
				if(input.remaining() < ctx.getByteNeeded()) return false;
			}
		
			byte[] buffer = new byte[ctx.getByteNeeded()];
			input.get(buffer);
		
			Request request = decodeBuffer(bufer,session);
			if(request!=null)
			{
				out.write(request);
			}
		
			ctx.setState(DecoderState.READY);
			session.removeAttribute(SessionType.CONTEXT_KEY);
			return true;
		}
		Boolean firstRequest = (Boolean)session.getAttribute(SessionType.FIRST_REQUEST_KEY);
		if(firstRequest == null)
		{
			firstRequest = Boolean.valueOf(true);
			session.setAttribute(SessionType.FIRST_REQUEST_KEY,firstRequest);
		}
		
		if(firstRequest.booleanValue())
		{
		
		}
	}
}