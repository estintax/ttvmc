package in.pinig.ttvmc;

import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utils {
	public static void broadcastMessageToAllPlayerWhoCanReadThis(String message) {
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		List<String> allowed = Main.config.getStringList("players");
		for(Player x : players) {
			int position = allowed.indexOf(x.getName());
			if(position != -1) {
				Player player = Bukkit.getPlayer(allowed.get(position));
				player.sendMessage(message);
			}
		}
	}
}
