package com.w67clement.advancedmotd.bungee.commands.subcommands;

import com.w67clement.advancedmotd.bungee.AdvancedMotd;
import com.w67clement.advancedmotd.bungee.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

public class ReloadCommand implements SubCommand
{

	@Override
	public String getName()
	{
		return "reload";
	}

	@Override
	public String getUsage()
	{
		return "/AdvancedMotd reload";
	}

	@Override
	public String getDescription()
	{
		return "Reload the configurations.";
	}

	@Override
	public String getPermissionRequired()
	{
		return "advancedmotd.cmd.reload";
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onExecute(CommandSender sender, String[] args)
	{
		sender.sendMessage(AdvancedMotd.PREFIX + ChatColor.GREEN
				+ "Reloading configuration...");
		AdvancedMotd.config.reload();
		return true;
	}

}
