package dk.sdu.cbse.CommonAsteroids;

import dk.sdu.cbse.Common.data.Entity;
import javafx.scene.paint.Color;

public class Asteroid extends Entity {
    private float velocityX;
    private float velocityY;
    private double rotationSpeed;
    private boolean shouldBeRemoved = false;
    private AsteroidSize size;
    private Color asteroidColor = Color.rgb(117, 117, 117);

    public Color getAsteroidColor() {
        return asteroidColor;
    }

    public void setAsteroidColor(Color asteroidColor) {
        this.asteroidColor = asteroidColor;
    }

    public AsteroidSize getSize() {
        return size;
    }

    public void setSize(AsteroidSize size) {
        this.size = size;
        this.setRadius(size.getRadius());
    }

    public float getVelocityX() { return velocityX; }

    public float getVelocityY() { return velocityY; }

    public void setVelocity(float vx, float vy) {
        this.velocityX = vx;
        this.velocityY = vy;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public boolean isMarkedForRemoval() {
        return shouldBeRemoved;
    }

    public void markForRemoval() {
        this.shouldBeRemoved = true;
    }
}
