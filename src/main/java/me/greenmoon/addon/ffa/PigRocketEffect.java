package me.greenmoon.addon.ffa;

import me.bedtwL.AliveFFA.api.effect.PureKillEffect;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

public class PigRocketEffect extends PureKillEffect {

    @Override
    public void killEffect(Location location, Player victim, Player killer) {
        spawnPigRocket(location);
    }

    @Override
    public String getName() {
        return "飛天豬豬~~";
    }

    @Override
    public String getItemNameKey() {
        return "pig_rocket";
    }

    @Override
    public ItemStack getItemBase() {
        ItemStack item = new ItemStack(Material.PORK);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§e飛天豬豬~~");
            meta.setLore(Collections.singletonList("§7殺人豬心，這隻豬吃到火藥並爆炸了...."));
            item.setItemMeta(meta);
        }
        return item;
    }

    private void spawnPigRocket(final Location location) {
        final Pig pig = (Pig) location.getWorld().spawnEntity(location, EntityType.PIG);
        final double velocityPerTick = 0.233;
        final int durationTicks = 33;

        new BukkitRunnable() {
            int ticksPassed = 0;

            @Override
            public void run() {
                if (!pig.isValid() || ticksPassed >= durationTicks) {
                    if (pig.isValid()) {
                        Location loc = pig.getLocation();
                        // 純爆炸效果
                        loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 1F, false, false);
                        pig.remove();
                    }
                    this.cancel();
                    return;
                }

                // 向上速度
                Vector vel = pig.getVelocity();
                vel.setY(velocityPerTick);
                pig.setVelocity(vel);

                Location loc = pig.getLocation();

                // 每 12 ticks (0.6s)
                if (ticksPassed % 12 == 0) {
                    for (int i = 0; i < 3; i++) {
                        loc.getWorld().playEffect(loc, Effect.FLAME, 0);
                    }
                }

                // 煙霧效果保持每 tick 產生一點
                loc.getWorld().playEffect(loc, Effect.SMOKE, 0);

                // 音階逐步播放
                float[] pitches = {0.5f, 0.55f, 0.6f, 0.65f, 0.7f, 0.75f, 0.8f};
                int noteIndex = ticksPassed % pitches.length;
                loc.getWorld().playSound(loc, Sound.NOTE_PIANO, 1.0f, pitches[noteIndex]);

                ticksPassed++;
            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(PigRocketEffect.class), 0L, 1L);
    }
}