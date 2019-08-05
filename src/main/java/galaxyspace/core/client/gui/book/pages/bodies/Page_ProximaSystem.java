package galaxyspace.core.client.gui.book.pages.bodies;

import galaxyspace.systems.ACentauriSystem.ACentauriSystemBodies;
import galaxyspace.systems.ACentauriSystem.core.registers.blocks.ACBlocks;
import net.minecraft.item.ItemStack;

public class Page_ProximaSystem extends Page_Systems{

	public Page_ProximaSystem()
	{
		super(ACentauriSystemBodies.ProximaSystem);
		this.setResources(ACentauriSystemBodies.proxima_b, new ItemStack(ACBlocks.PROXIMA_B_BLOCKS, 1, 5), new ItemStack(ACBlocks.PROXIMA_B_BLOCKS, 1, 6), new ItemStack(ACBlocks.PROXIMA_B_BLOCKS, 1, 7), new ItemStack(ACBlocks.PROXIMA_B_BLOCKS, 1, 8), new ItemStack(ACBlocks.PROXIMA_B_BLOCKS, 1, 9), new ItemStack(ACBlocks.PROXIMA_B_BLOCKS, 1, 10));
	}
	
}
