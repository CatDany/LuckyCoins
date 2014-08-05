package luckycoins.core;

import danylibs_luckycoins.LocalizationHelper;

public class DailyQuest
{
	private static int nextFreeID = 0;
	
	public static final DailyQuest PLAYER_KILL = new DailyQuest("PLAYER_KILL", 50, 3);
	public static final DailyQuest ZOMBIE_HEAL = new DailyQuest("ZOMBIE_HEAL", 70, 1);
	public static final DailyQuest HARVESTER = new DailyQuest("HARVESTER", 50, 50);
	public static final DailyQuest KNOCKDOWN = new DailyQuest("KNOCKDOWN", 60, 500);
	public static final DailyQuest FEED = new DailyQuest("FEED", 70, 16);
	public static final DailyQuest EAT_CAKE = new DailyQuest("EAT_CAKE", 50, 16);
	public static final DailyQuest ENDERMEN_KILL = new DailyQuest("ENDERMEN_KILL", 60, 3);
	
	public final int index;
	public final String name;
	public final int reward;
	public final int max;
	
	protected DailyQuest(String name, int reward, int max)
	{
		this.index = nextFreeID;
		nextFreeID++;
		this.name = name;
		this.reward = reward;
		this.max = max;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
	
	public String getTranslatedQuestName()
	{
		return LocalizationHelper.get("quest." + name + ".name");
	}
	
	public String getTranslatedQuestDescription()
	{
		return LocalizationHelper.get("quest." + name + ".desc");
	}
}