package com.count.money.core.safe.userSafe;

public class AppContext
{
	private static ThreadLocal<SessionData> sessionPools = new ThreadLocal<SessionData>();

	public static void putSession(SessionData sessionData)
	{
		sessionPools.set(sessionData);
	}

	public static SessionData getSession()
	{
		return sessionPools.get();
	}

}
