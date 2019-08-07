package galaxyspace.core.handler;

import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_Biome;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_ChunkProvider;
import asmodeuscore.core.astronomy.dimension.world.worldengine.WE_WorldProvider;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.BarnardsSystem.core.configs.BRConfigCore;
import galaxyspace.systems.BarnardsSystem.core.registers.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.dimension.WorldProviderBarnarda_C_WE;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ColorBlockHandler {
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerBlockColourHandlers(ColorHandlerEvent.Block event) {
		final BlockColors blockColors = event.getBlockColors();

		
		// Use the grass colour of the biome or the default grass colour
		final IBlockColor grassColourHandler = (state, blockAccess, pos, tintIndex) -> {
			
			if (blockAccess != null && pos != null) {
				World world = FMLClientHandler.instance().getWorldClient();
				if(world != null && world.provider instanceof WE_WorldProvider)
				{				
					WE_Biome biome = WE_Biome.getBiomeAt((long)pos.getX(), (long)pos.getZ());
					return biome.biomeBlockGrassColor;					
				}
				
				return BiomeColorHelper.getGrassColorAtPos(blockAccess, pos);
			}			
			return ColorizerGrass.getGrassColor(0.5D, 1.0D);
		};
		
		final IBlockColor water_grassColourHandler = (state, blockAccess, pos, tintIndex) -> {
			return 0x88CC44;
		};
		
		if(BRConfigCore.enableBarnardsSystems) {
			blockColors.registerBlockColorHandler(grassColourHandler, Blocks.GRASS, BRBlocks.BARNARDA_C_GRASS, BRBlocks.BARNARDA_C_WATER_GRASS);
			blockColors.registerBlockColorHandler(water_grassColourHandler, BRBlocks.BARNARDA_C_WATER_GRASS);
		}
				
		final IBlockColor waterColourHandler = (state, blockAccess, pos, tintIndex) -> {
			
			if (blockAccess != null && pos != null) {				
				World world = FMLClientHandler.instance().getWorldClient();
				
				if(world != null && world.provider instanceof WE_WorldProvider)
				{		
					return WE_Biome.getBiomeAt((long)pos.getX(), (long)pos.getZ()).biomeBlockWaterColor;					
				}			
				
				return BiomeColorHelper.getWaterColorAtPos(blockAccess, pos);
			}

			return ColorizerGrass.getGrassColor(0.5D, 1.0D);
		};

		blockColors.registerBlockColorHandler(waterColourHandler, Blocks.WATER, Blocks.FLOWING_WATER);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerItemColourHandlers(ColorHandlerEvent.Item event) {
		final BlockColors blockColors = event.getBlockColors();
		final ItemColors itemColors = event.getItemColors();

		// Use the Block's colour handler for an ItemBlock
		final IItemColor itemBlockColourHandler = (stack, tintIndex) -> {
			//@SuppressWarnings("deprecation")
			final IBlockState state = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
			return blockColors.colorMultiplier(state, null, null, tintIndex);
		};

		if(BRConfigCore.enableBarnardsSystems)
			itemColors.registerItemColorHandler(itemBlockColourHandler, BRBlocks.BARNARDA_C_GRASS);	
		
	}
}
