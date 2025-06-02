import dk.sdu.cbse.Collision.CollisionDetector;
import dk.sdu.cbse.Common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonAsteroids;
    provides IPostEntityProcessingService with CollisionDetector;

}