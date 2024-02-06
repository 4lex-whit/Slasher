package en.awhitaker.slasher.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class SlasherCommand implements CommandExecutor, TabCompleter {
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
		case 3:
			// check arg 1
			if (args[0].toLowerCase().matches("setspawn")) {
				// check arg 2
				if (args[1].toLowerCase().matches("slasher")) {
					return true;
				} else if (args[1].toLowerCase().startsWith("survivor")) {
					// check survivor number
					switch (args[1].toLowerCase().substring(8)) {
					case "1":
						return true;
					case "2":
						return true;
					case "3":
						return true;
					case "4":
						return true;
					case "5":
						return true;
					}
				}
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
				if (args[0].toLowerCase().startsWith(option))
					options.add(option);
		case 2:
			for (String option : List.of("slasher", "survivor1", "survivor2", "survivor3", "survivor4", "survivor5"))
				// check arg 1
				if (args[0].toLowerCase().startsWith(option))
					options.add(option);
		}
		
		return options;
	}
}
