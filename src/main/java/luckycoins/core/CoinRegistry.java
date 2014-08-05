package luckycoins.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import luckycoins.LuckyCoins;
import luckycoins.core.CoinBase.EnumCoinRarity;
import luckycoins.entity.EntityProjectile;
import luckycoins.items.ItemSpectralSword;
import luckycoins.items.core.ModItems;
import luckycoins.misc.ModDamageSources;
import luckycoins.misc.ModPotions;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.Loader;
import danylibs_luckycoins.LocalizationHelper;
import danylibs_luckycoins.PlayerUtils;

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
		int hit = RNG.nextInt(200);
		if (hit < 169)
		{
			return EnumCoinRarity.COMMON;
		}
		else if (hit >= 169 && hit < 194)
		{
			return EnumCoinRarity.RARE;
		}
		else if (hit >= 194 && hit < 199)
		{
			return EnumCoinRarity.EPIC;
		}
		else if (hit == 199)
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
		CoinBase OXYGEN_NOT_THING = new CoinOxygenNotThing().setName("OXYGEN_NOT_THING").setRarity(EnumCoinRarity.COMMON).registerCoin();
		CoinBase WEB_WRAP = new CoinWebWrap().setName("WEB_WRAP").setRarity(EnumCoinRarity.COMMON).registerCoin();
		CoinBase MYST_BUCKET = new CoinMystBucket().setName("MYST_BUCKET").setRarity(EnumCoinRarity.COMMON).registerCoin();
		//CoinBase RAIN_OR_NOT = new CoinRainOrNot().setName("RAIN_OR_NOT").setRarity(EnumCoinRarity.RARE).registerCoin();
		CoinBase BREAK_IT_DOWN = new CoinBreakItDown().setName("BREAK_IT_DOWN").setRarity(EnumCoinRarity.COMMON).registerCoin();
		CoinBase SHIELDS_UP = new CoinShieldsUp().setName("SHIELDS_UP").setRarity(EnumCoinRarity.COMMON).registerCoin();
	}
	
	private static class CoinInnerRage extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			player.attackEntityFrom(ModDamageSources.causeDamage(ModDamageSources.damageInnerrage, player), 4);
			CoinHelper.applyPotionEffect(player, Potion.damageBoost, 10 * 20, 1, true);
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
				boolean b0 = CoinHelper.applyPotionEffect(player, Potion.moveSpeed, 10 * 60 * 20, 2, true);
				boolean b1 = CoinHelper.applyPotionEffect(player, Potion.jump, 10 * 60 * 20, 1, true);
				boolean b2 = CoinHelper.applyPotionEffect(player, ModPotions.step_assist, 10 * 60 * 20, 0, true);
				return b0 || b1 || b2;
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
			boolean killed = false;
			for (EntityMob i : list)
			{
				i.attackEntityFrom(ModDamageSources.causeDamage(ModDamageSources.damageBlankSlate, player), Float.MAX_VALUE);
				if (i.getHealth() > 0)
				{
					i.setHealth(0);
				}
				killed = true;
			}
			return killed;
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
				witch.setCustomNameTag(CoinHelper.getRandomWitchName(RNG));
				witch.setAggressive(false);
				world.spawnEntityInWorld(witch);
				return true;
			}
		}
	}
	
	private static class CoinFencesOnMyWay extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			CoinHelper.applyPotionEffect(player, Potion.jump, 15 * 20, 2, true);
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
			horse.horseChest.setInventorySlotContents(0, new ItemStack(Items.saddle));
			horse.horseChest.setInventorySlotContents(1, new ItemStack(Items.iron_horse_armor));
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
			CoinHelper.applyPotionEffect(player, Potion.regeneration, 6 * 20, 3, true);
			return true;
		}
	}
	
	private static class CoinWeNeedToBeQuiet extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			CoinHelper.applyPotionEffect(player, Potion.invisibility, 3 * 60 * 20, 3, true);
			return true;
		}
	}
	
	private static class CoinOneWithNature extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{PlayerUtils.print(player, "This is not yet implemented (NYI). But you have a nice rare coin that doesn't do anything. Nice?");
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
				return false;
			}
			catch (Throwable t)
			{
				return true;
			}
		}
	}
	
	private static class CoinOxygenNotThing extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			CoinHelper.applyPotionEffect(player, Potion.fireResistance, 8 * 60 * 20, 0, true);
			CoinHelper.applyPotionEffect(player, Potion.waterBreathing, 8 * 60 * 20, 0, true);
			return true;
		}
	}
	
	private static class CoinWebWrap extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			world.spawnEntityInWorld(new EntityProjectile(world, player).setType("WEB_WRAP"));
			return true;
		}
	}
	
	private static class CoinMystBucket extends CoinBase
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
				if (!world.isAirBlock(x, y, z))
					return false;
				List<Block> fluids = new ArrayList<Block>();
				Iterator<Entry<String, Fluid>> ifluid = FluidRegistry.getRegisteredFluids().entrySet().iterator();
				while (ifluid.hasNext())
				{
					fluids.add(ifluid.next().getValue().getBlock());
				}
				Block fluidRandom = fluids.get(RNG.nextInt(fluids.size()));
				world.setBlock(x, y, z, fluidRandom);
				return true;
			}
			return false;
		}
	}
	
	/*
	@Deprecated
	private static class CoinRainOrNot extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			if (world.isRaining() || world.isThundering())
			{
				world.getWorldInfo().setRaining(false);
				world.getWorldInfo().setThundering(false);
				world.getWorldInfo().setRainTime(60 * 60 * 20);
			}
			else
			{
				world.getWorldInfo().setThundering(true);
			}
			return true;
		}
	}
	*/
	
	private static class CoinBreakItDown extends CoinBase
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
				if (world.getTileEntity(x, y, z) == null)
				{
					world.setBlock(x, y, z, Blocks.air);
					return true;
				}
			}
			return false;
		}
	}
	
	private static class CoinShieldsUp extends CoinBase
	{
		@Override
		public boolean action(World world, EntityPlayer player,
				MovingObjectPosition mop)
		{
			CoinHelper.applyPotionEffect(player, Potion.field_76434_w, 3 * 60 * 20, 0, true);
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