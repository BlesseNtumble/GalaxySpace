package galaxyspace.systems.BarnardsSystem.core.events;

import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnarda_c.blocks.Barnarda_C_Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BREventHandler {

	@SubscribeEvent
	public void onHoeUse(UseHoeEvent event)
	{
		World world = event.getWorld();
		IBlockState state = world.getBlockState(event.getPos());
		
		if(state == BRBlocks.BARNARDA_C_GRASS.getDefaultState() || state == BRBlocks.BARNARDA_C_BLOCKS.getDefaultState().withProperty(Barnarda_C_Blocks.BASIC_TYPE, Barnarda_C_Blocks.EnumBlockBarnardaC.DIRT))
		{
			world.setBlockState(event.getPos(), BRBlocks.BARNARDA_C_FARMLAND.getDefaultState(), 3);
			event.setResult(Result.ALLOW);
		}		
	}
	
	@SubscribeEvent
	public void furnanceFuel(FurnaceFuelBurnTimeEvent e)
	{
		ItemStack stack = e.getItemStack();
		if(stack.isItemEqual(new ItemStack(BRBlocks.BARNARDA_C_BLOCKS, 1, 6)))
		{
			e.setBurnTime(180);
		}
		
		if(stack.isItemEqual(new ItemStack(BRBlocks.BARNARDA_C_VIOLET_LOG, 1, 0)))
		{
			e.setBurnTime(-1);
		}
	}
}
