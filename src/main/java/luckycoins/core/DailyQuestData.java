package luckycoins.core;

import luckycoins.LuckyCoins;
import net.minecraft.nbt.NBTTagCompound;

public class DailyQuestData
{
	public DailyQuest daily;
	public String date;
	public int left;
	public boolean got_reward;
	
	public DailyQuestData(DailyQuest daily)
	{
		this.daily = daily;
		this.date = DailyQuestHandler.getFormattedDate();
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setString("Date", date);
		tag.setInteger("Left", left);
		tag.setBoolean("GotReward", got_reward);
	}
	
	public void readFromNBT(NBTTagCompound tag)
	{
		this.daily = LuckyCoins.quests.getCurrentDailyQuest();
		this.left = tag.getInteger("Left");
		this.got_reward = tag.getBoolean("GotReward");
	}
}