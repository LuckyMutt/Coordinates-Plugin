package me.luckymutt.cord;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CoordinatesPlayerManager {

    private final Map<UUID, CoordinatesPlayerData> playerMap = new HashMap<>();

    public void addCoordinatesPlayer(UUID uuid, CoordinatesPlayerData player) {
        playerMap.put(uuid, player);
    }

    public void removeCoordinatesPlayer(UUID uuid) {
        playerMap.remove(uuid);
    }

    public CoordinatesPlayerData getCoordinatesPlayer(UUID uuid) {
        return playerMap.get(uuid);
    }

}
