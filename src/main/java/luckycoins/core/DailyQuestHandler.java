package luckycoins.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import luckycoins.Configuration;
import luckycoins.LuckyCoins;

import com.ibm.icu.text.SimpleDateFormat;

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
	
	private void registerDailyQuest(DailyQuest daily)
	{
		list.add(daily);
	}
	
	public static int getQuestsNumber()
	{
		return LuckyCoins.quests.list.size();
	}
	
	public static DailyQuest getQuest(int index)
	{
		return LuckyCoins.quests.list.get(index);
	}
	
	public static int getNextFreeDailyQuestIndex()
	{
		return LuckyCoins.quests.list.size();
	}
	
	public static String getFormattedDate()
	{
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(Configuration.TIME_ZONE));
		return cal.YEAR + "-" + cal.MONTH + "-" + cal.DAY_OF_MONTH;
	}
	
	public static void initDailyQuests()
	{
		LuckyCoins.quests.registerDailyQuest(new DailyQuest("PLAYER_KILL", 50, 3));
		LuckyCoins.quests.registerDailyQuest(new DailyQuest("ZOMBIE_HEAL", 70, 3));
	}	
}