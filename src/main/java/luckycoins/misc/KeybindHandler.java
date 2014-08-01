package luckycoins.misc;

import luckycoins.Refs;
import luckycoins.gui.GuiDailyQuest;
import luckycoins.gui.GuiMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.client.settings.KeyBinding;

public class KeybindHandler
{
	public final KeyBinding keyGui = new KeyBinding("LuckyCoins GUI", Keyboard.KEY_HOME, Refs.MOD_NAME);
	public final KeyBinding keyDaily = new KeyBinding("LuckyCoins Daily Quests", Keyboard.KEY_END, Refs.MOD_NAME);
	
	public void initKeybindings()
	{
		ClientRegistry.registerKeyBinding(keyGui);
		ClientRegistry.registerKeyBinding(keyDaily);
		FMLCommonHandler.instance().bus().register(this);
	}
	
	@SubscribeEvent
	public void clientTick(TickEvent.ClientTickEvent e)
	{
		if (e.phase == Phase.START)
		{
			if (keyGui.getIsKeyPressed())
			{
				Minecraft.getMinecraft().displayGuiScreen(new GuiMain());
			}
			else if (keyDaily.getIsKeyPressed())
			{
				Minecraft.getMinecraft().displayGuiScreen(new GuiDailyQuest(true));
			}
		}
	}
}