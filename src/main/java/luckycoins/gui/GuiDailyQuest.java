package luckycoins.gui;

import luckycoins.core.LuckyCoinsData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuiDailyQuest extends ModGui
{
	@Override
	public void initGui()
	{
		buttonList.add(new GuiButton(0, width / 2 - 70, 150, 140, 20, GuiRefs.BACK));
		
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
		
		drawCenteredString(fontRendererObj, "-- " + LuckyCoinsData.CLIENT_DAILY_QUEST.getTranslatedQuestName() + " --", width / 2, 80, 0xffffff);
		drawCenteredString(fontRendererObj, "\"" + LuckyCoinsData.CLIENT_DAILY_QUEST.getTranslatedQuestDescription() + "\"", width / 2, 95, 0xffffff);
		drawCenteredString(fontRendererObj, LuckyCoinsData.CLIENT_DAILY_COMPLETED + "/" + LuckyCoinsData.CLIENT_DAILY_QUEST.max, width / 2, 110, 0xffffff);
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == 0)
			/** BACK **/
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiMain());
		}
	}
}