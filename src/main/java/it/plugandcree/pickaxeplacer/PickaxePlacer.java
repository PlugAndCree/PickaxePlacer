package it.plugandcree.pickaxeplacer;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import it.plugandcree.pickaxeplacer.events.PlaceEvent;

public class PickaxePlacer extends JavaPlugin {

	private static PickaxePlacer instance;
	private Material[] ores;
	private Map<BlockFace, Vector> locations;
	
	@Override
	public void onEnable() {
		instance = this;
		
		setOres(new Material[] {Material.COAL_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE,
				Material.NETHER_QUARTZ_ORE, Material.LAPIS_ORE, Material.REDSTONE_ORE});
	
		Map<BlockFace, Vector> locations = new HashMap<>();
		locations.put(BlockFace.UP, new Vector(0,1,0));
		locations.put(BlockFace.DOWN, new Vector(0,-1,0));
		locations.put(BlockFace.SOUTH, new Vector(0,0,1));
		locations.put(BlockFace.NORTH, new Vector(0,0,-1));
		locations.put(BlockFace.EAST, new Vector(1,0,0));
		locations.put(BlockFace.WEST, new Vector(-1,0,0));
		
		setLocations(locations);
		
		getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
		
	}

	public Material[] getOres() {
		return ores;
	}

	public void setOres(Material[] ores) {
		this.ores = ores;
	}

	public static PickaxePlacer getInstance() {
		return instance;
	}

	public Map<BlockFace, Vector> getLocations() {
		return locations;
	}

	public void setLocations(Map<BlockFace, Vector> locations) {
		this.locations = locations;
	}
	
}
