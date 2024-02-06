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
			
			return false;
		case 3:
			// check arg 1
			if (args[0].toLowerCase().matches("setspawn")) {
				
			}
			
			return false;
		}
		
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> options = new ArrayList<String>();
		
		// check args
		switch (args.length) {
		case 1:
			for (String option : List.of("join", "leave", "start", "stop"))
				if (option.toLowerCase().startsWith(args[0]))
					options.add(option);
		case 2:
			for (String option : List.of("setspawn"))
				if (option.toLowerCase().startsWith(args[0]))
					options.add(option);
		}
		
		return options;
	}
}
