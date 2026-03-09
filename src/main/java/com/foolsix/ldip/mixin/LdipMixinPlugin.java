package com.foolsix.ldip.mixin;

import com.foolsix.ldip.Ldip;
import net.minecraftforge.fml.loading.FMLPaths;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class LdipMixinPlugin implements IMixinConfigPlugin {
    private static boolean ENHANCE_MODE = false;

    @Override
    public void onLoad(String s) {
        Path configPath = FMLPaths.CONFIGDIR.get().resolve("ldip.toml");
        try {
            List<String> lines = Files.readAllLines(configPath);
            for (int i = lines.size() - 1; i > 0; --i) {
                String line = lines.get(i);
                if (line.contains("EnhanceMode")) {
                    if (line.contains("true")) {
                        ENHANCE_MODE = true;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.endsWith("ServerLevelMixin")) {
            return ENHANCE_MODE;
        }
        if (mixinClassName.endsWith("PlayerMixin")) {
            return !ENHANCE_MODE;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> set, Set<String> set1) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {

    }

    @Override
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {

    }
}
