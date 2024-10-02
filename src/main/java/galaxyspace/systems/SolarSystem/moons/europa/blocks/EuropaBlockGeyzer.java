package galaxyspace.systems.SolarSystem.moons.europa.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class EuropaBlockGeyzer extends Block
{
	int counter;

    @SideOnly(Side.CLIENT)
    protected IIcon BlockIconFront;
    protected IIcon BlockIconSide;
    protected IIcon BlockIconTop;
    
    public EuropaBlockGeyzer()
    {
        super(Material.rock);
        this.setBlockName("EuropaGeyzer");
        this.setHardness(2.0F);
        this.setStepSound(soundTypeStone);
        this.setResistance(3.0F);
    }
    
    @Override
    public Item getItemDropped(int meta, Random random, int par3)
    {
    	return null;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        BlockIconFront = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/europa/europagrunt");
        BlockIconSide = BlockIconFront;
        BlockIconTop = par1IconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/europa/europageyzer");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int par1int, int par2int)
    {
        
        if (par1int == 0 || par1int == 1) { return BlockIconTop; }
        else if (par1int != par2int) { return BlockIconSide; }
        else { return BlockIconFront; }
    }
    
    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) 
    {
    	if(entity instanceof EntityLivingBase)
    	{
    		entity.motionY += 1.5D;    		
    	}
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
    	if(world.isAirBlock(x, y + 1, z) || world.getBlock(x, y + 1, z) == Blocks.snow_layer) {
	    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D, 0.3D, 0.0D ), new Object [] { 50 , 5, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D} );
	    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D, 0.3D, 0.0D), new Object [] { 50, 5, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D, 0.3D, 0.0D), new Object [] { 50, 5, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	    	
	    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + 0.03D, 0.3D, 0.0D + 0.03D), new Object [] { 160 , 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D, 0.3D, 0.0D), new Object [] { 160, 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D - 0.03D, 0.3D, 0.0D - 0.03D), new Object [] { 160, 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	    
	    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + 0.03D, 0.3D, 0.0D - 0.03D), new Object [] { 160 , 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D - 0.03D, 0.3D, 0.0D + 0.03D), new Object [] { 160 , 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	    	
	    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + 0.03D, 0.3D, 0.0D + 0.03D), new Object [] { 160 , 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D, 0.3D, 0.0D), new Object [] { 160, 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D - 0.03D, 0.3D, 0.0D - 0.03D), new Object [] { 160, 17, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	   
    	} 
    	else if(world.getBlock(x, y + 1, z).getMaterial() == Material.water)
    	{
    		GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D, 0.3D, 0.0D ), new Object [] { 50 , 32, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D, 0.3D, 0.0D), new Object [] { 50, 32, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D, 0.3D, 0.0D), new Object [] { 50, 32, false, new Vector3(1.0F, 1.0F, 1.0F), 1.0D } );
	    	
    	}
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GSCreativeTabs.GSBlocksTab;
    }
}