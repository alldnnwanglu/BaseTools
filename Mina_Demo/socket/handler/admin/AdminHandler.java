/*
* 管理后台 
* 将 方法添加到 CMD_INVOKERS  dect<int,function>
* @author: rodking
*/
public class AdminHandler extends BaseHandler
{
	private MailFacade mailFacade;
	private AdminFacade adminFacade;
	private SystemConfigManager systemConfigManager;
	private OnlineStatisticManager onlineStatisticManager;
	private UserManager userManager;
	private RankManager rankManager;
	private AllianceManager allianceManager;
	private GameConfig gameConfigHelper;
	private HorseManager horseManager;
	private ActiveOperatorFacade activeOperatorFacade;
	
	// 模块id
	protected int getModule()
	{
		return Module.ADMIN;
	}
	
	protected void inititialize()
	{
		putInvoker(AdminCmd.CLEAR_CACHE,new Invoker()
		{
			public void invoke(IoSession session,Request request,Response response)
			{
				clearCache(session,reqeuset,response);
			}
		});
		
		// 开启服务器访问限制
		putInvoker(AdminCmd.OPEN_SERVER_ACCESS,new Invoker()
		{
			public void invoke(IoSession session,Request request,Response response)
			{
				openServerAccess(session,request,response);
			}
		});
		
		// 停服
		// etc
		
		// 方法实现 
		private void clearCache(IoSession session,Request request,Response response)
		{
			 // etc
		}
		
		private void openServerAccess(IoSession session,Request request,Response response)
		{
			 // etc
		}
		// ect
		
	}
}