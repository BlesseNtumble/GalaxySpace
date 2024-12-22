package galaxyspace.core.mixins;

import micdoodle8.mods.galacticraft.planets.venus.dimension.WorldProviderVenus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = WorldProviderVenus.class, remap = false)
public class MixinWorldProviderVenus {


    /**
     * @author ViTold
     * @reason
     */
    @Overwrite
    public double getSolarEnergyMultiplier() {
        return 0;
    }
}
