package luckycoins.items.core;

import luckycoins.items.ItemMasterCoin;
import net.minecraft.item.Item;

public class ModItems
{
	public static Item master_coin;
	
	public static void initItems()
	{
		master_coin = new ItemMasterCoin("master_coin");
	}
}