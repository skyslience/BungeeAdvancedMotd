package com.w67clement.advancedmotd.bungee;

import java.io.File;
import java.lang.reflect.Field;

import com.w67clement.advancedmotd.bungee.commands.AdvancedMotdCommand;
import com.w67clement.advancedmotd.bungee.config.MotdConfig;
import com.w67clement.advancedmotd.bungee.motd.ConnectionReplacement;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.netty.PipelineUtils;

public class AdvancedMotd extends Plugin
{

	public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.GOLD
			+ "Bungee" + ChatColor.DARK_AQUA + "Advanced" + ChatColor.DARK_GREEN
			+ "Motd" + ChatColor.GRAY + "]" + ChatColor.RESET + " ";
	private static AdvancedMotd instance;
	public static MotdConfig config;

	@Override
	public void onEnable()
	{
		instance = this;
		config = new MotdConfig(new File(this.getDataFolder(), "config.yml"),
				this);
		try
		{
			setStaticFinalValue(
					PipelineUtils.class.getDeclaredField("SERVER_CHILD"),
					new ConnectionReplacement());
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.getProxy().getPluginManager().registerCommand(this,
				new AdvancedMotdCommand());
	}

	public static AdvancedMotd getInstance()
	{
		return instance;
	}

	public static void setStaticFinalValue(Field field, Object newValue)
			throws Exception
	{
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
		field.set(null, newValue);
	}
}
