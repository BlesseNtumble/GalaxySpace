package galaxyspace.systems.SolarSystem.moons.europa.blocks;

import java.util.Random;

import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EuropaBlockUWGeyser extends Block {

	public EuropaBlockUWGeyser() {
		super(Material.GRASS);
		this.setUnlocalizedName("europa_uwgeyser");
		this.setSoundType(SoundType.GLASS);
		this.setHardness(0.5F);
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity)
    {
		entity.motionY += 0.8D; 
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand)
    {
    	if(world.isAirBlock(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())) || world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock() == Blocks.SNOW_LAYER) {
    		  	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D, 0.6D, 0.0D ), new Object [] { 50 , 5, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D} );
		    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D, 0.6D, 0.0D), new Object [] { 50, 5, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
		    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D, 0.6D, 0.0D), new Object [] { 50, 5, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
		    	
		    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + 0.03D, 0.6D, 0.0D + 0.03D), new Object [] { 160 , 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D} );
		    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D, 0.6D, 0.0D), new Object [] { 160, 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
		    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D - 0.03D, 0.6D, 0.0D - 0.03D), new Object [] { 160, 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
		    
		    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + 0.03D, 0.6D, 0.0D - 0.03D), new Object [] { 160 , 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D} );
		    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D - 0.03D, 0.6D, 0.0D + 0.03D), new Object [] { 160 , 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D} );
		    	
		    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D + 0.03D, 0.6D, 0.0D + 0.03D), new Object [] { 160 , 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D} );
		    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D, 0.6D, 0.0D), new Object [] { 160, 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
		    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D - 0.03D, 0.6D, 0.0D - 0.03D), new Object [] { 160, 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
    		
    	} 
    	else if(world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getMaterial() == Material.WATER)
    	{
    		GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D, 0.3D, 0.0D ), new Object [] { 50 , 32, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D, 0.3D, 0.0D), new Object [] { 50, 32, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(pos.getX() + rand.nextDouble(), pos.getY() + 1.0D + rand.nextDouble(), pos.getZ() + rand.nextDouble()), new Vector3(0.0D, 0.3D, 0.0D), new Object [] { 50, 32, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	    	
    	}
    }

}
