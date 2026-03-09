package com.foolsix.ldip.mixin;

import com.foolsix.ldip.Ldip;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @Inject(method = "sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", at = @At("HEAD"), cancellable = true)
    private <T extends ParticleOptions> void modifyParticles(
            T pType,
            double pPosX,
            double pPosY,
            double pPosZ,
            int pParticleCount,
            double pXOffset,
            double pYOffset,
            double pZOffset,
            double pSpeed,
            CallbackInfoReturnable<Integer> cir
    ) {
        if (pType == ParticleTypes.DAMAGE_INDICATOR) {

            pParticleCount = (int)Math.min(pParticleCount * Ldip.MULTIPLIER.get(), Ldip.DAMAGE_INDICATOR_CAP.get());

            if (pParticleCount <= 0) {
                cir.setReturnValue(0);
                return;
            }

            ServerLevel level = (ServerLevel)(Object)this;

            ClientboundLevelParticlesPacket clientboundlevelparticlespacket = new ClientboundLevelParticlesPacket(pType, false, pPosX, pPosY, pPosZ, (float)pXOffset, (float)pYOffset, (float)pZOffset, (float)pSpeed, pParticleCount);
            int i = 0;

            for(int j = 0; j < level.players().size(); ++j) {
                ServerPlayer serverplayer = level.players().get(j);
                if (((ServerLevelInvoker)level).invokeSendParticles(serverplayer, false, pPosX, pPosY, pPosZ, clientboundlevelparticlespacket)) {
                    ++i;
                }
            }
            cir.setReturnValue(i);
        }
    }
}
