package luckycoins.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import luckycoins.LuckyCoins;
import luckycoins.core.CoinBase.EnumCoinRarity;
import luckycoins.entity.EntityProjectile;
import luckycoins.items.core.ModItems;
import luckycoins.misc.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;
import danylibs.LocalizationHelper;

public class CoinRegistry
{
	public static final ArrayList<CoinBase> commons = new ArrayList<CoinBase>();
	public static final ArrayList<CoinBase> rares = new ArrayList<CoinBase>();
	public static final ArrayList<CoinBase> epics = new ArrayList<CoinBase>();
	public static final ArrayList<CoinBase> legendaries = new ArrayList<CoinBase>();
	
	public static final HashMap<String, CoinBase> map = new HashMap<String, CoinBase>();
	
	public static final Random RNG = new Random();
	
	public static CoinBase getRandomCoin()
	{
		EnumCoinRarity rarity = getRandomRarity();
		List list = getListFromRarity(rarity);
		int index = RNG.nextInt(list.size());
		return (CoinBase)list.get(index);
	}
	
	public static EnumCoinRarity getRandomRarity()
	{
		int hit = RNG.nextInt(100);
		if (hit < 64)
		{
			return EnumCoinRarity.COMMON;
		}
		else if (hit >= 64 && hit < 89)
		{
			return EnumCoinRarity.RARE;
		}
		else if (hit >= 89 && hit < 99)
		{
			return EnumCoinRarity.EPIC;
		}
		else if (hit == 99)
		{
			return EnumCoinRarity.LEGENDARY;
		}
		LuckyCoins.logger.warn("Unable to generate a random coin rarity! Random integer with range 0-99 isn't in range! Returning COMMON.");
		return EnumCoinRarity.COMMON;
	}
	
	public static List getListFromRarity(EnumCoinRarity rarity)
	{
		switch (rarity)
		{
			case COMMON:
				return commons;
			case RARE:
				return rares;
			case EPIC:
				return epics;
			case LEGENDARY:
				return legendaries;
		}
		return null;
	}
	
	protected static void registerCoin(CoinBase coin)
	{
		String name = coin.getUnlocalizedName();
		map.put(name, coin);
		List list = getListFromRarity(coin.getRarity());
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
		ItemStack stack = new ItemStack(ModItems.action_coin, 1, getCoin(name).getRarity() == EnumCoinRarity.COMMON ? 0 : (getCoin(name).getRarity() == EnumCoinRarity.RARE ? 1 : (getCoin(name).getRarity() == EnumCoinRarity.EPIC ? 2 : (getCoin(name).getRarity() == EnumCoinRarity.LEGENDARY ? 3 : 0))));
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
		CoinBase INNER_RAGE = new CoinInnerRage().setName("INNER_RAGE").setRarity(EnumCoinRarity.COMMON).registerCoin();
		CoinBase MINDSPIKE = new CoinMindspike().setName("MINDSPIKE").setRarity(EnumCoinRarity.COMMON).registerCoin();
		CoinBase LONG_JOURNEY = new CoinLongJourney().setName("LONG_JOURNEY").setRarity(EnumCoinRarity.RARE).registerCoin();
		CoinBase FROM_BLANK_SLATE = new CoinFromBlankSlate().setName("FROM_BLANK_SLATE").setRarity(EnumCoinRarity.EPIC).registerCoin();
		CoinBase REINFORCE = new CoinReinforce().setName("REINFORCE").setRarity(EnumCoinRarity.RARE).registerCoin();
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
	
	private static class CoinLongJourney extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			if (!player.isPotionActive(ModPotions.step_assist))
			{
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 10 * 60 * 20, 2, true));
				player.addPotionEffect(new PotionEffect(Potion.jump.id, 10 * 60 * 20, 1, true));
				player.addPotionEffect(new PotionEffect(ModPotions.step_assist.id, 10 * 60 * 20, 0, true));
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	private static class CoinFromBlankSlate extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(player.posX - 4.5, player.posY - 4.5, player.posZ - 4.5, player.posX + 4.5, player.posY + 4.5, player.posZ + 4.5);
			List<EntityMob> list = world.getEntitiesWithinAABB(EntityMob.class, aabb);
			for (EntityMob i : list)
			{
				i.attackEntityFrom(DamageSource.causeIndirectMagicDamage(player, player), Float.MAX_VALUE);
				if (i.getHealth() > 0)
				{
					i.setHealth(0);
				}
			}
			return true;
		}
	}
	
	private static class CoinReinforce extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			if (world.difficultySetting == EnumDifficulty.PEACEFUL)
			{
				return false;
			}
			else
			{
				EntityWitch witch = new EntityWitch(world);
				witch.setPosition(player.posX, player.posY, player.posZ);
				witch.setCustomNameTag(getRandomWitchName());
				witch.setAggressive(false);
				world.spawnEntityInWorld(witch);
				return true;
			}
		}
		
		private String getRandomWitchName()
		{
			int hit = RNG.nextInt(8) + 1;
			return LocalizationHelper.get("coin.COMMON.REINFORCE.data.witchName." + hit);
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