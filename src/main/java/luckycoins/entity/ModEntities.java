package luckycoins.entity;

import luckycoins.LuckyCoins;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ModEntities
{
	public static void initEntities()
	{
		EntityRegistry.registerModEntity(EntityProjectile.class, "EntityProjectile", EntityRegistry.findGlobalUniqueEntityId(), LuckyCoins.instance, 80, 3, true);
	}
}