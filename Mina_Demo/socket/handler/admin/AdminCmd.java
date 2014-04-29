/*
* 管理后台命令
* @author:rodking
*/

public interface AdminCmd
{
	int CLEAR_CACHE = 1;				
	int OPEN_SESERFER_ACCESS = 2;   // 开服务器
	int SHUTDOWN_SERVER = 3;        // 关闭服务器
	int CHECK_SERVER_STATE = 4;		// 服务器状态查询
	int GET_ONLINESTATISTIC = 5;    // 查询在线统计
	int GET_ONLINE_REALTIME = 6;	// 查询实时在线
	int GET_PLAYER_ONLINE = 7;		// 在线角色统计
	int GET_REGISTER_STATISTIC = 8; // 查询注册统计
	int GET_CAMP_ONLINE = 9;		// 查询阵营统计
	int GET_HORSE_LEVEL = 10;		// 统计全服坐骑拥有分布
	int GET_MERIDIANS_ICS = 11;		// 筋脉实时统计
	int GET_MORTAL_ICS = 12;		// 肉身实时统计
	int GET_PLAYER_LEVEL_STATISTIC = 13; // 在线玩家等级统计
	int GET_REGISTER_DETAIL_STATISTIC = 14; // 获取注册明细统计
	// etc
	
	/**** 角色模块 *******/
	int ADD_MONEY = 1000;			 // 角色充值
	int QUERY_PLAYER = 1001;		 // 查询用户信息
	// etc
	
	/*** 邮件/公告模块****/
	int NOTICE_ADD = 2000;			// 添加公告
	int NOTICE_UPDATE = 2001;		// 修改公共
	
	/*** 更改系统配置****/
	int UPDATE_SYS_CONF = 3000;		// 更新系统配置对象. 具体的系统配置信息见
	int BLOCK_IP = 3001;			// 封禁/解封ip
	int GET_SYSTEM_CONFIG = 3002;	// 查询系统配置
	int UPDATE_INDULGE_OPTION = 3003; // 更新防沉迷配置
}