package en.awhitaker.slasher.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import en.awhitaker.slasher.Slasher;
import en.awhitaker.slasher.utils.PlayerUtil;

public class Game {
	private static Slasher plugin = JavaPlugin.getPlugin(Slasher.class);
	
	private static boolean running;
	private static List<UUID> playerIds = new ArrayList<UUID>();
	private static UUID slasherId;
	private static List<UUID> survivorIds = new ArrayList<UUID>();
	private static boolean slasherFrozen;
	private static boolean survivorsFrozen;
	
	public static boolean isRunning() {
		return running;
	}
	
	public static List<UUID> getPlayerIds() {
		return new ArrayList<UUID>(playerIds);
	}
	
	public static UUID getSlasherId() {
		return slasherId;
	}
	
	public static List<UUID> getSurvivorIds() {
		return new ArrayList<UUID>(survivorIds);
	}
	
	public static boolean isSlasherFrozen() {
		return slasherFrozen;
	}
	
	public static boolean isSurvivorsFrozen() {
		return survivorsFrozen;
	}
	
	/**
	 * Gets a specified spawn from the "arena-data.yml" file.
	 * @param path the string to the desired spawn data in the "arena-data.yml" file
	 * @return the {@link org.bukkit.Location Location} of the spawn
	 */
	public static Location getSpawn(String path) {
		FileConfiguration arenaData = plugin.arenaDataManager.getConfig();
		
		return new Location(
				Bukkit.getWorld(UUID.fromString(arenaData.getString(path + ".world"))),
				arenaData.getDouble(path + ".x"),
				arenaData.getDouble(path + ".y"),
				arenaData.getDouble(path + ".z"),
				(float) arenaData.getDouble(path + ".yaw"),
				(float) arenaData.getDouble(path + ".pitch")
		);
	}
	
	/**
	 * Resets a specified player to an 'initial' state.
	 * <ul>
	 *   <li>Sets gamemode to survival</li>
	 *   <li>Clears inventory</li>
	 *   <li>Clears potion effects</li>
	 *   <li>Sets health to 20 (max)</li>
	 *   <li>Sets saturation to 0</li>
	 *   <li>Sets food level to 20 (max)</li>
	 * </ul>
	 * @param uuid the {@link java.util.UUID UUID} of the player
	 */
	public static void resetPlayer(UUID uuid) {
		Player player = Bukkit.getPlayer(uuid);
		
		// set game mode
		player.setGameMode(GameMode.SURVIVAL);
		
		// clear inventory and effects
		player.getInventory().clear();
		player.getActivePotionEffects().clear();
		
		// set health & hunger
		player.setHealth(20);
		player.setSaturation(0);
		player.setFoodLevel(20);
	}
	
	/**
	 * Adds a specified player to the game.<br/>
	 * The player will be teleported to the lobby and sent a message to say they have joined the game.
	 * @param uuid the {@link java.util.UUID UUID} of the player
	 */
	public static void addPlayer(UUID uuid) {
		Player player = Bukkit.getPlayer(uuid);
		
		// check player
		if (PlayerUtil.isPlayerPlaying(uuid)) {
			player.sendMessage("[Slasher] You are already in the game.");
			return;
		}
		
		// add to list
		playerIds.add(uuid);
		
		// teleport
		player.teleport(getSpawn("lobby"));
		
		// reset
		resetPlayer(uuid);
		
		// send message
		player.sendMessage("[Slasher] You have joined the game.");
	}
	
	/**
	 * Removes a specified player from the game.<br/>
	 * The player will be teleported to the hub and sent a message to say they have left the game.<br/>
	 * If the player is the Slasher or only remaining Survivor, the game will end.
	 * @param uuid the {@link java.util.UUID UUID} of the player
	 */
	public static void removePlayer(UUID uuid) {
		Player player = Bukkit.getPlayer(uuid);
		
		// check player
		if (!PlayerUtil.isPlayerPlaying(uuid)) {
			player.sendMessage("[Slasher] You are not in the game.");
			return;
		}
		
		// remove from list
		playerIds.remove(uuid);
		
		// teleport
		player.teleport(getSpawn("hub"));
		
		// reset
		resetPlayer(uuid);
		
		// send message
		Bukkit.getPlayer(uuid).sendMessage("[Slasher] You have left the game.");
		
		// check if player was slasher/survivor
		if (PlayerUtil.isPlayerSlasher(uuid)) // player was slasher
			end();
		else if (PlayerUtil.isPlayerSurvivor(uuid)) { // player was a survivor
			survivorIds.remove(uuid);
			
			// check for remaining survivors
			if (survivorIds.isEmpty()) // player was only survivor
				end();
		}
	}
	
	public static void start() {
		running = true;
		
		// prepare map
		
		
		// prepare players
		// pick slasher and survivors
		int slasherIndex = new Random().nextInt(playerIds.size());
		slasherId = playerIds.get(slasherIndex);
		survivorIds = playerIds;
		survivorIds.remove(slasherIndex);
		
		// freeze players
		slasherFrozen = true;
		survivorsFrozen = true;
		
		for (UUID uuid : playerIds) {
			// set path
			String path = "arena.";
			
			if (uuid.equals(slasherId))
				path += "slasher";
			else
				path += "survivor" + (survivorIds.indexOf(uuid) + 1);
			
			// teleport
			Bukkit.getPlayer(uuid).teleport(getSpawn(path));
			
			// reset
			resetPlayer(uuid);
		}
		
		// give slasher weapon and effect
		ItemStack weapon = new ItemStack(Material.GOLDEN_AXE, 1);
		ItemMeta meta = weapon.getItemMeta();
		meta.setUnbreakable(true);
		weapon.setItemMeta(meta);
		
		Player slasher = Bukkit.getPlayer(slasherId);
		slasher.getInventory().setItem(0, weapon);
		slasher.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, Integer.MAX_VALUE, false, false, false));
		
		
		// release players
		// release survivors
		survivorsFrozen = false;
		
		// release slasher & remove blindness after 1 minute
		Bukkit.getScheduler().runTaskLater(Slasher.getPlugin(Slasher.class), () -> {
			slasherFrozen = false;
			slasher.getActivePotionEffects().clear();
		}, 1200);
		
		
		// end game after 10 minutes
		Bukkit.getScheduler().runTaskLater(Slasher.getPlugin(Slasher.class), () -> {
			end();
		}, 12000);
	}
	
	public static void end() {
		running = false;
		
		// players
		playerIds.forEach(uuid -> {
			// teleport
			Bukkit.getPlayer(uuid).teleport(getSpawn("lobby"));
			
			// reset
			resetPlayer(uuid);
		});
		
		
		// announce winner
		
	}
}
