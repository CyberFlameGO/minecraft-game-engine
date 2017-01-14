package me.hugmanrique.engine.cinematics.data;

import me.hugmanrique.engine.cinematics.Cinematic;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

/**
 * @author Hugmanrique
 * @since 13/01/2017
 */
public class CinematicTask implements Runnable {
    private JavaPlugin plugin;
    private Player player;
    private Cinematic cinematic;

    private int delay = 1;
    private float timer;
    private int index;

    private CinematicView current;
    private CinematicView next;

    private Vector pos1;
    private Vector pos2;
    private Vector nextPos;

    private int taskId;

    public CinematicTask(JavaPlugin plugin, Player player, Cinematic cinematic) {
        this.plugin = plugin;
        this.player = player;
        this.cinematic = cinematic;

        this.nextPos = new Vector();
    }

    public void start() {
        this.index = 0;
        loadView();

        Location loc = player.getLocation();

        loc.setX(pos1.getX());
        loc.setY(pos1.getY());
        loc.setZ(pos1.getZ());

        loc.setWorld(cinematic.getWorld());

        player.teleport(loc);
        player.setFlySpeed(0F);
        player.setGameMode(GameMode.SPECTATOR);

        this.timer = 0;

        // run() every tick
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 0L);
    }

    public void run() {
        if (!player.isOnline()) {
            cancel();
            return;
        } else {
            player.setGameMode(GameMode.SPECTATOR);
        }

        player.closeInventory();
        timer += current.getSpeed() * delay;

        lerpNext();

        Vector playerPos = player.getLocation().toVector();
        float distance = (float) nextPos.distance(playerPos);

        nextPos.subtract(playerPos).normalize().multiply(distance * current.getSpeed());
        player.setVelocity(nextPos);

        // Arrived at view
        if (timer >= 1) {
            player.setFlySpeed(0);
            index++;
            timer--;

            if (++index >= cinematic.getViews().size() - 1) {
                exit();
                return;
            }

            loadView();
        }
    }

    private void loadView() {
        current = getView(index);
        next = getView(index + 1);

        pos1 = current.getPosition();
        pos2 = next.getPosition();
    }

    // Utility methods
    public CinematicView getView(int index) {
        return cinematic.getViews().get(index);
    }

    public void lerpNext() {
        nextPos.setX(lerp(pos1.getX(), pos2.getX(), timer));
        nextPos.setY(lerp(pos1.getY(), pos2.getY(), timer));
        nextPos.setZ(lerp(pos1.getZ(), pos2.getZ(), timer));
    }

    private static float lerp(double pos1, double pos2, float timer) {
        return (float) (pos1 + timer * (pos2 - pos1));
    }

    private void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public void exit() {
        cancel();
        player.setFlySpeed(0.1F);
        player.setGameMode(GameMode.ADVENTURE);
    }
}
