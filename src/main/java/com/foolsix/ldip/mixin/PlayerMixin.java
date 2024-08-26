package com.foolsix.ldip.mixin;

import com.foolsix.ldip.Ldip;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
abstract class PlayerMixin<T extends ParticleOptions> {
    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"))
    private int limitParticle(ServerLevel instance, T pType, double pPosX, double pPosY, double pPosZ, int pParticleCount, double pXOffset, double pYOffset, double pZOffset, double pSpeed) {
        int newParticleCount = (int) Math.min(pParticleCount * Ldip.MULTIPLIER.get(), Ldip.DAMAGE_INDICATOR_CAP.get());
        if (newParticleCount <= 0) return 0;
        return instance.sendParticles(pType, pPosX, pPosY, pPosZ, newParticleCount, pXOffset, pYOffset, pZOffset, pSpeed);
    }
}
