package en.awhitaker.slasher.filemanagers;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

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
		
		// write default arena-data file
		InputStream stream = plugin.getResource("arena-data.yml");
		
		// check stream
		if (stream == null)
			return;
		
		YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(stream));
		arenaDataConfig.setDefaults(defaultConfig);
	}
}
