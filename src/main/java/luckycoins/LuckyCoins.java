package luckycoins;

import luckycoins.blocks.core.ModBlocks;
import luckycoins.core.CoinRegistry;
import luckycoins.core.DailyQuestHandler;
import luckycoins.entity.ModEntities;
import luckycoins.event.EventClient;
import luckycoins.event.EventDailyQuests;
import luckycoins.event.EventPlayer;
import luckycoins.event.EventServer;
import luckycoins.items.core.ModItems;
import luckycoins.misc.KeybindHandler;
import luckycoins.misc.ModDamageSources;
import luckycoins.misc.ModPotions;
import luckycoins.network.PacketHandler;
import luckycoins.proxy.IProxy;
import luckycoins.recipes.Recipes;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danylibs_luckycoins.EventBusHelper;

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
	@SideOnly(Side.CLIENT)
	public static final KeybindHandler keybinds = new KeybindHandler();
	public static final DailyQuestHandler quests = new DailyQuestHandler();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		logger = e.getModLog();
		Configuration.init(e.getSuggestedConfigurationFile());
		Configuration.reloadConfiguration();
		CoinRegistry.initCoins();
		ModPotions.patchPotionSystem();
		proxy.preInit(e);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		ModBlocks.initBlocks();
		ModItems.initItems();
		ModEntities.initEntities();
		ModPotions.initPotions();
		ModDamageSources.initDamageSources();
		Recipes.initRecipes();
		
		PacketHandler.instance().init();
		quests.initDailyQuests();
		EventBusHelper.checkBusAndRegister(new EventPlayer());
		EventBusHelper.checkBusAndRegister(new EventClient());
		EventBusHelper.checkBusAndRegister(new EventDailyQuests());
		EventBusHelper.checkBusAndRegister(new EventServer());
		proxy.init(e);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		Recipes.postInitOreDictNames();
		proxy.postInit(e);
	}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent e)
	{
		//
	}
}