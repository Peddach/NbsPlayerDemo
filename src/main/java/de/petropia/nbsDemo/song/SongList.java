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
	
	//Checks if directory is present and load all .nbs files
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
	
	//Parse every nbs file to a song object and adds it to a list
	private void loadSongsFromNbs() {
		for(File file : nbsFiles) {
			songs.add(NBSDecoder.parse(file));
		}
	}
	
	//add every .nbs file to  the list nbsFiles if it ends with .nbs
	private void loadNbsFiles() {
		for(File file : directory.listFiles()) {
			if(!file.getName().endsWith(".nbs")) {
				continue;
			}
			nbsFiles.add(file);
		}
	}
	
	//checks if the where the songs are exists
	private boolean checkIfDirectoryExist() {
		if(!directory.exists()) {
			return false;
		}
		if(!directory.isDirectory()) {
			return false;
		}
		return true;
	}
	
	//create the directiry. Unpridictable!
	private void createDirectory() {
		directory.mkdirs();
		NbsDemo.getInstance().getLogger().info("Erstelle Ordner: " + directory.getAbsolutePath());
	}

	public List<Song> getSongs() {
		return songs;
	}
	
	public List<File> getNbsFiles(){
		return nbsFiles;
	}
}
