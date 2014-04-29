/*
* �����̨ 
* �� ������ӵ� CMD_INVOKERS  dect<int,function>
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
	
	// ģ��id
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
		
		// ������������������
		putInvoker(AdminCmd.OPEN_SERVER_ACCESS,new Invoker()
		{
			public void invoke(IoSession session,Request request,Response response)
			{
				openServerAccess(session,request,response);
			}
		});
		
		// ͣ��
		// etc
		
		// ����ʵ�� 
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