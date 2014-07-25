package luckycoins.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy implements IProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e) {}
	
	@Override
	public void init(FMLInitializationEvent e) {}
	
	@Override
	public void postInit(FMLPostInitializationEvent e) {}
}