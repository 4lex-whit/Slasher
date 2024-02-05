package en.awhitaker.slasher.filemanagers;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import en.awhitaker.slasher.Slasher;

public class ArenaDataManager {
	private Slasher plugin;
	
	private FileConfiguration arenaDataConfig = null;
	private File configFile = null;
	
	public ArenaDataManager(Slasher plugin) {
		this.plugin = plugin;
	}
	
	public void reload() {
		
	}
}
