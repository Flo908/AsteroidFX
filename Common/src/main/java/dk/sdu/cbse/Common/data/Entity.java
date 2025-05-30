package dk.sdu.cbse.Common.data;

import java.util.UUID;
import javafx.scene.paint.Color;

public class Entity {
    private final UUID ID = UUID.randomUUID();
    private double[] polygonCoordinates;
    private double x, y, rotation;
    private float radius;
    private Color fillColor;
    private Color strokeColor;
    private EntityType entityType; // Changed from String to enum

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Entity() {
        this.fillColor = Color.WHITE;
        this.strokeColor = Color.WHITE;
    }

    public String getID() {
        return ID.toString();
    }

    public double[] getPolygonCoordinates() {
        return polygonCoordinates;
    }

    public void setPolygonCoordinates(double... polygonCoordinates) {
        this.polygonCoordinates = polygonCoordinates;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color color) {
        this.strokeColor = color;
    }
}
