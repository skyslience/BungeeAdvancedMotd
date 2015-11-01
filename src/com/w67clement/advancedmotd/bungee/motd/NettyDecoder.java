package com.w67clement.advancedmotd.bungee.motd;

import java.util.List;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.w67clement.advancedmotd.bungee.AdvancedMotd;
import com.w67clement.advancedmotd.bungee.AdvancedMotdUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.protocol.PacketWrapper;
import net.md_5.bungee.protocol.packet.Handshake;
import net.md_5.bungee.protocol.packet.PingPacket;
import net.md_5.bungee.protocol.packet.StatusRequest;
import net.md_5.bungee.protocol.packet.StatusResponse;

public class NettyDecoder extends MessageToMessageDecoder<PacketWrapper>
{

	private static BungeeCord bungeecord = BungeeCord.getInstance();
	private boolean respondPing;

	@Override
	protected void decode(ChannelHandlerContext ctx, PacketWrapper packet,
			List<Object> out) throws Exception
	{

		if ((packet == null) || (packet.packet == null))
		{
			out.add(packet);
			return;
		}

		if ((packet.packet instanceof Handshake))
		{}

		if ((packet.packet instanceof PingPacket))
		{
			if (this.respondPing)
			{
				ctx.pipeline().writeAndFlush(
						new PingPacket(((PingPacket) packet.packet).getTime()));
				ctx.close();
				return;
			}

			ctx.pipeline()
					.writeAndFlush(new StatusResponse(buildResponseJSON()));
		}
		else if ((packet.packet instanceof StatusRequest))
		{
			ctx.pipeline()
					.writeAndFlush(new StatusResponse(buildResponseJSON()));

		}
		else
		{
			out.add(packet);
		}
	}

	private String buildResponseJSON()
	{
		JsonObject version = new JsonObject();
		version.addProperty("name", AdvancedMotdUtils
				.replaceVariable(AdvancedMotd.config.getPlayerCountMessage()));
		version.addProperty("protocol", Integer.valueOf(1));
		JsonArray playerArray = new JsonArray();
		for (String playerName : AdvancedMotd.config.getFakePlayerList())
		{
			JsonObject playerObject = new JsonObject();
			playerObject.addProperty("name",
					AdvancedMotdUtils.replaceVariable(playerName));
			playerObject.addProperty("id", UUID.randomUUID().toString());
			playerArray.add(playerObject);
		}

		JsonObject countData = new JsonObject();
		countData.addProperty("max", bungeecord.config.getPlayerLimit());
		countData.addProperty("online", bungeecord.getOnlineCount());
		// Fake player list
		if (AdvancedMotd.config.isFakePlayerListEnable())
			countData.add("sample", playerArray);
		JsonObject jsonObject = new JsonObject();
		// Version (PlayerCount)
		if (AdvancedMotd.config.isPlayerCountMessageEnabled())
		{
			jsonObject.add("version", version);
		}
		else
		{
			JsonObject defaultVersion = new JsonObject();
			defaultVersion.addProperty("name", bungeecord.getVersion());
			defaultVersion.addProperty("protocol",
					bungeecord.getProtocolVersion());
			jsonObject.add("version", defaultVersion);
		}
		jsonObject.add("players", countData);
		if (AdvancedMotd.config.allowChangeMotd())
		{
			jsonObject.addProperty("description",
					AdvancedMotdUtils.replaceVariable(
							AdvancedMotd.config.getMotdLine1() + "\n"
									+ AdvancedMotd.config.getMotdLine2()));
		}
		else
		{
			String motd = "";
			for (ListenerInfo info : bungeecord.config.getListeners())
			{
				if (info.getMotd() != null) motd = info.getMotd();
			}
			jsonObject.addProperty("description", motd);
		}

		String favicon = AdvancedMotd.config.getFavicon();

		if (favicon != null)
		{
			jsonObject.addProperty("favicon", favicon);
		}

		return jsonObject.toString();
	}
}
