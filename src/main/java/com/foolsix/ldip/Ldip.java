package com.foolsix.ldip;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;


@Mod(Ldip.MOD_ID)
public class Ldip {
    public static final String MOD_ID = "ldip";

    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec.IntValue DAMAGE_INDICATOR_CAP;
    public static final ForgeConfigSpec.DoubleValue MULTIPLIER;

    static {
        ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();
        CONFIG_BUILDER.push("Limited Damage Indicator Particle");
        DAMAGE_INDICATOR_CAP = CONFIG_BUILDER.defineInRange("MaximumNumberOfDamageIndicatorParticle", 100, 0, Integer.MAX_VALUE);
        MULTIPLIER = CONFIG_BUILDER.defineInRange("DamageIndicatorParticleMultiplier", 1.0, 0, Double.MAX_VALUE);
        CONFIG_BUILDER.pop();
        COMMON_CONFIG = CONFIG_BUILDER.build();
    }

    public Ldip() {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG, MOD_ID + ".toml");
    }
}
