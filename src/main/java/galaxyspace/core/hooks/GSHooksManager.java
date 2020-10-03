package galaxyspace.core.hooks;

import java.util.ArrayList;

import galaxyspace.core.events.SetBlockEvent;
import galaxyspace.core.hooklib.asm.Hook;
import galaxyspace.core.hooklib.asm.ReturnCondition;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.planets.mars.tile.TileEntityGasLiquefier;
import micdoodle8.mods.galacticraft.planets.mars.tile.TileEntityMethaneSynthesizer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.MinecraftForge;

public class GSHooksManager {
	

	//Регистрируем эвент установки блока.
	@Hook(returnCondition = ReturnCondition.ON_TRUE, booleanReturnConstant = false) 
    public static boolean setBlockState(World world, BlockPos pos, IBlockState newState, int flags) { 
    	return MinecraftForge.EVENT_BUS.post(new SetBlockEvent(world, pos, newState, flags)); 
    }
	
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static int getAirProducts(TileEntityMethaneSynthesizer te) {
		
		WorldProvider WP = te.getWorld().provider;
        if (WP instanceof IGalacticraftWorldProvider)
        {
            ArrayList<EnumAtmosphericGas> atmos = ((IGalacticraftWorldProvider) WP).getCelestialBody().atmosphere.composition;
            if (atmos.size() > 0)
            {
                if (atmos.get(0) == EnumAtmosphericGas.CO2)
                {
                    return 1;
                }
            }
            if (atmos.size() > 1)
            {
                if (atmos.get(1) == EnumAtmosphericGas.CO2)
                {
                    return 1;
                }
            }
            if (atmos.size() > 2)
            {
                if (atmos.get(2) == EnumAtmosphericGas.CO2)
                {
                    return 1;
                }
            }

            return 0;
        }

        return 0;
	}
	
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static int getAirProducts(TileEntityGasLiquefier te) {
		WorldProvider WP = te.getWorld().provider;
		if (WP instanceof IGalacticraftWorldProvider) {
			int result = 0;
			ArrayList<EnumAtmosphericGas> atmos = ((IGalacticraftWorldProvider) WP)
					.getCelestialBody().atmosphere.composition;
			if (atmos.size() > 0) {
				result = te.getIdFromName(atmos.get(0).name().toLowerCase()) + 1;
			}
			if (atmos.size() > 1) {
				result += 16 * (te.getIdFromName(atmos.get(1).name().toLowerCase()) + 1);
			}
			if (atmos.size() > 2) {
				result += 256 * (te.getIdFromName(atmos.get(2).name().toLowerCase()) + 1);
			}

			return result;
		}

		return 35;
	}
	/*
	@Hook(returnCondition = ReturnCondition.ALWAYS)
	public static void updateLightmap(float partialTicks)
	{
		if(lightmapUpdateNeeded)
		{
			
		}
	}*/
}