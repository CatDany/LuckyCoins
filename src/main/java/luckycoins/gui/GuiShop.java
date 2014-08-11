package luckycoins.gui;

import org.lwjgl.opengl.GL11;

import scala.xml.PrettyPrinter.Para;
import danylibs_luckycoins.InternetHelper;
import danylibs_luckycoins.Paragraph;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import luckycoins.Refs;
import luckycoins.core.LuckyCoinsData;
import luckycoins.network.PacketHandler;
import luckycoins.network.packet.PacketBuyBox;
import luckycoins.network.packet.PacketDataSync;

public class GuiShop extends ModGui
{
	private GuiButton[] unused = new GuiButton[]
			{
				new GuiButton(1, width / 2 - 83, 102, 120, 20, String.format("<unused>", 2)),
				new GuiButton(2, width / 2 - 83, 124, 120, 20, String.format("<unused>", 10)),
				new GuiButton(3, width / 2 - 83, 146, 120, 20, String.format("<unused>", 25))
			};
	
	@Override
	public void initGui()
	{
		super.initGui();
		buttonList.add(new GuiButton(0, width / 2 - 83, 80, 120, 20, String.format(GuiRefs.BUY_ONE, 1)));
		buttonList.add(unused[0]);
		buttonList.add(unused[1]);
		buttonList.add(unused[2]);
		buttonList.add(new GuiButton(4, width / 2 - 83, 168, 120, 20, String.format(GuiRefs.GET_FREE_LOOT_BOXES, 25)));
		buttonList.add(new GuiButton(5, width / 2 + 40, 168, 54, 20, GuiRefs.BACK));
		for (GuiButton i : unused)
		{
			i.enabled = false;
		}
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		super.drawScreen(par1, par2, par3);
		
		GL11.glColor4f(1, 1, 1, 1);
		mc.renderEngine.bindTexture(GuiMain.texture);
		drawTexturedModalRect(width / 2 - 70, 40, 0, 0, 32, 32);
		drawTexturedModalRect(width / 2 + 38, 41, 32, 0, 32, 32);
		drawCenteredString(fontRendererObj, GuiRefs.YOUR_STATS, width / 2, 35, 0xffffff);
		drawString(fontRendererObj, GuiMain.getNumberString(LuckyCoinsData.CLIENT_COINS), width / 2 - 40, 52, 0xffffff);
		drawString(fontRendererObj, GuiMain.getNumberString(LuckyCoinsData.CLIENT_BOXES), width / 2 + 40 - fontRendererObj.getStringWidth(GuiMain.getNumberString(LuckyCoinsData.CLIENT_BOXES)), 52, 0xffffff);
		
		mc.renderEngine.bindTexture(GuiMain.texture);
		drawTexturedModalRect(width / 2 + 64, 74, 0, 0, 32, 32);
		//drawTexturedModalRect(width / 2 + 64, 96, 64, 0, 32, 32);
		//drawTexturedModalRect(width / 2 + 64, 118, 64, 0, 32, 32);
		//drawTexturedModalRect(width / 2 + 64, 140, 64, 0, 32, 32);
		
		drawString(fontRendererObj, "200", width / 2 + 67 - (fontRendererObj.getStringWidth("200")), 86, 0xffffff);
		//drawString(fontRendererObj, "0.99", width / 2 + 67 - (fontRendererObj.getStringWidth("0.99")), 108, 0xffffff);
		//drawString(fontRendererObj, "7.99", width / 2 + 67 - (fontRendererObj.getStringWidth("7.99")), 130, 0xffffff);
		//drawString(fontRendererObj, "17.99", width / 2 + 67 - (fontRendererObj.getStringWidth("17.99")), 152, 0xffffff);
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
			InternetHelper.openWebpage(Refs.MAIN_HOST + "Files/LuckyCoins/free_boxes.html");
		}
		else if (button.id == 5)
			/** BACK **/
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiMain());
		}
	}
}