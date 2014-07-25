package luckycoins.misc;

import luckycoins.Refs;
import luckycoins.items.core.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModCreativeTab extends CreativeTabs
{
	public static final ModCreativeTab tabMod = new ModCreativeTab();
	
	public ModCreativeTab()
	{
		super(Refs.MOD_NAME);
	}
	
	@Override
	public String getTranslatedTabLabel()
	{
		return Refs.MOD_NAME;
	}
	
	@Override
	public Item getTabIconItem()
	{
		return ModItems.master_coin;
	}
}