package dk.sdu.cbse.Common.services;

import dk.sdu.cbse.Common.data.GameData;
import dk.sdu.cbse.Common.data.World;

public interface IGamePluginService {

    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
