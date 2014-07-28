package luckycoins.core;

import java.util.Random;

import com.ibm.icu.text.SimpleDateFormat;

public class DailyQuestHandler
{
	public DailyQuest getCurrentDailyQuest()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Random random = new Random();
		int index = random.nextInt(DailyQuest.getQuestsNumber());
		return DailyQuest.getQuest(index);
	}
}