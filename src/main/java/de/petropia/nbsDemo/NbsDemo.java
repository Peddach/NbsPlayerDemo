package de.petropia.nbsDemo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.petropia.nbsDemo.commands.SongCommand;
import de.petropia.nbsDemo.song.SongList;

public class NbsDemo extends JavaPlugin {
	
	private static NbsDemo instance;
	private SongList songList;
	
	@Override
	public void onEnable() {
		if(!isNoteBlockApiPresent()) {
			getLogger().warning("Bitte füge das NoteBlockAPI plugin in den Pluginsordner hinzu, damit dieses Plugin ordnungsgemäß funktioniert!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		instance = this;
		songList = new SongList(getDataFolder().toPath().toString() + "/Songs");
		getCommand("songs").setExecutor(new SongCommand());
	}
	
	private boolean isNoteBlockApiPresent() {
		return Bukkit.getServer().getPluginManager().getPlugin("NoteBlockAPI") != null;
	}
	
	public static NbsDemo getInstance() {
		return instance;
	}

	public SongList getSongList() {
		return songList;
	}

	public void setSongList(SongList songList) {
		this.songList = songList;
	}
	
}
