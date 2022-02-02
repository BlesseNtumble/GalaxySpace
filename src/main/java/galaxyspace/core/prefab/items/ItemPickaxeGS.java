package galaxyspace.core.prefab.items;

import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
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

        EntityPlayer player = (EntityPlayer) entityLiving;
        EnumFacing facing = entityLiving.getHorizontalFacing();

        if (entityLiving.rotationPitch < -45.0F)
        {
            facing = EnumFacing.UP;
        }
        else if (entityLiving.rotationPitch > 45.0F)
        {
            facing = EnumFacing.DOWN;
        }
        
        boolean yAxis = facing.getAxis() == EnumFacing.Axis.Y;
        boolean xAxis = facing.getAxis() == EnumFacing.Axis.X;
        
        for (int i = -breakRadius; i <= breakRadius; ++i)
        {
            for (int j = -breakRadius; j <= breakRadius && !stack.isEmpty(); ++j)
            {
                if (i == 0 && j == 0)
                {
                    continue;
                }

                BlockPos pos1;
                if (yAxis)
                {
                    pos1 = pos.add(i, 0, j);
                }
                else if (xAxis)
                {
                    pos1 = pos.add(0, i, j);
                }
                else
                {
                    pos1 = pos.add(i, j, 0);
                }

                //:Replicate logic of PlayerInteractionManager.tryHarvestBlock(pos1)
                IBlockState state1 = worldIn.getBlockState(pos1);
                float f = state1.getBlockHardness(worldIn, pos1);
                if (f >= 0F)
                {
                    BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(worldIn, pos1, state1, player);
                    MinecraftForge.EVENT_BUS.post(event);
                    if (!event.isCanceled())
                    {
                        Block block = state1.getBlock(); 
                        if ((block instanceof BlockCommandBlock || block instanceof BlockStructure) && !player.canUseCommandBlock())
                        {
                            worldIn.notifyBlockUpdate(pos1, state1, state1, 3);
                            continue;
                        }
                        TileEntity tileentity = worldIn.getTileEntity(pos1);
                        if (tileentity != null)
                        {
                            Packet<?> pkt = tileentity.getUpdatePacket();
                            if (pkt != null)
                            {
                                ((EntityPlayerMP)player).connection.sendPacket(pkt);
                            }
                        }
    
                        boolean canHarvest = block.canHarvestBlock(worldIn, pos1, player);
                        boolean destroyed = block.removedByPlayer(state1, worldIn, pos1, player, canHarvest);
                        if (destroyed)
                        {
                            block.onPlayerDestroy(worldIn, pos1, state1);
                        }
                        if (canHarvest && destroyed)
                        {
                            block.harvestBlock(worldIn, player, pos1, state1, tileentity, stack);
                            //stack.damageItem(1, player);
                        }
                    }
                }
            }
        }
        
        return ret;
    }
}
