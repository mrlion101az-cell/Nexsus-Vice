package com.nexusuniverse.vice;

import com.nexusuniverse.vice.alcohol.AlcoholType;
import com.nexusuniverse.vice.substances.Substance;
import org.bukkit.plugin.java.JavaPlugin;

public class ViceConfig {

    private final JavaPlugin plugin;

    public ViceConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    // --- Substances ---

    /** The name shown to players -- change this in config.yml, nothing in code needs to change. */
    public String displayName(Substance substance) {
        return plugin.getConfig().getString("substances." + substance.configKey() + ".display-name", titleCase(substance.name()));
    }

    public double dosePerItem(Substance substance) {
        return plugin.getConfig().getDouble("substances." + substance.configKey() + ".dose-per-item", substance.defaultDosePerItem());
    }

    public double overdoseThreshold(Substance substance) {
        return plugin.getConfig().getDouble("substances." + substance.configKey() + ".overdose-threshold", substance.defaultOverdoseThreshold());
    }

    public boolean hasOverdoseRisk(Substance substance) {
        return plugin.getConfig().getBoolean("substances." + substance.configKey() + ".has-overdose-risk", substance.defaultHasOverdoseRisk());
    }

    // --- Alcohol ---

    public String displayName(AlcoholType alcohol) {
        return plugin.getConfig().getString("alcohol." + alcohol.configKey() + ".display-name", titleCase(alcohol.name()));
    }

    public double dosePerItem(AlcoholType alcohol) {
        return plugin.getConfig().getDouble("alcohol." + alcohol.configKey() + ".dose-per-item", alcohol.defaultDosePerItem());
    }

    // --- Shared tuning ---

    public int tickIntervalSeconds() {
        return Math.max(1, plugin.getConfig().getInt("tick-interval-seconds", 5));
    }

    public double decayPerTick() {
        return plugin.getConfig().getDouble("decay-per-tick", 3.0);
    }

    public double vomitSevereChance() {
        return plugin.getConfig().getDouble("vomit.severe-chance", 0.3);
    }

    public double vomitOverdoseChance() {
        return plugin.getConfig().getDouble("vomit.overdose-chance", 1.0);
    }

    public int vomitCooldownSeconds() {
        return plugin.getConfig().getInt("vomit.cooldown-seconds", 30);
    }

    public double alcoholBlackoutThreshold() {
        return plugin.getConfig().getDouble("blackout.alcohol-threshold", 90.0);
    }

    public double blackoutDamagePerPulse() {
        return plugin.getConfig().getDouble("blackout.damage-per-pulse", 2.0);
    }

    public int blackoutPulseIntervalSeconds() {
        return plugin.getConfig().getInt("blackout.pulse-interval-seconds", 5);
    }

    public double comboDepressantAlcoholMultiplier() {
        return plugin.getConfig().getDouble("combo.depressant-alcohol-bonus-multiplier", 1.5);
    }

    public int rehabCooldownHours() {
        return plugin.getConfig().getInt("rehab.cooldown-hours", 24);
    }

    public void reload() {
        plugin.reloadConfig();
    }

    private String titleCase(String enumName) {
        String raw = enumName.toLowerCase().replace('_', ' ');
        return Character.toUpperCase(raw.charAt(0)) + raw.substring(1);
    }
}
