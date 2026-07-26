package com.nexusuniverse.vice.substances;

/**
 * These are original, invented substances -- not stand-ins for any real
 * drug. The enum name is only ever used as an internal config/data key
 * (lowercased, e.g. "fentinoli"); every DISPLAY name is read from
 * config.yml at runtime, so renaming what players see is a one-line
 * config edit, never a code change.
 *
 * The numbers here are DEFAULTS -- every one of them is overridable per
 * substance in config.yml under substances.<key>.*.
 */
public enum Substance {

    // category, dose added per item consumed, dose level that counts as "overdose", whether overdose has a real damage/blackout consequence
    FENTINOLI(Category.DEPRESSANT, 40, 55, true),
    XANAXEL(Category.DEPRESSANT, 30, 80, true),
    OPIATRIX(Category.DEPRESSANT, 35, 65, true),
    MOLOTINE(Category.STIMULANT, 30, 90, false),
    COCAINIUM(Category.STIMULANT, 35, 100, false),
    MOLLYQ(Category.STIMULANT, 30, 100, false),
    ACIDROP(Category.HALLUCINOGEN, 25, Integer.MAX_VALUE, false),
    HERBALIS(Category.MELLOW, 15, 200, false);

    private final Category category;
    private final double defaultDosePerItem;
    private final double defaultOverdoseThreshold;
    private final boolean defaultHasOverdoseRisk;

    Substance(Category category, double defaultDosePerItem, double defaultOverdoseThreshold, boolean defaultHasOverdoseRisk) {
        this.category = category;
        this.defaultDosePerItem = defaultDosePerItem;
        this.defaultOverdoseThreshold = defaultOverdoseThreshold;
        this.defaultHasOverdoseRisk = defaultHasOverdoseRisk;
    }

    public Category category() {
        return category;
    }

    public double defaultDosePerItem() {
        return defaultDosePerItem;
    }

    public double defaultOverdoseThreshold() {
        return defaultOverdoseThreshold;
    }

    public boolean defaultHasOverdoseRisk() {
        return defaultHasOverdoseRisk;
    }

    /** The config key this substance is looked up under, e.g. "fentinoli". */
    public String configKey() {
        return name().toLowerCase();
    }
}
