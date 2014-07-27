package luckycoins.network.packet;

import io.netty.buffer.ByteBuf;
import luckycoins.core.LuckyCoinsData;
import luckycoins.network.PacketHandler;
import luckycoins.network.packet.PacketDataSync.MessageDataSync;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import danylibs.PlayerUtils;

public class PacketDataSync implements IMessageHandler<MessageDataSync, IMessage>
{
	@Override
	public IMessage onMessage(MessageDataSync message, MessageContext ctx)
	{
		if (ctx.side.isServer())
		{
			LuckyCoinsData data = LuckyCoinsData.get(ctx.getServerHandler().playerEntity);
			IMessage msg = new MessageDataSync(data);
			PacketHandler.instance().net.sendTo(msg, ctx.getServerHandler().playerEntity);
		}
		else if (ctx.side.isClient())
		{
			LuckyCoinsData.CLIENT_COINS = message.coins;
			LuckyCoinsData.CLIENT_BOXES = message.boxes;
		}
		return null;
	}
	
	public static class MessageDataSync implements IMessage
	{
		private int coins;
		private int boxes;
		
		public MessageDataSync() {}
		
		public MessageDataSync(LuckyCoinsData data)
		{
			this.coins = data.coins;
			this.boxes = data.loot_boxes;
		}
		
		@Override
		public void fromBytes(ByteBuf buf)
		{
			this.coins = buf.readInt();
			this.boxes = buf.readInt();
		}
		
		@Override
		public void toBytes(ByteBuf buf)
		{
			buf.writeInt(coins);
			buf.writeInt(boxes);
		}
	}
}