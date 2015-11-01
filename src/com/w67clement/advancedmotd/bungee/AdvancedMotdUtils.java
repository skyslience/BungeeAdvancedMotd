package com.w67clement.advancedmotd.bungee;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;

public class AdvancedMotdUtils
{

	public static List<String> generateDefaultFakePlayerList()
	{
		List<String> list = new ArrayList<String>();
		list.add("&2=======================================");
		list.add("&aWelcome to &2%server%&a!");
		list.add("&7Online: &c%online%&7/&c%max_players%");
		list.add("&7Website: &chttps://github.com/w67clement");
		list.add("&2=======================================");
		return list;
	}

	public static String replaceVariable(String input)
	{
		return ChatColor.translateAlternateColorCodes('&', input)
				.replace("[++]", "\u2726")
				.replace("%online%",
						"" + BungeeCord.getInstance().getOnlineCount())
				.replace("%max_players%",
						"" + BungeeCord.getInstance().config.getPlayerLimit())
				.replace("%server%", "Server");
	}

}
