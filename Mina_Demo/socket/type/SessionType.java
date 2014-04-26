/*
* Õ¯¬Á≤„session¿‡–Õ
* @author:rodking
*/

public abstract interface SessionType
{
  public static final AttributeKey CONTEXT_KEY = new AttributeKey(SessionType.class, "context");

  public static final AttributeKey PLAYER_ID_KEY = new AttributeKey(SessionType.class, "playerId");

  public static final AttributeKey LAST_CHAT_KEY = new AttributeKey(SessionType.class, "lastChatTime");

  public static final AttributeKey REMOTE_HOST_KEY = new AttributeKey(SessionType.class, "remoteHost");

  public static final AttributeKey WHICH_CLIENTS = new AttributeKey(SessionType.class, "whichClients");

  public static final AttributeKey CLIENT_TYPE_KEY = new AttributeKey(Firewall.class, "CLIENT_TYPE_KEY");

  public static final AttributeKey BRANCHING_ID_KEY = new AttributeKey(SessionType.class, "branchingId");

  public static final AttributeKey FIRST_REQUEST_KEY = new AttributeKey(SessionType.class, "firstRequest");

  public static final AttributeKey FLOOD_RECORD_KEY = new AttributeKey(SessionType.class, "floodRecordKey");

  public static final AttributeKey SERVER_FLAG_KEY = new AttributeKey(SessionType.class, "serverFlagIdKey");
}