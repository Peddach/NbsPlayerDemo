package de.petropia.nbsDemo.commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;

import de.petropia.nbsDemo.NbsDemo;
import de.petropia.nbsDemo.song.SongList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class SongCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		//Check if sender is player cause only players can hear a song
		if (!(sender instanceof Player)) {
			final Component senderNotPlayerErrorMsg = Component.text("Dieser Command kann nur durch Spieler ausgeführt werden!").color(NamedTextColor.RED);
			sender.sendMessage(senderNotPlayerErrorMsg);
			return true;
		}
		
		final Player player = (Player) sender;
		final SongList songlist = NbsDemo.getInstance().getSongList();
		
		//Permission check and error message if fails
		if(!player.hasPermission("nbsdemo.song")) {
			final Component noPermsMsg = Component.text("Du hast leider keine Berechtigung diesen Command zu nutzen!").color(NamedTextColor.RED);
			player.sendMessage(noPermsMsg);
			return true;
		}
		
		//Check if arguments are present and if the first is play, than the song is played from the 2. argument
		if(args.length != 0) {
			if(args.length == 2 && args[0].equalsIgnoreCase("play")) {
				playSong(Integer.parseInt(args[1]), player);
			}
			return false;
		}
		
		player.sendMessage(Component.text("Folgende Songs sind verfügbar: ").color(NamedTextColor.GOLD));
		
		//Check if Songs are present
		if(songlist.getSongs().size() == 0) {
			player.sendMessage(Component.text("Keine Songs verfügbar").color(NamedTextColor.RED).decorate(TextDecoration.UNDERLINED));
			return true;
		}
		
		//loop over every song and sends it title as message to player with click event and when clicked, it play the song
		for(int i = 0; i < songlist.getSongs().size(); i++) {
			Song song = songlist.getSongs().get(i);
			Component message = Component.text()
			.append(Component.text("[").color(NamedTextColor.GRAY))
			.append(Component.text(i).color(NamedTextColor.GOLD))
			.append(Component.text("] ").color(NamedTextColor.GRAY))
			.append(Component.text(getSongName(song, i)).color(NamedTextColor.GRAY).decorate(TextDecoration.ITALIC))
			.hoverEvent(HoverEvent.showText(Component.text("Klicke um " + getSongName(song, i) + " zu hören")))
			.clickEvent(ClickEvent.runCommand("/songs play " + i))
			.build();
			player.sendMessage(message);
		}
		
		return false;
	}
	
	//return the given name in the .nbs file or, if it doesnt exist, the file name
	private String getSongName(Song song, int index) {
		String name = song.getTitle();
		if(!name.isEmpty() || !name.isBlank()) {
			return name;
		}
		File file = NbsDemo.getInstance().getSongList().getNbsFiles().get(index);
		return file.getName().replaceAll("^.*?(([^/\\\\\\.]+))\\.[^\\.]+$", "$1");
	}
	
	//plays the song for the player. Songid is equal to the index in SongList#getSongs()
	private void playSong(int songid, Player player) {
		Song song = NbsDemo.getInstance().getSongList().getSongs().get(songid);
		RadioSongPlayer radioSongPlayer = new RadioSongPlayer(song);
		radioSongPlayer.addPlayer(player);
		radioSongPlayer.setPlaying(true);
	}

}
