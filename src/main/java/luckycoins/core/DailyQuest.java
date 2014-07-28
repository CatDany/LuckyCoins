package luckycoins.core;

import java.util.ArrayList;

import net.minecraft.util.StatCollector;

public enum DailyQuest
{
	PLAYER_KILL(0, 3);
	
	public final int reward;
	public final int max;
	
	private static final ArrayList<DailyQuest> list = new ArrayList<DailyQuest>();
	
	public static int getQuestsNumber()
	{
		return list.size();
	}
	
	public static DailyQuest getQuest(int index)
	{
		return list.get(index);
	}
	
	private DailyQuest(int reward, int max)
	{
		this.reward = reward;
		this.max = max;
		addToList();
		System.out.println(toString());
	}
	
	private void addToList()
	{
		list.add(this);
	}
	
	public String getTranslatedQuestName()
	{
		return StatCollector.translateToLocal("quest.");
	}
	
	public String getTranslatedQuestDescription()
	{
		return StatCollector.translateToLocal("quest.");
	}
}