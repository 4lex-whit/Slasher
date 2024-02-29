package en.awhitaker.slasher.listeners;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import en.awhitaker.slasher.utils.PlayerUtil;

public class PlayerMoveListener implements Listener {
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		
		// check if player is frozen
		if (PlayerUtil.isPlayerFrozen(uuid)) {
			Location from = event.getFrom();
			Location to = event.getTo();
			
			// check if position has changed
			if ((from.getX() != to.getX()) || (from.getY() != to.getY()) || (from.getZ() != to.getZ()))
				event.setCancelled(true);
		}
	}
}
