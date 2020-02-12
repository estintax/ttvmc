package in.pinig.ttvmc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	private static Thread thr;
	public static FileConfiguration config;
	public static HashMap<String, Boolean> state;
	public static HashMap<String, String> channels;
	public static ArrayList<String> joinedChannels;
	@Override
	public void onEnable() {
		// config
		this.saveDefaultConfig();
		config = this.getConfig();

		state = new HashMap<String, Boolean>();
		channels = new HashMap<String, String>();
		joinedChannels = new ArrayList<String>();

		Utils.loadChannelsFromConfig();

		getServer().getPluginManager().registerEvents(new EListener(), this);
		this.getCommand("tmioff").setExecutor(new Commands());
		
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
