package luckycoins.proxy;

import luckycoins.LuckyCoins;
import luckycoins.misc.KeybindHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy implements IProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e) {}
	
	@Override
	public void init(FMLInitializationEvent e)
	{
		LuckyCoins.keybinds = new KeybindHandler();
		LuckyCoins.keybinds.initKeybindings();
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e) {}
	
	@Override
	public Side getSide()
	{
		return Side.CLIENT;
	}
}