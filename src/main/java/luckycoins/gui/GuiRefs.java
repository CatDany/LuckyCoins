package luckycoins.gui;

import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danylibs.LocalizationHelper;

@SideOnly(Side.CLIENT)
public class GuiRefs
{
	public static final String OPEN_LOOT_BOXES = LocalizationHelper.get("gui.main.open_loot_boxes");
	public static final String SHOP = LocalizationHelper.get("gui.main.shop");
	public static final String REDEEM_CODE = LocalizationHelper.get("gui.main.redeem_code");
	public static final String DAILY_QUESTS = LocalizationHelper.get("gui.main.daily_quests");
	public static final String HELP = LocalizationHelper.get("gui.main.help");
	public static final String BUY = LocalizationHelper.get("gui.shop.buy");
	public static final String BUY_ONE = LocalizationHelper.get("gui.shop.buy_one");
	public static final String GET_FREE_LOOT_BOXES = LocalizationHelper.get("gui.shop.free");
	public static final String NOT_ENOUGH_GOLD = LocalizationHelper.get("gui.shop.not_enough_gold");
	public static final String FREE_LOOT_BOXES = LocalizationHelper.get("gui.shop.your_stats");
	public static final String ENTER_CODE = LocalizationHelper.get("gui.redeem.enter_code");
	public static final String REDEEM = LocalizationHelper.get("gui.redeem.redeem");
	public static final String YOUR_STATS = LocalizationHelper.get("gui.your_stats");
	public static final String WAIT = LocalizationHelper.get("gui.redeem.wait");
	public static final String BACK = LocalizationHelper.get("gui.back");
}