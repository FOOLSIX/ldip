package com.foolsix.ldip;


import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

@Mod(Ldip.MOD_ID)
public class Ldip {
    public static final String MOD_ID = "ldip";

    public static final ModConfigSpec COMMON_CONFIG;
    public static final ModConfigSpec.BooleanValue ALWAYS_SHOW;
    public static final ModConfigSpec.IntValue DAMAGE_INDICATOR_CAP;
    public static final ModConfigSpec.DoubleValue MULTIPLIER;

    static {
        ModConfigSpec.Builder CONFIG_BUILDER = new ModConfigSpec.Builder();
        CONFIG_BUILDER.push("Limited Damage Indicator Particle");
        ALWAYS_SHOW = CONFIG_BUILDER.define("DisplayDamageParticlesEvenWhenDamageIsLow", false);
        DAMAGE_INDICATOR_CAP = CONFIG_BUILDER.defineInRange("MaximumNumberOfDamageIndicatorParticle", 100, 0, Integer.MAX_VALUE);
        MULTIPLIER = CONFIG_BUILDER.defineInRange("DamageIndicatorParticleMultiplier", 1.0, 0, Double.MAX_VALUE);
        CONFIG_BUILDER.pop();
        COMMON_CONFIG = CONFIG_BUILDER.build();
    }

    public Ldip(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG, MOD_ID + ".toml");
    }
}
