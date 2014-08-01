package luckycoins;

import java.io.File;

public class Configuration
{
	public static net.minecraftforge.common.config.Configuration config;
	
	public static boolean debug;
	
	public static void init(File file)
	{
		config = new net.minecraftforge.common.config.Configuration(file);
	}
	
	public static void reloadConfiguration()
	{
		config.load();
		
		debug = config.getBoolean("Debug Mode", "Settings", false, "Do not use if you don't know what it does!", "config.settings.debug_mode");
		
		if (config.hasChanged())
		{
			config.save();
		}
	}
}