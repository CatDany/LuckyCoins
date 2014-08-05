package luckycoins.gui;

import org.lwjgl.opengl.GL11;

import luckycoins.core.LuckyCoinsData;
import luckycoins.network.PacketHandler;
import luckycoins.network.packet.PacketLootBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import danylibs_luckycoins.LocalizationHelper;

public class GuiOpenLootBoxes extends ModGui
{
	@Override
	public void initGui()
	{
		super.initGui();
		buttonList.add(new GuiButton(0, width / 2 - 40, height / 2 - 30, 80, 20, "OPEN"));
		buttonList.add(new GuiButton(1, width / 2 - 70, height / 2 + 30, 140, 20, GuiRefs.BACK));
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		super.drawScreen(par1, par2, par3);
		
		((GuiButton)buttonList.get(0)).enabled = LuckyCoinsData.CLIENT_BOXES > 0;
		
		GL11.glColor4f(1, 1, 1, 1);
		mc.renderEngine.bindTexture(GuiMain.texture);
		drawTexturedModalRect(width / 2 - 20 - (fontRendererObj.getStringWidth("" + LuckyCoinsData.CLIENT_BOXES) / 2), height / 2 - 15, 32, 0, 32, 32);
		drawCenteredString(fontRendererObj, "" + LuckyCoinsData.CLIENT_BOXES, width / 2 + 10, height / 2 - 3, 0xffffff);
		drawCenteredString(fontRendererObj, GuiRefs.LOOT_BOX_INFO, width / 2, height / 2 - 50, 0xffffff);
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == 0)
			/** OPEN **/
		{
			if (LuckyCoinsData.CLIENT_BOXES > 0)
			{
				LuckyCoinsData.CLIENT_BOXES--;
				IMessage msg = new PacketLootBox.MessageLootBox();
				PacketHandler.instance().net.sendToServer(msg);
			}
		}
		else if (button.id == 1)
			/** BACK **/
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiMain());
		}
	}
}