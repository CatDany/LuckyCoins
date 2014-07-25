package luckycoins.items;

import danylibs.IconRegHelper;
import luckycoins.Refs;
import luckycoins.items.core.ModItemBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class ItemMasterCoin extends ModItemBase
{
	public static IIcon icon_coin;
	
	public ItemMasterCoin(String unlocName)
	{
		super(unlocName);
		setMaxStackSize(1);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		icons[0] = IconRegHelper.regItem(this, reg);
		icon_coin = reg.registerIcon(Refs.RESLOC + ":" + "coin");
	}
}