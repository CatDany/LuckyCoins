package luckycoins.network;

import io.netty.buffer.ByteBuf;
import luckycoins.Refs;
import luckycoins.network.packet.PacketBuyBox;
import luckycoins.network.packet.PacketDataSync;
import luckycoins.network.packet.PacketRedeem;
import luckycoins.network.packet.PacketResult;
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
		
		helper.registerMessage(0, PacketRedeem.class, PacketRedeem.MessageRedeem.class);
		helper.registerMessage(1, PacketResult.class, PacketResult.MessageResult.class);
		helper.registerMessage(2, PacketDataSync.class, PacketDataSync.MessageDataSync.class);
		helper.registerMessage(3, PacketBuyBox.class, PacketBuyBox.MessageBuyBox.class);
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
	
	public static void writeString(ByteBuf buf, String string)
	{
		buf.writeInt(string.getBytes().length);
		buf.writeBytes(string.getBytes());
	}
	
	public static String readString(ByteBuf buf)
	{
		return new String(buf.readBytes(buf.readInt()).array());
	}
}