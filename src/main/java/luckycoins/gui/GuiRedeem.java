package luckycoins.gui;

import luckycoins.core.LuckyCoinsData;
import luckycoins.network.PacketHandler;
import luckycoins.network.packet.PacketRedeem;
import luckycoins.thread.ThreadDisableGuiButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiRedeem extends ModGui
{
	private GuiTextField field;
	
	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 70, 130, 140, 20, GuiRefs.REDEEM));
		buttonList.add(new GuiButton(1, width / 2 - 70, 160, 140, 20, GuiRefs.BACK));
		this.field = new GuiTextField(fontRendererObj, width / 2 - 64, 90, 128, 10);
		field.setEnableBackgroundDrawing(false);
		field.setMaxStringLength(20);
		field.setFocused(true);
		String clipboard = "";
		clipboard = getClipboardString();
		if (!clipboard.matches("[a-zA-Z0-9]{20}"))
		{
			clipboard = "";
		}
		field.setText(clipboard);
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		super.drawScreen(par1, par2, par3);
		drawCenteredString(fontRendererObj, GuiRefs.ENTER_CODE, width / 2, 70, 0xffffff);
		drawField(field, "");
		
		mc.renderEngine.bindTexture(GuiMain.texture);
		drawTexturedModalRect(width / 2 - 70, 40, 0, 0, 32, 32);
		drawTexturedModalRect(width / 2 + 38, 41, 32, 0, 32, 32);
		drawCenteredString(fontRendererObj, GuiRefs.YOUR_STATS, width / 2, 35, 0xffffff);
		drawString(fontRendererObj, GuiMain.getNumberString(LuckyCoinsData.CLIENT_COINS), width / 2 - 40, 52, 0xffffff);
		drawString(fontRendererObj, GuiMain.getNumberString(LuckyCoinsData.CLIENT_BOXES), width / 2 + 40 - fontRendererObj.getStringWidth(GuiMain.getNumberString(LuckyCoinsData.CLIENT_BOXES)), 52, 0xffffff);
		
		// TODO Fix glitching rectangles
		if (!StringUtils.isNullOrEmpty(field.getSelectedText()))
		{
			drawCenteredString(fontRendererObj, "Please, do not report this glitch.", width / 2, 110, 0xffffff);
		}
	}
	
	/**
	 * @author Lizbeth "Vazkii" Rika
	 * @param field
	 * @param label
	 */
	private void drawField(GuiTextField field, String label)
	{
		GL11.glEnable(GL11.GL_BLEND);
		drawString(fontRendererObj, label, field.xPosition, field.yPosition - 12, 0xAAFFFFFF);
		drawRect(field.xPosition - 3, field.yPosition - 3, field.xPosition + field.width + 3, field.yPosition + field.height - 1, 0x22000000);
		drawRect(field.xPosition - 2, field.yPosition - 2, field.xPosition + field.width + 2, field.yPosition + field.height - 2, 0x66000000);

		GL11.glEnable(GL11.GL_BLEND);
		if(field.getText().isEmpty())
			drawString(fontRendererObj, "<blank>", field.xPosition, field.yPosition, 0x22FFFFFF);

		field.drawTextBox();
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == 0)
			/** REDEEM **/
		{
			if (field.getText().matches("[a-zA-Z0-9]{20}"))
			{
				PacketHandler.instance().net.sendToServer(new PacketRedeem.MessageRedeem(field.getText()));
				field.setText("");
			}
			else
			{
				mc.thePlayer.addChatMessage(new ChatComponentTranslation("message.redeem.fail").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			}
		}
		else if (button.id == 1)
			/** BACK **/
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiMain());
		}
	}
	
	@Override
	protected void keyTyped(char par1, int par2)
	{
		super.keyTyped(par1, par2);
		
		field.textboxKeyTyped(par1, par2);
	}
	
	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
}