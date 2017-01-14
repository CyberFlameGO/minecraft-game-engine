package me.hugmanrique.engine.cinematics.data;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * @author Hugmanrique
 * @since 13/01/2017
 */
public class CinematicView {
    private Vector position;
    private float speed = 0.01F;

    public CinematicView(Location location) {
        this(location.toVector());
    }

    public CinematicView(Vector position) {
        this.position = position;
    }

    public Vector getPosition() {
        return position;
    }

    public float getSpeed() {
        return speed;
    }
}
