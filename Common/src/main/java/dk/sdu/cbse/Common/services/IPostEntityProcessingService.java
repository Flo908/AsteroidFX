package dk.sdu.cbse.Common.services;

import dk.sdu.cbse.Common.data.World;
import dk.sdu.cbse.Common.data.GameData;

public interface IPostEntityProcessingService {

    void process(GameData gameData, World world);
}