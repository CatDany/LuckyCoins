package luckycoins.thread;

import java.util.Random;

import luckycoins.Configuration;
import luckycoins.LuckyCoins;
import luckycoins.Refs;
import luckycoins.core.CoinRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class ThreadOneWithNature extends Thread
{
	private long ms_start;
	private EntityPlayer player;
	private World world;
	private Random RNG;
	
	public ThreadOneWithNature(EntityPlayer player, Random random)
	{
		super(Refs.MOD_ID + ":" + "OneWithNatureParser");
		setDaemon(true);
		this.player = player;
		this.world = player.worldObj;
		this.RNG = random;
	}
	
	@Override
	public void run()
	{
		if (Configuration.debug)
		{
			LuckyCoins.logger.info(String.format("ThreadOneWithNature is started for %s.", player.getCommandSenderName()));
		}
		this.ms_start = System.currentTimeMillis();
		
		boolean cycle = true;
		
		while (cycle)
		{
			int hitX = (int)player.posX - 1000 + CoinRegistry.RNG.nextInt(2000);
			int hitZ = (int)player.posZ - 1000 + RNG.nextInt(2000);
			for (int i = MinecraftServer.getServer().getBuildLimit() - 1; i > 0; i--)
			{
				if (world.getChunkFromBlockCoords(hitX, hitZ).isEmpty())
				{
					break;
				}
				if (world.canBlockSeeTheSky(hitX, i, hitZ) && World.doesBlockHaveSolidTopSurface(world, hitX, i, hitZ) && world.getBlock(hitX, i + 1, hitZ) == null && world.getBlock(hitX, i + 2, hitZ) == null)
				{
					player.setPositionAndUpdate(hitX, i + 1, hitZ);
					if (Configuration.debug)
					{
						LuckyCoins.logger.info(String.format("ThreadOneWithNature is ended for %s (took %s ms).", player.getCommandSenderName(), System.currentTimeMillis() - ms_start));
					}
					cycle = false;
				}
			}
		}
	}
}