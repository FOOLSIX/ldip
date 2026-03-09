package com.foolsix.ldip.mixin;

import com.foolsix.ldip.Ldip;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Player.class)
abstract class PlayerMixin {
    @ModifyArg(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"
            ),
            index = 4
    )
    private int modifyParticleCount(int k) {
        return (int) Math.min(k * Ldip.MULTIPLIER.get(), Ldip.DAMAGE_INDICATOR_CAP.get());
    }
}
