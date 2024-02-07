package en.awhitaker.slasher.filemanagers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import en.awhitaker.slasher.Slasher;

public class ArenaDataManager {
	private Slasher plugin;
	
	private FileConfiguration arenaDataConfig = null;
	private File arenaDataFile = null;
	
	public ArenaDataManager(Slasher plugin) {
		this.plugin = plugin;
		saveDefaultConfig();
	}
	
	/**
	 * Creates the arena-data.yml file if it does not exist and attempts to fix any syntax errors.
	 */
	public void reloadConfig() {
		// check & set arenaDataFile and arenaDataConfig
		if (arenaDataFile == null)
			arenaDataFile = new File(plugin.getDataFolder(), "arena-data.yml");
		
		arenaDataConfig = YamlConfiguration.loadConfiguration(arenaDataFile);
		
		// write default arena-data file
		InputStream stream = plugin.getResource("arena-data.yml");
		
		// check stream
		if (stream == null)
			return;
		
		YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(stream));
		arenaDataConfig.setDefaults(defaultConfig);
	}
	
	/**
	 * Checks if the arena data config exists and creates it if not.
	 * @return the arena data config
	 */
	public FileConfiguration getConfig() {
		// check dataConfig
		if (arenaDataConfig == null)
			reloadConfig();
		
		return arenaDataConfig;
	}
	
	/**
	 * Saves the arena-data.yml file with new data. All comments not on the first line will be removed.<br/>
	 * If the arena data config or config file are null, the procedure will return.<br/>
	 * If the file cannot be saved, an error will be output.
	 */
	public void saveConfig() {
		// check arenaDataConfig & arenaDataFile
		if (arenaDataConfig == null || arenaDataFile == null)
			return;
		
		// save config
		try {
			getConfig().save(arenaDataFile);
		} catch (IOException exception) {
			plugin.getLogger().log(Level.SEVERE, String.format("Failed to save %s", arenaDataFile.toString()));
			exception.printStackTrace();
		}
	}
	
	/**
	 * Initialises the arena-data.yml file with default values.
	 */
	public void saveDefaultConfig() {
		// check arenaDataFile
		if (arenaDataFile == null)
			arenaDataFile = new File(plugin.getDataFolder(), "arena-data.yml");
		
		// save default arena-data file
		if (!arenaDataFile.exists())
			plugin.saveResource("arena-data.yml", false);
	}
}
