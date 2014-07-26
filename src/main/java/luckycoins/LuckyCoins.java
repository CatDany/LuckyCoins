package luckycoins;

import luckycoins.blocks.core.ModBlocks;
import luckycoins.event.EventPlayer;
import luckycoins.items.core.ModItems;
import luckycoins.network.PacketHandler;
import luckycoins.proxy.IProxy;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import danylibs.EventBusHelper;

@Mod
(
	modid = Refs.MOD_ID,
	name = Refs.MOD_NAME,
	version = Refs.VERSION,
	dependencies = Refs.DEPENDENCIES
)
public class LuckyCoins
{
	@Instance(Refs.MOD_ID)
	public static LuckyCoins instance;
	
	@SidedProxy(clientSide = Refs.PROXY_CLIENT, serverSide = Refs.PROXY_SERVER)
	public static IProxy proxy;
	
	public static Logger logger;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		logger = e.getModLog();
		proxy.preInit(e);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		ModBlocks.initBlocks();
		ModItems.initItems();
		
		PacketHandler.instance().init();
		EventBusHelper.checkBusAndRegister(new EventPlayer());
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		//
	}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent e)
	{
		//
	}
}