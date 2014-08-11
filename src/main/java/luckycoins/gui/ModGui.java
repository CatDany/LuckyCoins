package luckycoins.gui;

import org.lwjgl.opengl.GL11;

import danylibs_luckycoins.InternetHelper;
import luckycoins.Refs;
import luckycoins.network.PacketHandler;
import luckycoins.network.packet.PacketDataSync;
import luckycoins.thread.ThreadDisableGuiButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class ModGui extends GuiScreen
{
	protected GuiButton patreonButton;
	
	@Override
	public void initGui()
	{
		if (syncDataWhenOpened())
		{
			PacketHandler.instance().net.sendToServer(new PacketDataSync.MessageDataSync());
		}
		if (showPatreonButton())
		{
			patreonButton = new GuiButton(666, width - 75, height - 25, 70, 20, "@ Patreon");
			buttonList.add(patreonButton);
		}
	}
	
	@Override
	protected void mouseClicked(int x, int y, int wtf)
	{
		super.mouseClicked(x, y, wtf);
		if (showPatreonButton())
		{
			if (patreonButton.enabled && x >= patreonButton.xPosition && x <= patreonButton.xPosition + patreonButton.width && y >= patreonButton.yPosition && y <= patreonButton.yPosition + patreonButton.height)
			{
				InternetHelper.openWebpage("http://patreon.com/catdany");
				new ThreadDisableGuiButton(patreonButton, 3000).start();
			}
		}
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	protected boolean syncDataWhenOpened()
	{
		return true;
	}
	
	protected boolean showPatreonButton()
	{
		return true;
	}
}