package luckycoins.gui;

import luckycoins.network.PacketHandler;
import luckycoins.network.packet.PacketDataSync;
import net.minecraft.client.gui.GuiScreen;

public class ModGui extends GuiScreen
{
	@Override
	public void initGui()
	{
		if (syncDataWhenOpened())
		{
			PacketHandler.instance().net.sendToServer(new PacketDataSync.MessageDataSync());
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
}