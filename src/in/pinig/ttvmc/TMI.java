package in.pinig.ttvmc;

import java.io.*;
import java.net.Socket;

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
				out.println("JOIN #" + Main.config.getString("channel") + "\n");
				
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
						if(!Main.enabled) continue;
						String[] args = str.split(":", 3);
						String username = args[1].split("!")[0];
						String message = args[2];
						Utils.broadcastMessageToAllPlayerWhoCanReadThis("[§5Twitch§f] <" + username + ">: " + message);
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
				Utils.broadcastMessageToAllPlayerWhoCanReadThis("[§5Twitch§f] Ошибка I/O: " + e.getMessage());
				Utils.broadcastMessageToAllPlayerWhoCanReadThis("Stack Trace смотрите в журналах сервера");
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
			Utils.broadcastMessageToAllPlayerWhoCanReadThis("[§5Twitch§f] Вы отключены от TMI. Причина:");
			Utils.broadcastMessageToAllPlayerWhoCanReadThis(e.getMessage());
		}
	}
}
