package in.pinig.ttvmc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EListener implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		if(event.getPlayer().getDisplayName().indexOf("G0Z1") != -1) {
			event.getPlayer().sendMessage("[§5Twitch§f] Вы читаете чат Twitch");
		}
	}

}