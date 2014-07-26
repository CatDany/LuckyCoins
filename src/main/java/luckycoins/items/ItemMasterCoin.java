package luckycoins.items;

import danylibs.IconRegHelper;
import luckycoins.LuckyCoins;
import luckycoins.Refs;
import luckycoins.core.LuckyCoinsData;
import luckycoins.gui.GuiMain;
import luckycoins.items.core.ModItemBase;
import luckycoins.network.PacketHandler;
import luckycoins.network.packet.PacketDataSync;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMasterCoin extends ModItemBase
{
	public ItemMasterCoin(String unlocName)
	{
		super(unlocName);
		setMaxStackSize(1);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		icons[0] = IconRegHelper.regItem(this, reg);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player)
	{
		if (world.isRemote)
		{
			PacketHandler.instance().net.sendToServer(new PacketDataSync.MessageDataSync());
			Minecraft.getMinecraft().displayGuiScreen(new GuiMain());
		}
		return stack;
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z, int side,
			float hitX, float hitY, float hitZ)
	{
		onItemRightClick(stack, world, player);
		return true;
	}
}