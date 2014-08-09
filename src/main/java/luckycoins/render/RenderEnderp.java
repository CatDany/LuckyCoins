package luckycoins.render;

import luckycoins.Refs;
import luckycoins.entity.EntityEnderp;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.ResourceLocation;

public class RenderEnderp extends RenderLiving
{
	public static final ResourceLocation texture = new ResourceLocation(Refs.MOD_ID, "textures/models/Enderp.png");
	private ModelEnderman model;
	
	public RenderEnderp()
	{
		super(new ModelEnderman(), 0.5F);
		this.model = (ModelEnderman)mainModel;
		setRenderPassModel(model);
	}
	
	@Override
	public void doRender(Entity enderp, double a, double b, double c,
			float d, float e)
	{
		model.isAttacking = ((EntityEnderp)enderp).isScreaming();
		
		if (((EntityEnderp)enderp).isScreaming())
		{
			double d3 = 0.02D;
			a += enderp.worldObj.rand.nextGaussian() * d3;
			e += enderp.worldObj.rand.nextGaussian() * d3;
		}
		
		super.doRender((EntityLiving)enderp, a, b, c, d, e);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity var1)
	{
		return texture;
	}
}