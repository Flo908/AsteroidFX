import dk.sdu.cbse.Common.services.IEntityProcessingService;
import dk.sdu.cbse.Common.services.IGamePluginService;
import dk.sdu.cbse.Player.playerPlugin.PlayerPlugin;
import dk.sdu.cbse.Player.playerSystems.PlayerControlSystem;

module Player {
    requires Common;
    requires CommonBullet;
    requires javafx.graphics;
    uses dk.sdu.cbse.CommonBullet.BulletSPI;
    provides IGamePluginService with PlayerPlugin;
    provides IEntityProcessingService with PlayerControlSystem;
}