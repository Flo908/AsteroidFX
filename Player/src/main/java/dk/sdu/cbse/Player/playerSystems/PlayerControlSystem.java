package dk.sdu.cbse.Player.playerSystems;

import dk.sdu.cbse.Player.Player;
import dk.sdu.cbse.CommonBullet.BulletSPI;
import dk.sdu.cbse.Common.data.Entity;
import dk.sdu.cbse.Common.data.GameData;
import dk.sdu.cbse.Common.data.GameKeys;
import dk.sdu.cbse.Common.data.World;
import dk.sdu.cbse.Common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerControlSystem implements IEntityProcessingService {

    public double playerRotationSpeed = 5f;
    public double playerSpeed = 1.5f;
    private float timeSinceLastShot = 0f;
    private float ShootCooldown = 0.15f;

    @Override
    public void process(GameData gameData, World world) {
        float delta = gameData.getDelta();

        for (Entity e : world.getEntities(Player.class)) {
            Player player = (Player) e;
            processMovementInput(gameData, player, delta);
            processShootingInput(gameData, world, player);
            enforceScreenBounds(gameData, player);
        }
    }

    private void processMovementInput(GameData gameData, Player player, float delta) {
        if (gameData.getKeys().isDown(GameKeys.LEFT)) {
            player.setRotation(player.getRotation() - playerRotationSpeed * delta);
        }
        if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
            player.setRotation(player.getRotation() + playerRotationSpeed * delta);
        }
        if (gameData.getKeys().isDown(GameKeys.UP)) {
            double angleRad = Math.toRadians(player.getRotation());
            double dx = Math.cos(angleRad) * playerSpeed * delta;
            double dy = Math.sin(angleRad) * playerSpeed * delta;
            player.setX(player.getX() + dx);
            player.setY(player.getY() + dy);
        }
    }

    private void processShootingInput(GameData gameData, World world, Player player) {
        float timeSinceLastShot = player.getTimeSinceLastShot();
        timeSinceLastShot += gameData.getUnscaledDelta();
        player.setTimeSinceLastShot(timeSinceLastShot);

        if (gameData.getKeys().isDown(GameKeys.SPACE) &&
                timeSinceLastShot >= ShootCooldown) {
            getBulletSPIs().stream().findFirst().ifPresent(spi -> {
                world.addEntity(spi.createBullet(player, gameData));
            });
            System.out.println("Looser ");
            player.setTimeSinceLastShot(0f);
        }
    }

    private void enforceScreenBounds(GameData gameData, Entity player) {
        if (player.getX() < 0) player.setX(1);
        if (player.getX() > gameData.getDisplayWidth())
            player.setX(gameData.getDisplayWidth() - 1);
        if (player.getY() < 0) player.setY(1);
        if (player.getY() > gameData.getDisplayHeight())
            player.setY(gameData.getDisplayHeight() - 1);
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream()
                .map(ServiceLoader.Provider::get)
                .collect(toList());
    }
}
