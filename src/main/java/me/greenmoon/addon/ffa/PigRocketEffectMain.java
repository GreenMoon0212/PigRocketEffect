package me.greenmoon.addon.ffa;

import me.bedtwL.AliveFFA.api.effect.EffectAddon;
import me.bedtwL.AliveFFA.api.effect.EffectRegistry;
import me.bedtwL.AliveFFA.api.effect.EffectRegistryProvider;
import me.bedtwL.AliveFFA.api.effect.SimpleEffectRegistry;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.UUID;

public class PigRocketEffectMain extends JavaPlugin {

    @Override
    public void onEnable() {
        // create Addon
        FFAHandler handler = new FFAHandler();

        // resign豬豬特效
        PigRocketEffect effect = new PigRocketEffect();
        effect.registerKillEffect(handler);

        getLogger().info("飛天豬豬 Addon loaded");
    }


    public class FFAHandler implements EffectAddon, EffectRegistryProvider {
        private final EffectRegistry registry = new SimpleEffectRegistry();

        @Override
        public EffectRegistry getEffectRegistry() {
            return this.registry;
        }

        @Override
        public String getName() {
            return "PigRocketAddon";
        }

        @Override
        public String getAuthor() {
            return "GreenMoon";
        }

        @Override
        public UUID authorUUID() {
            // GreenMoon_TW UUID
            return UUID.fromString("53aaa7fb-569e-4391-9323-5762af38f255");
        }

        @Override
        public void onEnable() {}

        @Override
        public void onDisable() {}
    }
}