package luckycoins.items.core;

import luckycoins.Configuration;
import luckycoins.Refs;
import luckycoins.misc.ModCreativeTab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ModItemSword extends ItemSword
{
	private final String unlocName;
	
	public ModItemSword(String unlocName, ToolMaterial mat)
	{
		super(mat);
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
}