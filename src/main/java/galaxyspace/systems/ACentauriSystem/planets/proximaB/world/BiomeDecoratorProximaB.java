package galaxyspace.systems.ACentauriSystem.planets.proximaB.world;

import galaxyspace.core.world.worldengine.WE_Biome;
import galaxyspace.systems.ACentauriSystem.core.registers.blocks.ACBlocks;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.dimension.WorldProviderProximaB;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.world.features.WorldGenFrozenTree;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.world.we.Proxima_B_Forest;
import galaxyspace.systems.ACentauriSystem.planets.proximaB.world.we.Proxima_B_Ice_Plains;
import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorProximaB extends BiomeDecoratorSpace
{
	private World world;
	private WorldGenerator tree_1, coalOre, goldOre, tinOre, copperOre, siliconOre, emeraldOre, subsurfaceGen;


	public BiomeDecoratorProximaB()
	{
		tree_1 = new WorldGenFrozenTree(new BlockMetaPair(ACBlocks.ProximaBFrozenLogs, (byte) 0), new BlockMetaPair(Blocks.air, (byte) 0), 0);
		goldOre = new WorldGenMinableMeta(ACBlocks.ProximaBBlocks, 5, 5, true, ACBlocks.ProximaBBlocks, 2);
		tinOre = new WorldGenMinableMeta(ACBlocks.ProximaBBlocks, 6, 6, true, ACBlocks.ProximaBBlocks, 2);
		coalOre = new WorldGenMinableMeta(ACBlocks.ProximaBBlocks, 10, 8, true, ACBlocks.ProximaBBlocks, 2);
		copperOre = new WorldGenMinableMeta(ACBlocks.ProximaBBlocks, 6, 7, true, ACBlocks.ProximaBBlocks, 2);
		siliconOre = new WorldGenMinableMeta(ACBlocks.ProximaBBlocks, 4, 9, true, ACBlocks.ProximaBBlocks, 2);
		emeraldOre = new WorldGenMinableMeta(ACBlocks.ProximaBBlocks, 3, 10, true, ACBlocks.ProximaBBlocks, 2);
		subsurfaceGen = new WorldGenMinableMeta(ACBlocks.ProximaBBlocks, 14, 1, true, ACBlocks.ProximaBBlocks, 2);
	}	

	@Override
	protected void decorate()
	{
		
		this.generateOre(15, coalOre, 20, 90);
		this.generateOre(8, tinOre, 10, 80);
		this.generateOre(10, copperOre, 20, 90);
		this.generateOre(5, siliconOre, 0, 30);
		this.generateOre(5, goldOre, 0, 30);
		this.generateOre(2, emeraldOre, 0, 20);
		this.generateOre(100, subsurfaceGen, 30, 100);
		
		if(WE_Biome.getBiomeAt(((WorldProviderProximaB)getCurrentWorld().provider).chunk, chunkX, chunkZ) instanceof Proxima_B_Ice_Plains)
    	{
			for(int i = 0; i < 10; i++){
				int randPosX = chunkX + this.rand.nextInt(16);    			
    			int randPosZ = chunkZ + this.rand.nextInt(16);
    			int y = this.getCurrentWorld().getTopSolidOrLiquidBlock(randPosX, randPosZ);
    			int meta = this.getCurrentWorld().getBlockMetadata(randPosX, y - 1, randPosZ);
    			
    			if(y >= 68 && y < 74 && this.getCurrentWorld().getBlock(randPosX, y - 1, randPosZ) == ACBlocks.ProximaBBlocks && meta == 3)
    			{
    				new WorldGenFrozenTree(new BlockMetaPair(ACBlocks.ProximaBFrozenLogs, (byte) 0), new BlockMetaPair(Blocks.air, (byte) 0), 1 + rand.nextInt(2)).generate(getCurrentWorld(), rand, randPosX, y, randPosZ);
    			}
			}
			
			if(rand.nextInt(1) == 0){
				int randPosX = chunkX + this.rand.nextInt(16);
    			int randPosY = this.rand.nextInt(256);
    			int randPosZ = chunkZ + this.rand.nextInt(16);
    			int y = this.getCurrentWorld().getTopSolidOrLiquidBlock(randPosX, randPosZ);
    			
    			int meta = this.getCurrentWorld().getBlockMetadata(randPosX, y - 1, randPosZ);
    			
    			if(y >= 68 && y < 74 && this.getCurrentWorld().getBlock(randPosX, y - 1, randPosZ) == ACBlocks.ProximaBBlocks && meta == 3)
    			{
    				tree_1.generate(getCurrentWorld(), rand, randPosX, y, randPosZ);
    			}
			}			
			
    	}
		
		if(WE_Biome.getBiomeAt(((WorldProviderProximaB)getCurrentWorld().provider).chunk, chunkX, chunkZ) instanceof Proxima_B_Forest)
    	{
			for(int i = 0; i < 80; i++){
				int randPosX = chunkX + this.rand.nextInt(16);    			
    			int randPosZ = chunkZ + this.rand.nextInt(16);
    			int y = this.getCurrentWorld().getTopSolidOrLiquidBlock(randPosX, randPosZ);
    			int meta = this.getCurrentWorld().getBlockMetadata(randPosX, y - 1, randPosZ);
    			
    			if(this.getCurrentWorld().getBlock(randPosX, y - 1, randPosZ) == ACBlocks.ProximaBBlocks && meta == 0)
    			{
    				new WorldGenFrozenTree(new BlockMetaPair(ACBlocks.ProximaBBurntLogs, (byte) 0), new BlockMetaPair(Blocks.air, (byte) 0), 1 + rand.nextInt(2)).generate(getCurrentWorld(), rand, randPosX, y, randPosZ);
    			}
			}
			
			for(int i = 0; i < 5; i++){
				int randPosX = chunkX + this.rand.nextInt(16);
    			int randPosY = this.rand.nextInt(256);
    			int randPosZ = chunkZ + this.rand.nextInt(16);
    			int y = this.getCurrentWorld().getTopSolidOrLiquidBlock(randPosX, randPosZ);
    			
    			int meta = this.getCurrentWorld().getBlockMetadata(randPosX, y - 1, randPosZ);
    			
    			if(this.getCurrentWorld().getBlock(randPosX, y - 1, randPosZ) == ACBlocks.ProximaBBlocks && meta == 0)
    			{
    				new WorldGenFrozenTree(new BlockMetaPair(ACBlocks.ProximaBBurntLogs, (byte) 0), new BlockMetaPair(Blocks.air, (byte) 0), 0).generate(getCurrentWorld(), rand, randPosX, y, randPosZ);
    			}
			}		
			
    	}
		
		for(int i = 0; i < 60; i++){
			int randPosX = this.chunkX + this.rand.nextInt(16) + 8;
			int randPosY = this.rand.nextInt(100);
			int randPosZ = this.chunkZ + this.rand.nextInt(16) + 8;
			
			int y = this.getCurrentWorld().getTopSolidOrLiquidBlock(randPosX, randPosZ);
			
			int meta = this.getCurrentWorld().getBlockMetadata(randPosX, randPosY, randPosZ);
			
			if(this.getCurrentWorld().isAirBlock(randPosX, randPosY + 1, randPosZ) && this.getCurrentWorld().getBlock(randPosX, randPosY, randPosZ) == ACBlocks.ProximaBBlocks && meta == 0)
			{
				this.getCurrentWorld().setBlock(randPosX, randPosY + 1, randPosZ, Blocks.deadbush, 0, 3);
			}
		}
	}

	@Override
	protected void setCurrentWorld(World world)
	{
		this.world = world;
	}

	@Override
	protected World getCurrentWorld()
	{
		return this.world;
	}
}