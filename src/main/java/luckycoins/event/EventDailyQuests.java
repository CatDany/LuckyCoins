package luckycoins.event;

import java.util.List;

import luckycoins.LuckyCoins;
import luckycoins.core.DailyQuest;
import net.minecraft.block.BlockCake;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import danylibs.ItemUtils;

public class EventDailyQuests
{
	@SubscribeEvent
	public void PLAYER_KILL(LivingDeathEvent e)
	{
		if (e.entity.worldObj.isRemote)
			return;
		
		if (e.entity instanceof EntityPlayer && e.source.getEntity() != null && e.source.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer source = (EntityPlayer)e.source.getEntity();
			LuckyCoins.quests.triggerDailyQuest(source, DailyQuest.PLAYER_KILL, 1);
		}
	}
	
	@SubscribeEvent
	public void ZOMBIE_HEAL(LivingEvent.LivingUpdateEvent e)
	{
		if (e.entity.worldObj.isRemote)
			return;
		
		if (e.entity instanceof EntityZombie)
		{
			EntityZombie zombie = (EntityZombie)e.entity;
			if (zombie.isVillager() && zombie.isConverting() && zombie.conversionTime < 5 && zombie.conversionTime > 0)
			{
				AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(zombie.posX - 7, zombie.posY - 7, zombie.posZ - 7, zombie.posX + 7, zombie.posY + 7, zombie.posZ + 7);
				List<EntityPlayer> list = zombie.worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb);
				for (EntityPlayer i : list)
				{
					LuckyCoins.quests.triggerDailyQuest(i, DailyQuest.ZOMBIE_HEAL, 1);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void HARVESTER(BlockEvent.HarvestDropsEvent e)
	{
		if (e.world.isRemote)
			return;
		
		if ((e.block == Blocks.wheat || e.block == Blocks.carrots || e.block == Blocks.potatoes) && e.blockMetadata == 7)
		{
			LuckyCoins.quests.triggerDailyQuest(e.harvester, DailyQuest.HARVESTER, 1);
		}
	}
	
	@SubscribeEvent
	public void KNOCKDOWN(LivingAttackEvent e)
	{
		if (e.entity.worldObj.isRemote)
			return;
		
		if (e.entity instanceof EntityMob && e.source.getEntity() != null && e.source.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer source = (EntityPlayer)e.source.getEntity();
			LuckyCoins.quests.triggerDailyQuest(source, DailyQuest.KNOCKDOWN, (int)e.ammount);
		}
	}
	
	@SubscribeEvent
	public void FEED(EntityInteractEvent e)
	{
		if (e.entity.worldObj.isRemote)
			return;
		
		ItemStack stack = e.entityPlayer.inventory.getCurrentItem();
		if (e.target instanceof EntityWolf)
		{
			EntityWolf wolf = (EntityWolf)e.target;
			if (wolf.isTamed())
			{
				if (stack != null && stack.getItem() instanceof ItemFood && ((ItemFood)stack.getItem()).isWolfsFavoriteMeat())
				{
					LuckyCoins.quests.triggerDailyQuest(e.entityPlayer, DailyQuest.FEED, 1);
				}
			}
		}
		else if (e.target instanceof EntityOcelot)
		{
			EntityOcelot ocelot = (EntityOcelot)e.target;
			if (ocelot.isTamed())
			{
				if (stack != null && ItemUtils.compare(stack.getItem(), Items.fish))
				{
					LuckyCoins.quests.triggerDailyQuest(e.entityPlayer, DailyQuest.FEED, 1);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void EAT_CAKE(PlayerInteractEvent e)
	{
		if (e.entity.worldObj.isRemote)
			return;
		
		if (e.action == Action.RIGHT_CLICK_BLOCK)
		{
			if (e.world.getBlock(e.x, e.y, e.z) instanceof BlockCake)
			{
				LuckyCoins.quests.triggerDailyQuest(e.entityPlayer, DailyQuest.EAT_CAKE, 1);
			}
		}
	}
	
	@SubscribeEvent
	public void ENDERMEN_KILL(LivingDeathEvent e)
	{
		if (!e.entity.worldObj.isRemote && e.entity.worldObj.provider.dimensionId == 0 && e.entityLiving instanceof EntityEnderman && e.source.getEntity() != null && e.source.getEntity() instanceof EntityPlayer)
		{
			LuckyCoins.quests.triggerDailyQuest((EntityPlayer)e.source.getEntity(), DailyQuest.ENDERMEN_KILL, 1);
		}
	}
}