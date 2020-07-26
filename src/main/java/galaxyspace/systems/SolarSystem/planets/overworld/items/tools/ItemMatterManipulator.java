package galaxyspace.systems.SolarSystem.planets.overworld.items.tools;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.entities.EntityLaserBeam;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMatterManipulator extends Item {

	public static int DISTANCE = 15;
	
	public ItemMatterManipulator()
	{
		this.setMaxDamage(1000);
		this.setMaxStackSize(1);
		this.setUnlocalizedName("matter_manipulator");
		this.setCreativeTab(GSCreativeTabs.GSArmorTab);
	}
	
	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)    
    {
    	if (tab == GSCreativeTabs.GSArmorTab || tab == CreativeTabs.SEARCH)
        {
    		list.add(new ItemStack(this, 1, 0));
    		list.add(new ItemStack(this, 1, this.getMaxDamage()));
        }
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn) {
	
		if(stack.hasTagCompound())
		{
			list.add(EnumColor.DARK_RED + "Timer: " + stack.getTagCompound().getInteger("timer"));
		}
	}
	
	public static RayTraceResult getRay(World world, EntityPlayer player, boolean useLiquids)
	{
		float f = player.rotationPitch;
		float f1 = player.rotationYaw;
		double d0 = player.posX;
		double d1 = player.posY + (double) player.getEyeHeight();
		double d2 = player.posZ;
		Vec3d vec3d = new Vec3d(d0, d1, d2);
		float f2 = MathHelper.cos(-f1 * 0.017453292F - (float) Math.PI);
		float f3 = MathHelper.sin(-f1 * 0.017453292F - (float) Math.PI);
		float f4 = -MathHelper.cos(-f * 0.017453292F);
		float f5 = MathHelper.sin(-f * 0.017453292F);
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double d3 = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue() + 10;
		Vec3d vec3d1 = vec3d.addVector((double) f6 * d3, (double) f5 * d3, (double) f7 * d3);
		return world.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {		
		ItemStack stack = player.getHeldItem(hand);
		RayTraceResult ray = this.getRay(world, player, true);//player.rayTrace(DISTANCE, player.ticksExisted);
				
		/*if(world.isRemote)
		{
			GalaxySpace.proxy.spawnParticle("beamFX", new Vector3(), new Vector3(), new Object[] { new Vector3(0.0F, 0.0F, 0.0F) });
		}*/
		if(!world.isRemote) {
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setInteger("timer", 0);
			}
					
			stack.getTagCompound().setInteger("timer", stack.getTagCompound().getInteger("timer") + 1);
			
			int i = stack.getTagCompound().getInteger("timer");	
			
			//MODE: DIG
			
			if(ray != null && i >= 2) {
				for(BlockPos pos : ray.getBlockPos().getAllInBox(ray.getBlockPos().add(-1,0,-1), ray.getBlockPos().add(1, 0, 1))) {
					IBlockState block = world.getBlockState(pos);
				
				//EntityLaserBeam beam = new EntityLaserBeam(world, player, ray);
				//beam.posY = player.posY + (double)(player.height / 2.0F) + 0.5D;
				//beam.shoot(player, player.rotationPitch, player.rotationYawHead, 0.0F, 0.8F, 0.0F);
				//beam.shoot(player.posX, player.posY, player.posZ, 0.8F, 0.0F);
				//world.spawnEntity(beam);
				
					if(!world.isRemote && block.isBlockNormalCube() && block.getBlock() != Blocks.BEDROCK)
					{						
						if(player.inventory.getFirstEmptyStack() > 0)
							for(ItemStack stacks : world.getBlockState(pos).getBlock().getDrops(world, pos, world.getBlockState(pos), 0))
								player.inventory.addItemStackToInventory(stacks);
						world.destroyBlock(pos, false);
					}
				}			
				//return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
			}	
			
			/*
			if(i % 5 == 0) {
	
				EntityIceSpike entitysmallfireball = new EntityIceSpike(world, player);
				entitysmallfireball.posY = player.posY + (double)(player.height / 2.0F) + 0.5D;
				entitysmallfireball.shoot(player, player.rotationPitch, player.rotationYawHead, 0.0F, 0.8F, 0.0F);
				world.spawnEntity(entitysmallfireball);				
				
			}
			*/
			stack.getTagCompound().setInteger("timer", 1);
			player.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
		}
        return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
    }
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
		if(entityLiving instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer)entityLiving;
			
			//GalaxySpace.debug("work");
			if(entityplayer.getActiveItemStack().hasTagCompound())
			{
				if(entityplayer.getActiveItemStack().getTagCompound().hasKey("timer"))
				{
					entityplayer.getActiveItemStack().getTagCompound().setInteger("timer", 0);
				}
			}
		}
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 10;
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.NONE;
    }
	
	@SideOnly(Side.CLIENT)
	public static void drawLine(BlockPos start, BlockPos end, double x, double y, double z)
	{		
		Vec3d start1 = new Vec3d(start);
		Vec3d end1 = new Vec3d(end);
		Vec3d posDiff = end1.subtract(start1);
		GlStateManager.pushMatrix();
		GlStateManager.glLineWidth(2F);
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.translate(-x, -y, -z);
		
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bb = tessellator.getBuffer();
        //GalaxySpace.instance.debug(start1 + " | " + end1 + " | " + posDiff);
		bb.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		bb.pos(x, y, z).color(1, 0, 0, 1F).endVertex();
		bb.pos(x + posDiff.x, y + posDiff.y, z + posDiff.z).color(1, 1, 1, 0.5F).endVertex();
		tessellator.draw();
		
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.popMatrix();
	
	}
}
