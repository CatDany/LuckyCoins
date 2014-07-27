package luckycoins.recipes;

import luckycoins.items.core.ModItems;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes
{
	private static void initOreDictNames()
	{
		//
	}
	
	public static void postInitOreDictNames()
	{
		Item tconMaterial;
		if (Loader.isModLoaded("TConstruct") && (tconMaterial = GameRegistry.findItem("TConstruct", "materials")) != null)
		{
			ItemStack stackAlubrass = new ItemStack(tconMaterial, 1, 15);
			OreDictionary.registerOre("nuggetGold", stackAlubrass);
		}
	}
	
	public static void initRecipes()
	{
		addOreRecipe(ModItems.master_coin, 1, 0,
				"nnn", "nen", "nnn",
				'n', "nuggetGold",
				'e', Items.emerald);
	}
	
	private static void addOreRecipe(ItemStack output, Object... recipe)
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(output, recipe));
	}

	private static void addOreRecipe(Item output, int amount, int data, Object... recipe)
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(output, amount, data), recipe));
	}

	private static void addOreRecipe(Block output, int amount, int data, Object... recipe)
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(output, amount, data), recipe));
	}

	private static void addShapelessOreRecipe(ItemStack output, Object... recipe)
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(output, recipe));
	}

	private static void addShapelessOreRecipe(Item output, int amount, int data, Object... recipe)
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(output, amount, data), recipe));
	}

	private static void addShapelessOreRecipe(Block output, int amount, int data, Object... recipe)
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(output, amount, data), recipe));
	}
}