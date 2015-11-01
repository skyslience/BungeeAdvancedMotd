package com.w67clement.advancedmotd.bungee.motd;

import java.net.InetSocketAddress;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.netty.HandlerBoss;
import net.md_5.bungee.netty.PipelineUtils;
import net.md_5.bungee.protocol.KickStringWriter;
import net.md_5.bungee.protocol.LegacyDecoder;
import net.md_5.bungee.protocol.MinecraftDecoder;
import net.md_5.bungee.protocol.MinecraftEncoder;
import net.md_5.bungee.protocol.Protocol;

public class ConnectionReplacement extends ChannelInitializer<Channel> {
	
	
	@SuppressWarnings("unused")
	private static final String NETTY_LISTENER_NAME = "packet-interception";
	

	@SuppressWarnings("deprecation")
	protected void initChannel(Channel ch) throws Exception {
		BungeeCord
				.getInstance()
				.getConnectionThrottle()
				.throttle(((InetSocketAddress) ch.remoteAddress()).getAddress());

		PipelineUtils.BASE.initChannel(ch);
		ch.pipeline().addBefore("frame-decoder", "legacy-decoder",
				new LegacyDecoder());
		ch.pipeline().addAfter(
				"frame-decoder",
				"packet-decoder",
				new MinecraftDecoder(Protocol.HANDSHAKE, true, ProxyServer
						.getInstance().getProtocolVersion()));
		ch.pipeline().addAfter(
				"frame-prepender",
				"packet-encoder",
				new MinecraftEncoder(Protocol.HANDSHAKE, true, ProxyServer
						.getInstance().getProtocolVersion()));
		ch.pipeline().addAfter("packet-decoder", "packet-interception",
				new NettyDecoder());
		ch.pipeline().addBefore("frame-prepender", "legacy-kick",
				new KickStringWriter());
		((HandlerBoss) ch.pipeline().get(HandlerBoss.class))
				.setHandler(new InitialHandler(ProxyServer.getInstance(),
						(ListenerInfo) ch.attr(PipelineUtils.LISTENER).get()));
	}
}
