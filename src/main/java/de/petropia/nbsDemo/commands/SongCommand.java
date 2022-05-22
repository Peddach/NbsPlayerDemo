package de.petropia.nbsDemo.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class SongCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		//Check if sender is player cause only players can hear a song
		if (!(sender instanceof Player)) {
			final Component senderNotPlayerErrorMsg = Component.text("Dieser Command kann nur durch Spieler ausgeführt werden!").color(NamedTextColor.RED);
			sender.sendMessage(senderNotPlayerErrorMsg);
			return true;
		}
		
		Player player = (Player) sender;
		
		//Permission check and error message if fails
		if(!player.hasPermission("nbsdemo.song")) {
			final Component noPermsMsg = Component.text("Du hast leider keine Berechtigung diesen Command zu nutzen!").color(NamedTextColor.RED);
			player.sendMessage(noPermsMsg);
			return true;
		}
		
		player.sendMessage(Component.text("Folgende Songs sind verfügbar: ").color(NamedTextColor.GOLD));
		
		
		
		return false;
	}

}
