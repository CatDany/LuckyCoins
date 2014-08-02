package luckycoins.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import luckycoins.LuckyCoins;
import luckycoins.event.EventPlayer.MessageRefs;
import net.minecraft.entity.player.EntityPlayer;
import danylibs.Paragraph;
import danylibs.PlayerUtils;

public class DailyQuestHandler
{
	private final ArrayList<DailyQuest> list = new ArrayList<DailyQuest>();
	
	public DailyQuest getCurrentDailyQuest()
	{
		long seed = getFormattedDate().hashCode();
		Random random = new Random(seed);
		int index = random.nextInt(getQuestsNumber());
		return getQuest(index);
	}
	
	public void triggerDailyQuest(EntityPlayer player, DailyQuest daily, int amount)
	{
		LuckyCoinsData data = LuckyCoinsData.get(player);
		if (data.dailyData.daily == daily && !data.dailyData.got_reward)
		{
			data.dailyData.completed += amount;
			if (data.dailyData.completed >= data.dailyData.daily.max)
			{
				data.dailyData.completed = data.dailyData.daily.max;
				triggerDailyReward(player);
			}
		}
	}
	
	private void triggerDailyReward(EntityPlayer player)
	{
		LuckyCoinsData data = LuckyCoinsData.get(player);
		int reward = data.dailyData.daily.reward;
		data.coins += reward;
		data.dailyData.got_reward = true;
		PlayerUtils.print(player, Paragraph.gold + MessageRefs.DAILY_QUEST_COMPLETED_1);
		PlayerUtils.print(player, Paragraph.gold + String.format(MessageRefs.DAILY_QUEST_COMPLETED_2, data.dailyData.daily.reward));
	}
	
	public static int getQuestsNumber()
	{
		return LuckyCoins.quests.list.size();
	}
	
	public static DailyQuest getQuest(int index)
	{
		return LuckyCoins.quests.list.get(index);
	}
	
	public static String getFormattedDate()
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		date.setTime(System.currentTimeMillis());
		return format.format(date);
	}
	
	public void initDailyQuests()
	{
		list.add(DailyQuest.PLAYER_KILL);
		list.add(DailyQuest.ZOMBIE_HEAL);
		list.add(DailyQuest.HARVESTER);
		list.add(DailyQuest.KNOCKDOWN);
	}	
}