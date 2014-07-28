package luckycoins.core;

import java.util.ArrayList;

import luckycoins.LuckyCoins;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;

public class DailyQuest
{
	public final int index;
	public final String name;
	public final int reward;
	public final int max;
	
	protected DailyQuest(String name, int reward, int max)
	{
		this.index = DailyQuestHandler.getNextFreeDailyQuestIndex();
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
		return StatCollector.translateToLocal("quest." + name + ".name");
	}
	
	public String getTranslatedQuestDescription()
	{
		return StatCollector.translateToLocal("quest." + name + ".desc");
	}
}