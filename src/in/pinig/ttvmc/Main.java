package in.pinig.ttvmc;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	private static Thread thr;
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EListener(), this);
		Player player = Bukkit.getPlayer("G0Z1");
		if(player != null) {
			player.sendMessage("[§5Twitch§f] Вы уполномочены читать чат Twitch");
		}
		System.out.println("Starting thread for TMI...");
		thr = new TMI();
		thr.start();
	}
	
	@Override
	public void onDisable() {
		try {
			TMI.sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
