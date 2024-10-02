package galaxyspace.core.registers.fluids;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class FluidTexturesGS
{
    public static void init()
    {
        MinecraftForge.EVENT_BUS.register(new FluidTexturesGS());
    }


    @SubscribeEvent
    public void onStitch(TextureStitchEvent.Pre event)
    {
        if (event.map.getTextureType() == 0)
        {
        	GSFluids.LiquidEthaneMethane.setIcons(event.map.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/titan/methanestill"));
            //AsteroidsModule.fluidLiquidMethane.setIcons(event.map.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/titan/methanestill"));
            
            GSFluids.Helium3.setIcons(event.map.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfluids/helium3still"));
            GSFluids.Hydrogen2.setIcons(event.map.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfluids/hydrogen2still"));
            GSFluids.HeliumHydrogen.setIcons(event.map.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfluids/heliumhydrogenstill"));
            GSFluids.SulfurAcid.setIcons(event.map.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "overworld/blockfluids/sulfuricacidstill"));
            
        }
    }

}
