import dk.sdu.cbse.Enemy.enemyPlugin.EnemyPlugin;
import dk.sdu.cbse.Enemy.enemySystem.EnemyControlSystem;
import dk.sdu.cbse.Common.services.IEntityProcessingService;
import dk.sdu.cbse.Common.services.IGamePluginService;

module Enemy {
    requires Common;
    requires CommonBullet;
    requires javafx.graphics;
    uses dk.sdu.cbse.CommonBullet.BulletSPI;
    provides IGamePluginService with EnemyPlugin;
    provides IEntityProcessingService with EnemyControlSystem;
}