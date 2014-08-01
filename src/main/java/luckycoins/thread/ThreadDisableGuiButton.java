package luckycoins.thread;

import java.util.concurrent.TimeUnit;

import luckycoins.LuckyCoins;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

@Deprecated
public class ThreadDisableGuiButton extends Thread
{
	private GuiButton button;
	private int millis;
	
	public ThreadDisableGuiButton(GuiButton button, int millis)
	{
		setDaemon(true);
		setPriority(MIN_PRIORITY);
		this.button = button;
		this.millis = millis;
	}
	
	@Override
	public void run()
	{
		super.run();
		button.enabled = false;
		String oldLabel = button.displayString;
		String waitLabel = StatCollector.translateToLocal("gui.wait");
		button.displayString = waitLabel;
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
		button.displayString = oldLabel;
		button.enabled = true;
	}
}