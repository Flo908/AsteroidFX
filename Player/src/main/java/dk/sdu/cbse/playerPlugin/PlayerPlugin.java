package dk.sdu.cbse.playerPlugin;

import dk.sdu.cbse.Player;
import dk.sdu.cbse.Common.data.EntityType;
import dk.sdu.cbse.Common.data.GameData;
import dk.sdu.cbse.Common.data.World;
import dk.sdu.cbse.Common.services.IGamePluginService;
import dk.sdu.cbse.Common.data.Entity;
import javafx.scene.paint.Color;

public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {

        Player playerShip = new Player();
        playerShip.setPolygonCoordinates(-5,-5,10,0,-5,5);
        playerShip.setX(gameData.getDisplayHeight()/2);
        playerShip.setY(gameData.getDisplayWidth()/2);
        playerShip.setRadius(8);
        playerShip.setFillColor(Color.rgb(82, 77, 214));
        playerShip.setStrokeColor(Color.rgb(82, 77, 214));
        playerShip.setEntityType(EntityType.PLAYER);
        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }

}
