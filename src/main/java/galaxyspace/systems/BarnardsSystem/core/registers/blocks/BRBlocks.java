package galaxyspace.systems.BarnardsSystem.core.registers.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks.Barnarda_C_Blocks;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks.Barnarda_C_Dandelions;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks.Barnarda_C_FallingBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks.Barnarda_C_Grass;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks.Barnarda_C_Leaves;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks.Barnarda_C_Log;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks.Barnarda_C_Ores;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.blocks.Barnarda_C_Water_Grass;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.items.ItemBlocksBarnardaC;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.items.ItemBlocksBarnardaCDandelions;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.items.ItemBlocksBarnardaCFallingBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.items.ItemBlocksBarnardaCGrass;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.items.ItemBlocksBarnardaCLeaves;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.items.ItemBlocksBarnardaCOres;
import galaxyspace.systems.BarnardsSystem.planets.barnardaE.blocks.Barnarda_E_Blocks;
import galaxyspace.systems.BarnardsSystem.planets.barnardaE.items.ItemBlocksBarnardaEBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BRBlocks {

	public static final Block BarnardaCGrass = new Barnarda_C_Grass();
	public static final Block BarnardaCBlocks = new Barnarda_C_Blocks();
	public static final Block BarnardaCOres = new Barnarda_C_Ores();
	public static final Block BarnardaCLog = new Barnarda_C_Log();
	public static final Block BarnardaCLeaves = new Barnarda_C_Leaves();
	public static final Block BarnardaCFallingBlocks = new Barnarda_C_FallingBlocks();
	public static final Block BarnardaCDandelions = new Barnarda_C_Dandelions();
	public static final Block BarnardaCWaterGrass = new Barnarda_C_Water_Grass();
	
	public static final Block BarnardaEBlocks = new Barnarda_E_Blocks();
	
	public static void initialize() 
	{
		GameRegistry.registerBlock(BarnardaCGrass, ItemBlocksBarnardaCGrass.class, "barnardacgrass");
		GameRegistry.registerBlock(BarnardaCLog, "barnardaclog");
		GameRegistry.registerBlock(BarnardaCBlocks, ItemBlocksBarnardaC.class, "barnardacblocks");
		GameRegistry.registerBlock(BarnardaCOres, ItemBlocksBarnardaCOres.class, "barnardacores");
		GameRegistry.registerBlock(BarnardaCLeaves, ItemBlocksBarnardaCLeaves.class, "barnardacleaves");
		GameRegistry.registerBlock(BarnardaCFallingBlocks, ItemBlocksBarnardaCFallingBlocks.class, "barnardacfalling");
		GameRegistry.registerBlock(BarnardaCDandelions, ItemBlocksBarnardaCDandelions.class, "barnardacdandelions");
		GameRegistry.registerBlock(BarnardaCWaterGrass, "barnardacwatergrass");
		
		GameRegistry.registerBlock(BarnardaEBlocks, ItemBlocksBarnardaEBlocks.class, "barnardaeblocks");
		
		OreDictionary.registerOre("logWood", new ItemStack(BarnardaCLog));
		OreDictionary.registerOre("stone", new ItemStack(BarnardaCBlocks, 1, 1));
		OreDictionary.registerOre("cobblestone", new ItemStack(BarnardaCBlocks, 1, 2));
		OreDictionary.registerOre("plankWood", new ItemStack(BarnardaCBlocks, 1, 4));
		OreDictionary.registerOre("sand", new ItemStack(BarnardaCFallingBlocks, 1, 0));
		
		OreDictionary.registerOre("oreCoal", new ItemStack(BarnardaCOres, 1, 0));
		OreDictionary.registerOre("oreIron", new ItemStack(BarnardaCOres, 1, 1));
		OreDictionary.registerOre("oreGold", new ItemStack(BarnardaCOres, 1, 2));
		OreDictionary.registerOre("oreRedstone", new ItemStack(BarnardaCOres, 1, 3));
		OreDictionary.registerOre("oreLapis", new ItemStack(BarnardaCOres, 1, 4));
		OreDictionary.registerOre("oreDiamond", new ItemStack(BarnardaCOres, 1, 5));
		OreDictionary.registerOre("oreSilicon", new ItemStack(BarnardaCOres, 1, 6));
		OreDictionary.registerOre("oreCopper", new ItemStack(BarnardaCOres, 1, 7));
		OreDictionary.registerOre("oreTin", new ItemStack(BarnardaCOres, 1, 8));
		OreDictionary.registerOre("oreAluminum", new ItemStack(BarnardaCOres, 1, 9));
		OreDictionary.registerOre("oreQuartz", new ItemStack(BarnardaCOres, 1, 10));
		OreDictionary.registerOre("oreCobaltum", new ItemStack(BarnardaCOres, 1, 11));
		OreDictionary.registerOre("oreCobalt", new ItemStack(BarnardaCOres, 1, 11));
		OreDictionary.registerOre("oreNickel", new ItemStack(BarnardaCOres, 1, 12));
	}
}
