package luckycoins.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public abstract class CoinBase
{
	private EnumCoinRarity rarity;
	private String unlocalizedName;
	
	public EnumCoinRarity getRarity()
	{
		return rarity;
	}
	
	public String getUnlocalizedName()
	{
		return unlocalizedName;
	}
	
	public String getTranslatedName()
	{
		return StatCollector.translateToLocalFormatted("coin.%s.name", getUnlocalizedName());
	}
	
	public String getTranslatedDescription()
	{
		return StatCollector.translateToLocalFormatted("coin.%s.desc", getUnlocalizedName());
	}
	
	/**
	 * Called on Right-click
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param hitX
	 * @param hitY
	 * @param hitZ
	 * @param mop
	 * @return true if the action succeeded and you want to make a coin sound, otherwise false
	 */
	public abstract boolean action(World world, EntityPlayer player, int x, int y, int z, float hitX, float hitY, float hitZ, MovingObjectPosition mop);
	
	public static enum EnumCoinRarity
	{
		COMMON,
		RARE,
		EPIC,
		LEGENDARY;
	}
}