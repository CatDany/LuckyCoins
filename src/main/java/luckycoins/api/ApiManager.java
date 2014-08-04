package luckycoins.api;

public class ApiManager
{
	public static IApi getApi()
	{
		try
		{
			return (IApi)Class.forName("luckycoins.core.API").newInstance();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			return null;
		}
	}
}