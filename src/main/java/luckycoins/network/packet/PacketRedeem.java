package luckycoins.network.packet;

import io.netty.buffer.ByteBuf;
import luckycoins.core.LuckyCoinsData;
import luckycoins.misc.Results.EnumResult;
import luckycoins.network.PacketHandler;
import luckycoins.network.packet.PacketRedeem.MessageRedeem;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import danylibs.InternetHelper;
import danylibs.Paragraph;
import danylibs.PlayerUtils;

public class PacketRedeem implements IMessageHandler<MessageRedeem, IMessage>
{
	@Override
	public IMessage onMessage(MessageRedeem message, MessageContext ctx)
	{
		if (ctx.side.isServer())
		{
			String code = message.code;
			int boxes = Integer.parseInt(InternetHelper.readRemoteFile("http://mods.hoppix.ru/Files/remote/LuckyCoins/check.php?key=%s", message.code));
			EnumResult result = boxes > 0 ? EnumResult.SUCCESS : EnumResult.FAIL;
			
			LuckyCoinsData.get(ctx.getServerHandler().playerEntity).loot_boxes += boxes;
			
			PlayerUtils.print(ctx.getServerHandler().playerEntity, 
					result == EnumResult.SUCCESS
					? Paragraph.green + StatCollector.translateToLocalFormatted("message.redeem.success", boxes)
					: Paragraph.rose + StatCollector.translateToLocal("message.redeem.fail"));
		}
		return null;
	}
	
	public static class MessageRedeem implements IMessage
	{
		private String code;
		
		public MessageRedeem() {}
		
		public MessageRedeem(String code)
		{
			this.code = code;
		}
		
		@Override
		public void fromBytes(ByteBuf buf)
		{
			this.code = PacketHandler.readString(buf);
		}
		
		@Override
		public void toBytes(ByteBuf buf)
		{
			PacketHandler.writeString(buf, code);
		}
	}
}