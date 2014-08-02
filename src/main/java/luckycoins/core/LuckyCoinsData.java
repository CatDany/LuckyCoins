package luckycoins.core;

import luckycoins.LuckyCoins;
import luckycoins.Refs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LuckyCoinsData implements IExtendedEntityProperties
{
	public DailyQuestData dailyData;
	public int loot_boxes;
	public int coins;
	public boolean read_welcome_message;
	public boolean the_legend;
	
	public LuckyCoinsData() {}
	
	public LuckyCoinsData(EntityPlayer player)
	{
		this.loot_boxes = 0;
		this.coins = 0;
		this.read_welcome_message = false;
		this.dailyData = new DailyQuestData(LuckyCoins.quests.getCurrentDailyQuest());
		this.the_legend = false;
	}
	
	@Override
	public void loadNBTData(NBTTagCompound tag)
	{
		NBTTagCompound data = tag.getCompoundTag(Refs.MOD_ID);
		this.loot_boxes = data.getInteger("LootBoxes");
		this.coins = data.getInteger("Coins");
		this.read_welcome_message = data.getBoolean("ReadWelcomeMessage");
		this.the_legend = data.getBoolean("TheLegend-DontEvenTryToModifyThisSucker");
		
		NBTTagCompound dailyDataTag = data.getCompoundTag("DailyQuestsData");
		dailyData.readFromNBT(dailyDataTag);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound tag)
	{
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("LootBoxes", loot_boxes);
		data.setInteger("Coins", coins);
		data.setBoolean("ReadWelcomeMessage", read_welcome_message);
		tag.setTag(Refs.MOD_ID, data);
		tag.setBoolean("TheLegend-DontEvenTryToModifyThisSucker", the_legend);
		
		NBTTagCompound dailyDataTag = new NBTTagCompound();
		dailyData.writeToNBT(dailyDataTag);
		data.setTag("DailyQuestsData", dailyDataTag);
	}
	
	@Override
	public void init(Entity entity, World world) {}
	
	public static void register(EntityPlayer player)
	{
		player.registerExtendedProperties(Refs.MOD_ID, new LuckyCoinsData(player));
	}
	
	public static LuckyCoinsData get(EntityPlayer player)
	{
		return (LuckyCoinsData)player.getExtendedProperties(Refs.MOD_ID);
	}
	
	@SideOnly(Side.CLIENT)
	public static int CLIENT_COINS = 0;
	@SideOnly(Side.CLIENT)
	public static int CLIENT_BOXES = 0;
	@SideOnly(Side.CLIENT)
	public static DailyQuest CLIENT_DAILY_QUEST = DailyQuest.PLAYER_KILL;
	@SideOnly(Side.CLIENT)
	public static int CLIENT_DAILY_COMPLETED = 0;
}