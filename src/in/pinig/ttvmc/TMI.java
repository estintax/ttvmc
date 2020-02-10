package in.pinig.ttvmc;

import java.io.*;
import java.net.Socket;

public class TMI extends Thread {
	public static Socket sock;
	private static BufferedReader in;
	public static BufferedWriter out;
	public void run() {
		try {
			try {
				sock = new Socket("irc.chat.twitch.tv", 6667);
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
				
				out.write("PASS oauth:frpcq53xy2qqoara15edjuvln0126j\n"); out.flush();
				out.write("NICK piniginbot\n"); out.flush();
				out.write("JOIN #spider_kubla\n"); out.flush();
				
				for (;;) {
					if(sock.isClosed()) break;
					String str = in.readLine();
					String[] params = str.split(" ");
					if(params[0].equals("PING")) {
						System.out.println("Sending PING to TMI.");
						out.write("PONG " + params[1]); out.flush();
					}
					if(params[1].equals("PRIVMSG")) {
						String[] args = str.split(":");
						String username = args[1].split("!")[0];
						String message = args[2];
						EListener.player.sendMessage("[§5Twitch§f] <" + username + ">: " + message);
					}
					if(params[0].equals("QUIT")) {
						break;
					}
				}
			}
			catch(IOException e) {
				EListener.player.sendMessage("Исключение ввода/вывода: " + e.getMessage());
			}
			finally {
				sock.close();
				in.close();
				out.close();
			}
		}
		catch (IOException e) {
			EListener.player.sendMessage("Ошибка при подключении к чату TMI.");
			EListener.player.sendMessage(e.getMessage());
		}
	}
}
