package luckycoins.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

public interface IProxy
{
	/**
	 * Calls on preinitialization of mod
	 * @param e Event Data
	 */
	public void preInit(FMLPreInitializationEvent e);
	
	/**
	 * Calls on initialization of mod
	 * @param e Event Data
	 */
	public void init(FMLInitializationEvent e);
	
	/**
	 * Calls on postinitialization of mod
	 * @param e Event Data
	 */
	public void postInit(FMLPostInitializationEvent e);
	
	/**
	 * 
	 * @return Side (Client or Server)
	 */
	public Side getSide();
}