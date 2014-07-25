package luckycoins.network;

import luckycoins.Refs;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import danylibs.NetworkHelper;

public class PacketHandler
{
	private static PacketHandler instance = new PacketHandler();
	public SimpleNetworkWrapper net;
	
	private PacketHandler() {}
	
	public void init()
	{
		NetworkHelper helper = NetworkHelper.addNetHandler(Refs.MOD_ID.toUpperCase());
		net = helper.net;
	}
	
	public static PacketHandler instance()
	{
		return instance;
	}

	public static PacketHandler newInstance()
	{
		instance = new PacketHandler();
		return instance;
	}
}