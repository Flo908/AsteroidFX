package dk.sdu.cbse.CommonAsteroids;

public enum AsteroidSize {
    BIG(15f),
    MEDIUM(8f),
    SMALL(6f);

    private final float radius;

    AsteroidSize(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }
}
