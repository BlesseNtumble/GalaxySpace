package galaxyspace.core.mixins;

import galaxyspace.core.configs.GSConfigCore;
import micdoodle8.mods.galacticraft.planets.asteroids.dimension.WorldProviderAsteroids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WorldProviderAsteroids.class, remap = false)
public class MixinWorldProviderAsteroids {

    /**
     * @author
     * @reason
     */
    @Inject(at = @At("HEAD"), method = "getGravity", cancellable = true)
    public void getGravity(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(GSConfigCore.enableZeroGravityOnAsteroids ? 0.08F : 0.072F);
    }
}
