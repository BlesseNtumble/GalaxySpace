package galaxyspace.systems.SolarSystem.moons.io.blocks;

import galaxyspace.systems.SolarSystem.moons.io.tile.TileEntityDungeonSpawnerIo;
import micdoodle8.mods.galacticraft.core.blocks.BlockBossSpawner;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBossSpawnerIo extends BlockBossSpawner
{
    public BlockBossSpawnerIo(String assetName)
    {
        super(assetName);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityDungeonSpawnerIo();
    }
}
