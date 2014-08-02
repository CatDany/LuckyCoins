package luckycoins.event;

import luckycoins.core.LuckyCoinsData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEvent.Action;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ServerChatEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import danylibs.LocalizationHelper;
import danylibs.Paragraph;

public class EventServer
{
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void serverChat(ServerChatEvent e)
	{
		LuckyCoinsData data = LuckyCoinsData.get(e.player);
		if (data.the_legend)
		{
			IChatComponent msg = new ChatComponentText("[").appendSibling(new ChatComponentText(Paragraph.gold + MessageRefs.THE_LEGEND + Paragraph.reset).setChatStyle(new ChatStyle().setChatHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ChatComponentText(MessageRefs.LEGEND_INFO))))).appendText("] <" + e.player.getCommandSenderName() + "> " + e.message);
			e.setCanceled(true);
			MinecraftServer.getServer().addChatMessage(msg);
			for (WorldServer w : MinecraftServer.getServer().worldServers)
			{
				for (Object p : w.playerEntities)
				{
					EntityPlayer player = (EntityPlayer)p;
					player.addChatMessage(msg);
				}
			}				
		}
	}
	
	public static class MessageRefs
	{
		public static final String THE_LEGEND = LocalizationHelper.get("coin.LEGENDARY.LEGEND.name");
		public static final String LEGEND_INFO = LocalizationHelper.get("message.legend_info").replace("%EOL%", "\n");
	}
}