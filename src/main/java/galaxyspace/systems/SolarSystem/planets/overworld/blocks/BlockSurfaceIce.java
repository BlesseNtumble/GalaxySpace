package galaxyspace.systems.SolarSystem.planets.overworld.blocks;

import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSurfaceIce extends Block implements ITerraformableBlock{

	public BlockSurfaceIce() {
		super(Material.ICE);
		this.setUnlocalizedName("surface_ice");
		this.setHardness(1.0F);
        this.setSoundType(SoundType.GLASS);
	}

	@Override
	public boolean isTerraformable(World world, BlockPos pos) {
		return false;
	}
}
