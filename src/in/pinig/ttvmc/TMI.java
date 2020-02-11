package in.pinig.ttvmc;

import java.io.*;
import java.net.Socket;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TMI extends Thread {
	public static Socket sock;
	private static BufferedReader in;
	private static PrintWriter out;
	
	@Override
	public void run() {
		try {
			try {
				sock = new Socket("irc.chat.twitch.tv", 6667);
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				out = new PrintWriter(sock.getOutputStream(), true);
				
				out.println("PASS oauth:frpcq53xy2qqoara15edjuvln0126j\n");
				out.println("NICK piniginbot\n");
				out.println("JOIN #spider_kubla\n");
				
				for (;;) {
					if(sock.isClosed()) break;
					String str = in.readLine();
					if(str == null) continue;
					String[] params = str.split(" ");
					if(params[0].equals("PING")) {
						System.out.println("Sending PONG to TMI.");
						out.println("PONG " + params[1]);
					}
					if(params[1].equals("PRIVMSG")) {
						String[] args = str.split(":");
						String username = args[1].split("!")[0];
						String message = args[2];
						Player player = Bukkit.getPlayer("G0Z1");
						if(player != null)
							player.sendMessage("[§5Twitch§f] <" + username + ">: " + message);
					}
					if(params[0].equals("QUIT")) {
						break;
					}
				}
			}
			catch(NullPointerException e) {
				e.printStackTrace();
			}
			catch(IOException e) {
				Player player = Bukkit.getPlayer("G0Z1");
				if(player != null) {
					player.sendMessage("[§5Twitch§f] Ошибка I/O: " + e.getMessage());
					player.sendMessage("Stack Trace смотрите в журналах сервера");
				}
				e.printStackTrace();
			}
			finally {
				sock.close();
				in.close();
				out.close();
			}
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			Player player = Bukkit.getPlayer("G0Z1");
			if(player != null) {
				player.sendMessage("[§5Twitch§f] Вы отключены от TMI. Причина:");
				player.sendMessage(e.getMessage());
			}
		}
	}
}
