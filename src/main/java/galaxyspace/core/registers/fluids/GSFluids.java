package galaxyspace.core.registers.fluids;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.core.prefab.blocks.BlockFluidGS;
import galaxyspace.core.prefab.items.ItemGSBucket;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.moons.titan.blocks.TitanBlockMethane;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemGSCanisterGeneric;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemHeliumCanister;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemHeliumHydrogenCanister;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemHydrogenCanister;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class GSFluids 
{
	
	public static Fluid LiquidEthaneMethane;
	public static Fluid Helium3;
	public static Fluid Hydrogen2;
	public static Fluid HeliumHydrogen;
	public static Fluid SulfurAcid;
	//--------------------------------------
	public static Block BlockHelium3;
	public static Block BlockHydrogen2;
	public static Block BlockLiquidMethane;
	public static Block BlockHeliumHydrogen;
	public static Block BlockSulfurAcid;
	

	public static void initialize() {
		
		FluidRegistration();
		BlockFluidRegistration();
		ItemsFluidRegistration();
	}
	
	public static void FluidRegistration()
	{
		LiquidEthaneMethane = new Fluid("liquidethanemethane").setGaseous(false).setDensity(400).setViscosity(2500);
		Helium3 = new Fluid("helium3").setGaseous(true).setDensity(-1000).setViscosity(1000);
		Hydrogen2 = new Fluid("hydrogen2").setGaseous(false).setDensity(1000).setViscosity(3000);
		
		HeliumHydrogen = registerFluid("heliumhydrogen", 1200, 1500, 10, false);
		SulfurAcid = registerFluid("sulfuricacid", 1000, 1500, 150, false);
		
        FluidRegistry.registerFluid(LiquidEthaneMethane);
        FluidRegistry.registerFluid(Helium3);
        FluidRegistry.registerFluid(Hydrogen2);       
        
	}
	
	private static Fluid registerFluid(String fluidName, int density, int viscosity, int temperature, boolean gaseous)
    {
        Fluid returnFluid = FluidRegistry.getFluid(fluidName);
    	if (returnFluid == null)
        {
    		FluidRegistry.registerFluid(new Fluid(fluidName).setDensity(density).setViscosity(viscosity).setTemperature(temperature).setGaseous(gaseous));
    		returnFluid = FluidRegistry.getFluid(fluidName);
        }
    	return returnFluid;
    }
	
	public static void BlockFluidRegistration()
	{
		BlockLiquidMethane = new TitanBlockMethane(LiquidEthaneMethane, Material.water).setBlockName("EthaneMethane");
        BlockHelium3 = new BlockFluidGS(Helium3, Material.water, false).setBlockName("Helium3");
        BlockHydrogen2 = new BlockFluidGS(Hydrogen2, Material.water, false).setBlockName("Hydrogen2");
        BlockHeliumHydrogen = new BlockFluidGS(HeliumHydrogen, Material.water, false).setBlockName("HeliumHydrogen"); 
        BlockSulfurAcid = new BlockFluidGS(SulfurAcid, Material.water, true).setBlockName("SulfurAcid"); 
        
        GameRegistry.registerBlock(BlockLiquidMethane, "liquidethanemethane");
        GameRegistry.registerBlock(BlockHelium3, "helium3");
        GameRegistry.registerBlock(BlockHydrogen2, "hydrogen2");
        GameRegistry.registerBlock(BlockHeliumHydrogen, "heliumhydrogen");
        GameRegistry.registerBlock(BlockSulfurAcid, "sulfuracid");
	}
	
	public static void ItemsFluidRegistration()
	{
		GSItems.IceBucket = new ItemGSBucket(Blocks.ice, "IceBucket", "bucket_ice");
		GSItems.DetheriumBucket = new ItemGSBucket(BlockHydrogen2, "DetheriumBucket", "bucket_detherium");
		GSItems.EthaneMethaneBucket = new ItemGSBucket(BlockLiquidMethane, "EthaneMethaneBucket", "bucket_methane");
		GSItems.HeliumHydrogenBucket = new ItemGSBucket(BlockHeliumHydrogen, "HeliumHydrogenBucket", "bucket_heliumhydrogen_fuel");
		//GSItems.MethaneBucket = new ItemMethaneBucket(BlockLiquidMethane);
		GSItems.Helium3Canister = new ItemHeliumCanister("ItemHelium3Canister");
		GSItems.HydrogenCanister = new ItemHydrogenCanister("ItemHydrogenCanister");
		GSItems.HeliumHydrogenCanister = new ItemHeliumHydrogenCanister("ItemHeliumHydrogenCanister");
		GSItems.SulfurAcidBucket = new ItemGSBucket(BlockSulfurAcid, "SulfurAcidBucket", "bucket_sulfur_acid");
		
        GSItems.registerItem(GSItems.EthaneMethaneBucket);
        GSItems.registerItem(GSItems.Helium3Canister);
        GSItems.registerItem(GSItems.HydrogenCanister);
        GSItems.registerItem(GSItems.HeliumHydrogenCanister);

        GSItems.registerItem(GSItems.IceBucket);
        GSItems.registerItem(GSItems.SulfurAcidBucket);
        GSItems.registerItem(GSItems.HeliumHydrogenBucket);
        
        FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(HeliumHydrogen, FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(GSItems.HeliumHydrogenCanister, 1, 1), new ItemStack(GCItems.oilCanister, 1, ItemCanisterGeneric.EMPTY)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(HeliumHydrogen, FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(GSItems.HeliumHydrogenBucket), new ItemStack(Items.bucket)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(LiquidEthaneMethane, FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(GSItems.EthaneMethaneBucket), new ItemStack(Items.bucket)));
        //FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(Hydrogen2, FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(GSItems.DetheriumBucket), new ItemStack(Items.bucket)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(SulfurAcid, FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(GSItems.SulfurAcidBucket), new ItemStack(Items.bucket)));
        FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(Helium3, 1000), new ItemStack(GSItems.Helium3Canister, 1, 1), new ItemStack(GSItems.Helium3Canister, 1, ItemGSCanisterGeneric.EMPTY)));
        
        FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(FluidRegistry.getFluid("hydrogen"), 1000), new ItemStack(GSItems.HydrogenCanister, 1, 1), new ItemStack(GCItems.oilCanister, 1, ItemCanisterGeneric.EMPTY)));

	}
}