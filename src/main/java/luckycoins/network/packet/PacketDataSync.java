package luckycoins.network.packet;

import io.netty.buffer.ByteBuf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import luckycoins.core.DailyQuestHandler;
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
			LuckyCoinsData.CLIENT_DAILY_QUEST = DailyQuestHandler.getQuest(message.dailyId);
			LuckyCoinsData.CLIENT_DAILY_COMPLETED = message.dailyCompleted;
			LuckyCoinsData.CLIENT_TIME_AND_DATE = message.time_and_date;
		}
		return null;
	}
	
	public static class MessageDataSync implements IMessage
	{
		private int coins;
		private int boxes;
		private short dailyId;
		private short dailyCompleted;
		private String time_and_date;
		
		public MessageDataSync()
		{
			this.time_and_date = "";
		}
		
		public MessageDataSync(LuckyCoinsData data)
		{
			this.coins = data.coins;
			this.boxes = data.loot_boxes;
			this.dailyId = (short)data.dailyData.daily.index;
			this.dailyCompleted = (short)data.dailyData.completed;
			
			Date date = new Date(System.currentTimeMillis());
			DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			this.time_and_date = format.format(date);
		}
		
		@Override
		public void fromBytes(ByteBuf buf)
		{
			this.coins = buf.readInt();
			this.boxes = buf.readInt();
			this.dailyId = buf.readShort();
			this.dailyCompleted = buf.readShort();
			this.time_and_date = PacketHandler.readString(buf);
		}
		
		@Override
		public void toBytes(ByteBuf buf)
		{
			buf.writeInt(coins);
			buf.writeInt(boxes);
			buf.writeShort(dailyId);
			buf.writeShort(dailyCompleted);
			PacketHandler.writeString(buf, time_and_date);
		}
	}
}