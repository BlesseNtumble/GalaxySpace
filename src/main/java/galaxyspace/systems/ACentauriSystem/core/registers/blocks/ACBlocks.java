package galaxyspace.systems.ACentauriSystem.core.registers.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import galaxyspace.core.registers.items.GSItemBlockDesc;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.blocks.ProximaBBlocks;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.blocks.ProximaBLogs;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.items.ItemBlocksProximaB;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ACBlocks {

	public static Block ProximaBBlocks = new ProximaBBlocks();
	public static Block ProximaBFrozenLogs = new ProximaBLogs("FrozenLog");
	public static Block ProximaBBurntLogs = new ProximaBLogs("BurntLog");
	public static void initialize() 
	{
		GameRegistry.registerBlock(ProximaBBlocks, ItemBlocksProximaB.class, "proximabblocks"); 
		GameRegistry.registerBlock(ProximaBFrozenLogs, GSItemBlockDesc.class, "proximabfrozenlog"); 
		GameRegistry.registerBlock(ProximaBBurntLogs, GSItemBlockDesc.class, "proximabburntlog"); 
		
		oreDictRegistration();
	}

	private static void oreDictRegistration() {
		 OreDictionary.registerOre("oreCoal", new ItemStack(ProximaBBlocks, 1, 8));
		 OreDictionary.registerOre("oreGold", new ItemStack(ProximaBBlocks, 1, 5));
		 OreDictionary.registerOre("oreTin", new ItemStack(ProximaBBlocks, 1, 6));
		 OreDictionary.registerOre("oreCopper", new ItemStack(ProximaBBlocks, 1, 7));
		 OreDictionary.registerOre("oreSilicon", new ItemStack(ProximaBBlocks, 1, 9));
		 OreDictionary.registerOre("oreEmerald", new ItemStack(ProximaBBlocks, 1, 10));
	}
}
