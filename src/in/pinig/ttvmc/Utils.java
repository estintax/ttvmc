package in.pinig.ttvmc;

import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utils {
	public static void broadcastMessageToAllPlayerWhoCanReadThis(String chan, String message) {
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		for(Player x : players) {
			String channel = Main.channels.get(x.getName());
			if(channel == null) continue;
			if(channel.equals(chan) || chan.equals("none")) {
				if(!Main.state.get(x.getName())) continue;
				x.sendMessage(message);
			}
		}
	}
	
	public static void loadChannelsFromConfig() {
		List<String> playerToChannel = Main.config.getStringList("channels");
		for(String x: playerToChannel) {
			if(x.indexOf(":") == -1) {
				System.err.println("ttvmc config: line \"" + x + "\" is incorrect");
				continue;
			}
			String[] splitted = x.split(":", 2);
			Main.channels.put(splitted[0], splitted[1]);
			Main.state.put(splitted[0], Main.config.getBoolean("default"));
		}
	}
}
