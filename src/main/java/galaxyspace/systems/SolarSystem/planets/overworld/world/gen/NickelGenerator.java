package galaxyspace.systems.SolarSystem.planets.overworld.world.gen;

import java.util.Random;

import galaxyspace.core.GSBlocks;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockOres;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.planets.asteroids.blocks.AsteroidBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class NickelGenerator implements IWorldGenerator{

	private final IBlockState asteroidBlock = AsteroidBlocks.blockBasic.getStateFromMeta(0); 
	private final IBlockState nickelBlock = GSBlocks.OVERWORLD_ORES.getDefaultState().withProperty(BlockOres.BASIC_TYPE, BlockOres.EnumBlockOres.NICKEL);
	private final IBlockState copperBlock = GCBlocks.basicBlock.getStateFromMeta(5);
	
	private final int maxGenerateLevel = 40;
    private final int minGenerateLevel = 5;
    
    byte[][][] MATRIX = new byte[][][] { 
    	{
    		{0,0,0,0},
    		{0,1,1,0},
    		{0,1,1,0},
    		{0,0,0,0}
    	},{
    		{0,1,1,0},
    		{1,2,2,1},
    		{1,3,2,1},
    		{0,1,1,0}
    	},{
    		{1,1,1,0},
    		{1,2,1,0},
    		{1,1,1,0},
    		{0,0,0,0}
    	},{
    		{0,1,0,0},
    		{1,1,1,0},
    		{0,1,0,0},
    		{0,0,0,0}
    	}
    };
    		
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		
		if (world.provider.getDimension() == 0)
        {		
        
			if (random.nextInt(100) < 85) {
				int x = chunkX * 16 + random.nextInt(16) + 8;
				int z = chunkZ * 16 + random.nextInt(16) + 8;
				int y = random.nextInt(Math.max(this.maxGenerateLevel - this.minGenerateLevel, 0)) + this.minGenerateLevel;
				BlockPos pos = new BlockPos(x, y, z);
				this.generateOre(world, random, pos);			
			}
        }
	}
	
	private boolean generateOre(World world, Random rand, BlockPos pos) {

		for (int py = 0; py < MATRIX.length; ++py) {
			for (int px = 0; px < MATRIX[py].length; ++px) {
				for (int pz = 0; pz < MATRIX[py][px].length; ++pz) {
					int px2 = px, py2 = py, pz2 = pz;

					px2 -= MATRIX[py].length / 2;
					pz2 -= MATRIX[py][px].length / 2;

					int matrix1 = MATRIX[py][px][pz];

					switch (rand.nextInt(4)) {
						case 0:
							break;
						case 1:
							matrix1 = MATRIX[py][pz][px];
							break;
						case 2:
							matrix1 = MATRIX[py][(MATRIX[py].length - 1) - px][(MATRIX[py][px].length - 1) - pz];
							break;
						case 3:
							matrix1 = MATRIX[py][(MATRIX[py][px].length - 1) - pz][(MATRIX[py].length - 1) - px];
							break;
					}
					generateBlock(world, rand, new BlockPos(pos.getX() + px2, pos.getY() + py2, pos.getZ() + pz2),	matrix1);
				}
			}
		}

		return true;
	}
	
	protected void generateBlock(World world, Random random, BlockPos position, int matrixValue) {
		IBlockState rand_block = random.nextBoolean() ? copperBlock : nickelBlock;
		
		if(!world.getBlockState(position).isNormalCube()) return;
		
		
		switch(matrixValue) {
			case 1:
				world.setBlockState(position, asteroidBlock);
				break;
			case 2:
				world.setBlockState(position, nickelBlock);
				break;
			case 3:			
				world.setBlockState(position, rand_block);
				break;
				
			default: 
				break;
		}
	}

}
