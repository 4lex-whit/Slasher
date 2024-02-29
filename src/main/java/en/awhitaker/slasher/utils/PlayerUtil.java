package en.awhitaker.slasher.utils;

import java.util.UUID;

import en.awhitaker.slasher.models.Game;

public class PlayerUtil {
	/**
	 * Checks if the specified player is in the Slasher game.
	 * @param uuid the {@link java.util.UUID UUID} of the player
	 * @return {@link true} if the player is playing<br/>
	 *         {@code false} otherwise
	 */
	public boolean isPlayerPlaying(UUID uuid) {
		if (Game.getPlayerIds().contains(uuid))
			return true;
		return false;
	}
}
