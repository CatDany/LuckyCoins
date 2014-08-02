package luckycoins.network.packet;

import io.netty.buffer.ByteBuf;
import luckycoins.LuckyCoins;
import luckycoins.event.EventClient;
import luckycoins.network.packet.PacketSplashWarning.MessageSplashWarning;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

@Deprecated
public class PacketSplashWarning implements IMessageHandler<MessageSplashWarning, IMessage>
{
	@Override
	public IMessage onMessage(MessageSplashWarning message, MessageContext ctx)
	{
		if (ctx.side.isClient())
		{
			if (message.type == 0)
			{
				EventClient.show_one_with_nature_warning = message.value == 1 ? true : false;
			}
			else
			{
				LuckyCoins.logger.warn("Client received a packet with unknown type of MessageSplashWarning. This might be a result of something bad. Report to mod author!");
			}
		}
		return null;
	}
	
	public static class MessageSplashWarning implements IMessage
	{
		/**
		 * 0 - One With Nature Warning
		 */
		private byte type;
		private byte value;
		
		public MessageSplashWarning() {}
		
		public MessageSplashWarning(byte type, byte value)
		{
			this.type = type;
			this.value = value;
		}
		
		@Override
		public void fromBytes(ByteBuf buf)
		{
			this.type = buf.readByte();
		}
		
		@Override
		public void toBytes(ByteBuf buf)
		{
			buf.writeByte(type);
		}
	}
}