package com.nexusuniverse.vice;

import java.util.UUID;

/**
 * Public read-only surface for other plugins (NexusLegends' Fame system,
 * primarily) to query lifetime stats without a hard compile-time
 * dependency on this plugin. Registered via Bukkit's ServicesManager on
 * enable, same pattern as NexusSeasonsAPI. A plugin with no compile-time
 * dependency on NexusVice looks this up reflectively -- see
 * NexusLegends' ViceBridge for that pattern.
 */
public interface NexusViceAPI {

    /** How many times this player has crossed into an active overdose/blackout state, ever (persisted, survives restarts). */
    int getOverdoseCount(UUID playerId);
}
