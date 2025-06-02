package dk.sdu.cbse.Player;

import dk.sdu.cbse.Common.data.Entity;

public class Player extends Entity {
    private float timeSinceLastShot = 0f;

    public float getTimeSinceLastShot() {
        return timeSinceLastShot;
    }

    public void setTimeSinceLastShot(float time) {
        this.timeSinceLastShot = time;
    }
}
