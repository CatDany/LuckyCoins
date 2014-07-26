package luckycoins.network.packet;

import io.netty.buffer.ByteBuf;
import luckycoins.core.LuckyCoinsData;
import luckycoins.network.PacketHandler;
import luckycoins.network.packet.PacketDataSync.MessageDataSync;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketDataSync implements IMessageHandler<MessageDataSync, IMessage>
{
	@Override
	public IMessage onMessage(MessageDataSync message, MessageContext ctx)
	{
		if (ctx.side.isServer() && !message.toClient)
		{
			PacketHandler.instance().net.sendTo(new MessageDataSync(LuckyCoinsData.get(ctx.getServerHandler().playerEntity)), ctx.getServerHandler().playerEntity);
		}
		else if (ctx.side.isClient() && message.toClient)
		{
			LuckyCoinsData.CLIENT_COINS = message.coins;
			LuckyCoinsData.CLIENT_BOXES = message.boxes;
		}
		return null;
	}
	
	public static class MessageDataSync implements IMessage
	{
		private boolean toClient;
		private int coins;
		private int boxes;
		
		public MessageDataSync()
		{
			this.toClient = false;
		}
		
		public MessageDataSync(LuckyCoinsData data)
		{
			this.toClient = true;
			this.coins = data.coins;
			this.boxes = data.loot_boxes;
		}
		
		@Override
		public void fromBytes(ByteBuf buf)
		{
			this.toClient = buf.readBoolean();
			this.coins = buf.readInt();
			this.boxes = buf.readInt();
		}
		
		@Override
		public void toBytes(ByteBuf buf)
		{
			buf.writeBoolean(toClient);
			buf.writeInt(coins);
			buf.writeInt(boxes);
		}
	}
}