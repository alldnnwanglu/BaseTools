/*
* 网络层服务端写网络数据给玩家
* @author : rodking
*/
public class SessionManager
{
	
	private static final ConcurrentHashMap<Long,Iosession>  MIS_IOSESSION_MAP = new ConcurrentHashMap();
	// 匿名用户 列表
	private static final ConcurrentHashMap<Long,Iosession>  ANONYMOUS_IOSESSION_MAP = new ConcurrentHashMap();
	// 在线玩家 session 列表
	private static final ConcurrentHashMap<Long,Iosession>  ONLINE_PLAYERID_IOSESSION_MAP = new ConcurrentHashMap();
	
	private int maxOnlineCount = 0;
	private int minOnlineCount = 0;
	
	private ResponseEncoder encoder;
	
	public void setEncoder(ResponseEncoder encoder)
	{
		this.encoder = encoder;
	}
	
	public Long getPlayerId(IoSession session)
	{
		Long playerId = null;
		if(session != null)
			playerId = (Long)session.getAttribute(SessionType.PLAYER_ID_KEY);
		
		return Long.valueOf(playerId == null ? 0L: playerId.longValue());
	}
	
	// 把玩家加入 到  在线列表中
	public void put2OnlineList(Long playerId,IoSession session)
	{
		if(session == null) return;
		
		long sessionId = session.getId();
		if(ANONYMOUS_IOSESSION_MAP.containsKey(Long.valueOf(session)))
		{
			ANONYMOUS_IOSESSION_MAP.remove(Long.valueOf(session));
		}
		
		IoSession orignSession = (IoSession)ONLINE_PLAYERID_IOSESSION_MAP.put(Long.valueOf(playerId),session);
		if((orignSession != null)&&(orignSession.isConnected()))
			orignSession.close(true);
		
		session.setAttribute(SessionType.PLAYER_ID_KEY,Long.valueOf(playerId));
		
		int onlineCount = getCurrentOnlineCount();
		if(this.maxOnlineCount < onlineCount)
			this.maxOnlineCount = onlineCount;
		
	}
	
	
	public void removeFromOnlineList(long playerId)
	{
			IoSession session = (IoSession)ONLINE_PLAYERID_IOSESSION_MAP.remove(Long.valueOf(playerId))l
			if(session == null) return;
			
			if(session.isConnected())
				ANONTMOUSE_IOSESSION_MAP.put(Long.valueOf(session.getId()),session);
				
			int onlineCount = getCurrentOnlineCount();
			if(this.minOnlineCount > onlineCount)
				this.minOnlineCount = onlineCount;
	}
	
	public int getMaxOnlineCount()
	{
		return this.maxOnlineCount;
	}
	
	public void setMaxOnlineCount(int maxOnlineCount)
	{
		this.maxOnlineCount = maxOnlineCount;
	}
	
	public int getMinOnlineCount()
	{
		return this.minOnlineCount;
	}
	
	public void setMinOnlineCount(int minOnlineCount)
	{
		this.minOnlineCount = minOnlineCount
	}
	
	public void resetOnlineUserCount()
	{
		int onlineCount = getCurrentOnlineCount();
		this.maxOnlineCount = onlineCount;
		this.maxOnlineCount = onlineCount;
	}
	
	public void removeFormOnlineList(IoSession session)
	{
		Long playerId = getPlayerId(session);
		IoSession oldSession = getIoSession(playerId.longValue());
		
		if((oldSession != null) &&(session == oldSession))
		{
			removeFromOnlineList(playerId.longValue());
		}
	}
	
	public void put2AnonymousList(IoSession session)
	{
		if(session !=null)
			ANONYMOUS_IOSESSION_MAP.put(Long.valueOf(session.getId()),session);
	}
	
	public void removeFormAnonymousList(IoSession session)
	{
		if(session != null)
		{
			long sessionId = session.getId();
			if(ANONYMOUS_IOSESSION_MAP.containsKey(Long.valueOf(session)))
				ANONYMOUS_IOSESSION_MAP.remove(Long.value(sessionId));
		}
	}
	
	public boolean isOnline(long playerId)
	{
		return ONLINE_PLAYERID_IOSESSION_MAP.containsKey(Long.valueOf(playerId));
	}
	
	public Set<Long> getOnLinePlayerIdList()
	{
		set onLinePlayerIdList = new HashSet();
		set onlinePlayerIds = ONLINE_PLAYER_IOSESSION_MAP.keySet();
		if((onlinePlayerIds != null)&&(!onlinePlayerIds.isEmpty()))
			onLinePlayerIdList.addAll(onlinePlayerIds);
		
		return onLinePlayerIdList;
	}
	
	public int getCurrentOnlineCount()
	{
		return ONLINE_PLAYERID_IOSESSION_MAP.size();
	}
	
	public IoSession getIoSession(long playerId)
	{
		return (IoSession)ONLINE_PLAYERID_IOSESSION_MAP.values();
	}
	
	public List<Iosession> getMisSession()
	{
		return new ArrayList(ANONYMOUS_IOSESSION_MAP.values());
	}
	
	public void put2MisList(IoSession session)
	{
		if(session != null)
		{
			if(ANONYMOUS_IOSESSION_MAP.containsKey(Long.valueOf(session.getId())))
				ANONYMOUS_IOSESSION_MAP.remove(Long.valueOf(session.getId()));
		}
		
		MIS_IOSESSION_MAP.put(Long.valueOf(session.getId()),session);
	}
	
	public void removeForMisList(Iosession session)
	{
		if(session != null)
		{
			if(MIS_IOSESSION_MAP.containsKey(Long.valueOf(session.getId())))
				MIS_IOSESSION_MAP.remove(Long.valueOf(session.getId()));
		}
		if(session.isConnected())
			ANONYMOUS_IOSESSION_MAP.put(Long.valueOf(Session.getId()),session);
	}
	
	public void write(long playerId,Response response)
	{
		IoSession session = getIoSession(playerId);
		write(session,response);
	}
	
	// 写消息给说有在线玩家
	public void writeAllOnline(Response response)
	{
		write(new HashSet(ONLINE_PLAYERID_IOSESSION_MAP.keySet()),response);
	}
	
	public void write(long playerId,IoBuffer buffer)
	{
		if(buffer!=null)
			write(getIoSession(playerId),buffer);
	}
	
	// 写数据给指定玩家 
	public void write(IoSession session,Response response)
	{
		if(session == null)return;
		
		Long playerId = getPlayerId(session);
		if(session.isConnected())
		{
			if(response != null)
				session.write(response);
		}
		else
		{
			closeIoSession(session,true);
		}
	}
	
	public void closeIoSession(IoSession session,boolean immediately)
	{
		if(session != null)
			session.close(immediately);
	}
	
	public void write(IoSession session,IoBuffer buffer)
	{
		if(session == null) return;
			Long playerId = getPlayerId(session);
		if(session.isConnected())
		{
			if(buffer != null)
				session.write(buffer);
		}
		else
		{
			closeIoSession(session,true);
		}
	}
	
	public String getRemoteIp(IoSession session)
	{
		if(session == null) return "";
		
		String remoteIp = (String) session.getAttribute(SessionType.REMOTE_HOST_KEY);
		if(!StringUtils.isBlack(remoteIp)
			return remoteIp;
		
		try
		{
			remoteIp = ((InetSocketAddress)session.getRemoteAddress().getHostAddress());
			if(StringUtils.isBlank(remoteIp))
				remoteIp = ((InetSocketAddress)session.getLocalAddress()).getAddress().getHostAddress();
			
			session.setAttribute(SessionType.REMOTE_HOST_KEY,remoteIp);
		}
		catch(Exception e)
		{
			remoteIp = null;
		}
		return StringUtils.defaultIfBlank(remoteIp,"");
	}
}