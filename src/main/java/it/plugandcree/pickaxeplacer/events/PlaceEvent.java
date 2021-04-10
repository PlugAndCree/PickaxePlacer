package it.plugandcree.pickaxeplacer.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import it.plugandcree.pickaxeplacer.PickaxePlacer;

public class PlaceEvent implements Listener {

	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().getGameMode() == GameMode.SURVIVAL
				&& e.getPlayer().getInventory().getItemInMainHand().getType().toString().toUpperCase()
						.endsWith("PICKAXE")) {
			
			if (!e.getPlayer().hasPermission("pickaxeplacer.place"))
				return;

			if (e.getHand() == EquipmentSlot.HAND) {
				System.out.println("BLOCCATO -> " + e.getHand().name());
				return;
			}
			System.out.println("FUNZIONA -> " + e.getHand().name());
			for (Material m : PickaxePlacer.getInstance().getOres()) {
				ItemStack item = new ItemStack(m);

				if (e.getPlayer().getInventory().containsAtLeast(item, 1)) {

					Location newLoc = e.getClickedBlock().getLocation()
							.add(PickaxePlacer.getInstance().getLocations().get(e.getBlockFace()));

					if (newLoc.getWorld().getBlockAt(newLoc).getType() == Material.WATER
							|| newLoc.getWorld().getBlockAt(newLoc).getType() == Material.LAVA)
						return;

					BlockBreakEvent bre = new BlockBreakEvent(e.getClickedBlock(), e.getPlayer());

					Bukkit.getPluginManager().callEvent(bre);
					if (bre.isCancelled()) {
						e.setCancelled(true);
						return;
					}

					for (ItemStack i : e.getPlayer().getInventory().getContents()) {
						if (i != null && i.getType() == m) {
							i.setAmount(i.getAmount() - 1);
							break;
						}
					}
					newLoc.getWorld().getBlockAt(newLoc).setType(m);

					break;
				}
			}
		}
	}

}
