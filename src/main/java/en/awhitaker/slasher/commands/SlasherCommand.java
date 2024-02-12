package en.awhitaker.slasher.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import en.awhitaker.slasher.Slasher;

public class SlasherCommand implements CommandExecutor, TabCompleter {
	private Slasher plugin;
	
	public SlasherCommand(Slasher plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// check sender
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command.");
			return true;
		}
		
		Player player = (Player) sender;
		
		// check args
		switch (args.length) {
		case 1:
			// check arg 1
			switch (args[0].toLowerCase()) {
			case "join":
				return true;
			case "leave":
				return true;
			case "start":
				return true;
			case "stop":
				return true;
			}
			
			break;
		case 2:
			// check arg 1
			if (args[0].toLowerCase().matches("setspawn")) {
				// check arg 2 & set path
				String path = "";
				
				if (args[1].equalsIgnoreCase("hub"))
					path = "hub.";
				else if (List.of("slasher", "survivor1", "survivor2", "survivor3", "survivor4", "survivor5").contains(args[1].toLowerCase()))
					path = String.format("arena.%s.", args[1].toLowerCase());
				else
					break;
				
				// save location data to arena-data.yml
				FileConfiguration arenaData = plugin.arenaDataManager.getConfig();
				Location location = player.getLocation();
				
				arenaData.set(path + "world", location.getWorld().getUID().toString());
				arenaData.set(path + "x", location.getX());
				arenaData.set(path + "y", location.getY());
				arenaData.set(path + "z", location.getZ());
				arenaData.set(path + "pitch", location.getPitch());
				arenaData.set(path + "yaw", location.getYaw());
				
				plugin.arenaDataManager.saveConfig();
				
				// send message
				player.sendMessage(String.format("Successfully set spawn for %s.", args[1].toLowerCase()));
				
				return true;
			}
			
			break;
		}
		
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> options = new ArrayList<String>();
		
		// check args
		switch (args.length) {
		case 1:
			for (String option : List.of("join", "leave", "start", "stop", "setspawn"))
				// check arg 1
				if (option.startsWith(args[0].toLowerCase()))
					options.add(option);
			
			break;
		case 2:
			// check arg 1
			if (args[0].toLowerCase().matches("setspawn")) {
				for (String option : List.of("hub", "slasher", "survivor1", "survivor2", "survivor3", "survivor4", "survivor5"))
					// check arg 2
					if (option.startsWith(args[1].toLowerCase()))
						options.add(option);
			}
			
			break;
		}
		
		return options;
	}
}
