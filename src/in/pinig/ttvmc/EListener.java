package in.pinig.ttvmc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EListener implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(Main.channels.get(event.getPlayer().getName()) != null) {
			event.getPlayer().sendMessage("[§5Twitch§f] Вы уполномочены читать чат канала " + Main.channels.get(event.getPlayer().getName()));
			event.getPlayer().sendMessage("[§5Twitch§f] Чтобы отключить/включить чат, введите §c/tmioff");
		}
	}

}