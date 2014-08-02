package luckycoins.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import luckycoins.LuckyCoins;
import luckycoins.core.CoinBase.EnumCoinRarity;
import luckycoins.entity.EntityProjectile;
import luckycoins.items.ItemSpectralSword;
import luckycoins.items.core.ModItems;
import luckycoins.misc.ModDamageSources;
import luckycoins.misc.ModPotions;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import cpw.mods.fml.common.Loader;
import danylibs.LocalizationHelper;
import danylibs.PlayerUtils;

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
		CoinBase FENCES_ON_MY_WAY = new CoinFencesOnMyWay().setName("FENCES_ON_MY_WAY").setRarity(EnumCoinRarity.COMMON).registerCoin();
		CoinBase UNFAIR_ADVANTAGE = new CoinUnfairAdvantage().setName("UNFAIR_ADVANTAGE").setRarity(EnumCoinRarity.COMMON).registerCoin();
		CoinBase DONT_NEED_CAR = new CoinDontNeedCar().setName("DONT_NEED_CAR").setRarity(EnumCoinRarity.COMMON).registerCoin();
		CoinBase ONE_WITH_NATURE = new CoinOneWithNature().setName("ONE_WITH_NATURE").setRarity(EnumCoinRarity.RARE).registerCoin();
		CoinBase DUNGEONS_ARE_NASTY = new CoinDungeonsAreNasty().setName("DUNGEONS_ARE_NASTY").setRarity(EnumCoinRarity.RARE).registerCoin();
		CoinBase SPECTRAL_KNIGHT = new CoinSpectralKnight().setName("SPECTRAL_KNIGHT").setRarity(EnumCoinRarity.EPIC).registerCoin();
		
		CoinBase LEGEND = new CoinLegend().setName("LEGEND").setRarity(EnumCoinRarity.LEGENDARY).registerCoin();
	}
	
	private static class CoinInnerRage extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			player.attackEntityFrom(ModDamageSources.causeDamage(ModDamageSources.damageInnerrage, player), 4);
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
			world.spawnEntityInWorld(new EntityProjectile(world, player).setType("MINDSPIKE"));
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
				i.attackEntityFrom(ModDamageSources.causeDamage(ModDamageSources.damageBlankSlate, player), Float.MAX_VALUE);
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
	
	private static class CoinFencesOnMyWay extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			player.addPotionEffect(new PotionEffect(Potion.jump.id, 15 * 20, 2, true));
			return true;
		}
	}
	
	private static class CoinUnfairAdvantage extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			world.spawnEntityInWorld(new EntityProjectile(world, player).setType("UNFAIR_ADVANTAGE"));
			return true;
		}
	}
	
	private static class CoinDontNeedCar extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			EntityHorse horse = new EntityHorse(world);
			if (mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
			{
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;
				switch (mop.sideHit)
				{
				case 0:
					y--; break;
				case 1:
					y++; break;
				case 2:
					z--; break;
				case 3:
					z++; break;
				case 4:
					x--; break;
				case 5:
					x++; break;
				}
				horse.setPosition(x + 0.5, y + 0.5, z + 0.5);
			}
			else
			{
				horse.setPosition(player.posX, player.posY, player.posZ);
			}
			horse.setHorseTamed(true);
			horse.setTamedBy(player);
			horse.setHorseSaddled(true);
			horse.func_146086_d(new ItemStack(Items.iron_horse_armor));
			world.spawnEntityInWorld(horse);
			return true;
		}
	}
	
	private static class CoinHealingTouch extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			player.setHealth(player.getMaxHealth());
			player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 6 * 20, 3, true));
			return true;
		}
	}
	
	private static class CoinWeNeedToBeQuiet extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			player.addPotionEffect(new PotionEffect(Potion.invisibility.id, 3 * 60 * 20, 3, true));
			return true;
		}
	}
	
	private static class CoinOneWithNature extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			PlayerUtils.print(player, "IT'S NOT WORKING! I'LL GO YELL AT MOD AUTHOR THAT I'M TOTALLY BLIND AND DON'T EVEN KNOW WHAT NYI MEANS");
			PlayerUtils.print(player, "I mean. This is not implemented yet. But you have a nice rare coin that doesn't do anything. Nice?");
			return false;
		}
	}
	
	private static class CoinDungeonsAreNasty extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			if (mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
			{
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;
				switch (mop.sideHit)
				{
				case 0:
					y--; break;
				case 1:
					y++; break;
				case 2:
					z--; break;
				case 3:
					z++; break;
				case 4:
					x--; break;
				case 5:
					x++; break;
				}
				if (world.isAirBlock(x, y, z))
				{
					world.setBlock(x, y, z, Blocks.chest);
					world.setBlockMetadataWithNotify(x, y, z, world.rand.nextInt(4), 3);
					IInventory chestInventory = (IInventory)world.getTileEntity(x, y, z);
					WeightedRandomChestContent[] content = ChestGenHooks.getItems(ChestGenHooks.DUNGEON_CHEST, world.rand);
					WeightedRandomChestContent.generateChestContents(world.rand, content, chestInventory, ChestGenHooks.getCount(ChestGenHooks.DUNGEON_CHEST, world.rand));
					return true;
				}
			}
			return false;
		}
	}
	
	private static class CoinSpectralKnight extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			ItemStack sword = ItemSpectralSword.getSpectralSwordStack();
			EntityItem item = new EntityItem(world, player.posX, player.posY, player.posZ, sword);
			world.spawnEntityInWorld(item);
			item.onCollideWithPlayer(player);
			return true;
		}
	}
	
	private static class CoinLegend extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			LuckyCoinsData data = LuckyCoinsData.get(player);
			if (data.the_legend)
			{
				return false;
			}
			data.the_legend = true;
			try
			{
				Class.forName("net.minecraftforge.cauldron.configuration.CauldronConfig");
				PlayerUtils.print(player, LocalizationHelper.get("message.the_legend.cauldron"));
			}
			catch (Throwable t)
			{
				// not a cauldron
			}
			return true;
		}
	}
	
	private static class CoinOxygenNotThing extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			
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