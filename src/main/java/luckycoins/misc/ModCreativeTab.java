package luckycoins.misc;

import luckycoins.Refs;
import luckycoins.items.core.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ModCreativeTab extends CreativeTabs
{
	private static ModCreativeTab tab;
	
	public ModCreativeTab()
	{
		super(Refs.MOD_NAME);
	}
	
	public static ModCreativeTab tab()
	{
		if (tab == null)
		{
			tab = new ModCreativeTab();
		}
		return tab;
	}
	
	@Override
	public String getTranslatedTabLabel()
	{
		return Refs.MOD_NAME;
	}
	
	@Override
	public Item getTabIconItem()
	{
		return Items.gold_nugget;
	}
}