package luckycoins.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import luckycoins.LuckyCoins;

public class CoinRegistry
{
	public static final ArrayList<CoinBase> commons = new ArrayList<CoinBase>();
	public static final ArrayList<CoinBase> rares = new ArrayList<CoinBase>();
	public static final ArrayList<CoinBase> epics = new ArrayList<CoinBase>();
	public static final ArrayList<CoinBase> legendaries = new ArrayList<CoinBase>();
	
	public static final HashMap<String, CoinBase> map = new HashMap<String, CoinBase>();
	
	private static void registerCoin(String name, CoinBase coin)
	{
		map.put(name, coin);
		List list = null;
		switch (coin.getRarity())
		{
			case COMMON:
				list = commons;
				break;
			case RARE:
				list = rares;
				break;
			case EPIC:
				list = epics;
				break;
			case LEGENDARY:
				list = legendaries;
				break;
		}
		list.add(coin);
		LuckyCoins.logger.info("Added a coin with name '%s' with rarity '%s' by %s", name, coin.getRarity(), Loader.instance().activeModContainer().getModId());
	}
	
	public static void initCoins()
	{
		CoinBase INNER_RAGE = new CoinInnerRage();
		registerCoin("INNER_RAGE", INNER_RAGE);
	}
	
	private static class CoinInnerRage extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player, int x, int y,
				int z, float hitX, float hitY, float hitZ,
				MovingObjectPosition mop)
		{
			player.attackEntityFrom(DamageSource.magic, 4);
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 10 * 20, 1, true));
			return true;
		}
	}
}