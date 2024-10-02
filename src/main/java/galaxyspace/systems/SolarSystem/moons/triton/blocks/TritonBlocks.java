package galaxyspace.systems.SolarSystem.moons.triton.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class TritonBlocks extends Block implements ITerraformableBlock, IDetectableResource
{
	public static String[] metadata = new String[] {
		"TritonGrunt",
		"TritonGrunt_1",
		"TritonSubGrunt",
		"TritonStone",
		"TritonGeyser",
		"TritonUraniumOre"
	};
	
	protected IIcon[] textures = new IIcon[this.metadata.length];	
	protected IIcon BlockIconTop;
	
    public TritonBlocks()
    {
        super(Material.ice);
        this.setBlockName("TritonBlocks");
        this.setHardness(3.0F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 2);
        this.setBlockTextureName("dirt");
        this.setTickRandomly(true);
    }
    
    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
    	if(world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) == 4)
    		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world) + world.rand.nextInt(10));
    }
    
    @Override
    public int getDamageValue(World world, int x, int y, int z)
    {    	
        return world.getBlockMetadata(x, y, z);
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
    	
	    	if(world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) == 4)
	    	{
		    	if(world.isAirBlock(x, y + 1, z)) {
		    		/*for(int i = 0; i < 1; i++)
		    		{*/
		    			GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.0001D, 0.0D + ((rand.nextFloat() / 20) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 600 + rand.nextInt(400), 5, false, new Vector3(0.65F, 0.6F, 0.6F), 1.0D} );
		    			GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 20) * (rand.nextBoolean() ? -1 : 1)), 0.0001D, 0.0D + ((rand.nextFloat() / 20) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 600 + rand.nextInt(400), 5, false, new Vector3(0.4F, 0.4F, 0.4F), 1.0D} );
		    			GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 20) * (rand.nextBoolean() ? -1 : 1)), 0.0001D, 0.0D + ((rand.nextFloat() / 20) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 600 + rand.nextInt(400), 5, false, new Vector3(0.1F, 0.1F, 0.1F), 1.0D} );
		    		//}
		    		/*
		    		for(int i = 0; i < 1; i++)
		    		{
		    			GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.0001D, 0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 600 + rand.nextInt(400), 5, false, new Vector3(0.5F, 0.5F, 0.5F), 1.0D} );
		    			GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.0001D, 0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 600 + rand.nextInt(400), 5, false, new Vector3(0.4F, 0.4F, 0.4F), 1.0D } );
		    			GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.0001D, 0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 600 + rand.nextInt(400), 5, false, new Vector3(0.1F, 0.1F, 0.1F), 1.0D } );
		    		}
			    	    */		
		    	} 	    
		    	
		    	world.scheduleBlockUpdate(x, y, z, this, 0);
	    	}   
    	
    	
    }
 
    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GSCreativeTabs.GSBlocksTab;
    }
    
    @Override
	public boolean isTerraformable(World world, int x, int y, int z) {
		return true;
	}
    
    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
        world.setBlockMetadataWithNotify(x, y, z, is.getItemDamage(), 3);
        world.scheduleBlockUpdate(x, y, z, this, 0);
    }    
    
    @Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
	   	super.registerBlockIcons(iconRegister);
	   	
	   	for(int i = 0; i < this.metadata.length; i++)
	   		this.textures[i] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/triton/" + this.metadata[i].toLowerCase());

	   	this.BlockIconTop = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/triton/tritongeyser");
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (meta < 0 || meta >= this.textures.length)
        {
            return this.textures[0];
        }
        
        if(meta == 4)
        {
        	if(side == 1) return this.BlockIconTop;
        	else return this.textures[0];
        }
        
        return this.textures[meta];
    }

    @Override
	public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list)
	{
		for (int i = 0; i < this.textures.length; ++i)
		{
			list.add(new ItemStack(block, 1, i));
		}
	}
    
    @Override
    public Item getItemDropped(int meta, Random random, int par3)
    {
    	return Item.getItemFromBlock(this);
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        return 1;
    }

	@Override
	public boolean isValueable(int metadata) {	
		return false;
	}
}