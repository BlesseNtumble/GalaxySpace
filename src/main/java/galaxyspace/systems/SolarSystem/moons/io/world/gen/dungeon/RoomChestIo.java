package galaxyspace.systems.SolarSystem.moons.io.world.gen.dungeon;

import java.util.Random;

import asmodeuscore.core.astronomy.dimension.world.gen.dungeons.standart.DungeonConfiguration;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.block.BlockChest;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class RoomChestIo extends RoomEmptyIo{

	public RoomChestIo()
	{
	}

	public RoomChestIo(DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ, EnumFacing entranceDir)
	{
		this(configuration, rand, blockPosX, blockPosZ, rand.nextInt(4) + 14, configuration.getRoomHeight(), rand.nextInt(4) + 14, entranceDir);
	}
	 
	public RoomChestIo(DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ, int sizeX, int sizeY, int sizeZ, EnumFacing entranceDir)
    {
        super(configuration, rand, blockPosX, blockPosZ, sizeX, sizeY, sizeZ, entranceDir);
    }
	
	@Override
	public boolean addComponentParts(World worldIn, Random rand, StructureBoundingBox boundingBox) {
		if (super.addComponentParts(worldIn, rand, boundingBox)) {
			int chestX = this.sizeX / 2;
			int chestY = 1;
			int chestZ = this.sizeZ / 2;
			

			for(int x = -2; x < 3; x++)
				for(int z = -2; z < 3; z++)
				{
					//if((x == -3 || x == 2) && (z == -3 || z == 2))
						//this.setBlockState(worldIn, Blocks.PACKED_ICE.getDefaultState(), chestX + x, 1, chestZ + z, boundingBox);
					
					/*for(int y = 0; y < 2; y++) {
						if(x % 2 != 0 && z % 2 != 0) 
							this.setBlockState(worldIn, Blocks.PACKED_ICE.getDefaultState(), chestX + x, chestY + y, chestZ + z, boundingBox);
						else 
							this.setBlockState(worldIn, Blocks.ICE.getDefaultState(), chestX + x, chestY + y, chestZ + z, boundingBox);
					}*/
					
					//if(x != 0 || z != 0)
						//for(int y = 2; y < this.configuration.getRoomHeight() - 2; y++)
							//this.setBlockState(worldIn, Blocks.PACKED_ICE.getDefaultState(), chestX + x, chestY + y, chestZ + z, boundingBox);
					
				}

			
			this.setBlockState(worldIn, Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING,
					this.getDirection().getOpposite()), chestX, chestY, chestZ, boundingBox);
			
			BlockPos blockpos = new BlockPos(this.getXWithOffset(chestX, chestZ), this.getYWithOffset(chestY),
					this.getZWithOffset(chestX, chestZ));
			TileEntityChest chest = (TileEntityChest) worldIn.getTileEntity(blockpos);

			if (chest != null) {
				ResourceLocation chesttype = RoomTreasureIo.IOCHEST;
				if (worldIn.provider instanceof IGalacticraftWorldProvider) {
					chesttype = ((IGalacticraftWorldProvider) worldIn.provider).getDungeonChestType();
				}
				chest.setLootTable(chesttype, rand.nextLong());
			}
			/*
			this.setBlockState(worldIn, Blocks.MOB_SPAWNER.getDefaultState(), chestX, chestY - 1, chestZ, boundingBox);
			TileEntityMobSpawner spawner = (TileEntityMobSpawner) worldIn.getTileEntity(new BlockPos(chestX, chestY - 1, chestZ));
			  
			if (spawner != null)
            {
                spawner.getSpawnerBaseLogic().setEntityId(getMob(rand));
            }*/
			
			return true;
		}

		return false;
	}
	
	private static ResourceLocation getMob(Random rand)
    {
        switch (rand.nextInt(4))
        {
	        case 0:
	            return new ResourceLocation(Constants.MOD_ID_CORE, "evolved_spider");
	        case 1:
	            return new ResourceLocation(Constants.MOD_ID_CORE, "evolved_creeper");
	        case 2:
	            return new ResourceLocation(Constants.MOD_ID_CORE, "evolved_skeleton");
	        case 3:
	        default:
	            return new ResourceLocation(Constants.MOD_ID_CORE, "evolved_zombie");
        }
    }
}
