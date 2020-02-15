package in.pinig.ttvmc;

import java.util.Collection;
import java.util.List;
import java.util.HashMap;

import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class Utils {
	public static void broadcastMessageToAllPlayerWhoCanReadThis(String chan, String message, Boolean highlight) {
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		for(Player x : players) {
			String channel = Main.channels.get(x.getName());
			if(channel == null) continue;
			if(channel.equals(chan) || chan.equals("none")) {
				if(!Main.state.get(x.getName())) continue;
				if(highlight && Main.config.getBoolean("options.highlightSound", false)) {
					x.playSound(x.getLocation(), Sound.ENTITY_CAT_HURT, 1f, 1f);
				}
				x.sendMessage(message);
			}
		}
	}
	
	public static void loadChannelsFromConfig() {
		List<String> playerToChannel = Main.config.getStringList("channels");
		for(String x: playerToChannel) {
			if(!x.contains(":")) {
				System.err.println("ttvmc config: line \"" + x + "\" is incorrect");
				continue;
			}
			String[] splitted = x.split(":", 2);
			Main.channels.put(splitted[0], splitted[1]);
			Main.state.put(splitted[0], Main.config.getBoolean("default"));
		}
	}

	public static HashMap<String, String> parseTags(@NotNull String raw) {
		HashMap<String, String> result = new HashMap<>();

		String[] splitted = raw.split(";");
		for (String x: splitted) {
			String[] keyAndValue = x.split("=", 2);
			result.put(keyAndValue[0], keyAndValue.length == 2?keyAndValue[1]:null);
		}

		return result;
	}

	public static String badgeToColor(String badges) {
		if(badges == null) return null;

		if(badges.contains("staff")) return "§8";
		else if(badges.contains("admin")) return "§6";
		else if(badges.contains("broadcaster")) return "§c";
		else if(badges.contains("moderator")) return "§2";
		else if(badges.contains("vip")) return "§d";
		else if(badges.contains("subscriber")) return "§e";
		else if(badges.contains("prime")) return "§b";

		return null;
	}
}
