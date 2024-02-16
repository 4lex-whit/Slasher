package en.awhitaker.slasher.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import en.awhitaker.slasher.Slasher;
import en.awhitaker.slasher.models.Game;

public class PlayerMoveListener implements Listener {
	private Slasher plugin;
	public PlayerMoveListener(Slasher plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		// check player
		if ((player.getUniqueId().equals(Game.getSlasherId()) && Game.isSlasherFrozen()) || (Game.getSurvivorIds().contains(player.getUniqueId()) && Game.isSurvivorsFrozen())) {
			event.setCancelled(true);
		}
	}
}
