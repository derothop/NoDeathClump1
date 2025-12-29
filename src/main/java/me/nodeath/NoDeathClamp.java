package me.nodeath;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class NoDeathClamp extends JavaPlugin implements Listener {

    private static final double MIN_HEALTH = 1.0;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        if (isHoldingTotem(player)) return;

        double health = player.getHealth();
        double finalDamage = event.getFinalDamage();
        double newHealth = health - finalDamage;

        if (newHealth <= MIN_HEALTH) {
            event.setDamage(Math.max(0, health - MIN_HEALTH));
        }
    }

    private boolean isHoldingTotem(Player player) {
        ItemStack main = player.getInventory().getItemInMainHand();
        ItemStack off = player.getInventory().getItemInOffHand();

        return main.getType() == Material.TOTEM_OF_UNDYING
                || off.getType() == Material.TOTEM_OF_UNDYING;
    }
}
