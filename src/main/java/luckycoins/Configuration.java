package luckycoins;

import java.io.File;

public class Configuration
{
	public static net.minecraftforge.common.config.Configuration config;
	
	public static String TIME_ZONE;
	
	public static void init(File file)
	{
		config = new net.minecraftforge.common.config.Configuration(file);
	}
	
	public static void reloadConfiguration()
	{
		config.load();
		
		TIME_ZONE = config.get(net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL, "TimeZone", "GMT+4:00").getString();
		
		if (config.hasChanged())
		{
			config.save();
		}
	}
}