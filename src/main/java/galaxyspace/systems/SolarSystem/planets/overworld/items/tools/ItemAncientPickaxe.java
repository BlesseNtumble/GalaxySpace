package galaxyspace.systems.SolarSystem.planets.overworld.items.tools;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.items.ItemPickaxeGS;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemAncientPickaxe extends ItemPickaxeGS{

	protected IIcon[] icons = new IIcon[2];
	private String name;
	
	public ItemAncientPickaxe(String assetName, ToolMaterial material) {
		super(assetName, material, true);
		this.name = assetName;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
    {
		return false;		
    }
	
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
    	this.icons[0] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":tools/" + name);
   		this.icons[1] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":tools/" + name + "_1");
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass)
    {
		return stack.stackTagCompound != null && stack.stackTagCompound.getBoolean(drill) ? this.icons[1] : this.icons[0];
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
		return stack.stackTagCompound != null && stack.stackTagCompound.getBoolean(drill) ? this.icons[1] : this.icons[0];
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List par2List, boolean b)
    { 			
		par2List.add(GCCoreUtil.translate("gui.ancient_item"));
		par2List.add(GCCoreUtil.translate("gui.pickaxe.ancient1"));
    	par2List.add(GCCoreUtil.translate("gui.pickaxe.ancient2"));  
    	par2List.add("");
    	super.addInformation(stack, player, par2List, b);
    }
	
	@Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity)
    {
    	EntityPlayer player = (EntityPlayer)entity;

    	if ((double)block.getBlockHardness(world, x, y, z) != 0.0D)
        {
        	if(stack.stackTagCompound == null || stack.stackTagCompound != null && !stack.stackTagCompound.getBoolean(drill))	        		
        		if(world.rand.nextInt(5) == 0) 
        			stack.damageItem(1, entity);

        }       

        return true;
    }
}
