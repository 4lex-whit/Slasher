package en.awhitaker.slasher;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import en.awhitaker.slasher.filemanagers.ArenaDataManager;
import en.awhitaker.slasher.listeners.PlayerMoveListener;

public class Slasher extends JavaPlugin {
	public ArenaDataManager arenaDataManager;
	
	@Override
	public void onEnable() {
		arenaDataManager = new ArenaDataManager(this);
		
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
	}
}
