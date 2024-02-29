package en.awhitaker.slasher.utils;

import java.util.UUID;

import en.awhitaker.slasher.models.Game;

public class PlayerUtil {
	/**
	 * Checks if the specified player is in the Slasher game.
	 * @param uuid the {@link java.util.UUID UUID} of the player
	 * @return {@code true} if the player is playing<br/>
	 *         {@code false} otherwise
	 */
	public boolean isPlayerPlaying(UUID uuid) {
		if (Game.getPlayerIds().contains(uuid))
			return true;
		return false;
	}
	
	/**
	 * Checks if the specified player is the slasher.
	 * @param uuid the {@link java.util.UUID UUID} of the player
	 * @return {@code true} if the player is the slasher<br/>
	 *         {@code false} otherwise
	 */
	public boolean isPlayerSlasher(UUID uuid) {
		if (uuid.equals(Game.getSlasherId()))
			return true;
		return false;
	}
	
	/**
	 * Checks if the specified player is a survivor.
	 * @param uuid the {@link java.util.UUID UUID} of the player
	 * @return {@code true} if the player is a survivor<br/>
	 *         {@code false} otherwise
	 */
	public boolean isPlayerSurvivor(UUID uuid) {
		if (Game.getSurvivorIds().contains(uuid))
			return true;
		return false;
	}
}
