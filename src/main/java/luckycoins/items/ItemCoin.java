package luckycoins.items;

import java.util.Iterator;
import java.util.List;

import luckycoins.Configuration;
import luckycoins.core.CoinBase;
import luckycoins.core.CoinRegistry;
import luckycoins.items.core.ModItemBase;
import luckycoins.misc.ModCreativeTab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import danylibs.IconRegHelper;
import danylibs.KeyBoardHelper;
import danylibs.Paragraph;

public class ItemCoin extends ModItemBase
{
	private IIcon icon_common;
	private IIcon icon_rare;
	private IIcon icon_epic;
	private IIcon icon_legendary;
	
	public ItemCoin(String unlocName)
	{
		super(unlocName);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		this.icon_common = IconRegHelper.regItem(this, reg, "_common");
		this.icon_rare = IconRegHelper.regItem(this, reg, "_rare");
		this.icon_epic = IconRegHelper.regItem(this, reg, "_epic");
		this.icon_legendary = IconRegHelper.regItem(this, reg, "_legendary");
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return StatCollector.translateToLocal("item.action_coin_" + stack.getTagCompound().getString("CoinRarity").toLowerCase() + ".name");
	}
	
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		switch (meta)
		{
		case 0:
			return icon_common;
		case 1:
			return icon_rare;
		case 2:
			return icon_epic;
		case 3:
			return icon_legendary;
		}
		return Items.potato.getIconFromDamage(0);
	}
	
	@Override
	public void addInformation(ItemStack stack,
			EntityPlayer player, List list, boolean par4)
	{
		list.add(CoinRegistry.getCoin(stack.getTagCompound().getString("CoinType")).getTranslatedName());
		if (KeyBoardHelper.isShiftDown())
		{
			String desc = CoinRegistry.getCoin(stack.getTagCompound().getString("CoinType")).getTranslatedDescription();
			while (desc.length() > 0)
			{
				if (desc.length() > 12)
				{
					int spaceIndex = desc.indexOf(" ", 12);
					if (spaceIndex == -1)
					{
						list.add(Paragraph.light_green + Paragraph.italic + desc);
						break;
					}
					else
					{
						list.add(Paragraph.light_green + Paragraph.italic + desc.substring(0, spaceIndex));
						desc = desc.substring(spaceIndex + 1);
					}
				}
				else
				{
					list.add(Paragraph.light_green + Paragraph.italic + desc);
					break;
				}
			}
		}
		else
		{
			list.add(Paragraph.light_green + Paragraph.italic + "Hold SHIFT for details");
		}
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		if (Configuration.debug)
		{
			Iterator<String> icoin = CoinRegistry.map.keySet().iterator();
			while (icoin.hasNext())
			{
				String coin = icoin.next();
				list.add(CoinRegistry.getCoinStack(coin));
			}
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if (stack.getTagCompound() == null)
		{
			stack = new ItemStack(Blocks.dirt);
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		String name = stack.getTagCompound().getString("CoinType");
		CoinBase coin = CoinRegistry.getCoin(name);
		if (!world.isRemote && coin.action(world, player, getMovingObjectPositionFromPlayer(world, player, false)))
		{
			stack.stackSize--;
			world.playSoundEffect(player.posX, player.posY + 0.5, player.posZ, "random.orb", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
		}
		return stack;
	}
}