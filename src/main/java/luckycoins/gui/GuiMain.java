package luckycoins.gui;

import luckycoins.Refs;
import luckycoins.core.LuckyCoinsData;
import luckycoins.network.PacketHandler;
import luckycoins.network.packet.PacketDataSync;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiMain extends GuiScreen
{
	private static final ResourceLocation texture = new ResourceLocation(Refs.RESLOC + ":" + "textures/gui/sheet1.png");
	
	@Override
	public void initGui()
	{
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 70, 80, 140, 20, GuiRefs.OPEN_LOOT_BOXES));
		buttonList.add(new GuiButton(1, width / 2 - 70, 110, 140, 20, GuiRefs.SHOP));
		buttonList.add(new GuiButton(2, width / 2 - 70, 140, 140, 20, GuiRefs.REDEEM_CODE));
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		super.drawScreen(par1, par2, par3);
		
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(width / 2 - 70, 40, 0, 0, 32, 32);
		drawString(fontRendererObj, "" + LuckyCoinsData.CLIENT_COINS, width / 2 - 40, 52, 0xffffff);
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == 0)
			/** OPEN_LOOT_BOXES **/
		{
			//Minecraft.getMinecraft().displayGuiScreen(new GuiOpenLoot());
		}
		else if (button.id == 1)
			/** SHOP **/
		{
			//Minecraft.getMinecraft().displayGuiScreen(new GuiShop());
		}
		else if (button.id == 2)
			/** REDEEM_CODE **/
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiRedeem());
		}
	}
	
	@Override
	protected void mouseClicked(int x, int y, int wtf)
	{
		int minX = width / 2 - 70 + 4;
		int minY = 40 + 4;
		int maxX = minX + 32 - 8;
		int maxY = minY + 32 - 8;
		if (x > minX && y > minY && x < maxX && y < maxY)
		{
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("random.orb"), 1.0F));
		}
	}
}