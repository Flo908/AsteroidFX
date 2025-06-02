package dk.sdu.cbse.Collision;

import dk.sdu.cbse.CommonAsteroids.Asteroid;
import dk.sdu.cbse.Common.data.Entity;
import dk.sdu.cbse.Common.data.GameData;
import dk.sdu.cbse.Common.data.World;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class CollisionDetectorTest {

    private CollisionDetector collisionDetector;
    private GameData gameData;
    private World world;

    @BeforeEach
    public void setup() {
        collisionDetector = new CollisionDetector();
        gameData = mock(GameData.class);
        world = mock(World.class);
    }

    @Test
    public void testCollision() {
        Asteroid asteroid = mock(Asteroid.class);
        Entity ship = mock(Entity.class);

        when(asteroid.getX()).thenReturn(0.0);
        when(asteroid.getY()).thenReturn(0.0);
        when(asteroid.getRadius()).thenReturn(10f);

        when(ship.getX()).thenReturn(5.0);
        when(ship.getY()).thenReturn(0.0);
        when(ship.getRadius()).thenReturn(10f);

        when(world.getEntities()).thenReturn(Arrays.asList(asteroid, ship));

        collisionDetector.process(gameData, world);

        verify(asteroid, times(1)).markForRemoval();
        verify(world, times(1)).removeEntity((Entity) ship);
    }

    @Test
    public void testNoCollision() {
        Entity e1 = mock(Entity.class);
        Entity e2 = mock(Entity.class);

        when(e1.getX()).thenReturn(0.0);
        when(e1.getY()).thenReturn(0.0);
        when(e1.getRadius()).thenReturn(5f);

        when(e2.getX()).thenReturn(100.0);
        when(e2.getY()).thenReturn(100.0);
        when(e2.getRadius()).thenReturn(5f);

        when(world.getEntities()).thenReturn(Arrays.asList(e1, e2));

        collisionDetector.process(gameData, world);

        verify(world, never()).removeEntity((Entity) any());
    }
}
