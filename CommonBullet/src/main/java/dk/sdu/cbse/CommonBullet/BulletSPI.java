package dk.sdu.cbse.CommonBullet;

import dk.sdu.cbse.Common.data.Entity;
import dk.sdu.cbse.Common.data.GameData;

public interface BulletSPI {
    Entity createBullet(Entity e, GameData gameData);
}
