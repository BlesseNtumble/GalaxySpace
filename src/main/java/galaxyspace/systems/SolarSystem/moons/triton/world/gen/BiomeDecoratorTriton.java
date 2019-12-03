package galaxyspace.systems.SolarSystem.moons.triton.world.gen;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.systems.SolarSystem.moons.triton.blocks.TritonBlocks;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BiomeDecoratorTriton  extends BiomeDecoratorSpace{

	private World currentWorld;
	public BiomeDecoratorTriton()
	{		 
	}

	@Override
	protected void decorate() {		
		for (int i = 0; i < 1; i++) {
			
			 int randPosX = posX + rand.nextInt(16) + 8;
			 int randPosY = rand.nextInt(70);
			 int randPosZ = posZ + rand.nextInt(16) + 8;
			 
			 BlockPos pos = new BlockPos(randPosX, randPosY + 1, randPosZ);
			 BlockPos pos1 = new BlockPos(randPosX, randPosY, randPosZ);
			 BlockPos pos2 = new BlockPos(randPosX, randPosY - 1, randPosZ);
			 
			 if(this.getCurrentWorld().isAirBlock(pos))
			 {
				 IBlockState state = this.getCurrentWorld().getBlockState(pos1);
				 if(this.getCurrentWorld().getBlockState(pos1) == GSBlocks.TRITON_BLOCKS.getDefaultState())					 
				 {
					 this.getCurrentWorld().setBlockState(pos1, GSBlocks.TRITON_BLOCKS.getDefaultState().withProperty(TritonBlocks.BASIC_TYPE, TritonBlocks.EnumTritonBlocks.TRITON_GEYSER), 3);
					 this.getCurrentWorld().scheduleBlockUpdate(pos1, GSBlocks.TRITON_BLOCKS.getDefaultState().withProperty(TritonBlocks.BASIC_TYPE, TritonBlocks.EnumTritonBlocks.TRITON_GEYSER).getBlock(), 0, 0);
				 }
			 }
		}
		
		for (int i = 0; i < 2; i++) {
			
			 int randPosX = posX + rand.nextInt(16) + 8;
			 int randPosY = rand.nextInt(70);
			 int randPosZ = posZ + rand.nextInt(16) + 8;
			 
			 BlockPos pos = new BlockPos(randPosX, randPosY + 1, randPosZ);
			 BlockPos pos1 = new BlockPos(randPosX, randPosY, randPosZ);
			 BlockPos pos2 = new BlockPos(randPosX, randPosY - 1, randPosZ);
			 
			 if(this.getCurrentWorld().isAirBlock(pos) && randPosX % 50 == 0)
			 {
				 IBlockState state = this.getCurrentWorld().getBlockState(pos1);
				 if(this.getCurrentWorld().getBlockState(pos1) == GSBlocks.TRITON_BLOCKS.getDefaultState())					 
				 {
					 this.getCurrentWorld().setBlockState(pos1, GSBlocks.TRITON_BLOCKS.getDefaultState().withProperty(TritonBlocks.BASIC_TYPE, TritonBlocks.EnumTritonBlocks.TRITON_GEYSER_2), 3);
					 this.getCurrentWorld().scheduleBlockUpdate(pos1, GSBlocks.TRITON_BLOCKS.getDefaultState().withProperty(TritonBlocks.BASIC_TYPE, TritonBlocks.EnumTritonBlocks.TRITON_GEYSER_2).getBlock(), 0, 0);
						
				 }
			 }
		}
	}
	
	@Override
	protected void setCurrentWorld(World world) {
		this.currentWorld = world;
	}

	@Override
	protected World getCurrentWorld() {
		return this.currentWorld;
	}
}
