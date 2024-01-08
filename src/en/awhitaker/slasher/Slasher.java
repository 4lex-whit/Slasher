package en.awhitaker.slasher;

import org.bukkit.plugin.java.JavaPlugin;

import en.awhitaker.slasher.commands.SlasherCommand;

public class Slasher extends JavaPlugin {
	@Override
	public void onEnable() {
		this.getCommand("slasher").setExecutor(new SlasherCommand());
	}
}
