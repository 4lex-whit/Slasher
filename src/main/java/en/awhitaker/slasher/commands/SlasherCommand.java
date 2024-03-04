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
import net.md_5.bungee.api.ChatColor;

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
			case "help":
				String msg = """
						&e---------- &rSlasher Help &e----------
						&6/slasher join&r: Adds you to the game
						&6/slasher leave&r: Removes you from the game""";
				
				// check perms
				if (player.hasPermission("slasher.admin"))
					msg += """
							
							&6/slasher start&r: Starts the game
							&6/slasher stop&r: Stops the game
							&6/slasher setspawn &e<&6hub&e|&6lobby&e|&6slasher&e|&6survivor1&e|&6survivor2&e|&6survivor3&e|&6survivor4&e|&6survivor5&e>&r: Sets a spawn to your current location""";
				
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
				
				return true;
			case "join":
				return true;
			case "leave":
				return true;
			case "start":
				// check perms
				if (!player.hasPermission("slasher.admin"))
					break;
				
				return true;
			case "stop":
				// check perms
				if (!player.hasPermission("slasher.admin"))
					break;
				
				return true;
			}
			
			break;
		case 2:
			// check arg 1
			if (args[0].equalsIgnoreCase("setspawn")) {
				// check perms
				if (!player.hasPermission("slasher.admin"))
					break;
				
				// check arg 2 & set path
				String path = "";
				
				if (args[1].equalsIgnoreCase("hub"))
					path = "hub.";
				else if (args[1].equalsIgnoreCase("lobby"))
					path = "lobby.";
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
				player.sendMessage(String.format("[Slasher] Successfully set spawn for %s.", args[1].toLowerCase()));
				
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
			for (String option : List.of("help", "join", "leave"))
				// check arg 1
				if (option.startsWith(args[0].toLowerCase()))
					options.add(option);
			
			// check perms
			if (sender.hasPermission("slasher.admin"))
				for (String option : List.of("start", "stop", "setspawn"))
					// check arg 1
					if (option.startsWith(args[0].toLowerCase()))
						options.add(option);
			
			break;
		case 2:
			// check arg 1
			if (args[0].equalsIgnoreCase("setspawn"))
				// check perms
				if (sender.hasPermission("slasher.admin"))
					for (String option : List.of("hub", "lobby", "slasher", "survivor1", "survivor2", "survivor3", "survivor4", "survivor5"))
						// check arg 2
						if (option.startsWith(args[1].toLowerCase()))
							options.add(option);
			
			break;
		}
		
		return options;
	}
}
