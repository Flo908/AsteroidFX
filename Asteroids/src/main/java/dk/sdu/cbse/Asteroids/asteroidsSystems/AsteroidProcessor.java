package dk.sdu.cbse.Asteroids.asteroidsSystems;

import dk.sdu.cbse.CommonAsteroids.Asteroid;
import dk.sdu.cbse.CommonAsteroids.IAsteroidSplitter;
import dk.sdu.cbse.Common.data.Entity;
import dk.sdu.cbse.Common.data.GameData;
import dk.sdu.cbse.Common.data.World;
import dk.sdu.cbse.Common.services.IEntityProcessingService;
import dk.sdu.cbse.Asteroids.asteroidsPlugin.AsteroidPlugin;

import java.util.ArrayList;
import java.util.List;

public class AsteroidProcessor implements IEntityProcessingService {

    private IAsteroidSplitter asteroidSplitter = new AsteroidSplitter();
    private float maxAsteroids;
    float buffer = 35;

    @Override
    public void process(GameData gameData, World world) {

        // Updates maxAsteroids with DifficultyMultiplier so more asteroids can spawn over time
        maxAsteroids = 2 * gameData.getDifficultyMultiplier();

        // Gather all marked asteroids
        List<Asteroid> toSplit = new ArrayList<>();
        for (Entity e : world.getEntities(Asteroid.class)) {
            Asteroid a = (Asteroid) e;
            if (a.isMarkedForRemoval()) {
                toSplit.add(a);
            }
        }

        // Split's the original asteroid and then removes it
        for (Asteroid a : toSplit) {
            asteroidSplitter.split(a, world);
            world.removeEntity(a);
        }

        // Spawn's new asteroids if below max
        int currentCount = world.getEntities(Asteroid.class).size();
        int missing = (int) Math.ceil(maxAsteroids - currentCount);
        for (int i = 0; i < missing; i++) {
            new AsteroidPlugin().start(gameData, world);
        }

        // Moves the asteroids and wraps them around the screen
        float delta = gameData.getDelta();
        for (Entity e : world.getEntities(Asteroid.class)) {
            Asteroid a = (Asteroid) e;
            a.setX(a.getX() + a.getVelocityX() * delta);
            a.setY(a.getY() + a.getVelocityY() * delta);
            a.setRotation((a.getRotation() + a.getRotationSpeed() * delta) % 360f);

            if (a.getX() < -buffer) {
                a.setX(gameData.getDisplayWidth());
            } else if (a.getX() > gameData.getDisplayWidth() + buffer) {
                a.setX(-buffer);
            }
            if (a.getY() < -buffer) {
                a.setY(gameData.getDisplayHeight());
            }else if (a.getY() > gameData.getDisplayHeight() + buffer) {
                a.setY(-buffer);
            }
        }
    }
}
