package luckycoins.proxy;

import luckycoins.LuckyCoins;
import luckycoins.core.DailyQuest;
import luckycoins.core.LuckyCoinsData;
import luckycoins.entity.EntityEnderp;
import luckycoins.misc.KeybindHandler;
import luckycoins.render.RenderEnderp;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy implements IProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		LuckyCoinsData.CLIENT_BOXES = 0;
		LuckyCoinsData.CLIENT_COINS = 0;
		LuckyCoinsData.CLIENT_DAILY_COMPLETED = 0;
		LuckyCoinsData.CLIENT_DAILY_QUEST = DailyQuest.PLAYER_KILL;
		LuckyCoinsData.CLIENT_TIME_AND_DATE = "Updating...";
	}
	
	@Override
	public void init(FMLInitializationEvent e)
	{
		LuckyCoins.keybinds = new KeybindHandler();
		LuckyCoins.keybinds.initKeybindings();
		
		//RenderingRegistry.registerEntityRenderingHandler(EntityEnderp.class, new RenderEnderp());
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e) {}
	
	@Override
	public Side getSide()
	{
		return Side.CLIENT;
	}
}