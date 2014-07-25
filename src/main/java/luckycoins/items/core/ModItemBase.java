package luckycoins.items.core;

import java.util.List;

import luckycoins.misc.ModCreativeTab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.common.registry.GameRegistry;
import danylibs.IconRegHelper;

public class ModItemBase extends Item
{
	protected IIcon[] icons = new IIcon[16];
	protected int subItems = 1;
	private final String unlocName; 
	
	public ModItemBase(String unlocName)
	{
		super();
		setUnlocalizedName(unlocName);
		setCreativeTab(ModCreativeTab.tabMod);
		
		this.unlocName = unlocName;
		GameRegistry.registerItem(this, unlocName);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		icons[0] = IconRegHelper.regItem(this, reg);
		if (getHasSubtypes())
		{
			for (int i = 1; i < subItems; i++)
			{
				icons[i] = IconRegHelper.regItem(this, reg, "" + i);
			}
		}
	}
	
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return getHasSubtypes() ? icons[meta] : icons[0];
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		if (getHasSubtypes())
		{
			for (int i = 0; i < subItems; i++)
			{
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return getHasSubtypes() ? getUnlocalizedName() + "|" + stack.getItemDamage() : getUnlocalizedName();
	}
}