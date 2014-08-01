package luckycoins.misc;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import luckycoins.Refs;
import net.minecraft.potion.Potion;
import cpw.mods.fml.common.FMLLog;

public class ModPotions
{
	public static Potion step_assist;
	
	public static void initPotions()
	{
		step_assist = new ModPotionBase("step_assist", false, 0xffffff, 0, 0);
	}
	
	public static class ModPotionBase extends Potion
	{
		private static int nextPotionID = 40;
		
		public ModPotionBase(String name, boolean bad, int particleColor, int iconX, int iconY)
		{
			super(nextPotionID, bad, particleColor);
			setPotionName("potion." + name + ".name");
			setIconIndex(iconX, iconY);
			nextPotionID++;
		}
	}
	
	public static void patchPotionSystem()
	{
		Potion[] potionTypes = null;

		for (Field f : Potion.class.getDeclaredFields())
		{
			f.setAccessible(true);
			try
			{
				if ("potionTypes".equals(f.getName())
				 || "field_76425_a".equals(f.getName()))
				{
					Field modfield = Field.class.getDeclaredField("modifiers");
					modfield.setAccessible(true);
					modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
					potionTypes = (Potion[])f.get(null);
					final Potion[] newPotionTypes = new Potion[256];
					System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
					f.set(null, newPotionTypes);
				}
			}
			catch (Throwable t)
			{
				FMLLog.severe("Failed to expand potion effects array by %s. Report to the mod author!", Refs.MOD_ID);
				t.printStackTrace();
			}
		}
	}
}