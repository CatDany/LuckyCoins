package luckycoins.items.core;

import java.util.HashMap;
import java.util.Iterator;

import cpw.mods.fml.common.registry.GameRegistry;
import luckycoins.items.ItemMasterCoin;
import net.minecraft.item.Item;

public class ModItems
{
	public static Item master_coin;
	
	public static void initItems()
	{
		master_coin = new ItemMasterCoin("master_coin");
		
		registerItems();
	}
	
	private static void registerItems()
	{
		Iterator<String> iitem = items.keySet().iterator();
		while (iitem.hasNext())
		{
			String name = iitem.next();
			ModItemBase item = items.get(name);
			GameRegistry.registerItem(item, name);
		}
	}
	
	protected static final HashMap<String, ModItemBase> items = new HashMap<String, ModItemBase>();
}