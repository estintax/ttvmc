package in.pinig.ttvmc;

import java.util.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EListener implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		List<String> players = Main.config.getStringList("players");
		if(players.indexOf(event.getPlayer().getName()) != -1) {
			event.getPlayer().sendMessage("[§5Twitch§f] Вы уполномочены читать чат Twitch");
			event.getPlayer().sendMessage("[§5Twitch§f] Чтобы отключить/включить чат, введите §c/tmioff");
		}
	}

}