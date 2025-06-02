package dk.sdu.cbse.enemySystem;

import dk.sdu.cbse.Enemy;
import dk.sdu.cbse.CommonBullet.BulletSPI;
import dk.sdu.cbse.Common.data.Entity;
import dk.sdu.cbse.Common.data.GameData;
import dk.sdu.cbse.Common.data.World;
import dk.sdu.cbse.enemyPlugin.EnemyPlugin;
import dk.sdu.cbse.Common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

public class EnemyControlSystem implements IEntityProcessingService {

    private final Random random = new Random();
    public double enemyRotationSpeed = 5f;
    public double enemySpeed = 1.5f;
    private float timeSinceLastShot = 0f;
    private float ShootCooldown = 0.15f;
    private int maxEnemy = 3;
    private float buffer = 30f;

    @Override
    public void process(GameData gameData, World world) {
        float delta = gameData.getDelta();

        Collection<Entity> enemies = world.getEntities(Enemy.class);
        if (enemies.size() < maxEnemy) {
            int toSpawn = maxEnemy - enemies.size();
            for (int i = 0; i < toSpawn; i++) {
                new EnemyPlugin().start(gameData, world);
            }
        }

        for (Entity e : world.getEntities(Enemy.class)) {
            Enemy enemy = (Enemy) e;

            processMovement(enemy, delta);
            processShootingInput(gameData, world, enemy);
            enforceScreenBounds(gameData, enemy);
        }
    }


    private void processMovement(Enemy enemy, float delta){
        // Simple drifting movement: move forward in current rotation direction
        double angleRad = Math.toRadians(enemy.getRotation());

        double dx = Math.cos(angleRad) * enemySpeed * delta;
        double dy = Math.sin(angleRad) * enemySpeed * delta;

        enemy.setX(enemy.getX() + dx);
        enemy.setY(enemy.getY() + dy);

        float rotationChange = (random.nextFloat() - 0.5f) * 2 * (float) enemyRotationSpeed * delta;

        enemy.setRotation(normalizeAngle(enemy.getRotation() + rotationChange));
    }

    private double normalizeAngle(double angle) {
        angle %= 360.0;
        if (angle < 0) angle += 360.0;
        return angle;
    }

    private void processShootingInput(GameData gameData, World world, Enemy enemy) {
        // There is a 10% chance for the enemy to shoot every frame
        if (random.nextFloat() < 0.1f) {
            timeSinceLastShot = timeSinceLastShot + gameData.getUnscaledDelta();

            if (timeSinceLastShot >= ShootCooldown) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> world.addEntity(spi.createBullet(enemy, gameData))
                );
                timeSinceLastShot = 0f;
            }
        }
    }

    private void enforceScreenBounds(GameData gameData, Entity e) {
        if (e.getX() < -buffer) {
            e.setX(gameData.getDisplayWidth() + buffer);
        } else if (e.getX() > gameData.getDisplayWidth() + buffer) {
            e.setX(-buffer);
        }

        if (e.getY() < -buffer) {
            e.setY(gameData.getDisplayHeight() + buffer);
        } else if (e.getY() > gameData.getDisplayHeight() + buffer) {
            e.setY(-buffer);
        }
    }


    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream()
                .map(ServiceLoader.Provider::get)
                .toList();
    }
}
