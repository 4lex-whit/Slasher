package en.awhitaker.slasher.filemanagers;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import en.awhitaker.slasher.Slasher;

public class ArenaDataManager {
	private Slasher plugin;
	
	private FileConfiguration arenaDataConfig = null;
	private File configFile = null;
	
	public ArenaDataManager(Slasher plugin) {
		this.plugin = plugin;
	}
	
	public void reload() {
		// check & set configFile and arenaDataConfig
		if (configFile == null)
			configFile = new File(plugin.getDataFolder(), "arena-data.yml");
		
		arenaDataConfig = YamlConfiguration.loadConfiguration(configFile);
	}
}
