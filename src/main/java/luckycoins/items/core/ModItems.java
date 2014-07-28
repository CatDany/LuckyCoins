package luckycoins.items.core;

import java.util.HashMap;
import java.util.Iterator;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems
{
	public static void initItems()
	{
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