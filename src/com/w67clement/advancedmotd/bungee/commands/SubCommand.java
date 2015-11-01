package com.w67clement.advancedmotd.bungee.commands;

import net.md_5.bungee.api.CommandSender;

public interface SubCommand {

	public String getName();
	
	public String getUsage();
	
	public String getDescription();
	
	public String getPermissionRequired();
	
	public boolean onExecute(CommandSender sender, String[] args);
}
