package en.awhitaker.slasher.models;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Game {
	private static boolean running;
	private static List<Player> players = new ArrayList<Player>();
	private static Player slasher;
	private static List<Player> survivors = new ArrayList<Player>();
	
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
	
	public static void start() {
		// prepare map
		
		// prepare players
		
		// release players
		
		// end game
	}
}
