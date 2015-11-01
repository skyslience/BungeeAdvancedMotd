package com.w67clement.advancedmotd.bungee.config;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import com.w67clement.advancedmotd.bungee.AdvancedMotd;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class MotdConfig
{
	private AdvancedMotd pl;
	private File configFile;
	private Configuration config;
	private String favicon;

	@SuppressWarnings("deprecation")
	public MotdConfig(File file, AdvancedMotd plugin) {
		plugin.getProxy().getConsole().sendMessage(AdvancedMotd.PREFIX
				+ ChatColor.GREEN + "Starting load configuration...");
		if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
		if (!file.exists())
		{
			try (InputStream in = plugin.getResourceAsStream("config.yml"))
			{
				Files.copy(in, file.toPath());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			this.config = ConfigurationProvider
					.getProvider(YamlConfiguration.class).load(file);
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		this.configFile = file;
		this.pl = plugin;
		plugin.getProxy().getConsole()
				.sendMessage(AdvancedMotd.PREFIX + ChatColor.GREEN
						+ "Configuration was loaded successfully!...");
		File faviconFile = new File("server-icon.png");
		if (faviconFile.exists())
		{
			try
			{
				BufferedImage image = ImageIO.read(faviconFile);
				if (image.getWidth() == 64 && image.getHeight() == 64)
				{
					ByteArrayOutputStream ouput = new ByteArrayOutputStream();
					ImageIO.write(image, "png", ouput);
					ouput.flush();
					this.favicon = "data:image/png;base64," + DatatypeConverter
							.printBase64Binary(ouput.toByteArray());
				}
				else
				{
					throw new RuntimeException(
							"Your server-icon.png needs to be 64*64!");
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		if (!this.config.getKeys().contains("ConfigVersion")
				|| this.config.getInt("ConfigVersion") != 1)
		{
			plugin.getProxy().getConsole().sendMessage(AdvancedMotd.PREFIX
					+ ChatColor.DARK_RED + "[Error]" + ChatColor.RED
					+ " The file \"config.yml\" was invalid or outdated! Deleting and generating it...");
			try (InputStream in = plugin.getResourceAsStream("config.yml"))
			{
				Files.copy(in, file.toPath());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			this.reload();
		}
	}

	@SuppressWarnings("deprecation")
	public void reload()
	{
		this.pl.getProxy().getConsole().sendMessage(AdvancedMotd.PREFIX
				+ ChatColor.GREEN + "Reloading configuration...");
		if (!this.configFile.exists())
		{
			try (InputStream in = this.pl.getResourceAsStream("config.yml"))
			{
				Files.copy(in, this.configFile.toPath());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			this.config = ConfigurationProvider
					.getProvider(YamlConfiguration.class).load(this.configFile);
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		File faviconFile = new File("server-icon.png");
		if (faviconFile.exists())
		{
			try
			{
				BufferedImage image = ImageIO.read(faviconFile);
				if (image.getWidth() == 64 && image.getHeight() == 64)
				{
					ByteArrayOutputStream ouput = new ByteArrayOutputStream();
					ImageIO.write(image, "png", ouput);
					ouput.flush();
					this.favicon = "data:image/png;base64," + DatatypeConverter
							.printBase64Binary(ouput.toByteArray());
				}
				else
				{
					throw new RuntimeException(
							"Your server-icon.png needs to be 64*64!");
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		this.pl.getProxy().getConsole().sendMessage(AdvancedMotd.PREFIX
				+ ChatColor.GREEN + "Configuration was reloaded successfully!");
	}

	public String getPlayerCountMessage()
	{
		return this.config.getString(
				DataPathOfConfig.PlayerCountMessage.getPath(),
				DataPathOfConfig.PlayerCountMessage.getStringValue());
	}

	public void setPlayerCountMessage(String playerCountMessage)
	{
		this.config.set(DataPathOfConfig.PlayerCountMessage.getPath(),
				playerCountMessage);
	}

	public boolean isPlayerCountMessageEnabled()
	{
		return this.config.getBoolean(
				DataPathOfConfig.PlayerCountMessageEnable.getPath(), true);
	}

	public List<String> getFakePlayerList()
	{
		if (this.config.getKeys()
				.contains((DataPathOfConfig.FakePlayerList.getPath())))
			return this.config
					.getStringList(DataPathOfConfig.FakePlayerList.getPath());
		return DataPathOfConfig.FakePlayerList.getListStringValue();
	}

	public boolean isFakePlayerListEnable()
	{
		return this.config.getBoolean(
				DataPathOfConfig.FakePlayerListEnable.getPath(), true);
	}

	public boolean allowChangeMotd()
	{
		return this.config.getBoolean(DataPathOfConfig.ChangeMotd.getPath(),
				DataPathOfConfig.ChangeMotd.getBooleanValue());
	}

	public String getMotdLine1()
	{
		return this.config.getString(DataPathOfConfig.MotdLine1.getPath(),
				DataPathOfConfig.MotdLine1.getStringValue());
	}

	public String getMotdLine2()
	{
		return this.config.getString(DataPathOfConfig.MotdLine2.getPath(),
				DataPathOfConfig.MotdLine2.getStringValue());
	}

	public String getFavicon()
	{
		return this.favicon;
	}
}
