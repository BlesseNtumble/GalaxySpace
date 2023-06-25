package galaxyspace.core.prefab.items;

import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.core.util.GSUtils;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPickaxeGS extends ItemPickaxe {

	private boolean drillMode;
	//private boolean isAncient;
	public int breakRadius;
    public int breakDepth;
    
    static Material[] materials = new Material[] { Material.ROCK, Material.IRON, Material.ICE, Material.GLASS, Material.PISTON, Material.ANVIL };

    
	public ItemPickaxeGS(String assetName, ToolMaterial material, boolean drillMode) {
		super(material);
		this.setTranslationKey(assetName);
		this.setNoRepair();
		
		this.drillMode = drillMode;
		//this.isAncient = isAncient;
		this.breakRadius = 1;		
	}
	
	@Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSArmorTab;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
		if(this.drillMode)
			if(stack.getTagCompound() != null && stack.getTagCompound().getBoolean("drill_mode"))
				list.add(EnumColor.DARK_GREEN + GCCoreUtil.translate("gui.pickaxe.drillmodeon"));
		 	else 
		 		list.add(EnumColor.DARK_RED + GCCoreUtil.translate("gui.pickaxe.drillmodeoff"));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn)
    {
		ItemStack stack = player.getHeldItem(handIn);
		
		if(drillMode && !stack.hasTagCompound()) 
			stack.setTagCompound(new NBTTagCompound());		
		
		if (!world.isRemote && drillMode) {			
			if(!stack.getTagCompound().getBoolean("drill_mode"))
			{
				stack.getTagCompound().setBoolean("drill_mode", true);
				player.sendMessage(new TextComponentString(EnumColor.DARK_GREEN + GCCoreUtil.translateWithFormat("gui.pickaxe.drillmodeon")));
			}
			else
			{
				stack.getTagCompound().setBoolean("drill_mode", false);
				player.sendMessage(new TextComponentString(EnumColor.DARK_RED + GCCoreUtil.translateWithFormat("gui.pickaxe.drillmodeoff")));
			}
			
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
			
		}
		
        return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(handIn));
    }

	@Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
		boolean ret = super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);

        if (!(entityLiving instanceof EntityPlayer) || worldIn.isRemote)
        {
            return ret;
        }
        
        
        if(!stack.hasTagCompound() || stack.hasTagCompound() && !stack.getTagCompound().getBoolean("drill_mode")) 
        	return ret;

        GSUtils.tryHarvestBlock(stack, worldIn, pos, entityLiving, breakRadius);
        
        return ret;
    }
}
