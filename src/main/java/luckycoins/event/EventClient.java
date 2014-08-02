package luckycoins.event;

import luckycoins.misc.TransientScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import danylibs.LocalizationHelper;

public class EventClient
{
	@SubscribeEvent
	public void clientTick(TickEvent.ClientTickEvent e)
	{
		//
	}
	
	public static boolean show_one_with_nature_warning = false;
	
	@SubscribeEvent
	public void clientRenderOverlay(RenderGameOverlayEvent.Post e)
	{
		if (show_one_with_nature_warning)
		{
			if (e.type == ElementType.ALL)
			{
				Minecraft mc = Minecraft.getMinecraft();
				TransientScaledResolution resolution = new TransientScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
				int width = resolution.getScaledWidth();
				int height = resolution.getScaledHeight();
				FontRenderer font = mc.fontRenderer;
				
				int x = (width - font.getStringWidth(MessageRefs.ONE_WITH_NATURE_WARNING)) / 2;
				int y = height - 72;
				
				font.drawStringWithShadow(MessageRefs.ONE_WITH_NATURE_WARNING, x, y, 0xffffff);
			}
		}
	}
	
	public static class MessageRefs
	{
		public static final String ONE_WITH_NATURE_WARNING = LocalizationHelper.get("message.one_with_nature_warning").replace("%EOL%", "\n");
	}
}