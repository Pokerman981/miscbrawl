package com.pokebrawl.miscbrawl;

import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;
import com.pokebrawl.miscbrawl.events.connectionListener;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Plugin(id = "miscbrawl", name = "miscbrawl", version = "1.0")

public class Main {


	@Inject
	@DefaultConfig(sharedRoot = false)
	private Path defaultConfig;

	@Inject
	@DefaultConfig(sharedRoot = false)
	public ConfigurationLoader<CommentedConfigurationNode> loader;

	@Inject
	@ConfigDir(sharedRoot = false)
	private Path ConfigDir;

	public static CommentedConfigurationNode config;

	public static CommentedConfigurationNode config() {
		return config;
	}

	public void save() throws IOException {
		loader.save(config);
	}

	@Inject
	private Logger logger;

	public Logger getLogger() {
		return logger;
	}
	
	public void RegisterListeners(GameInitializationEvent e){
		Sponge.getEventManager().registerListeners(this, new connectionListener(this));
	}
	
	public void registerNodes() throws IOException{
			config = loader.load();
			if (!defaultConfig.toFile().exists()) {
				CommentedConfigurationNode root = config.getNode("messages");
				root.getNode("join-message").setValue("&1>>> %player% Joined The Server &1<<<").setComment("%player% replaces with players name. Only works for people with perm 'onJoin.bcast'");
				save();
			}
	}
	
	public void registerCommand() {
		CommandSpec reloadCommand = CommandSpec.builder()
				.description(Text.of("Reload the config"))
				.permission("miscbrawl.admin").executor(new CommandExecutor() {
					@Override
					public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
						try {
							config = loader.load();
							sendMessage(src, "&aReloaded!");} catch (IOException e) {e.printStackTrace();}
						return CommandResult.success();
					}
				})
				.build();
	}

	public static String color(String string) {
		return org.spongepowered.api.text.serializer.TextSerializers.FORMATTING_CODE
				.serialize(org.spongepowered.api.text.Text.of(string));
	}

	public static void sendMessage(CommandSource sender, String message) {
		if (sender == null)
			return;
		sender.sendMessage(
				org.spongepowered.api.text.serializer.TextSerializers.FORMATTING_CODE.deserialize(color(message)));
	};

	
}
