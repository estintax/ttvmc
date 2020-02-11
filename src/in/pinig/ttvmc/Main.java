package in.pinig.ttvmc;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EListener(), this);
	}
	
	@Override
	public void onDisable() {
		try {
			TMI.out.write("QUIT\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
