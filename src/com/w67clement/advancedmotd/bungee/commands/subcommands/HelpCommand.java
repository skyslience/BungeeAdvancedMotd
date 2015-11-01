package com.w67clement.advancedmotd.bungee.commands.subcommands;

import com.w67clement.advancedmotd.bungee.commands.AdvancedMotdCommand;
import com.w67clement.advancedmotd.bungee.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class HelpCommand implements SubCommand
{

	@Override
	public String getName()
	{
		return "help";
	}

	@Override
	public String getUsage()
	{
		return "/advancedmotd help";
	}

	@Override
	public String getDescription()
	{
		return "Shows the help menu.";
	}

	@Override
	public String getPermissionRequired()
	{
		return "advancedmotd.cmd.help";
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onExecute(CommandSender sender, String[] args)
	{
		if (args.length == 1)
		{
			// Head
			sender.sendMessage(ChatColor.DARK_AQUA + "\u2726" + ChatColor.AQUA
					+ " =====------==== " + ChatColor.DARK_AQUA + "Advanced"
					+ ChatColor.DARK_GREEN + "Motd" + ChatColor.DARK_AQUA
					+ " Help" + ChatColor.AQUA + " ====------===== "
					+ ChatColor.DARK_AQUA + "\u2726");
			// Body
			for (SubCommand subCMD : AdvancedMotdCommand.subCommands.values())
			{
				TextComponent text = new TextComponent(
						"/AdvancedMotd " + subCMD.getName());
				text.setColor(ChatColor.RED);
				text.setClickEvent(new ClickEvent(
						ClickEvent.Action.SUGGEST_COMMAND, subCMD.getUsage()));
				TextComponent hover = new TextComponent("Permission: ");
				hover.setColor(ChatColor.RED);
				TextComponent hoverExtra = new TextComponent(
						subCMD.getPermissionRequired());
				hoverExtra.setColor(ChatColor.DARK_RED);
				hover.addExtra(hoverExtra);
				text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
						new BaseComponent[] { hover }));
				TextComponent extra = new TextComponent(
						" - " + subCMD.getDescription());
				extra.setColor(ChatColor.GRAY);
				text.addExtra(extra);
				sender.sendMessage(text);
			}
			// Foot
			sender.sendMessage(ChatColor.DARK_AQUA + "\u2726" + ChatColor.AQUA
					+ " =====------==== " + ChatColor.DARK_AQUA + "Advanced"
					+ ChatColor.DARK_GREEN + "Motd" + ChatColor.DARK_AQUA
					+ " Help" + ChatColor.AQUA + " ====------===== "
					+ ChatColor.DARK_AQUA + "\u2726");
			return true;
		}
		return false;
	}

}
