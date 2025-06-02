import dk.sdu.cbse.Common.services.IEntityProcessingService;
import dk.sdu.cbse.Common.services.IGamePluginService;
import dk.sdu.cbse.Common.services.IPostEntityProcessingService;

module Core {
    requires Common;

    requires javafx.graphics;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    exports dk.sdu.cbse.Core;
    opens dk.sdu.cbse.Core to javafx.graphics,spring.core;
    uses IGamePluginService;
    uses IEntityProcessingService;
    uses IPostEntityProcessingService;
}