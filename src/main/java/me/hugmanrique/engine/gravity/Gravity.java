package me.hugmanrique.engine.gravity;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

/**
 * @author Hugmanrique
 * @since 13/01/2017
 */
public class Gravity implements Listener {
    private static final Vector GRAVITY = new Vector(0, -9.81F, 0);

    private JavaPlugin plugin;

    public Gravity(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.isCancelled() || !e.canBuild()) {
            return;
        }

        e.setCancelled(true);

        Block block = e.getBlockPlaced();
        Location loc = e.getBlock().getLocation();

        MaterialData data = new MaterialData(block.getType(), block.getData());

        FallingBlock entity = loc.getWorld().spawnFallingBlock(loc, data);
        entity.setVelocity(GRAVITY);
    }
}
