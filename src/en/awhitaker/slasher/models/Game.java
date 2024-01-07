package en.awhitaker.slasher.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Game {
	private static boolean running;
	private static List<Player> players = new ArrayList<Player>();
	private static Player slasher;
	private static List<Player> survivors = new ArrayList<Player>();
	private static boolean slasherFrozen;
	private static boolean survivorsFrozen;
	
	public static boolean isRunning() {
		return running;
	}
	
	public static List<Player> getPlayers() {
		return players;
	}
	
	public static Player getSlasher() {
		return slasher;
	}
	
	public static List<Player> getSurvivors() {
		return survivors;
	}
	
	public static boolean isSlasherFrozen() {
		return slasherFrozen;
	}
	
	public static boolean isSurvivorsFrozen() {
		return survivorsFrozen;
	}
	
	public static void start() {
		running = true;
		
		// prepare map
		
		
		// prepare players
		// pick slasher and survivors
		int slasherIndex = new Random().nextInt(getPlayers().size());
		slasher = players.get(slasherIndex);
		survivors = players;
		survivors.remove(slasherIndex);
		
		players.forEach(player -> {
			// teleport
			
			// set game modes
			player.setGameMode(GameMode.SURVIVAL);
			
			// clear inventories and effects
			player.getInventory().clear();
			player.getActivePotionEffects().clear();
			
			// set health & hunger
			player.setHealth(20);
			player.setSaturation(0);
			player.setFoodLevel(20);
		});
		
		// give slasher weapon and effect
		ItemStack weapon = new ItemStack(Material.GOLDEN_AXE, 1);
		ItemMeta meta = weapon.getItemMeta();
		meta.setUnbreakable(true);
		weapon.setItemMeta(meta);
		
		slasher.getInventory().setItem(0, weapon);
		slasher.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, Integer.MAX_VALUE, false, false, false));
		
		
		// release players
		
		
		// end game
	}
}
