package dk.sdu.cbse.Bullet.bulletSystems;

import dk.sdu.cbse.CommonBullet.Bullet;
import dk.sdu.cbse.CommonBullet.BulletSPI;
import dk.sdu.cbse.Common.data.Entity;
import dk.sdu.cbse.Common.data.EntityType;
import dk.sdu.cbse.Common.data.GameData;

public class BulletFactory implements BulletSPI {
    private float bulletSize = 2;
    private float offsetDistance = 10f;
    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();
        float size = bulletSize;

        // Set bullet size
        bullet.setPolygonCoordinates(
                size, -size,
                size, size,
                -size, size,
                -size, -size
        );

        // Calculate bullet start position using shooter rotation and offset distance
        double angleRad = Math.toRadians(shooter.getRotation());
        bullet.setX(shooter.getX() + Math.cos(angleRad) * offsetDistance);
        bullet.setY(shooter.getY() + Math.sin(angleRad) * offsetDistance);

        // Set bullet rotation same as shooter rotation
        bullet.setRotation(shooter.getRotation());

        // Set bullet collision radius
        bullet.setRadius(size);

        bullet.setEntityType(EntityType.BULLET);

        return bullet;
    }
}
