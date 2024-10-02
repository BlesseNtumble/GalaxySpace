package galaxyspace.core.prefab.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.dimension.WorldProviderMoon;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemGSBucket extends ItemBucket{
	
	private Block isFull;
	
	public ItemGSBucket(Block block, String name)
	{
		super(block);
		this.setUnlocalizedName(name);
        this.setTextureName(GalaxySpace.ASSET_PREFIX + ":" + name);
        this.setContainerItem(Items.bucket);
        
        this.isFull = block;
	}
	
	public ItemGSBucket(Block block, String name, String texture)
	{
		super(block);
		this.setUnlocalizedName(name);
        this.setTextureName(GalaxySpace.ASSET_PREFIX + ":" + texture);
        this.setContainerItem(Items.bucket);
        
        this.isFull = block;
	}
 
    @SideOnly(Side.CLIENT)
    @Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSItemsTab;
    }    
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
    	boolean flag = this.isFull == Blocks.air;
    	MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, flag);

    	if(stack.getItem().equals(GSItems.IceBucket)) return stack;
    	if(stack.getItem().equals(GSItems.WaterBucket)) 
    	{
    		if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

				if (this.tryPlaceContainedLiquid(world, i, j, k) && !player.capabilities.isCreativeMode) {
					return new ItemStack(Items.bucket);
				}
                
            }
    	}
    	
    	return super.onItemRightClick(stack, world, player);
    }

    public boolean tryPlaceContainedLiquid(World world, int x, int y, int z)
    {
        if (this.isFull == Blocks.air)
        {
            return false;
        }
        else
        {
            Material material = world.getBlock(x, y, z).getMaterial();
            boolean flag = !material.isSolid();

            if (!world.isAirBlock(x, y, z) && !flag)
            {
                return false;
            }
            else
            {
                if ((world.provider.isHellWorld || world.provider instanceof IGalacticraftWorldProvider && ((IGalacticraftWorldProvider)world.provider).getThermalLevelModifier() > 2.0F) && this.isFull == Blocks.flowing_water)
                {
                    world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

                    for (int l = 0; l < 8; ++l)
                    {
                        world.spawnParticle("largesmoke", (double)x + Math.random(), (double)y + Math.random(), (double)z + Math.random(), 0.0D, 0.0D, 0.0D);
                    }
                }
                else if (world.provider instanceof IGalacticraftWorldProvider && (((IGalacticraftWorldProvider)world.provider).getThermalLevelModifier() < -2.0F || world.provider instanceof WorldProviderMoon) && this.isFull == Blocks.flowing_water)
                {
                	world.setBlock(x, y, z, Blocks.packed_ice);
                }
                else
                {
                    if (!world.isRemote && flag && !material.isLiquid())
                    {
                        world.func_147480_a(x, y, z, true);
                    }

                    world.setBlock(x, y, z, this.isFull, 0, 3);
                }

                return true;
            }
        }
    }
}
