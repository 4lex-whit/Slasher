package en.awhitaker.slasher;

import org.bukkit.plugin.java.JavaPlugin;

import en.awhitaker.slasher.commands.SlasherCommand;
import en.awhitaker.slasher.filemanagers.ArenaDataManager;

public class Slasher extends JavaPlugin {
	public ArenaDataManager arenaDataManager;
	
	@Override
	public void onEnable() {
		arenaDataManager = new ArenaDataManager(this);
		
		this.getCommand("slasher").setExecutor(new SlasherCommand(this));
	}
}
