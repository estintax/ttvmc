package in.pinig.ttvmc;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EListener implements Listener
{
	public static Player player;
	private static TMI thr;
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		if(event.getPlayer().getDisplayName().indexOf("G0Z1") != -1) {
			event.getPlayer().sendMessage("Вы уполномочены читать чат канала #spider_kubla, подключаемся.");
			player = event.getPlayer();
			thr = new in.pinig.ttvmc.TMI();
			thr.start();
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(player.getName().indexOf("G0Z1") != -1) {
			try {
				if(!TMI.sock.isClosed()) {
					System.out.println("Closing socket to TMI");
					TMI.out.write("QUIT\n"); TMI.out.flush();
				}
			}
			catch (IOException ex) {
				
			}
		}
	}
}