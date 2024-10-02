package galaxyspace.core.achievements;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class AchievementList {
	
	public static final Achievement MiningCopperGC = new Achievement("miningcoppergc", "miningcoppergc", -5, -5, new ItemStack(GCBlocks.basicBlock, 1, 5), null).registerStat();
	public static final Achievement MiningTinGC = new Achievement("miningtingc", "miningtingc", -4, -5, new ItemStack(GCBlocks.basicBlock, 1, 6), null).registerStat();
	public static final Achievement MiningAluminiumGC = new Achievement("miningaluminiumgc", "miningaluminiumgc", -5, -4, new ItemStack(GCBlocks.basicBlock, 1, 7), null).registerStat();
	public static final Achievement MiningSiliconGC = new Achievement("miningsilicongc", "miningsilicongc", -4, -4, new ItemStack(GCBlocks.basicBlock, 1, 8), null).registerStat();
	//public static final Achievement LeadArmorGC = new Achievement("leadarmorgc", "leadarmorgc", -5, -3, new ItemStack(GSItems.LeadPlate, 1, 0), null).registerStat();
	
	public static final Achievement ElectricGC = new Achievement("electricgc", "electricgc", 1, -5, new ItemStack(GCBlocks.aluminumWire, 1, 0), null).registerStat();
	public static final Achievement Electric2GC = new Achievement("electric2gc", "electric2gc", 2, -5, new ItemStack(GCBlocks.aluminumWire, 1, 1), ElectricGC).registerStat();
	public static final Achievement EnergyCellGC = new Achievement("energycellgc", "energycellgc", 4, -5, new ItemStack(GCBlocks.machineTiered, 1, 0), Electric2GC).registerStat();
	
	public static final Achievement CoalGeneratorGC = new Achievement("coalgeneratorgc", "coalgeneratorgc", 1, -3, new ItemStack(GCBlocks.machineBase, 1, 0), ElectricGC).registerStat();
	public static final Achievement FuelGeneratorGC = new Achievement("geothermalgeneratorgs", "geothermalgeneratorgs", 3, -3, new ItemStack(GSBlocks.FuelGenerator, 1, 0), CoalGeneratorGC).registerStat();
	public static final Achievement SolarGC = new Achievement("solargc", "solargc", 2, -6, new ItemStack(GCItems.basicItem, 1, 1), Electric2GC).registerStat();
	public static final Achievement CircuitGC = new Achievement("circuitgc", "circuitgc", 1, -1, new ItemStack(GCBlocks.machineBase2, 1, 4), CoalGeneratorGC).registerStat();
	public static final Achievement CompressorGC = new Achievement("compressorgc", "compressorgc", -1, -1, new ItemStack(GCBlocks.machineBase, 1, 12), CircuitGC).registerStat();
	public static final Achievement HdpGC = new Achievement("hdpgc", "hdpgc", -1, -2, new ItemStack(GCItems.heavyPlatingTier1, 1, 0), CompressorGC).registerStat();
	public static final Achievement Hdp2GC = new Achievement("hdp2gc", "hdp2gc", -1, -3, new ItemStack(MarsItems.marsItemBasic, 1, 3), HdpGC).registerStat();
	
	public static final Achievement FanGC = new Achievement("fangc", "fangc", -2, -1, new ItemStack(GCItems.oxygenFan, 1, 0), CompressorGC).registerStat();
	public static final Achievement FuranceGC = new Achievement("furancegc", "furancegc", 3, -1, new ItemStack(GCBlocks.machineTiered, 1, 4), CircuitGC).registerStat();
	public static final Achievement AssemblyGC = new Achievement("assemblygc", "assemblygc", 1, 1, new ItemStack(GSBlocks.AssemblyMachine, 1, 0), CircuitGC).registerStat();
	public static final Achievement NASAGC = new Achievement("nasagc", "nasagc", 1, 2, new ItemStack(GCBlocks.nasaWorkbench, 1, 0), AssemblyGC).registerStat().setSpecial();
	public static final Achievement RefineryGC = new Achievement("refinerygc", "refinerygc", -1, 0, new ItemStack(GCBlocks.refinery, 1, 0), CompressorGC).registerStat();
	public static final Achievement OilGC = new Achievement("oilgc", "oilgc", -1, 2, new ItemStack(GCItems.bucketOil, 1, 0), RefineryGC).registerStat();
	public static final Achievement FuelGC = new Achievement("fuelgc", "fuelgc", -2, 2, new ItemStack(GCItems.bucketFuel, 1, 0), OilGC).registerStat();
	
	public static final Achievement RocketGC = new Achievement("rocketgc", "rocketgc", 1, 3, new ItemStack(GCItems.rocketTier1, 1, 0), NASAGC).registerStat();
	public static final Achievement MoonGC = new Achievement("moongc", "moongc", 1, 5, new ItemStack(GCBlocks.blockMoon, 1, 2), RocketGC).registerStat().setSpecial();
	public static final Achievement BossMoonGC = new Achievement("bossmoongc", "bossmoongc", -1, 5, new ItemStack(Items.skull, 1, 1), MoonGC).registerStat().setSpecial();
	public static final Achievement FallingMetallGC = new Achievement("fallingmetallgc", "fallingmetallgc", 0, 6, new ItemStack(GCBlocks.fallenMeteor, 1, 0), MoonGC).registerStat();
	public static final Achievement JetpackGC = new Achievement("jetpackgc", "jetpackgc", 0, 7, new ItemStack(GSItems.JetPack, 1, 0), FallingMetallGC).registerStat();
	public static final Achievement Rocket2GC = new Achievement("rocket2gc", "rocket2gc", -3, 5, new ItemStack(MarsItems.spaceship, 1, 0), BossMoonGC).registerStat();
	public static final Achievement MarsGC = new Achievement("marsgc", "marsgc", -3, 3, new ItemStack(MarsBlocks.marsBlock, 1, 9), Rocket2GC).registerStat().setSpecial();
	
	public static void load()
	{
		AchievementPage.registerAchievementPage(new AchievementPage
				(
				"GalactiCraft3",
				MiningCopperGC,
				MiningTinGC,
				MiningAluminiumGC,
				MiningSiliconGC,
				//LeadArmorGC,
				FanGC,
				ElectricGC,
				Electric2GC,
				EnergyCellGC,
				CoalGeneratorGC,
				FuelGeneratorGC,
				SolarGC,
				CircuitGC,
				CompressorGC,
				HdpGC,
				Hdp2GC,
				FuranceGC,
				AssemblyGC,
				NASAGC,
				OilGC,
				RefineryGC,
				FuelGC,
				RocketGC,
				MoonGC,
				BossMoonGC,
				FallingMetallGC,
				JetpackGC,
				Rocket2GC,
				MarsGC
				)
		);
	}
}
