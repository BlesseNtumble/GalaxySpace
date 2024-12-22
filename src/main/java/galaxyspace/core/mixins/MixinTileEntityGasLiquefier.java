package galaxyspace.core.mixins;

import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.planets.mars.tile.TileEntityGasLiquefier;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;

@Mixin(value = TileEntityGasLiquefier.class, remap = false)
public class MixinTileEntityGasLiquefier extends TileEntity {

    /**
     * @author ViTold
     * @reason
     */
    @Overwrite
    public int getAirProducts() {
        GalaxySpace.info("MIXIN TEST AIR PRODUCTS IN GL");
        TileEntityGasLiquefier gl = (TileEntityGasLiquefier)(Object)this;

        WorldProvider WP = this.getWorld().provider;
        if (WP instanceof IGalacticraftWorldProvider) {
            int result = 0;
            ArrayList<EnumAtmosphericGas> atmos = ((IGalacticraftWorldProvider)WP).getCelestialBody().atmosphere.composition;
            if (atmos.size() > 0) {
                result = gl.getIdFromName(((EnumAtmosphericGas)atmos.get(0)).name().toLowerCase()) + 1;
            }

            if (atmos.size() > 1) {
                result += 16 * (gl.getIdFromName(((EnumAtmosphericGas)atmos.get(1)).name().toLowerCase()) + 1);
            }

            if (atmos.size() > 2) {
                result += 256 * (gl.getIdFromName(((EnumAtmosphericGas)atmos.get(2)).name().toLowerCase()) + 1);
            }

            return result;
        }
        return 35;
    }

}
