package in.pinig.ttvmc;

import java.io.*;
import java.net.Socket;
import java.util.Map;

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
				
				out.println("NICK justinfan35815\n");
				for(Map.Entry<String, String> e: Main.channels.entrySet()) {
					String channel = e.getValue();
					if(Main.joinedChannels.contains(channel)) continue;
					System.out.println("Joining to #" + channel + " just for " + e.getKey());
					out.println("JOIN #" + channel + "\n");
					Main.joinedChannels.add(channel);
				}
				
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
						String channel = params[2].replace("#", "");
						String[] args = str.split(":", 3);
						String username = args[1].split("!")[0];
						String message = args[2];
						Utils.broadcastMessageToAllPlayerWhoCanReadThis(channel, "[§5Twitch§f] <" + username + ">: " + message);
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
				Utils.broadcastMessageToAllPlayerWhoCanReadThis("none", "[§5Twitch§f] Ошибка I/O: " + e.getMessage());
				Utils.broadcastMessageToAllPlayerWhoCanReadThis("none", "Stack Trace смотрите в журналах сервера");
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
			Utils.broadcastMessageToAllPlayerWhoCanReadThis("none", "[§5Twitch§f] Вы отключены от TMI. Причина:");
			Utils.broadcastMessageToAllPlayerWhoCanReadThis("none", e.getMessage());
		}
	}
}
