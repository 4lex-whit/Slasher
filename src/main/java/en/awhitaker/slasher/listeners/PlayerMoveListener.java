package en.awhitaker.slasher.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import en.awhitaker.slasher.models.Game;

public class PlayerMoveListener implements Listener {
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		// check if player is frozen
		if ((player.getUniqueId().equals(Game.getSlasherId()) && Game.isSlasherFrozen()) || (Game.getSurvivorIds().contains(player.getUniqueId()) && Game.isSurvivorsFrozen())) {
			Location from = event.getFrom();
			Location to = event.getTo();
			
			// check if position has changed
			if ((from.getX() != to.getX()) || (from.getY() != to.getY()) || (from.getZ() != to.getZ()))
				event.setCancelled(true);
		}
	}
}
