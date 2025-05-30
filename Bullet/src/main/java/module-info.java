import dk.sdu.cbse.CommonBullet.BulletSPI;
import dk.sdu.cbse.Bullet.bulletSystems.BulletControlSystem;
import dk.sdu.cbse.Bullet.bulletPlugin.BulletPlugin;
import dk.sdu.cbse.Common.services.IEntityProcessingService;
import dk.sdu.cbse.Common.services.IGamePluginService;
import dk.sdu.cbse.Bullet.bulletSystems.BulletFactory;

module Bullet {
    requires Common;
    requires CommonBullet;
    provides IGamePluginService with BulletPlugin;
    provides BulletSPI with BulletFactory;
    provides IEntityProcessingService with BulletControlSystem;
}