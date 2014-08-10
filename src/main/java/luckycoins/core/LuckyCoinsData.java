package luckycoins.core;

import java.util.HashMap;

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
	private static final HashMap<String, NBTTagCompound> temporaryDataMap = new HashMap<String, NBTTagCompound>();
	
	public DailyQuestData dailyData;
	public int loot_boxes;
	public int coins;
	public int used_loot_box_codes;
	public boolean read_welcome_message;
	public boolean the_legend;
	
	public LuckyCoinsData() {}
	
	public LuckyCoinsData(EntityPlayer player)
	{
		this.loot_boxes = 0;
		this.coins = 0;
		this.read_welcome_message = false;
		this.used_loot_box_codes = 0;
		this.dailyData = new DailyQuestData(LuckyCoins.quests.getCurrentDailyQuest());
		this.the_legend = false;
	}
	
	@Override
	public void loadNBTData(NBTTagCompound tag)
	{
		NBTTagCompound data = tag.getCompoundTag(Refs.MOD_ID);
		this.loot_boxes = data.getInteger("LootBoxes");
		this.coins = data.getInteger("Coins");
		this.used_loot_box_codes = data.getInteger("UsedLootBoxCodes");
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
		data.setInteger("UsedLootBoxCodes", used_loot_box_codes);
		data.setBoolean("ReadWelcomeMessage", read_welcome_message);
		data.setBoolean("TheLegend-DontEvenTryToModifyThisSucker", the_legend);
		
		tag.setTag(Refs.MOD_ID, data);
		
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
		try
		{
			return (LuckyCoinsData)player.getExtendedProperties(Refs.MOD_ID);
		}
		catch (Throwable t)
		{
			LuckyCoins.logger.warn("Unable to get extended properties of entity: " + (player == null ? "null" : player.toString()));
			LuckyCoins.logger.catching(t);
			return new LuckyCoinsData(null);
		}
	}
	
	public static void saveTempData(EntityPlayer player)
	{
		String name = player.getCommandSenderName();
		NBTTagCompound tag = new NBTTagCompound();
		get(player).saveNBTData(tag);
		temporaryDataMap.put(name, tag);
	}
	
	public static void restoreTempData(EntityPlayer player)
	{
		String name = player.getCommandSenderName();
		if (!temporaryDataMap.containsKey(name))
		{
			return;
		}
		NBTTagCompound tag = temporaryDataMap.get(name);
		get(player).loadNBTData(tag);
		temporaryDataMap.remove(name);
	}
	
	@SideOnly(Side.CLIENT)
	public static int CLIENT_COINS;
	@SideOnly(Side.CLIENT)
	public static int CLIENT_BOXES;
	@SideOnly(Side.CLIENT)
	public static DailyQuest CLIENT_DAILY_QUEST;
	@SideOnly(Side.CLIENT)
	public static int CLIENT_DAILY_COMPLETED;
	@SideOnly(Side.CLIENT)
	public static String CLIENT_TIME_AND_DATE;
}