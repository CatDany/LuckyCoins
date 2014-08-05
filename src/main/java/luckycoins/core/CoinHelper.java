package luckycoins.core;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import danylibs_luckycoins.LocalizationHelper;

public class CoinHelper
{
	public static boolean applyPotionEffect(EntityLivingBase living, Potion potion, int duration, int amplifier, boolean hideParticles)
	{
		if (living.isPotionActive(potion))
		{
			if (living.getActivePotionEffect(potion).getAmplifier() == amplifier)
			{
				int durationStacked = living.getActivePotionEffect(potion).getDuration() + duration;
				living.addPotionEffect(new PotionEffect(potion.id, durationStacked, amplifier, hideParticles));
				return true;
			}
			else if (living.getActivePotionEffect(potion).getAmplifier() < amplifier)
			{
				living.addPotionEffect(new PotionEffect(potion.id, duration, amplifier, hideParticles));
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			living.addPotionEffect(new PotionEffect(potion.id, duration, amplifier, hideParticles));
			return true;
		}
	}
	
	public static String getRandomWitchName(Random random)
	{
		int hit = random.nextInt(8) + 1;
		return LocalizationHelper.get("coin.COMMON.REINFORCE.data.witchName." + hit);
	}
}