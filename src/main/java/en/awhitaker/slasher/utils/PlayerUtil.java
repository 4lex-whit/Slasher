package en.awhitaker.slasher.utils;

import java.util.UUID;

import en.awhitaker.slasher.models.Game;

public class PlayerUtil {
	/**
	 * Checks if the specified player is in the Slasher game.
	 * @param uuid the {@link java.util.UUID UUID} of the player
	 * @return {@code true}:
	 *         <ul>
	 *           <li>if the player is playing</li>
	 *         </ul>
	 *         {@code false}:
	 *         <ul>
	 *           <li>otherwise</li>
	 *         </ul>
	 */
	public static boolean isPlayerPlaying(UUID uuid) {
		if (Game.getPlayerIds().contains(uuid))
			return true;
		return false;
	}
	
	/**
	 * Checks if the specified player is the slasher.
	 * @param uuid the {@link java.util.UUID UUID} of the player
	 * @return {@code true}:
	 *         <ul>
	 *           <li>if the player is the slasher</li>
	 *         </ul>
	 *         {@code false}:
	 *         <ul>
	 *           <li>otherwise</li>
	 *         </ul>
	 */
	public static boolean isPlayerSlasher(UUID uuid) {
		if (uuid.equals(Game.getSlasherId()))
			return true;
		return false;
	}
	
	/**
	 * Checks if the specified player is a survivor.
	 * @param uuid the {@link java.util.UUID UUID} of the player
	 * @return {@code true}:
	 *         <ul>
	 *           <li>if the player is a survivor</li>
	 *         </ul>
	 *         {@code false}:
	 *         <ul>
	 *           <li>otherwise</li>
	 *         </ul>
	 */
	public static boolean isPlayerSurvivor(UUID uuid) {
		if (Game.getSurvivorIds().contains(uuid))
			return true;
		return false;
	}
	
	/**
	 * Checks if the specified player is frozen.
	 * @param uuid the {@link java.util.UUID UUID} of the player
	 * @return {@code true}:
	 *         <ul>
	 *           <li>if the player is the slasher and the slasher is frozen</li>
	 *           <li>if the player is a survivor and the survivors are frozen</li>
	 *         </ul>
	 *         {@code false}:
	 *         <ul>
	 *           <li>otherwise</li>
	 *         </ul>
	 */
	public static boolean isPlayerFrozen(UUID uuid) {
		if ((isPlayerSlasher(uuid) && Game.isSlasherFrozen()) || (isPlayerSurvivor(uuid) && Game.isSurvivorsFrozen()))
			return true;
		return false;
	}
}
