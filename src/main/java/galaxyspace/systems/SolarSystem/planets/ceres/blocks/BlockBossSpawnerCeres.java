package galaxyspace.systems.SolarSystem.planets.ceres.blocks;

import galaxyspace.systems.SolarSystem.planets.ceres.tile.TileEntityDungeonSpawnerCeres;
import micdoodle8.mods.galacticraft.core.blocks.BlockBossSpawner;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBossSpawnerCeres extends BlockBossSpawner
{
    public BlockBossSpawnerCeres(String assetName)
    {
        super(assetName);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityDungeonSpawnerCeres();
    }
}
