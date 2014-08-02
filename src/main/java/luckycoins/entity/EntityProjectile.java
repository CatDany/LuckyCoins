package luckycoins.entity;

import luckycoins.core.CoinRegistry;
import luckycoins.misc.ModDamageSources;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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