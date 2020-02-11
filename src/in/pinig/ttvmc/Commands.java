package in.pinig.ttvmc;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(Main.channels.get(player.getName()) != null) {
			
				if(Main.state.get(player.getName())) {
					Main.state.put(player.getName(), false);
					player.sendMessage("[§5Twitch§f] Отображение сообщений §cвыключено");
				} else {
					Main.state.put(player.getName(), true);
					player.sendMessage("[§5Twitch§f] Отображение сообщений §aвключено");
				}
			} else {
				player.sendMessage("§cВы не уполномочены использовать эту команду");
			}
		}
		
		return true;
	}
}
