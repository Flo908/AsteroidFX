package dk.sdu.cbse.Common.data;

public class GameData {
    private int displayWidth = 800;
    private int displayHeight = 800;
    private final GameKeys keys = new GameKeys();
    private float delta;
    private float simulationSpeed = 150.0f; // Simulation deltaTime
    private float unscaledDelta; // Real-world deltaTime
    private float playTimeSeconds = 0f;
    private float difficultyMultiplier;
    private float difficultyGrowthRate = 1.1f;

    public float getDifficultyGrowthRate() {
        return difficultyGrowthRate;
    }

    public void setDifficultyGrowthRate(float difficultyGrowthRate) {
        this.difficultyGrowthRate = difficultyGrowthRate;
    }

    public float getDifficultyMultiplier() {
        return difficultyMultiplier;
    }

    public void setDifficultyMultiplier(float difficultyMultiplier) {
        this.difficultyMultiplier = difficultyMultiplier;
    }

    public void updatePlayTime() {
        this.playTimeSeconds += unscaledDelta;
    }

    public float getPlayTimeSeconds() {
        return playTimeSeconds;
    }

    public float getUnscaledDelta() {
        return unscaledDelta;
    }

    public void setUnscaledDelta(float unscaledDelta) {
        this.unscaledDelta = unscaledDelta;
    }

    public float getSimulationSpeed() {
        return simulationSpeed;
    }

    public void setSimulationSpeed(float simulationSpeed) {
        this.simulationSpeed = simulationSpeed;
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public GameKeys getKeys() {
        return keys;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }
}
