package luckycoins.gui;

import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiRefs
{
	public static final String OPEN_LOOT_BOXES = StatCollector.translateToLocal("gui.main.open_loot_boxes");
	public static final String SHOP = StatCollector.translateToLocal("gui.main.shop");
	public static final String REDEEM_CODE = StatCollector.translateToLocal("gui.main.redeem_code");
	public static final String BUY = StatCollector.translateToLocal("gui.shop.buy");
	public static final String ENTER_CODE = StatCollector.translateToLocal("gui.redeem.enter_code");
	public static final String REDEEM = StatCollector.translateToLocal("gui.redeem.redeem");
	public static final String WAIT = StatCollector.translateToLocal("gui.redeem.wait");
	public static final String BACK = StatCollector.translateToLocal("gui.back");
}