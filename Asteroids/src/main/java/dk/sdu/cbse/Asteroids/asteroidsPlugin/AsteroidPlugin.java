package dk.sdu.cbse.Asteroids.asteroidsPlugin;

import dk.sdu.cbse.CommonAsteroids.Asteroid;
import dk.sdu.cbse.CommonAsteroids.AsteroidSize;
import dk.sdu.cbse.Common.data.Entity;
import dk.sdu.cbse.Common.data.EntityType;
import dk.sdu.cbse.Common.data.GameData;
import dk.sdu.cbse.Common.data.World;
import dk.sdu.cbse.Common.services.IGamePluginService;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    float buffer = 20; // Distance from screen edge to make sure asteroids spawn off-screen
    float maxSpeed = 4.0f; // max speed limit you can adjust


    @Override
    public void start(GameData gameData, World world) {
        Entity asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Asteroid asteroid = new Asteroid();
        Random rnd = new Random();

        // Pick a random size (BIG, MEDIUM, SMALL)
        AsteroidSize[] sizes = AsteroidSize.values();
        AsteroidSize randomSize = sizes[rnd.nextInt(sizes.length)];
        asteroid.setSize(randomSize);

        float r = randomSize.getRadius();
        asteroid.setPolygonCoordinates(r, -r, -r, -r, -r, r, r, r);

        float xPos = 0, yPos = 0;

        // Pick a random edge for the asteroid to spawn at
        int edge = rnd.nextInt(4); // 0 = top, 1 = right, 2 = bottom, 3 = left

        // Pick a random position along that edge, with buffer to spawn off-screen
        switch (edge) {
            case 0:
                xPos = rnd.nextInt(gameData.getDisplayWidth());
                yPos = -buffer;
                break;
            case 1:
                xPos = gameData.getDisplayWidth() + buffer;
                yPos = rnd.nextInt(gameData.getDisplayHeight());
                break;
            case 2:
                xPos = rnd.nextInt(gameData.getDisplayWidth());
                yPos = gameData.getDisplayHeight() + buffer;
                break;
            case 3:
                xPos = -buffer;
                yPos = rnd.nextInt(gameData.getDisplayHeight());
                break;
        }

        asteroid.setX(xPos);
        asteroid.setY(yPos);

        // Pick a random angle the asteroid starts facing
        asteroid.setRotation(rnd.nextInt(360));

        // Pick a random rotation speed
        asteroid.setRotationSpeed(rnd.nextFloat());

        // Pick a random point inside the screen as target
        float targetX = rnd.nextInt(gameData.getDisplayWidth());
        float targetY = rnd.nextInt(gameData.getDisplayHeight());

        // Calculate angle from spawn to target
        double angle = Math.atan2(targetY - yPos, targetX - xPos);

        // Pick a random speed base and multiply by DifficultyMultiplier
        // Clamp speed so it doesn’t get too fast at higher difficulties
        float baseSpeed = rnd.nextFloat(1.5f) + 1;
        float speed = baseSpeed * gameData.getDifficultyMultiplier();

        // Limit the speed to maxSpeed
        if (speed > maxSpeed) {
            speed = maxSpeed;
        }

        float vx = (float) (Math.cos(angle) * speed);
        float vy = (float) (Math.sin(angle) * speed);
        asteroid.setVelocity(vx, vy);

        asteroid.setFillColor(asteroid.getAsteroidColor());
        asteroid.setStrokeColor(asteroid.getAsteroidColor());
        asteroid.setEntityType(EntityType.ASTEROID);

        return asteroid;
    }
}
