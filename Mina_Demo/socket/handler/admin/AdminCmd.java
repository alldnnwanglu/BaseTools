/*
* �����̨����
* @author:rodking
*/

public interface AdminCmd
{
	int CLEAR_CACHE = 1;				
	int OPEN_SESERFER_ACCESS = 2;   // ��������
	int SHUTDOWN_SERVER = 3;        // �رշ�����
	int CHECK_SERVER_STATE = 4;		// ������״̬��ѯ
	int GET_ONLINESTATISTIC = 5;    // ��ѯ����ͳ��
	int GET_ONLINE_REALTIME = 6;	// ��ѯʵʱ����
	int GET_PLAYER_ONLINE = 7;		// ���߽�ɫͳ��
	int GET_REGISTER_STATISTIC = 8; // ��ѯע��ͳ��
	int GET_CAMP_ONLINE = 9;		// ��ѯ��Ӫͳ��
	int GET_HORSE_LEVEL = 10;		// ͳ��ȫ������ӵ�зֲ�
	int GET_MERIDIANS_ICS = 11;		// ����ʵʱͳ��
	int GET_MORTAL_ICS = 12;		// ����ʵʱͳ��
	int GET_PLAYER_LEVEL_STATISTIC = 13; // ������ҵȼ�ͳ��
	int GET_REGISTER_DETAIL_STATISTIC = 14; // ��ȡע����ϸͳ��
	// etc
	
	/**** ��ɫģ�� *******/
	int ADD_MONEY = 1000;			 // ��ɫ��ֵ
	int QUERY_PLAYER = 1001;		 // ��ѯ�û���Ϣ
	// etc
	
	/*** �ʼ�/����ģ��****/
	int NOTICE_ADD = 2000;			// ��ӹ���
	int NOTICE_UPDATE = 2001;		// �޸Ĺ���
	
	/*** ����ϵͳ����****/
	int UPDATE_SYS_CONF = 3000;		// ����ϵͳ���ö���. �����ϵͳ������Ϣ��
	int BLOCK_IP = 3001;			// ���/���ip
	int GET_SYSTEM_CONFIG = 3002;	// ��ѯϵͳ����
	int UPDATE_INDULGE_OPTION = 3003; // ���·���������
}