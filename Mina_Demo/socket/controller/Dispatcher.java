public abstract interface Dispatcher
{
	public abstract void put(int paramInt,Handler paramHandler);
	public abstract void dispatch(IoSession paramIoSession,Request paramRequest);
}