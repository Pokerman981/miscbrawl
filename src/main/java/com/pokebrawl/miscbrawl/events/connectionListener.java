package com.pokebrawl.miscbrawl.events;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.serializer.TextParseException;
import org.spongepowered.api.text.serializer.TextSerializer;
import org.spongepowered.api.text.serializer.TextSerializers;

import com.pokebrawl.miscbrawl.Main;

public class connectionListener {
	
	public Main plugin;

	public connectionListener(Main pluginInstance) {
		this.plugin = pluginInstance;
	}

	@Listener
	public void onJoin(ClientConnectionEvent.Join e){
		if (e.getTargetEntity().hasPermission("onJoin.bcast")){
			String message = plugin.config().getNode("messages", "join-message").getString().replace("%player%", e.getTargetEntity().getName());
			MessageChannel.TO_ALL.send(Text.of(TextSerializers.FORMATTING_CODE.deserialize(TextSerializers.FORMATTING_CODE.serialize(Text.of(message)))));
		}
		
	}
	
	
}
