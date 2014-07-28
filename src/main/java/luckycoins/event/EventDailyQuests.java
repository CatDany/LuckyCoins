package luckycoins.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventDailyQuests
{
	@SubscribeEvent
	public void PLAYER_KILL(LivingDeathEvent e)
	{
		if (e.entity instanceof EntityPlayer && e.source.getEntity() != null && e.source.getEntity() instanceof EntityPlayer)
		{
			
		}
	}
}