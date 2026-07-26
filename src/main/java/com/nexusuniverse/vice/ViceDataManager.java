package com.nexusuniverse.vice;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ViceDataManager {

    private final Map<UUID, VicePlayerData> data = new ConcurrentHashMap<>();

    public VicePlayerData get(UUID playerId) {
        return data.computeIfAbsent(playerId, id -> new VicePlayerData());
    }

    public void remove(UUID playerId) {
        data.remove(playerId);
    }

    public Map<UUID, VicePlayerData> all() {
        return data;
    }
}
