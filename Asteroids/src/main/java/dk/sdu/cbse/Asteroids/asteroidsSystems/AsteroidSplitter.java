package dk.sdu.cbse.Asteroids.asteroidsSystems;

import dk.sdu.cbse.CommonAsteroids.Asteroid;
import dk.sdu.cbse.CommonAsteroids.AsteroidSize;
import dk.sdu.cbse.CommonAsteroids.IAsteroidSplitter;
import dk.sdu.cbse.Common.data.Entity;
import dk.sdu.cbse.Common.data.EntityType;
import dk.sdu.cbse.Common.data.World;

import java.util.Random;

public class AsteroidSplitter implements IAsteroidSplitter {
    @Override
    public void split(Entity entity, World world) {

        Random RANDOM = new Random();
        Asteroid parent = (Asteroid) entity;
        Asteroid child = new Asteroid();

        AsteroidSize parentSize = parent.getSize();

        // If the asteroid is already small, don't split it
        if (parentSize == AsteroidSize.SMALL) {
            return;
        }

        // Pick new sizes based on the parent's size
        AsteroidSize childSize = null;
        if (parentSize == AsteroidSize.BIG) {
            childSize = AsteroidSize.MEDIUM;
        } else if (parentSize == AsteroidSize.MEDIUM) {
            childSize = AsteroidSize.SMALL;
        }

        float childRadius = childSize.getRadius();
        float parentRadius = parent.getRadius();

        // Pick a random base angle to send children in opposite directions
        double baseAngle = RANDOM.nextDouble() * 360.0;

        // Distance to place children away from the parent, so they don't overlap
        double offset = parentRadius + childRadius + 1.0;

        for (int i = 0; i < 2; i++) {
            // Slightly change the angle so both children go in different directions
            double angleOffset = baseAngle + i * 180.0 + (RANDOM.nextDouble() - 0.5) * 30.0;
            double rad = Math.toRadians(angleOffset);

            child.setSize(childSize);

            int s = (int) childRadius;
            child.setPolygonCoordinates(s, -s, -s, -s, -s, s, s, s);

            // Place child outside the parent using the offset distance
            child.setX((float)(parent.getX() + Math.cos(rad) * offset));
            child.setY((float)(parent.getY() + Math.sin(rad) * offset));

            // Takes its parent rotation speed
            child.setRotationSpeed(parent.getRotationSpeed());

            // Pick's a random speed
            float burst = (0.5f + RANDOM.nextFloat());

            float newVx = (float)(Math.cos(rad) * burst);
            float newVy = (float)(Math.sin(rad) * burst);

            child.setVelocity(newVx, newVy);

            child.setFillColor(child.getAsteroidColor());
            child.setStrokeColor(child.getAsteroidColor());
            child.setEntityType(EntityType.ASTEROID);

            world.addEntity(child);
        }
    }
}
