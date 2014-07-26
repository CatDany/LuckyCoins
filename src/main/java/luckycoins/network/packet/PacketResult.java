package luckycoins.network.packet;

import io.netty.buffer.ByteBuf;
import luckycoins.misc.Results;
import luckycoins.misc.Results.EnumResult;
import luckycoins.network.PacketHandler;
import luckycoins.network.packet.PacketResult.MessageResult;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketResult implements IMessageHandler<MessageResult, IMessage>
{
	@Override
	public IMessage onMessage(MessageResult message, MessageContext ctx)
	{
		Results.results.put(message.name, message.result);
		return null;
	}
	
	public static class MessageResult implements IMessage
	{
		private String name;
		private EnumResult result;
		
		public MessageResult() {}
		
		public MessageResult(String name, EnumResult result)
		{
			Results.results.put(name, result);
			this.name = name;
			this.result = result;
		}
		
		@Override
		public void fromBytes(ByteBuf buf)
		{
			this.name = PacketHandler.readString(buf);
			this.result = Results.readFromByteBuf(buf);
		}
		
		@Override
		public void toBytes(ByteBuf buf)
		{
			PacketHandler.writeString(buf, name);
			Results.writeToByteBuf(name, buf);
		}
	}
}