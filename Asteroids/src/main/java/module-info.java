import dk.sdu.cbse.Common.services.IEntityProcessingService;
import dk.sdu.cbse.Common.services.IGamePluginService;
import dk.sdu.cbse.Asteroids.asteroidsPlugin.AsteroidPlugin;
import dk.sdu.cbse.Asteroids.asteroidsSystems.AsteroidProcessor;

module Asteroid {
    requires Common;
    requires CommonAsteroids;
    requires javafx.graphics;
    provides IGamePluginService with AsteroidPlugin;
    provides IEntityProcessingService with AsteroidProcessor;
}