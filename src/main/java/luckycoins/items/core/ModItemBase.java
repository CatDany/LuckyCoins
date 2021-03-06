package luckycoins.items.core;

import luckycoins.Configuration;
import luckycoins.Refs;
import luckycoins.misc.ModCreativeTab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModItemBase extends Item
{
	private final String unlocName; 
	
	public ModItemBase(String unlocName)
	{
		super();
		setUnlocalizedName(unlocName);
		if (Configuration.debug)
		{
			setCreativeTab(ModCreativeTab.tab());
		}
		
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