package dk.sdu.cbse.Collision;

import dk.sdu.cbse.CommonAsteroids.Asteroid;
import dk.sdu.cbse.Common.services.IPostEntityProcessingService;
import dk.sdu.cbse.Common.data.Entity;
import dk.sdu.cbse.Common.data.GameData;
import dk.sdu.cbse.Common.data.World;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> entities = new ArrayList<>(world.getEntities());
        int size = entities.size();

        // Check every unique pair of entities for collisions
        for (int i = 0; i < size; i++) {
            Entity e1 = entities.get(i);
            for (int j = i + 1; j < size; j++) {
                Entity e2 = entities.get(j);

                if (collides(e1, e2)) {

                    if (e1 instanceof Asteroid) {
                        ((Asteroid) e1).markForRemoval();
                    } else {
                        world.removeEntity(e1);
                    }

                    if (e2 instanceof Asteroid) {
                        ((Asteroid) e2).markForRemoval();
                    } else {
                        world.removeEntity(e2);
                    }
                }
            }
        }
    }


    private boolean collides(Entity a, Entity b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        float dist = (float) Math.hypot(dx, dy);
        return dist < (a.getRadius() + b.getRadius());
    }
}
