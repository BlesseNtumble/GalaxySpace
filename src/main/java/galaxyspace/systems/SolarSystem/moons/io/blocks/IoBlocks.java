package galaxyspace.systems.SolarSystem.moons.io.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class IoBlocks extends Block implements ITerraformableBlock, IDetectableResource
{
	public static String[] metadata = new String[] {
		"IoGrunt",		//0
		"IoAsh",		//1
		"IoStone",		//2
		"IoCopperOre",	//3
		"IoSulfurOre",	//4
		"IoTop",		//5
		"IoFloor",		//6
		"IoSulfurGeyser",//7
		"IoLavaGeyser"	//8
		//"IoGrunt2"
	};
	
	protected IIcon[] textures = new IIcon[this.metadata.length];
	protected IIcon[] BlockIconTop = new IIcon[2];
	
    public IoBlocks()
    {
        super(Material.rock);
        this.setBlockName("IOBlocks");
        this.setHardness(3.0F);
        this.setStepSound(soundTypeStone);
        this.setHarvestLevel("pickaxe", 1);
        this.setBlockTextureName("dirt");
        //this.setBlockTextureName(GalaxySpace.ASSET_PREFIX + ":" + "io/iogrunt");
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
    public int getDamageValue(World world, int x, int y, int z)
    {    	
        return world.getBlockMetadata(x, y, z);
    }
    
    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	if(world.getBlock(x, y, z) == this && (meta == 7 || meta == 8))
    		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world) + world.rand.nextInt(10));
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
    	
    	if(world == null) 
    		return;
    	
    	int meta = world.getBlockMetadata(x, y, z);
    	
	    if(world.getBlock(x, y, z) == this)
	    {
	    	if(meta == 7) 
	    	{
			    if(world.isAirBlock(x, y + 1, z) && world.rand.nextInt(10) == 0) {		    		
			    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.8D, 0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 20, 4, false, new Vector3(0.6F, 0.8F, 0.2F), 1.0D} );
			    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.5D, 0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 10, 5, false, new Vector3(0.6F, 0.8F, 0.2F), 1.0D} );
			    	GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.5D, 0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 10, 6, false, new Vector3(0.6F, 0.8F, 0.2F), 1.0D} );
			    } 	
	    	}
			else if(meta == 8 && world.getBlock(x, y - 1, z).getMaterial() == Material.lava)
			{
				if(!world.isBlockNormalCubeDefault(x, y + 1, z, true) && world.rand.nextInt(4) == 0) {		    		
		    		GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.01D, 0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 200 + rand.nextInt(40), 4, false, new Vector3(0.1F, 0.1F, 0.1F), 1.0D} );
		    		GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.01D, 0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 200 + rand.nextInt(40), 4, false, new Vector3(0.1F, 0.1F, 0.1F), 1.0D} );
		    		GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1)), 0.01D, 0.0D + ((rand.nextFloat() / 10) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 200 + rand.nextInt(40), 6, false, new Vector3(0.1F, 0.1F, 0.1F), 1.0D} );
		    		if(world.rand.nextInt(2) == 0) {
		    			GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1)), 0.8D, 0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 100 + rand.nextInt(40), 48, true, new Vector3(1.0F, 1.0F, 1.0F), 1.0D} );
			    		GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1)), 0.8D, 0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 100 + rand.nextInt(40), 48, true, new Vector3(1.0F, 1.0F, 1.0F), 2.0D} );
			    		GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1)), 0.8D, 0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 100 + rand.nextInt(40), 48, true, new Vector3(1.0F, 1.0F, 1.0F), 2.0D} );
			    		GalaxySpace.proxy.spawnParticle("waterbubbles", new Vector3(x + rand.nextDouble(), y + 1.0D + rand.nextDouble(), z + rand.nextDouble()), new Vector3(0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1)), 1.2D + rand.nextDouble(), 0.0D + ((rand.nextFloat() / 5) * (rand.nextBoolean() ? -1 : 1))), new Object [] { 100 + rand.nextInt(40), 48, true, new Vector3(1.0F, 1.0F, 1.0F), 2.0D} );
		    		}
				} 	 	
			}
		    	
		    world.scheduleBlockUpdate(x, y, z, this, 0);
	    }   
    }
    
    @Override
    public int damageDropped(int metadata) {
    	if(metadata == 4) return 9;
        return metadata;
    }
    
    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) 
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if(world.getBlock(x, y, z) == this && meta == 8 && entity instanceof EntityLivingBase)
    	{
    		entity.setFire(4); 		
    	}
    }
    
    @Override
    public Item getItemDropped(int meta, Random random, int par3)
    {
    	switch(meta)
    	{
    		case 4: return GSItems.BasicItems;
    		case 7:
    		case 8: return null;
    		default: return Item.getItemFromBlock(this);
    	}
    	
    }
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
        world.setBlockMetadataWithNotify(x, y, z, is.getItemDamage(), 3);
    }    
    
    @Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
	   	super.registerBlockIcons(iconRegister);
		for(int i = 0; i < this.metadata.length; i++)
	   		this.textures[i] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/io/" + this.metadata[i].toLowerCase());

		this.BlockIconTop[0] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/io/iolavageyser");
		this.BlockIconTop[1] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":" + "solarsystem/io/iosulfurgeyser");

	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (meta < 0 || meta >= this.textures.length)
        {
            return this.textures[0];
        }

        if(meta == 7)
        {
        	if(side == 1) return this.BlockIconTop[1];
        	else return this.textures[0];
        }
        
        if(meta == 8)
        {
        	if(side == 1) return this.BlockIconTop[0];
        	else return this.textures[1];
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
	public boolean isValueable(int metadata) {
		for(int i = 4; i <= 5; i++)
		{
			if(metadata == i) return true;
		}
		return false;
	}
	//public void onEntityWalking(World world, int x, int y, int z, Entity entity)
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        float f = 0.025F;
        if(world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) == 1)
        	return AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)((float)(y + 1) - f), (double)(z + 1));
        
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {		
		entity.motionX *= 0.5D;
		entity.motionZ *= 0.5D;
    }
}