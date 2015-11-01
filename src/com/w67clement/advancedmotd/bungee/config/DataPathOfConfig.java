package com.w67clement.advancedmotd.bungee.config;

import java.util.List;

import com.w67clement.advancedmotd.bungee.AdvancedMotdUtils;

public enum DataPathOfConfig
{
	ChangeMotd("ChangeMotd", false),
	ConfigVersion("ConfigVersion", 1),
	FakePlayerList("FakePlayerList", null),
	FakePlayerListEnable("FakePlayerListEnable", true),
	MotdLine1("MotdLine1", "&aHello! Welcome on &2%server%&a!"),
	MotdLine2("MotdLine2", "&aGood Game!"),
	PlayerCountMessage(
						"PlayerCountMessage",
						"&7>>> &c%online%&7/&c%max_players%"),
	PlayerCountMessageEnable("PlayerCountMessageEnable", true);

	private String path;
	private Object value;

	private DataPathOfConfig(String path, Object value) {
		this.path = path;
		this.value = value;
	}

	public String getPath()
	{
		return this.path;
	}

	public int getIntValue()
	{
		return (int) this.value;
	}

	public boolean getBooleanValue()
	{
		return (boolean) this.value;
	}

	public String getStringValue()
	{
		return (String) this.value;
	}

	public List<String> getListStringValue()
	{
		if (this.equals(FakePlayerList))
		{
			return AdvancedMotdUtils.generateDefaultFakePlayerList();
		}
		else
		{
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) this.value;
			return list;
		}
	}
}
