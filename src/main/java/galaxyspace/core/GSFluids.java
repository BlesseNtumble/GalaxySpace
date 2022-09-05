package galaxyspace.core;

import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.blocks.BlockFluidGS;
import galaxyspace.core.prefab.blocks.BlockFluidNatureGas;
import galaxyspace.systems.SolarSystem.moons.titan.blocks.BlockLiquidEthaneMethane;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class GSFluids {

	public static Fluid LiquidEthaneMethane;
	public static Fluid Helium3;
	public static Fluid Ethane;
	public static Fluid HeliumHydrogen;
	public static Fluid NatureGas;
	
	public static Block BLOCK_LEMETHANE;
	public static Block BLOCK_HELIUM3;
	public static Block BLOCK_ETHANE;
	public static Block BLOCK_HELIUM_HYDROGEN;
	public static Block BLOCK_NATURE_GAS;
	
	public static final Material LEMethane = new MaterialLiquid(MapColor.BROWN){
		
		@Override
		public EnumPushReaction getPushReaction() {
			return EnumPushReaction.DESTROY;
		}
	};
	public static final Material HELIUM = new MaterialLiquid(MapColor.SNOW){
		
		@Override
		public EnumPushReaction getPushReaction() {
			return EnumPushReaction.DESTROY;
		}
	};
	public static final Material HH = new MaterialLiquid(MapColor.PINK){
		
		@Override
		public EnumPushReaction getPushReaction(){
			return EnumPushReaction.DESTROY;
		}
	};
	
	public static void initialize()
    {
		LiquidEthaneMethane = registerFluid("liquidethanemethane", 1200, 140, 90, false, "liquid_ethanemethane");
		Helium3 = registerFluid("helium3", -1000, 1000, 0, true, "liquid_helium3");
		Ethane = registerFluid("ethane", -1000, 1000, 0, true, "liquid_ethane");
		HeliumHydrogen = registerFluid("heliumhydrogen", 1200, 140, 90, false, "liquid_heliumhydrogen");
		
		NatureGas = registerFluid("naturegas", -800, 1000, 10, true, "liquid_naturegas");
		
		GalacticraftCore.proxy.registerFluidTexture(LiquidEthaneMethane, new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/fluids/liquid_ethanemethane_still.png"));
		
		BlockFluidRegistration();		
    }	
	
	public static void BlockFluidRegistration()
	{
		BLOCK_LEMETHANE = new BlockLiquidEthaneMethane();
		BLOCK_HELIUM3 = new BlockFluidGS(Helium3, HELIUM, false);
		BLOCK_ETHANE = new BlockFluidGS(Ethane, HH, false);
		BLOCK_HELIUM_HYDROGEN = new BlockFluidGS(HeliumHydrogen, HH, false);
		BLOCK_NATURE_GAS = new BlockFluidNatureGas(NatureGas, HELIUM);
		
		GSBlocks.registerBlock(BLOCK_LEMETHANE, null);
		GSBlocks.registerBlock(BLOCK_HELIUM3, null);
		GSBlocks.registerBlock(BLOCK_ETHANE, null);
		GSBlocks.registerBlock(BLOCK_HELIUM_HYDROGEN, null);
		GSBlocks.registerBlock(BLOCK_NATURE_GAS, null);
	}
	
	private static Fluid registerFluid(String fluidName, int density, int viscosity, int temperature, boolean gaseous, String fluidTexture) {
		Fluid returnFluid = FluidRegistry.getFluid(fluidName);

		if (returnFluid == null) {
			
			ResourceLocation textureStill = new ResourceLocation(GalaxySpace.TEXTURE_PREFIX + "fluids/" + fluidTexture + "_still");
			ResourceLocation textureFlowing = new ResourceLocation(GalaxySpace.TEXTURE_PREFIX + "fluids/" + fluidTexture + "_flowing");
			FluidRegistry.registerFluid(new Fluid(fluidName, textureStill, textureFlowing).setDensity(density).setViscosity(viscosity).setTemperature(temperature).setGaseous(gaseous));
			returnFluid = FluidRegistry.getFluid(fluidName);
			//if(!returnFluid.isGaseous())
				FluidRegistry.addBucketForFluid(returnFluid);
		} else {
			returnFluid.setGaseous(gaseous);
		}
		
		//GalaxySpace.debug(returnFluid.getName() + "");

		return returnFluid;
	}

}
