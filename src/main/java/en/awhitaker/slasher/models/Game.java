package en.awhitaker.slasher.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import en.awhitaker.slasher.Slasher;

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
		return playerIds;
	}
	
	public static UUID getSlasherId() {
		return slasherId;
	}
	
	public static List<UUID> getSurvivorIds() {
		return survivorIds;
	}
	
	public static boolean isSlasherFrozen() {
		return slasherFrozen;
	}
	
	public static boolean isSurvivorsFrozen() {
		return survivorsFrozen;
	}
	
	public static void addPlayer(UUID uuid) {
		playerIds.add(uuid);
		
		Bukkit.getPlayer(uuid).sendMessage("[Slasher] You have joined the game.");
	}
	
	public static void removePlayer(UUID uuid) {
		playerIds.remove(uuid);
		
		Bukkit.getPlayer(uuid).sendMessage("[Slasher] You have left the game.");
		
		// check if player was slasher/survivor
		if (uuid.equals(slasherId))
			end();
		else if (survivorIds.contains(uuid)) {
			survivorIds.remove(uuid);
			
			// check for remaining survivors
			if (survivorIds.isEmpty())
				end();
		}
	}
	
	public static void start() {
		running = true;
		
		// prepare map
		
		
		// prepare players
		// pick slasher and survivors
		int slasherIndex = new Random().nextInt(getPlayerIds().size());
		slasherId = playerIds.get(slasherIndex);
		survivorIds = playerIds;
		survivorIds.remove(slasherIndex);
		
		// freeze players
		slasherFrozen = true;
		survivorsFrozen = true;
		
		for (UUID uuid : playerIds) {
			Player player = Bukkit.getPlayer(uuid);
			
			// teleport
			
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
			
		}, 12000);
	}
	
	public static void end() {
		running = false;
		
		// players
		playerIds.forEach(uuid -> {
			Player player = Bukkit.getPlayer(uuid);
			
			// teleport
			
			// set game mode
			player.setGameMode(GameMode.SURVIVAL);
			
			// clear inventory and effects
			player.getInventory().clear();
			player.getActivePotionEffects().clear();
			
			// set health & hunger
			player.setHealth(20);
			player.setSaturation(0);
			player.setFoodLevel(20);
		});
		
		
		// announce winner
		
	}
}
