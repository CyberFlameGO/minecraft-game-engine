package me.hugmanrique.engine.cinematics;

import me.hugmanrique.engine.cinematics.data.CinematicTask;
import me.hugmanrique.engine.cinematics.data.CinematicView;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * @author Hugmanrique
 * @since 13/01/2017
 */
public class Cinematic {
    private JavaPlugin plugin;

    private World world;
    private List<CinematicView> views;

    public Cinematic(JavaPlugin plugin, List<CinematicView> views, World world) {
        this.plugin = plugin;
        this.views = views;
        this.world = world;
    }

    public void start(Player player) {
        new CinematicTask(plugin, player, this).start();
    }

    public List<CinematicView> getViews() {
        return views;
    }

    public World getWorld() {
        return world;
    }
}
