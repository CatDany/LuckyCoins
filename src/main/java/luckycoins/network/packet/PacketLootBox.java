package luckycoins.network.packet;

import io.netty.buffer.ByteBuf;
import luckycoins.core.CoinRegistry;
import luckycoins.core.LuckyCoinsData;
import luckycoins.network.packet.PacketLootBox.MessageLootBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketLootBox implements IMessageHandler<MessageLootBox, IMessage>
{
	@Override
	public IMessage onMessage(MessageLootBox message, MessageContext ctx)
	{
		if (ctx.side.isServer())
		{
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			LuckyCoinsData data = LuckyCoinsData.get(player);
			if (data.loot_boxes > 0)
			{
				spawnDroppedCoin(player, 0.9, 0.9);
				spawnDroppedCoin(player, -0.9, 0.9);
				spawnDroppedCoin(player, 0.9, -0.5);
				spawnDroppedCoin(player, -0.9, -0.5);
				spawnDroppedCoin(player, 0, -0.9);
				data.loot_boxes--;
			}
		}
		return null;
	}
	
	private void spawnDroppedCoin(Entity entity, double motionX, double motionZ)
	{
		double d = 1 / 20;
		double motionY = 0.8 * d;
		motionX = motionX * d;
		motionZ = motionZ * d;
		ItemStack coin = CoinRegistry.getCoinStack(CoinRegistry.getRandomCoin().getUnlocalizedName());
		EntityItem item = new EntityItem(entity.worldObj, entity.posX, entity.posY + (entity.height / 2), entity.posZ, coin);
		item.addVelocity(motionX, motionY, motionZ);
		item.delayBeforeCanPickup = 20;
		entity.worldObj.spawnEntityInWorld(item);
	}
	
	public static class MessageLootBox implements IMessage
	{
		@Override
		public void fromBytes(ByteBuf buf) {}
		
		@Override
		public void toBytes(ByteBuf buf) {}
	}
}