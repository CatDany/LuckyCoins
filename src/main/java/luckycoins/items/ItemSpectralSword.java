package luckycoins.items;

import java.util.List;

import danylibs.LocalizationHelper;
import luckycoins.items.core.ModItemSword;
import luckycoins.items.core.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ItemSpectralSword extends ModItemSword
{
	public static final ToolMaterial mat = EnumHelper.addToolMaterial("SpectalSword", 0, 1000, 0, 3.5F, 0);
	
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
		}
		else
		{
			stack.stackSize = 0;
		}
		stack.getTagCompound().setInteger("TicksLeft", ticks);
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