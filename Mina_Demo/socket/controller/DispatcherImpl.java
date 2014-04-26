/*
* 逻辑派发类 (提供给具体逻辑 继承，自动注册函数到其中)
* @author:rodking
*/
public class DispathcerImpl implements Dispatcher
{
	private static final Map<Integer,Handler> MODULE_HANDLERS = new HashMap(5);
	
	public void dispatch(IoSession session,Request request)
	{
		if((session == null) || (request == null)) return;
		
		int module = request.getModule();
		// 每一个逻辑 对应 一个 id (通过id 取得逻辑)
		Handler handler = (Handler)MODULE_HANDLERS.get(Integer.valueOf(module));
		
		if(handler == null) return;
		handler.dispatch(session,request);
	}
	
	// 放入 逻辑对应 id
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