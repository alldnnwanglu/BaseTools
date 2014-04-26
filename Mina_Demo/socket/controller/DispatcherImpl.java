/*
* �߼��ɷ��� (�ṩ�������߼� �̳У��Զ�ע�ắ��������)
* @author:rodking
*/
public class DispathcerImpl implements Dispatcher
{
	private static final Map<Integer,Handler> MODULE_HANDLERS = new HashMap(5);
	
	public void dispatch(IoSession session,Request request)
	{
		if((session == null) || (request == null)) return;
		
		int module = request.getModule();
		// ÿһ���߼� ��Ӧ һ�� id (ͨ��id ȡ���߼�)
		Handler handler = (Handler)MODULE_HANDLERS.get(Integer.valueOf(module));
		
		if(handler == null) return;
		handler.dispatch(session,request);
	}
	
	// ���� �߼���Ӧ id
	public void put(int modukeKey,Handler handler)
	{
		if(handler != null)
		{
			if(MODULE_HANDLERS.containsKey(Integer.valueOf(moduleKey))
			{
				throw new RuntimeException("");
			}
			MODULE_HANDLERS.put(Integer.valueOf(moduleKey),handler);
		}
	}
	
	public Handler getHandler(int moduleKey)
	{
		return (Handler)MODULE_HANDLERS.get(Integer.valueOf(moduleKey));
	}
}