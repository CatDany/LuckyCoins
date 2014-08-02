package luckycoins.items.core;

import java.util.List;

import luckycoins.Refs;
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
	private final String unlocName; 
	
	public ModItemBase(String unlocName)
	{
		super();
		setUnlocalizedName(unlocName);
		setCreativeTab(ModCreativeTab.tab());
		
		this.unlocName = unlocName;
		ModItems.items.put(unlocName, this);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		itemIcon = reg.registerIcon(Refs.RESLOC + ":" + unlocName);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return getHasSubtypes() ? getUnlocalizedName() + "|" + stack.getItemDamage() : getUnlocalizedName();
	}
}