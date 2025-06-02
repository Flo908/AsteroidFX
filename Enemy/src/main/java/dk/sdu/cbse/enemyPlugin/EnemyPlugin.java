package dk.sdu.cbse.enemyPlugin;

import dk.sdu.cbse.Enemy;
import dk.sdu.cbse.Common.data.GameData;
import dk.sdu.cbse.Common.data.World;
import dk.sdu.cbse.Common.services.IGamePluginService;
import dk.sdu.cbse.Common.data.Entity;
import javafx.scene.paint.Color;

import java.util.Random;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;
    private float buffer = 20;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemy(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemy(GameData gameData) {
        Enemy enemyShip = new Enemy();
        Random rnd = new Random();


        enemyShip.setPolygonCoordinates(-5,-5,10,0,-5,5);

        float xPos = 0, yPos = 0;
        int edge = rnd.nextInt(4);

        switch (edge) {
            case 0:
                xPos = rnd.nextInt(gameData.getDisplayWidth());
                yPos = -buffer;
                break;
            case 1:
                xPos = gameData.getDisplayWidth() + buffer;
                yPos = rnd.nextInt(gameData.getDisplayHeight());
                break;
            case 2:
                xPos = rnd.nextInt(gameData.getDisplayWidth());
                yPos = gameData.getDisplayHeight() + buffer;
                break;
            case 3:
                xPos = -buffer;
                yPos = rnd.nextInt(gameData.getDisplayHeight());
                break;
        }

        enemyShip.setX(xPos);
        enemyShip.setY(yPos);
        enemyShip.setRadius(8);
        enemyShip.setFillColor(Color.rgb(214, 77, 77));
        enemyShip.setStrokeColor(Color.rgb(214, 77, 77));
        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }

}
