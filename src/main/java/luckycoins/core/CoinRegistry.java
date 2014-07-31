package luckycoins.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import luckycoins.LuckyCoins;
import luckycoins.core.CoinBase.EnumCoinRarity;
import luckycoins.entity.EntityProjectile;
import luckycoins.items.core.ModItems;

public class CoinRegistry
{
	public static final ArrayList<CoinBase> commons = new ArrayList<CoinBase>();
	public static final ArrayList<CoinBase> rares = new ArrayList<CoinBase>();
	public static final ArrayList<CoinBase> epics = new ArrayList<CoinBase>();
	public static final ArrayList<CoinBase> legendaries = new ArrayList<CoinBase>();
	
	public static final HashMap<String, CoinBase> map = new HashMap<String, CoinBase>();
	
	private static void registerCoin(CoinBase coin)
	{
		String name = coin.getUnlocalizedName();
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
		LuckyCoins.logger.info(String.format("Added a coin with name '%s' with rarity '%s' by %s", name, coin.getRarity(), Loader.instance().activeModContainer().getModId()));
	}
	
	public static ItemStack getCoinStack(String name)
	{
		if (!map.containsKey(name))
		{
			LuckyCoins.logger.warn("Unable to find a coin with name %s!", name);
			Throwable t = new NullPointerException();
			LuckyCoins.logger.catching(t);
			return null;
		}
		ItemStack stack = new ItemStack(ModItems.action_coin);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("CoinType", name);
		tag.setString("CoinRarity", getCoin(name).getRarity().toString());
		stack.setTagCompound(tag);
		return stack;
	}
	
	public static CoinBase getCoin(String name)
	{
		if (!map.containsKey(name))
		{
			LuckyCoins.logger.warn("Unable to find a coin with name %s!", name);
			Throwable t = new NullPointerException();
			LuckyCoins.logger.catching(t);
			return null;
		}
		return map.get(name);
	}
	
	public static void initCoins()
	{
		CoinBase INNER_RAGE = new CoinInnerRage().setName("INNER_RAGE").setRarity(EnumCoinRarity.COMMON);
		registerCoin(INNER_RAGE);
		CoinBase MINDSPIKE = new CoinMindspike().setName("MINDSPIKE").setRarity(EnumCoinRarity.COMMON);
		registerCoin(MINDSPIKE);
	}
	
	private static class CoinInnerRage extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			player.attackEntityFrom(DamageSource.magic, 4);
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 10 * 20, 1, true));
			return true;
		}
	}
	
	private static class CoinMindspike extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			if (!world.isRemote)
			{
				world.spawnEntityInWorld(new EntityProjectile(world, player).setType("MINDSPIKE"));
			}
			return true;
		}
	}
	
	private static class Coin extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			
			return true;
		}
	}
}