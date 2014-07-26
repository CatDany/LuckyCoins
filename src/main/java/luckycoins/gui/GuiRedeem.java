package luckycoins.gui;

import luckycoins.network.PacketHandler;
import luckycoins.network.packet.PacketRedeem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.opengl.GL11;

public class GuiRedeem extends GuiScreen
{
	private GuiTextField field;
	
	@Override
	public void initGui()
	{
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
}