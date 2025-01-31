package com.foolsix.ldip.mixin;

import com.foolsix.ldip.Ldip;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin<T extends IParticleData> {
    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/server/ServerWorld;sendParticles(Lnet/minecraft/particles/IParticleData;DDDIDDDD)I"))
    private int limitParticle(ServerWorld instance, T pType, double pPosX, double pPosY, double pPosZ, int pParticleCount, double pXOffset, double pYOffset, double pZOffset, double pSpeed) {
        int newParticleCount = (int) Math.min(pParticleCount * Ldip.MULTIPLIER.get(), Ldip.DAMAGE_INDICATOR_CAP.get());
        if (newParticleCount <= 0 && !Ldip.ALWAYS_SHOW.get()) return 0;
        return instance.sendParticles(pType, pPosX, pPosY, pPosZ, newParticleCount, pXOffset, pYOffset, pZOffset, pSpeed);
    }
}
