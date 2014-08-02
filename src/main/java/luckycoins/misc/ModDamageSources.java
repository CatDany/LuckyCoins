package luckycoins.misc;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class ModDamageSources
{
	public static DamageSource damageInnerrage;
	public static DamageSource damageMindspike;
	public static DamageSource damageBlankSlate;
	
	public static void initDamageSources()
	{
		damageInnerrage = new DamageSource("INNER_RAGE").setDamageIsAbsolute();
		damageMindspike = new DamageSource("MINDSPIKE").setProjectile();
		damageBlankSlate = new DamageSource("FROM_BLANK_SLATE").setDamageIsAbsolute();
	}
	
	public static DamageSource causeDamage(DamageSource source, EntityLivingBase living)
	{
		return new EntityDamageSource(source.getDamageType(), living);
	}
}