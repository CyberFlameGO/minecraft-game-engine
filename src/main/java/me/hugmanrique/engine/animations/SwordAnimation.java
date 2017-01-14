package me.hugmanrique.engine.animations;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * @author Hugmanrique
 * @since 13/01/2017
 */
public class SwordAnimation {
    // Config variables
    private static final float SEPARATOR = 2;
    private static final float RAD_PER_SEC = 1.5F;
    private static final float RAD_PER_TICK = RAD_PER_SEC / 20F;

    private Location center;
    private double radius;

    private List<ArmorStand> swords;

    public SwordAnimation(Location center, double radius) {
        this.center = center;
        this.radius = radius;
        swords = Lists.newArrayList();
    }

    public void start(JavaPlugin plugin) {
        for (double angle = 0; angle < Math.PI * 2; angle += SEPARATOR) {
            spawnStand(angle);
        }

        new BukkitRunnable(){
            int tick = 0;

            public void run() {
                ++tick;

                for (int i = 0; i < swords.size(); i++) {
                    ArmorStand stand = swords.get(i);
                    Location loc = getLocationInCircle(RAD_PER_TICK * tick + SEPARATOR * i);

                    // Entity falling bug
                    stand.setVelocity(new Vector(1, 0, 0));

                    stand.teleport(loc);
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    private void spawnStand(double angle) {
        Location loc = getLocationInCircle(angle);

        ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);

        // TODO Customize by hiding, adding items, rotation...

        swords.add(stand);
    }

    private Location getLocationInCircle(double angle) {
        double x = center.getX() + radius * Math.cos(angle);
        double z = center.getZ() + radius * Math.sin(angle);

        return new Location(center.getWorld(), x, center.getY(), z);
    }
}
