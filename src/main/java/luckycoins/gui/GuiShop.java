package luckycoins.gui;

import scala.xml.PrettyPrinter.Para;
import danylibs.InternetHelper;
import danylibs.Paragraph;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import luckycoins.core.LuckyCoinsData;
import luckycoins.network.PacketHandler;
import luckycoins.network.packet.PacketBuyBox;
import luckycoins.network.packet.PacketDataSync;

public class GuiShop extends ModGui
{
	@Override
	public void initGui()
	{
		super.initGui();
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 83, 80, 120, 20, String.format(GuiRefs.BUY_ONE, 1)));
		buttonList.add(new GuiButton(1, width / 2 - 83, 102, 120, 20, String.format(GuiRefs.BUY, 2)));
		buttonList.add(new GuiButton(2, width / 2 - 83, 124, 120, 20, String.format(GuiRefs.BUY, 10)));
		buttonList.add(new GuiButton(3, width / 2 - 83, 146, 120, 20, String.format(GuiRefs.BUY, 25)));
		buttonList.add(new GuiButton(4, width / 2 - 83, 168, 120, 20, String.format(GuiRefs.GET_FREE_LOOT_BOXES, 25)));
		buttonList.add(new GuiButton(5, width / 2 + 40, 168, 54, 20, GuiRefs.BACK));
		((GuiButton)buttonList.get(1)).enabled = false;
		((GuiButton)buttonList.get(2)).enabled = false;
		((GuiButton)buttonList.get(3)).enabled = false;
		
		// Fix of flashing rectangles
		buttonList.add(new GuiButton(99, 0, 0, 0, 0, ""));
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		super.drawScreen(par1, par2, par3);
		
		mc.renderEngine.bindTexture(GuiMain.texture);
		drawTexturedModalRect(width / 2 - 70, 40, 0, 0, 32, 32);
		drawTexturedModalRect(width / 2 + 38, 41, 32, 0, 32, 32);
		drawCenteredString(fontRendererObj, GuiRefs.YOUR_STATS, width / 2, 35, 0xffffff);
		drawString(fontRendererObj, GuiMain.getNumberString(LuckyCoinsData.CLIENT_COINS), width / 2 - 40, 52, 0xffffff);
		drawString(fontRendererObj, GuiMain.getNumberString(LuckyCoinsData.CLIENT_BOXES), width / 2 + 40 - fontRendererObj.getStringWidth(GuiMain.getNumberString(LuckyCoinsData.CLIENT_BOXES)), 52, 0xffffff);
		
		mc.renderEngine.bindTexture(GuiMain.texture);
		drawTexturedModalRect(width / 2 + 64, 74, 0, 0, 32, 32);
		drawTexturedModalRect(width / 2 + 64, 96, 32, 0, 32, 32);
		drawTexturedModalRect(width / 2 + 64, 118, 32, 0, 32, 32);
		drawTexturedModalRect(width / 2 + 64, 140, 32, 0, 32, 32);
		
		drawString(fontRendererObj, "200", width / 2 + 67 - (fontRendererObj.getStringWidth("200")), 86, 0xffffff);
		drawString(fontRendererObj, "0.99", width / 2 + 67 - (fontRendererObj.getStringWidth("0.99")), 108, 0xffffff);
		drawString(fontRendererObj, "7.99", width / 2 + 67 - (fontRendererObj.getStringWidth("7.99")), 130, 0xffffff);
		drawString(fontRendererObj, "17.99", width / 2 + 67 - (fontRendererObj.getStringWidth("17.99")), 152, 0xffffff);
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == 0)
			/** BUY_FOR_GOLD **/
		{
			if (LuckyCoinsData.CLIENT_COINS >= 200)
			{
				PacketHandler.instance().net.sendToServer(new PacketBuyBox.MessageBuyBox());
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("random.orb"), 1.0F));
			}
			else
			{
				mc.thePlayer.addChatMessage(new ChatComponentText(Paragraph.rose + GuiRefs.NOT_ENOUGH_GOLD));
			}
		}
		else if (button.id == 1 || button.id == 2 || button.id == 3)
			/** BUY_FOR_REAL_MONEY **/
		{
			//
		}
		else if (button.id == 4)
			/** FREE_BOXES **/
		{
			InternetHelper.openWebpage("http://mods.hoppix.ru/Files/LuckyCoins/free_boxes.html");
		}
		else if (button.id == 5)
			/** BACK **/
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiMain());
		}
	}
	
	@Override
	protected void mouseClicked(int x, int y, int wtf)
	{
		super.mouseClicked(x, y, wtf);
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