package en.awhitaker.slasher.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import en.awhitaker.slasher.Slasher;

public class PlayerMoveListener implements Listener {
	private Slasher plugin;
	public PlayerMoveListener(Slasher plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		
	}
}
