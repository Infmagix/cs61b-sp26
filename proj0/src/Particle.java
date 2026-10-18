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
        lifespan = switch (flavor) {
            case PLANT, FLOWER, FIRE -> LIFESPANS.get(flavor);
            default -> -1;
        };
    }

    public Color color() {
        return switch (flavor) {
            case EMPTY    -> Color.BLACK;
            case SAND     -> Color.YELLOW;
            case BARRIER  -> Color.GRAY;
            case WATER    -> Color.BLUE;
            case FOUNTAIN -> Color.CYAN;
            case PLANT    -> {
                double ratio = (double) Math.max(0, Math.min(lifespan, PLANT_LIFESPAN)) / PLANT_LIFESPAN;
                int g = 120 + (int) Math.round((255 - 120) * ratio);
                yield new Color(0, g, 0);
            }
            case FIRE     -> {
                double ratio = (double) Math.max(0, Math.min(lifespan, FIRE_LIFESPAN)) / FIRE_LIFESPAN;
                int r = (int) Math.round(255 * ratio);
                yield new Color(r, 0, 0);
            }
            case FLOWER   -> {
                double ratio = (double) Math.max(0, Math.min(lifespan, FLOWER_LIFESPAN)) / FLOWER_LIFESPAN;
                int r = 120 + (int) Math.round((255 - 120) * ratio);
                int g = 70 + (int) Math.round((141 - 70) * ratio);
                int b = 80 + (int) Math.round((161 - 80) * ratio);
                yield new Color(r, g, b);
            }
        };
    }

    public void decrementLifespan() {
        if (lifespan > 0) {
            lifespan--;
        }
        if (lifespan == 0) {
            flavor = ParticleFlavor.EMPTY;
            lifespan = -1;
        }
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
        Particle particle;

        switch (StdRandom.uniformInt(3)) {
            case 0:
                particle = neighbors.get(Direction.LEFT);
                break;
            case 1:
                particle = neighbors.get(Direction.RIGHT);
                break;
            default:
                return;
        }

        if (particle.flavor == ParticleFlavor.EMPTY) {
            moveInto(particle);
        }
    }

    public void grow(Map<Direction, Particle> neighbors) {
        Particle particle;

        switch (StdRandom.uniformInt(10)) {
            case 0:
                particle = neighbors.get(Direction.UP);
                break;
            case 1:
                particle = neighbors.get(Direction.LEFT);
                break;
            case 2:
                particle = neighbors.get(Direction.RIGHT);
                break;
            default:
                return;
        }

        if (particle.flavor == ParticleFlavor.EMPTY) {
            particle.flavor = flavor;
            particle.lifespan = LIFESPANS.get(flavor);
        }
    }

    public void burn(Map<Direction, Particle> neighbors) {
        for (Direction dir : Direction.values()) {
            Particle neighbor = neighbors.get(dir);
            if (neighbor.flavor == ParticleFlavor.PLANT || neighbor.flavor == ParticleFlavor.FLOWER) {
                if (StdRandom.uniformInt(10) < 4) {
                    neighbor.flavor = ParticleFlavor.FIRE;
                    neighbor.lifespan = FIRE_LIFESPAN;
                }
            }
        }
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
        if (flavor == ParticleFlavor.FIRE)
            burn(neighbors);
    }
}