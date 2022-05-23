package de.petropia.nbsDemo.song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;

import de.petropia.nbsDemo.NbsDemo;

public class SongList {
	
	private final File directory;
	private final List<File> nbsFiles = new ArrayList<>();
	private final List<Song> songs = new ArrayList<>();
	
	public SongList(String path) {
		directory = new File(path);
		if(!checkIfDirectoryExist()) {
			createDirectory();
			return;
		}
		loadNbsFiles();
		NbsDemo.getInstance().getLogger().info(nbsFiles.size() + " Dateien wurden geladen");
		loadSongsFromNbs();
	}
	
	private void loadSongsFromNbs() {
		for(File file : nbsFiles) {
			songs.add(NBSDecoder.parse(file));
		}
	}
	
	private void loadNbsFiles() {
		for(File file : directory.listFiles()) {
			if(!file.getName().endsWith(".nbs")) {
				continue;
			}
			nbsFiles.add(file);
		}
	}
	
	private boolean checkIfDirectoryExist() {
		if(!directory.exists()) {
			return false;
		}
		if(!directory.isDirectory()) {
			return false;
		}
		return true;
	}
	
	private void createDirectory() {
		directory.mkdirs();
	}

	public List<Song> getSongs() {
		return songs;
	}
}
