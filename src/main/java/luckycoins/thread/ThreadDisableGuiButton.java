package luckycoins.thread;

import java.util.concurrent.TimeUnit;

import luckycoins.LuckyCoins;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class ThreadDisableGuiButton extends Thread
{
	private GuiButton button;
	private int millis;
	
	public ThreadDisableGuiButton(GuiButton button, int millis)
	{
		setDaemon(true);
		this.button = button;
		this.millis = millis;
	}
	
	@Override
	public void run()
	{
		button.enabled = false;
		try
		{
			sleep(millis);
		}
		catch (Throwable t)
		{
			LuckyCoins.logger.warn("ThreadDisableGuiButton has thrown an exception!");
			LuckyCoins.logger.warn(String.format("Additional Data: {\"millis\"=%s}", millis));
			LuckyCoins.logger.catching(t);
		}
		button.enabled = true;
	}
}