package galaxyspace.core.hooks;

import galaxyspace.core.events.SetBlockEvent;
import galaxyspace.core.hooklib.asm.Hook;
import galaxyspace.core.hooklib.asm.ReturnCondition;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class GSHooksManager {
	

	//Регистрируем эвент установки блока.
	@Hook(returnCondition = ReturnCondition.ON_TRUE, booleanReturnConstant = false) 
    public static boolean setBlockState(World world, BlockPos pos, IBlockState newState, int flags) { 
    	return MinecraftForge.EVENT_BUS.post(new SetBlockEvent(world, pos, newState, flags)); 
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