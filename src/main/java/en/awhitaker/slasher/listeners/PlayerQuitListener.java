package en.awhitaker.slasher.listeners;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import en.awhitaker.slasher.models.Game;
import en.awhitaker.slasher.utils.PlayerUtil;

public class PlayerQuitListener implements Listener {
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		
		// check if player is playing
		if (PlayerUtil.isPlayerPlaying(uuid))
			Game.removePlayer(uuid);
	}
}
