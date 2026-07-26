package com.nexusuniverse.vice;

import com.nexusuniverse.vice.alcohol.AlcoholItems;
import com.nexusuniverse.vice.alcohol.AlcoholType;
import com.nexusuniverse.vice.substances.Substance;
import com.nexusuniverse.vice.substances.SubstanceItems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ViceCommand implements CommandExecutor {

    private final ViceConfig config;
    private final ViceDataManager viceData;
    private final SubstanceItems substanceItems;
    private final AlcoholItems alcoholItems;

    public ViceCommand(ViceConfig config, ViceDataManager viceData, SubstanceItems substanceItems, AlcoholItems alcoholItems) {
        this.config = config;
        this.viceData = viceData;
        this.substanceItems = substanceItems;
        this.alcoholItems = alcoholItems;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Players only.");
            return true;
        }

        if (args.length == 0 || args[0].equalsIgnoreCase("status")) {
            sendStatus(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "rehab" -> handleRehab(player);
            case "give" -> handleGive(player, args);
            case "reload" -> handleReload(player);
            default -> player.sendMessage("§cUsage: /vice <status|rehab|give <name> [player]>");
        }
        return true;
    }

    private void sendStatus(Player player) {
        VicePlayerData data = viceData.get(player.getUniqueId());
        player.sendMessage("§7--- Status ---");

        boolean anyActive = false;
        for (Substance substance : Substance.values()) {
            double dose = data.substanceDose(substance);
            if (dose <= 0) continue;
            anyActive = true;
            double threshold = config.overdoseThreshold(substance);
            player.sendMessage("§f" + config.displayName(substance) + ": §e" + String.format("%.0f", dose)
                    + " §7/ " + String.format("%.0f", threshold) + " (overdose)");
        }
        if (data.alcoholDose() > 0) {
            anyActive = true;
            player.sendMessage("§fAlcohol: §e" + String.format("%.0f", data.alcoholDose())
                    + " §7/ " + String.format("%.0f", config.alcoholBlackoutThreshold()) + " (blackout)");
        }
        if (!anyActive) {
            player.sendMessage("§7Stone cold sober.");
        }
        if (data.isInBlackout()) {
            player.sendMessage("§4§lIN AN ACTIVE OVERDOSE -- taking damage until it passes or you get help.");
        }
    }

    private void handleRehab(Player player) {
        VicePlayerData data = viceData.get(player.getUniqueId());
        long cooldownTicks = 20L * 3600 * config.rehabCooldownHours();
        long now = player.getWorld().getFullTime();

        if (data.lastRehabTick() != Long.MIN_VALUE && now - data.lastRehabTick() < cooldownTicks) {
            long remainingTicks = cooldownTicks - (now - data.lastRehabTick());
            player.sendMessage("§cYou can't check into rehab again yet (" + (remainingTicks / 20 / 60) + " minutes left).");
            return;
        }

        data.clearAllDoses();
        data.setLastRehabTick(now);
        player.sendMessage("§aYou check into rehab. Everything clears -- a clean slate.");
    }

    private void handleGive(Player sender, String[] args) {
        if (!sender.hasPermission("nexusvice.admin")) {
            sender.sendMessage("§cNo permission.");
            return;
        }
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /vice give <name> [player]");
            return;
        }

        Player target = args.length >= 3 ? Bukkit.getPlayerExact(args[2]) : sender;
        if (target == null) {
            sender.sendMessage("§cPlayer not found.");
            return;
        }

        String name = args[1].toUpperCase();
        try {
            Substance substance = Substance.valueOf(name);
            target.getInventory().addItem(substanceItems.create(substance));
            sender.sendMessage("§aGave " + target.getName() + " " + config.displayName(substance) + ".");
            return;
        } catch (IllegalArgumentException ignored) {
            // not a substance name -- try alcohol next
        }

        try {
            AlcoholType alcohol = AlcoholType.valueOf(name);
            target.getInventory().addItem(alcoholItems.create(alcohol));
            sender.sendMessage("§aGave " + target.getName() + " " + config.displayName(alcohol) + ".");
        } catch (IllegalArgumentException e) {
            sender.sendMessage("§cUnknown name. Options: fentinoli, xanaxel, opiatrix, molotine, cocainium, mollyq, acidrop, herbalis, "
                    + "nicotane, caffinex, cryotine, sporeline, ketrazine, anabolex, driftweed, somnara, titanex, euphorion, blissenta, rapturine, "
                    + "beer, wine, liquor");
        }
    }

    private void handleReload(Player sender) {
        if (!sender.hasPermission("nexusvice.admin")) {
            sender.sendMessage("§cNo permission.");
            return;
        }
        config.reload();
        sender.sendMessage("§aConfig reloaded.");
    }
}
