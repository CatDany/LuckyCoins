package luckycoins.core;

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
	public int loot_boxes;
	public int coins;
	public boolean read_welcome_message;
	public boolean read_coin_message;
	public boolean read_greedy_message;
	
	public LuckyCoinsData() {}
	
	public LuckyCoinsData(EntityPlayer player)
	{
		this.loot_boxes = 0;
		this.coins = 0;
		this.read_welcome_message = false;
		this.read_coin_message = false;
		this.read_greedy_message = false;
	}
	
	@Override
	public void loadNBTData(NBTTagCompound tag)
	{
		NBTTagCompound data = tag.getCompoundTag(Refs.MOD_ID);
		this.loot_boxes = data.getInteger("LootBoxes");
		this.coins = data.getInteger("Coins");
		this.read_welcome_message = data.getBoolean("ReadWelcomeMessage");
		this.read_coin_message = data.getBoolean("ReadCoinMessage");
		this.read_greedy_message = data.getBoolean("ReadGreedyMessage");
	}
	
	@Override
	public void saveNBTData(NBTTagCompound tag)
	{
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("LootBoxes", loot_boxes);
		data.setInteger("Coins", coins);
		data.setBoolean("ReadWelcomeMessage", read_welcome_message);
		data.setBoolean("ReadCoinMessage", read_coin_message);
		data.setBoolean("ReadGreedyMessage", read_greedy_message);
		tag.setTag(Refs.MOD_ID, data);
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
}