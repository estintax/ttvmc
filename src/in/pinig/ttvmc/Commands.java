package in.pinig.ttvmc;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.getName().indexOf("G0Z1") != -1) {
			
				if(Main.enabled) {
					Main.enabled = false;
					player.sendMessage("[§5Twitch§f] Отображение сообщений §cвыключено");
				} else {
					Main.enabled = true;
					player.sendMessage("[§5Twitch§f] Отображение сообщений §aвключено");
				}
			} else {
				player.sendMessage("§cЭту команду может использовать только медиа-личность");
			}
		}
		
		return true;
	}
}
