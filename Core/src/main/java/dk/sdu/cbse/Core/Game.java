package dk.sdu.cbse.Core;

import dk.sdu.cbse.Common.data.GameData;
import dk.sdu.cbse.Common.data.Entity;
import dk.sdu.cbse.Common.data.World;
import dk.sdu.cbse.Common.data.GameKeys;
import dk.sdu.cbse.Common.data.EntityType;
import dk.sdu.cbse.Common.services.IEntityProcessingService;
import dk.sdu.cbse.Common.services.IGamePluginService;
import dk.sdu.cbse.Common.services.IPostEntityProcessingService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServiceList;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;
    private Text destroyedAsteroidsText;
    private int destroyedAsteroids = 0;
    private Text playTimeText;
    private Text gameOverText;
    private boolean gameOver = false;

    Game(List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityProcessingServiceList,
         List<IPostEntityProcessingService> postEntityProcessingServices) {
        this.gamePluginServices = gamePluginServices;
        this.entityProcessingServiceList = entityProcessingServiceList;
        this.postEntityProcessingServices = postEntityProcessingServices;
    }

    public void start(Stage window) throws Exception {
        setupStartLayout(window);

        Scene scene = new Scene(gameWindow);
        updateKeys(scene);

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getGamePluginServices()) {
            iGamePlugin.start(gameData, world);
            System.out.println(iGamePlugin);
        }
        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }
        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.setResizable(false);
        window.show();
    }

    public void render() {
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                float unscaledDelta = (now - lastTime) / 1_000_000_000f; // seconds
                lastTime = now;

                gameData.setUnscaledDelta(unscaledDelta);
                gameData.updatePlayTime();
                gameData.setDelta(unscaledDelta * gameData.getSimulationSpeed());

                gameData.setDifficultyMultiplier((float) (1.0 + Math.log1p(gameData.getPlayTimeSeconds()) * gameData.getDifficultyGrowthRate()));

                update();
                draw();
                gameData.getKeys().update();
                checkIfGameOver();
            }
        };
        timer.start();
    }

    private void update() {
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity polygonEntity : polygons.keySet()) {
            if (!world.getEntities().contains(polygonEntity)) {
                Polygon removedPolygon = polygons.get(polygonEntity);
                polygons.remove(polygonEntity);
                gameWindow.getChildren().remove(removedPolygon);

                if (polygonEntity.getEntityType() == EntityType.ASTEROID && !gameOver) {
                    destroyedAsteroids++;
                    destroyedAsteroidsText.setText("Destroyed asteroids: " + destroyedAsteroids);
                }

            }
        }

        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
            polygon.setFill(entity.getFillColor());
            polygon.setStroke(entity.getStrokeColor());
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }

        if (!gameOver){
            float time = gameData.getPlayTimeSeconds();
            playTimeText.setText(String.format("Time: %.1fs", time));
        }
    }

    private void updateKeys(Scene scene){
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }

        });

    }

    private void setupStartLayout(Stage window) {
        destroyedAsteroidsText = new Text(10, 20, "Destroyed asteroids: " + destroyedAsteroids);
        destroyedAsteroidsText.setStyle("-fx-fill: white; -fx-font-family: 'Courier New'; -fx-font-size: 18px;");
        gameWindow.getChildren().add(destroyedAsteroidsText);

        playTimeText = new Text(10, 45, "Time: 0.0s");
        playTimeText.setStyle("-fx-fill: white; -fx-font-family: 'Courier New'; -fx-font-size: 18px;");
        gameWindow.getChildren().add(playTimeText);

        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.setStyle("-fx-background-color: #121212;");
    }

    private void checkIfGameOver() {
        boolean playerAlive = world.getEntities().stream()
                .anyMatch(e -> e.getEntityType() == EntityType.PLAYER);

        if (!playerAlive && !gameOver) {
            gameOver = true;

            destroyedAsteroidsText.setOpacity(0);
            playTimeText.setOpacity(0);
            gameOverText = new Text(
                    "GAME OVER\n" + destroyedAsteroidsText.getText() + "\n" + playTimeText.getText()
            );

            gameOverText.setStyle("-fx-fill: #ee4646; -fx-font-family: 'Courier New'; -fx-font-size: 18px;");

            gameOverText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
            gameOverText.setTextOrigin(javafx.geometry.VPos.CENTER);

            gameOverText.setX(0);

            gameWindow.getChildren().add(gameOverText);

            double centerX = gameData.getDisplayWidth() / 2;
            double centerY = gameData.getDisplayHeight() / 2;

            double textWidth = gameOverText.getBoundsInParent().getWidth();
            double textHeight = gameOverText.getBoundsInParent().getHeight();

            gameOverText.setLayoutX(centerX - textWidth);
            gameOverText.setLayoutY(centerY - textHeight / 2);
        }
    }

    public List<IGamePluginService> getGamePluginServices() {
        return gamePluginServices;
    }

    public List<IEntityProcessingService> getEntityProcessingServices() {
        return entityProcessingServiceList;
    }

    public List<IPostEntityProcessingService> getPostEntityProcessingServices() {
        return postEntityProcessingServices;
    }

}