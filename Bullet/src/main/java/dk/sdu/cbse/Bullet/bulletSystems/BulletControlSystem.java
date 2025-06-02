package dk.sdu.cbse.Bullet.bulletSystems;

import dk.sdu.cbse.CommonBullet.Bullet;
import dk.sdu.cbse.Common.data.Entity;
import dk.sdu.cbse.Common.data.GameData;
import dk.sdu.cbse.Common.data.World;
import dk.sdu.cbse.Common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService {
    private float bulletSpeed = 6f;
    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            Bullet bulletVariables = new Bullet();
            updateBulletMovement(bullet, bulletVariables, gameData.getDelta());
            destroyIfOffScreen(gameData, world, bullet);
        }
    }

    // Moves the bullets
    private void updateBulletMovement(Entity bullet, Bullet bulletVariables, float delta) {
        double angleRad = Math.toRadians(bullet.getRotation());
        float dx = (float) Math.cos(angleRad) * bulletSpeed * delta;
        float dy = (float) Math.sin(angleRad) * bulletSpeed * delta;

        bullet.setX(bullet.getX() + dx);
        bullet.setY(bullet.getY() + dy);
    }

    // Remove the bullet if it moves outside the screen boundaries
    private void destroyIfOffScreen(GameData gameData, World world, Entity bullet) {
        if (bullet.getX() < 0 || bullet.getX() > gameData.getDisplayWidth()
                || bullet.getY() < 0 || bullet.getY() > gameData.getDisplayHeight()) {
            world.removeEntity(bullet);
        }
    }
}
