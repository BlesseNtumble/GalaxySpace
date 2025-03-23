package galaxyspace.core.mixins;

import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.planets.mars.tile.TileEntityMethaneSynthesizer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;

@Mixin(TileEntityMethaneSynthesizer.class)
public class MixinTileEntityMethaneSynthesizer extends TileEntity {

    /**
     * @author ViTold
     * @reason
     */
    @Overwrite(remap = false)
    public int getAirProducts(){
        WorldProvider WP = this.getWorld().provider;
        if (WP instanceof IGalacticraftWorldProvider) {
            ArrayList<EnumAtmosphericGas> atmos = ((IGalacticraftWorldProvider) WP).getCelestialBody().atmosphere.composition;

            if (atmos.contains(EnumAtmosphericGas.CO2))
                return 1;
        }
        return 0;
    }
}
