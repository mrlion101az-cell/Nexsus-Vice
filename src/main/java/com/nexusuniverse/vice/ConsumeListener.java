package com.nexusuniverse.vice;

import com.nexusuniverse.vice.alcohol.AlcoholItems;
import com.nexusuniverse.vice.alcohol.AlcoholType;
import com.nexusuniverse.vice.substances.Substance;
import com.nexusuniverse.vice.substances.SubstanceItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ConsumeListener implements Listener {

    private final SubstanceItems substanceItems;
    private final AlcoholItems alcoholItems;
    private final ViceDataManager viceData;
    private final ViceConfig config;

    public ConsumeListener(SubstanceItems substanceItems, AlcoholItems alcoholItems, ViceDataManager viceData, ViceConfig config) {
        this.substanceItems = substanceItems;
        this.alcoholItems = alcoholItems;
        this.viceData = viceData;
        this.config = config;
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        Substance substance = substanceItems.readSubstance(event.getItem());
        if (substance != null) {
            VicePlayerData data = viceData.get(player.getUniqueId());
            data.addSubstanceDose(substance, config.dosePerItem(substance));
            player.sendMessage("§7You take " + config.displayName(substance) + ".");
            return;
        }

        AlcoholType alcohol = alcoholItems.readType(event.getItem());
        if (alcohol != null) {
            VicePlayerData data = viceData.get(player.getUniqueId());
            data.addAlcoholDose(config.dosePerItem(alcohol));
            player.sendMessage("§7You drink " + config.displayName(alcohol) + ".");
        }
    }
}
