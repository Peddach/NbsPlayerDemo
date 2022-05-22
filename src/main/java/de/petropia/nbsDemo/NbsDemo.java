package de.petropia.nbsDemo;

import org.bukkit.plugin.java.JavaPlugin;

public class NbsDemo extends JavaPlugin {
	
	private static JavaPlugin instance;
	
	@Override
	public void onEnable() {
		instance = this;
	}
	
	public static JavaPlugin getInstance() {
		return instance;
	}
}
