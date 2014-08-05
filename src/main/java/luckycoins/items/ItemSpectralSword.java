package luckycoins.items;

import java.util.List;

import danylibs_luckycoins.LocalizationHelper;
import luckycoins.items.core.ModItemSword;
import luckycoins.items.core.ModItems;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ItemSpectralSword extends ModItemSword
{
	public static final ToolMaterial mat = EnumHelper.addToolMaterial("SpectalSword", 0, 1, 0, 3.5F, 0);
	
	public ItemSpectralSword(String unlocName)
	{
		super(unlocName, mat);
		setCreativeTab(null);
	}
	
	@Override
	public void addInformation(ItemStack stack,
			EntityPlayer player, List list, boolean par4)
	{
		int ticks = stack.getTagCompound().getInteger("TicksLeft");
		int minutes = ticks / 20 / 60;
		int seconds = ticks / 20;
		list.add(LocalizationHelper.get("tooltip.spectral_sword.tooltip", minutes, seconds - minutes * 60));
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		int ticks = stack.getTagCompound().getInteger("TicksLeft");
		if (ticks > 0)
		{
			ticks--;
			stack.getTagCompound().setInteger("TicksLeft", ticks);
		}
		else
		{
			stack.func_150996_a(Items.stick);
		}
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase living, EntityLivingBase source)
	{
		return true;
	}
	
	public static ItemStack getSpectralSwordStack()
	{
		ItemStack stack = new ItemStack(ModItems.spectral_sword);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("TicksLeft", 2 * 60 * 20);
		stack.setTagCompound(tag);
		stack.addEnchantment(Enchantment.looting, 10);
		return stack;
	}
}