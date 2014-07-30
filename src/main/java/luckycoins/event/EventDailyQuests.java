package luckycoins.event;

import java.util.List;

import luckycoins.LuckyCoins;
import luckycoins.core.DailyQuest;
import luckycoins.core.LuckyCoinsData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danylibs.PlayerUtils;

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
			LuckyCoins.quests.triggerDailyQuest(source, DailyQuest.KNOCKDOWN, (int)(e.ammount * 2));
		}
	}
}