package in.pinig.ttvmc;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
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
				
				out.println("NICK justinfan35815");
				out.println("CAP REQ :twitch.tv/tags");
				for(Map.Entry<String, String> e: Main.channels.entrySet()) {
					String channel = e.getValue();
					if(Main.joinedChannels.contains(channel)) continue;
					System.out.println("Joining to #" + channel + " just for " + e.getKey());
					out.println("JOIN #" + channel);
					Main.joinedChannels.add(channel);
				}
				
				while (!sock.isClosed()) {
					String str = in.readLine();
					if(str == null) continue;
					String[] preParseParams = str.split(" ");
					if(preParseParams[0].equals("PING")) {
						out.println("PONG " + preParseParams[1]);
						continue;
					}

					String rawTags = str.split(" :")[0];
					str = str.replace(rawTags, "");

					String[] args = str.split(":", 3);
					args[0] = args[0].replaceAll(" ", "");
					str = String.join(":", args);
					String[] params = str.split(" ");
					if(params.length > 2 && params[1].equals("PRIVMSG")) {
						String channel = params[2].replace("#", "");
						String username = args[1].split("!")[0];
						String message;
						message = new String(args[2].getBytes(), "UTF8");

						HashMap<String, String> tags = Utils.parseTags(rawTags.replace("@", ""));
						String displayName = Main.config.getBoolean("options.useDisplayName")?tags.get("display-name"):username;
						if(Main.config.getBoolean("options.typesColor")) {
							String color = Utils.badgeToColor(tags.get("badges"));
							if(color != null && !color.equals("")) displayName = color + displayName + "§f";
						}
						if(message.contains("\u0001")) {
							message = message.replaceAll("\u0001", "");
							String[] subParams = message.split(" ", 2);
							if(subParams[0].equals("ACTION")) {
								Utils.broadcastMessageToAllPlayerWhoCanReadThis(channel, "[§5Twitch§f] §o* " + displayName + " " + subParams[1], false);
							}
						} else {
							String highlight;
							if(tags.get("msg-id") != null && tags.get("msg-id").equals("highlighted-message")) highlight = "§c[!]§f ";
							else highlight = "";
							Utils.broadcastMessageToAllPlayerWhoCanReadThis(channel, "[§5Twitch§f] " + highlight + "<" + displayName + ">: " + message, highlight.equals("")?false:true);
						}
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
			Utils.broadcastMessageToAllPlayerWhoCanReadThis("none", "[§5Twitch§f] Вы отключены от TMI. Причина:", false);
			Utils.broadcastMessageToAllPlayerWhoCanReadThis("none", e.getMessage(), false);
		}
	}
}
