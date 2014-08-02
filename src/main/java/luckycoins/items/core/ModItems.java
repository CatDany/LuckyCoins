package luckycoins.items.core;

import java.util.HashMap;
import java.util.Iterator;

import luckycoins.items.ItemCoin;
import luckycoins.items.ItemSpectralSword;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems
{
	public static Item action_coin;
	public static Item spectral_sword;
	
	public static void initItems()
	{
		action_coin = new ItemCoin("action_coin");
		spectral_sword = new ItemSpectralSword("spectral_sword");
		registerItems();
	}
	
	private static void registerItems()
	{
		Iterator<String> iitem = items.keySet().iterator();
		while (iitem.hasNext())
		{
			String name = iitem.next();
			Item item = items.get(name);
			GameRegistry.registerItem(item, name);
		}
	}
	
	protected static final HashMap<String, Item> items = new HashMap<String, Item>();
}