package en.awhitaker.slasher.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SlasherCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// check sender
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command.");
			return true;
		}
		
		Player player = (Player) sender;
		
		// check args
		if (args.length == 1) {
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
		}
		
		return false;
	}
}
