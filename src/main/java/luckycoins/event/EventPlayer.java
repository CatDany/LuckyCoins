package luckycoins.event;

import org.lwjgl.input.Keyboard;

import luckycoins.LuckyCoins;
import luckycoins.Refs;
import luckycoins.core.DailyQuest;
import luckycoins.core.DailyQuestData;
import luckycoins.core.DailyQuestHandler;
import luckycoins.core.LuckyCoinsData;
import luckycoins.items.core.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import danylibs.ItemUtils;
import danylibs.Paragraph;
import danylibs.PlayerUtils;

public class EventPlayer
{
	@SubscribeEvent
	public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent e)
	{
		if (e.player.worldObj.isRemote)
			return;
		
		if (!LuckyCoinsData.get(e.player).read_welcome_message)
		{
			PlayerUtils.print(e.player, Paragraph.cyan + String.format(MessageRefs.WELCOME_1, Refs.MOD_ID));
			PlayerUtils.print(e.player, Paragraph.cyan + MessageRefs.WELCOME_2);
			PlayerUtils.print(e.player, Paragraph.cyan + MessageRefs.WELCOME_3);
			PlayerUtils.print(e.player, Paragraph.cyan + MessageRefs.WELCOME_4);
			PlayerUtils.print(e.player, Paragraph.cyan + String.format(MessageRefs.WELCOME_5, Keyboard.getKeyName(LuckyCoins.keybinds.keyGui.getKeyCode())));
			PlayerUtils.print(e.player, Paragraph.cyan + MessageRefs.WELCOME_6);
			
			LuckyCoinsData.get(e.player).read_welcome_message = true;
			LuckyCoins.logger.info("Shown welcome message to " + e.player.getCommandSenderName());
		}
	}
	
	@SubscribeEvent
	public void entityConstruct(EntityConstructing e)
	{
		if (e.entity.worldObj.isRemote)
			return;
		
		if (e.entity instanceof EntityPlayer)
		{
			LuckyCoinsData.register((EntityPlayer)e.entity);
			LuckyCoins.logger.info("Loaded additional data for player.");
		}
	}
	
	@SubscribeEvent
	public void itemCrafted(PlayerEvent.ItemCraftedEvent e)
	{
		if (e.player.worldObj.isRemote)
			return;
	}
	
	public void playerTick(TickEvent.PlayerTickEvent e)
	{
		if (e.player.worldObj.isRemote)
			return;
		
		LuckyCoinsData data = LuckyCoinsData.get(e.player);
		
		if (!data.dailyData.date.equals(DailyQuestHandler.getFormattedDate()))
		{
			data.dailyData = new DailyQuestData(LuckyCoins.quests.getCurrentDailyQuest());
			PlayerUtils.print(e.player, Paragraph.gold + MessageRefs.DAILY_QUEST_CHANGED_1);
			PlayerUtils.print(e.player, Paragraph.gold + MessageRefs.DAILY_QUEST_CHANGED_2);
		}
	}
	
	private static class MessageRefs
	{
		public static final String WELCOME_1 = StatCollector.translateToLocal("message.welcome.1");
		public static final String WELCOME_2 = StatCollector.translateToLocal("message.welcome.2");
		public static final String WELCOME_3 = StatCollector.translateToLocal("message.welcome.3");
		public static final String WELCOME_4 = StatCollector.translateToLocal("message.welcome.4");
		public static final String WELCOME_5 = StatCollector.translateToLocal("message.welcome.5");
		public static final String WELCOME_6 = StatCollector.translateToLocal("message.welcome.6");
		
		public static final String DAILY_QUEST_CHANGED_1 = StatCollector.translateToLocal("message.daily_quest_changed.1");
		public static final String DAILY_QUEST_CHANGED_2 = StatCollector.translateToLocal("message.daily_quest_changed.2");
	}
}