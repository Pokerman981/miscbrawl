package com.pokebrawl.miscbrawl.commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class StaffchatCommand implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String message = args.getOne("message").get().toString();

        Sponge.getServer().getConsole().sendMessage(Text.of(TextColors.AQUA, "{", TextColors.WHITE, src.getName(), TextColors.AQUA, "} ", TextColors.AQUA, message));

        for (Player player : Sponge.getServer().getOnlinePlayers()) {
            if (player.hasPermission("miscbrawl.adminchat")) {
                player.sendMessage(Text.of(TextColors.AQUA, "{", TextColors.WHITE, src.getName(), TextColors.AQUA, "} " + message));
            }
        }
		return CommandResult.success();
	}
	
}
