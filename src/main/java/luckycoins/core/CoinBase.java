package luckycoins.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import danylibs.LocalizationHelper;

public abstract class CoinBase
{
	private EnumCoinRarity rarity;
	private String unlocalizedName;
	
	public EnumCoinRarity getRarity()
	{
		return rarity;
	}
	
	public CoinBase setRarity(EnumCoinRarity rarity)
	{
		this.rarity = rarity;
		return this;
	}
	
	public String getUnlocalizedName()
	{
		return unlocalizedName;
	}
	
	public CoinBase setName(String name)
	{
		this.unlocalizedName = name;
		return this;
	}
	
	public CoinBase registerCoin()
	{
		CoinRegistry.registerCoin(this);
		return this;
	}
	
	public String getTranslatedName()
	{
		return LocalizationHelper.get("coin." + getRarity() + "." + getUnlocalizedName() + ".name");
	}
	
	public String getTranslatedDescription()
	{
		return LocalizationHelper.get("coin." + getRarity() + "." + getUnlocalizedName() + ".desc");
	}
	
	/**
	 * Called on Right-click
	 * @param world
	 * @param mop
	 * @return true if the action succeeded and you want to make a coin sound, otherwise false
	 */
	public abstract boolean action(World world, EntityPlayer player, MovingObjectPosition mop);
	
	public static enum EnumCoinRarity
	{
		COMMON,
		RARE,
		EPIC,
		LEGENDARY;
	}
}