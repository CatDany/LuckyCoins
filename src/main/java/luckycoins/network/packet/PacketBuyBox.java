package luckycoins.network.packet;

import io.netty.buffer.ByteBuf;
import luckycoins.core.LuckyCoinsData;
import luckycoins.network.PacketHandler;
import luckycoins.network.packet.PacketBuyBox.MessageBuyBox;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketBuyBox implements IMessageHandler<MessageBuyBox, IMessage>
{
	@Override
	public IMessage onMessage(MessageBuyBox message, MessageContext ctx)
	{
		if (ctx.side.isServer())
		{
			LuckyCoinsData data = LuckyCoinsData.get(ctx.getServerHandler().playerEntity);
			if (data.coins >= 200)
			{
				data.coins -= 200;
				data.loot_boxes++;
			}
			IMessage msg = new PacketDataSync.MessageDataSync(data);
			PacketHandler.instance().net.sendTo(msg, ctx.getServerHandler().playerEntity);
		}
		return null;
	}
	
	public static class MessageBuyBox implements IMessage
	{
		public MessageBuyBox() {}
		
		@Override
		public void fromBytes(ByteBuf buf) {}
		
		@Override
		public void toBytes(ByteBuf buf) {}
	}
}
