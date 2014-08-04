package luckycoins.entity;

import java.util.List;

import luckycoins.core.CoinRegistry;
import luckycoins.misc.ModDamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityProjectile extends EntityThrowable
{
	private String coinType;
	
	public EntityProjectile(World world)
	{
		super(world);
		setSize(0, 0);
	}
	
	public EntityProjectile(World world, EntityPlayer player)
	{
		super(world, player);
		setSize(0, 0);
	}
	
	public EntityProjectile(World world, double x, double y, double z)
	{
		super(world, x, y, z);
		setSize(0, 0);
	}
	
	public EntityProjectile setType(String coinType)
	{
		this.coinType = coinType;
		return this;
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);
		this.coinType = tag.getString("CoinType");
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);
		tag.setString("CoinType", coinType);
	}
	
	@Override
	protected void onImpact(MovingObjectPosition mop)
	{
		if (!worldObj.isRemote)
		{
			setDead();
		}
		if ("MINDSPIKE".equals(coinType))
		{
			if (mop.typeOfHit == MovingObjectType.ENTITY && mop.entityHit instanceof EntityLivingBase)
			{
				mop.entityHit.attackEntityFrom(ModDamageSources.causeDamage(ModDamageSources.damageMindspike, getThrower()), 50);
				return;
			}
		}
		else if ("UNFAIR_ADVANTAGE".equals(coinType))
		{
			if (mop.typeOfHit == MovingObjectType.ENTITY && mop.entityHit instanceof EntityLivingBase)
			{
				EntityLivingBase entity = (EntityLivingBase)mop.entityHit;
				entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 15 * 20, 1, true));
				entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 15 * 20, 0, true));
				entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 15 * 20, 3, true));
				entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 15 * 20, 2, true));
				return;
			}
		}
		else if ("WEB_WRAP".equals(coinType))
		{
			if (mop.typeOfHit == MovingObjectType.BLOCK)
			{
				int maxX = mop.blockX + 2;
				int maxY = mop.blockY + 2;
				int maxZ = mop.blockZ + 2;
				for (int x = mop.blockX - 2; x <= maxX; x++)
				{
					for (int y = mop.blockY - 2; y <= maxY; y++)
					{
						for (int z = mop.blockZ - 2; z <= maxZ; z++)
						{
							if (worldObj.isAirBlock(x, y, z))
							{
								worldObj.setBlock(x, y, z, Blocks.web);
							}
						}
					}
				}
				AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(mop.blockX - 2, mop.blockY - 2, mop.blockZ - 2, mop.blockX + 3, mop.blockY + 3, mop.blockZ + 3);
				List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(getThrower(), aabb);
				for (Entity i : list)
				{
					if (i instanceof EntityLivingBase)
					{
						((EntityLivingBase)i).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5 * 20, 2, false));
					}
				}
				return;
			}
		}
		
		if (!worldObj.isRemote)
		{
			if (getThrower() != null && getThrower() instanceof EntityPlayer)
			{
				EntityPlayer thrower = (EntityPlayer)getThrower();
				ItemStack coin = CoinRegistry.getCoinStack(coinType);
				thrower.inventory.addItemStackToInventory(coin);
			}
		}
	}
}