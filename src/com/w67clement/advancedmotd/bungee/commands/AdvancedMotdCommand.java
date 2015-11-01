package com.w67clement.advancedmotd.bungee.commands;

import java.util.HashMap;

import com.w67clement.advancedmotd.bungee.AdvancedMotd;
import com.w67clement.advancedmotd.bungee.commands.subcommands.HelpCommand;
import com.w67clement.advancedmotd.bungee.commands.subcommands.ReloadCommand;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class AdvancedMotdCommand extends Command
{

	public static HashMap<String, SubCommand> subCommands = new HashMap<String, SubCommand>();

	private AdvancedMotd plugin = AdvancedMotd.getInstance();

	public AdvancedMotdCommand() {
		super("advancedmotd", "advancedmotd.cmd");
		this.registerSubCommand(new HelpCommand());
		this.registerSubCommand(new ReloadCommand());
	}

	private void registerSubCommand(SubCommand subCmd)
	{
		subCommands.put(subCmd.getName().toLowerCase(), subCmd);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if (args.length == 0)
		{
			sender.sendMessage(ChatColor.GOLD + "Bungee" + ChatColor.DARK_AQUA
					+ "Advanced" + ChatColor.DARK_GREEN + "Motd"
					+ ChatColor.GRAY + " v" + ChatColor.RED
					+ plugin.getDescription().getVersion());
			sender.sendMessage(
					ChatColor.GRAY + "Author: " + ChatColor.RED + "w67clement");
			sender.sendMessage(ChatColor.GRAY + "Github: " + ChatColor.RED
					+ "https://github.com/w67clement/AdvancedMotd/");
			TextComponent text = new TextComponent("Help: ");
			text.setColor(ChatColor.GRAY);
			TextComponent extra = new TextComponent("/AdvancedMotd help");
			extra.setColor(ChatColor.RED);
			extra.setClickEvent(new ClickEvent(
					ClickEvent.Action.SUGGEST_COMMAND, "/advancedmotd help"));
			text.addExtra(extra);
			sender.sendMessage(text);
		}
		else if (args.length >= 1)
		{
			String subcmd = args[0].toLowerCase();
			if (!subCommands.containsKey(subcmd))
			{
				TextComponent text = new TextComponent("[Usage] ");
				text.setColor(ChatColor.DARK_RED);
				TextComponent extra = new TextComponent(
						"/AdvancedMotd [Help|SubCommand]");
				extra.setColor(ChatColor.RED);
				extra.setClickEvent(
						new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
								"/advancedmotd help"));
				text.addExtra(extra);
				sender.sendMessage(text);
			}
			else
			{
				if (!subCommands.get(subcmd).onExecute(sender, args))
				{
					String usage = subCommands.get(subcmd).getUsage();
					TextComponent text = new TextComponent("[Usage] ");
					text.setColor(ChatColor.DARK_RED);
					TextComponent extra = new TextComponent(usage);
					extra.setColor(ChatColor.RED);
					extra.setClickEvent(new ClickEvent(
							ClickEvent.Action.SUGGEST_COMMAND, usage));
					text.addExtra(extra);
					sender.sendMessage(text);
				}
			}
		}
	}

}
