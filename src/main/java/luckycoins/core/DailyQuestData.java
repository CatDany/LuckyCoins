package luckycoins.core;

import luckycoins.LuckyCoins;
import net.minecraft.nbt.NBTTagCompound;

public class DailyQuestData
{
	public DailyQuest daily;
	public String date;
	public int completed;
	public boolean got_reward;
	
	public DailyQuestData(DailyQuest daily)
	{
		this.daily = daily;
		this.date = DailyQuestHandler.getFormattedDate();
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setString("Date", date);
		tag.setInteger("Completed", completed);
		tag.setBoolean("GotReward", got_reward);
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		this.daily = LuckyCoins.quests.getCurrentDailyQuest();
		this.date = tag.getString("Date");
		this.completed = tag.getInteger("Completed");
		this.got_reward = tag.getBoolean("GotReward");
	}
}