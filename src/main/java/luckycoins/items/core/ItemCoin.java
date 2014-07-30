package luckycoins.items.core;

import java.util.List;

import danylibs.IconRegHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemCoin extends ModItemBase
{
	private IIcon icon_common;
	private IIcon icon_rare;
	private IIcon icon_epic;
	private IIcon icon_legendary;
	
	public ItemCoin(String unlocName)
	{
		super(unlocName);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		this.icon_common = IconRegHelper.regItem(this, reg, "_common");
		this.icon_rare = IconRegHelper.regItem(this, reg, "_rare");
		this.icon_epic = IconRegHelper.regItem(this, reg, "_epic");
		this.icon_legendary = IconRegHelper.regItem(this, reg, "_legendary");
	}
	
	@Override
	public void addInformation(ItemStack stack,
			EntityPlayer player, List list, boolean par4)
	{
		//list.add();
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if (stack.getTagCompound() == null)
		{
			stack.stackSize = 0;
		}
	}
}