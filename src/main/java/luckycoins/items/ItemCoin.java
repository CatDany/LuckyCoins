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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import danylibs.IconRegHelper;

public class ItemCoin extends ModItemBase
{
	private IIcon icon_common;
	private IIcon icon_rare;
	private IIcon icon_epic;
	private IIcon icon_legendary;
	
	public ItemCoin(String unlocName)
	{
		super(unlocName);
		if (Configuration.debug)
		{
			setCreativeTab(ModCreativeTab.tab());
		}
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
	public IIcon getIcon(ItemStack stack, int pass)
	{
		String r = stack.getTagCompound().getString("CoinRarity");
		if ("COMMON".equals(r))
			return icon_common;
		else if ("RARE".equals(r))
			return icon_rare;
		else if ("EPIC".equals(r))
			return icon_epic;
		else if ("LEGENDARY".equals(r))
			return icon_legendary;
		else
			return icon_common;
	}
	
	@Override
	public void addInformation(ItemStack stack,
			EntityPlayer player, List list, boolean par4)
	{
		list.add(CoinRegistry.getCoin(stack.getTagCompound().getString("CoinType")).getTranslatedName());
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
		if (coin.action(world, player, getMovingObjectPositionFromPlayer(world, player, false)))
		{
			stack.stackSize--;
			world.playSoundEffect(player.posX, player.posY + 0.5, player.posZ, "random.orb", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
		}
		return stack;
	}
}