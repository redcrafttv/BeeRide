package eu.brickpics.beeride;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public final class BeeRide extends JavaPlugin implements Listener {

    public HashMap<Player, Location> locations = new HashMap<Player, Location>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onRide(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType().equals(EntityType.BEE)) {
            event.getRightClicked().setPassenger(event.getPlayer());
            locations.put(event.getPlayer(), event.getPlayer().getLocation());
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getVehicle() != null) {
                if (player.getVehicle().getType().equals(EntityType.BEE)) {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onDisembark(PlayerToggleSneakEvent event) {
        if (Objects.requireNonNull(event.getPlayer().getVehicle()).getType().equals(EntityType.BEE)) {
            event.getPlayer().teleport(locations.get(event.getPlayer()));
            locations.remove(event.getPlayer());
        }
    }
}
