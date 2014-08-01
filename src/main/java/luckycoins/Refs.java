package luckycoins;

import luckycoins.proxy.ClientProxy;
import luckycoins.proxy.ServerProxy;

public class Refs
{
	public static final String MOD_ID		= "LuckyCoins";
	public static final String MOD_NAME		= "LuckyCoins";
	public static final String VERSION		= "";
	public static final String DEPENDENCIES	= ""
			+ "required-after:Forge@[10.12.2.1147,)";
	public static final String RESLOC		= MOD_ID.toLowerCase();
	
	public static final String PROXY_CLIENT = "luckycoins.proxy.ClientProxy";
	public static final String PROXY_SERVER = "luckycoins.proxy.ServerProxy";
	
	public static final String MAIN_HOST = "http://mods.hoppix.ru/";
}