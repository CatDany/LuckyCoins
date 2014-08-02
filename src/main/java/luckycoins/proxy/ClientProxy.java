package luckycoins.proxy;

import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.entity.RenderHorse;
import luckycoins.entity.EntityHorseCoin;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy implements IProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e) {}
	
	@Override
	public void init(FMLInitializationEvent e) {}
	
	@Override
	public void postInit(FMLPostInitializationEvent e) {}
	
	@Override
	public Side getSide()
	{
		return Side.CLIENT;
	}
}