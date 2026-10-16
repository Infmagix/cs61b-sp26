import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;
import java.util.Map;

public class Particle {
    public ParticleFlavor flavor;
    public int lifespan;

    public static final int PLANT_LIFESPAN = 150;
    public static final int FLOWER_LIFESPAN = 75;
    public static final int FIRE_LIFESPAN = 10;
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                   ParticleFlavor.PLANT, PLANT_LIFESPAN,
                   ParticleFlavor.FIRE, FIRE_LIFESPAN);

    public Particle(ParticleFlavor flavor) {
        this.flavor = flavor;
        lifespan = -1;
    }

    public Color color() {
        return switch (flavor) {
            case EMPTY -> Color.BLACK;
            case SAND -> Color.YELLOW;
            case BARRIER -> Color.GRAY;
            case WATER -> Color.BLUE;
            case FOUNTAIN -> Color.CYAN;
            case PLANT -> new Color(0, 255, 0);
            case FIRE -> new Color(255, 0, 0);
            case FLOWER -> new Color(255, 141, 161);
        };
    }

    public void moveInto(Particle other) {
        other.flavor = flavor;
        other.lifespan = lifespan;

        flavor = ParticleFlavor.EMPTY;
        lifespan = -1;
    }

    public void fall(Map<Direction, Particle> neighbors) {
        Particle particle_down = neighbors.get(Direction.DOWN);
        // Assume that none of neighbors are null.
        if (particle_down.flavor == ParticleFlavor.EMPTY) {
            moveInto(particle_down);
        }
    }

    public void flow(Map<Direction, Particle> neighbors) {
        int number = StdRandom.uniformInt(3);
        switch (number) {
            case 0:
                break;
            case 1:
                Particle particle_left = neighbors.get(Direction.LEFT);
                if (particle_left.flavor == ParticleFlavor.EMPTY) {
                    moveInto(particle_left);
                }
                break;
            case 2:
                Particle particle_right = neighbors.get(Direction.RIGHT);
                if (particle_right.flavor == ParticleFlavor.EMPTY) {
                    moveInto(particle_right);
                }
                break;
        }
    }

    public void grow(Map<Direction, Particle> neighbors) {
        int number = StdRandom.uniformInt(10);
        switch (number) {
            case 0:
                Particle particle_up = neighbors.get(Direction.UP);
                if (particle_up.flavor == ParticleFlavor.EMPTY) {
                    particle_up.flavor = flavor;
                    particle_up.lifespan = LIFESPANS.get(flavor);
                }
                break;
            case 1:
                Particle particle_left = neighbors.get(Direction.LEFT);
                if (particle_left.flavor == ParticleFlavor.EMPTY) {
                    particle_left.flavor = flavor;
                    particle_left.lifespan = LIFESPANS.get(flavor);
                }
                break;
            case 2:
                Particle particle_right = neighbors.get(Direction.RIGHT);
                if (particle_right.flavor == ParticleFlavor.EMPTY) {
                    particle_right.flavor = flavor;
                    particle_right.lifespan = LIFESPANS.get(flavor);
                }
                break;
            default:
                break;
        }
    }

    public void burn(Map<Direction, Particle> neighbors) {
    }

    public void action(Map<Direction, Particle> neighbors) {
        if (flavor == ParticleFlavor.EMPTY)
            return;
        if (flavor != ParticleFlavor.BARRIER)
            fall(neighbors);
        if (flavor == ParticleFlavor.WATER)
            flow(neighbors);
        if (flavor == ParticleFlavor.PLANT || flavor == ParticleFlavor.FLOWER)
            grow(neighbors);
    }
}