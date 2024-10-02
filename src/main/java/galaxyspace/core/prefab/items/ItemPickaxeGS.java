package galaxyspace.core.prefab.items;

import java.util.List;

import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;

public class ItemPickaxeGS extends ItemPickaxe
{
	protected boolean drillMode;
	public static String drill = "drill";	
	public int breakRadius;
    public int breakDepth;
    
    protected IIcon[] icons = new IIcon[2];
    static Material[] materials = new Material[] { Material.rock, Material.iron, Material.ice, Material.glass, Material.piston, Material.anvil };

	public ItemPickaxeGS(String assetName, ToolMaterial material, boolean drillMode)
    {
		super(material);
		this.setUnlocalizedName(assetName);
		this.setTextureName(GalaxySpace.ASSET_PREFIX + ":tools/" + assetName);
        this.drillMode = drillMode;        
        this.breakRadius = 1;
        this.breakDepth = 0;
        this.setNoRepair();        
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return GSCreativeTabs.GSArmorTab;
    }
    
    @Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
    	
    	if (!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
    	
    	if (!world.isRemote) {
    		if(this.drillMode)
    		{
    			if(!stack.stackTagCompound.getBoolean(drill))
    			{
    				stack.stackTagCompound.setBoolean(drill, true);
    				player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal("gui.pickaxe.drillmodeon")));
    			}
    			else
    			{
    				stack.stackTagCompound.setBoolean(drill, false);
    				player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("gui.pickaxe.drillmodeoff")));     			   
    			}
    		}
    	}
    	return stack;
    }
    /*
    @Override
    @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {

    	this.icons[0] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":tools/" + name);
    	//if(this.drillMode && this.isAncientPickaxe) this.icons[1] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":tools/" + name + "_1");
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass)
    {
		//return stack.stackTagCompound != null && stack.stackTagCompound.getBoolean(drill) && this.isAncientPickaxe ? this.icons[1] : this.icons[0];
    	return icons[0];
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
		//return stack.stackTagCompound != null && stack.stackTagCompound.getBoolean(drill) && this.isAncientPickaxe ? this.icons[1] : this.icons[0];
    	return icons[0];
    }
    */
    public boolean isEffective (Block block, int meta)
    {
        if(this.getHarvestType().equals(block.getHarvestTool(meta)))
            return true;

        else return isEffective(block.getMaterial());
    }

    public boolean isEffective (Material material)
    {
        for (Material m : getEffectiveMaterials())
            if (m == material)
                return true;

        return false;
    }
    
    protected String getHarvestType ()
    {
        return "pickaxe";
    }
    
    protected Material[] getEffectiveMaterials ()
    {
        return materials;
    }
    
    @Override
    public boolean func_150897_b (Block block)
    {
        return isEffective(block.getMaterial());
    }
    
    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        // only effective materials matter. We don't want to aoe when beraking dirt with a hammer.
        Block block = player.worldObj.getBlock(x,y,z);
        int meta = player.worldObj.getBlockMetadata(x,y,z);
        if(block == null || !isEffective(block, meta) || !stack.hasTagCompound())
            return super.onBlockStartBreak(stack, x,y,z, player);

       
        if(!stack.getTagCompound().getBoolean(drill))
            return super.onBlockStartBreak(stack, x,y,z, player);

        MovingObjectPosition mop = this.raytraceFromEntity(player.worldObj, player, false, 4.5d);
        if(mop == null)
            return super.onBlockStartBreak(stack, x,y,z, player);
        int sideHit = mop.sideHit;
        //int sideHit = Minecraft.getMinecraft().objectMouseOver.sideHit;

        // we successfully destroyed a block. time to do AOE!
        int xRange = breakRadius;
        int yRange = breakRadius;
        int zRange = breakDepth;
        switch (sideHit) {
            case 0:
            case 1:
                yRange = breakDepth;
                zRange = breakRadius;
                break;
            case 2:
            case 3:
                xRange = breakRadius;
                zRange = breakDepth;
                break;
            case 4:
            case 5:
                xRange = breakDepth;
                zRange = breakRadius;
                break;
        }

        for (int xPos = x - xRange; xPos <= x + xRange; xPos++)
            for (int yPos = y - yRange; yPos <= y + yRange; yPos++)
                for (int zPos = z - zRange; zPos <= z + zRange; zPos++) {
                    // don't break the originally already broken block, duh
                    if (xPos == x && yPos == y && zPos == z)
                        continue;

                    if(!super.onBlockStartBreak(stack, xPos, yPos, zPos, player))
                        breakExtraBlock(player.worldObj, xPos, yPos, zPos, sideHit, player, x,y,z);
                    
                }
        		if(!player.capabilities.isCreativeMode)
        			stack.setItemDamage(stack.getItemDamage() + 1);


        return super.onBlockStartBreak(stack, x, y, z, player);
    }
    
    public static MovingObjectPosition raytraceFromEntity (World world, Entity player, boolean par3, double range)
    {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f;
        if (!world.isRemote && player instanceof EntityPlayer)
            d1 += 1.62D;
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = range;
        if (player instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
        return world.func_147447_a(vec3, vec31, par3, !par3, par3);
    }

    protected void breakExtraBlock(World world, int x, int y, int z, int sidehit, EntityPlayer playerEntity, int refX, int refY, int refZ) {
        // prevent calling that stuff for air blocks, could lead to unexpected behaviour since it fires events
        if (world.isAirBlock(x, y, z))
            return;


     
        //if(!Minecraft.getMinecraft().isSingleplayer() && !world.isRemote && Loader.isModLoaded("EventHelper")) if(EventUtils.cantBreak(playerEntity, x, y, z)) return;
        
        // what?
        if(!(playerEntity instanceof EntityPlayerMP))
            return;
        EntityPlayerMP player = (EntityPlayerMP) playerEntity;

        // check if the block can be broken, since extra block breaks shouldn't instantly break stuff like obsidian
        // or precious ores you can't harvest while mining stone
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        Block refBlock = world.getBlock(refX, refY, refZ);
        float refStrength = ForgeHooks.blockStrength(refBlock, player, world, refX, refY, refZ);
        float strength = ForgeHooks.blockStrength(block, player, world, x,y,z);

        // only harvestable blocks that aren't impossibly slow to harvest
        if (!ForgeHooks.canHarvestBlock(block, player, meta) || refStrength/strength > 10f)
            return;
        
        if(!ForgeHooks.canToolHarvestBlock(block, meta, player.getCurrentEquippedItem()))
        	return;

        // send the blockbreak event
        BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, player.theItemInWorldManager.getGameType(), player, x,y,z);
        if(event.isCanceled())
            return;

        if (player.capabilities.isCreativeMode) {
            block.onBlockHarvested(world, x, y, z, meta, player);
            if (block.removedByPlayer(world, player, x, y, z, false))
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);

            // send update to client
            if (!world.isRemote) {
                player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
            }
            return;
        }

        // callback to the tool the player uses. Called on both sides. This damages the tool n stuff.
        player.getCurrentEquippedItem().func_150999_a(world, block, x, y, z, player);

        // server sided handling
        if (!world.isRemote) {
            // serverside we reproduce ItemInWorldManager.tryHarvestBlock

            // ItemInWorldManager.removeBlock
            block.onBlockHarvested(world, x,y,z, meta, player);

            if(block.removedByPlayer(world, player, x,y,z, true)) // boolean is if block can be harvested, checked above
            {
                block.onBlockDestroyedByPlayer( world, x,y,z, meta);
                block.harvestBlock(world, player, x,y,z, meta);
                block.dropXpOnBlockBreak(world, x,y,z, event.getExpToDrop());
            }

            // always send block update to client
            player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
        }
        // client sided handling
        else {
            //PlayerControllerMP pcmp = Minecraft.getMinecraft().playerController;
            // clientside we do a "this clock has been clicked on long enough to be broken" call. This should not send any new packets
            // the code above, executed on the server, sends a block-updates that give us the correct state of the block we destroy.

            // following code can be found in PlayerControllerMP.onPlayerDestroyBlock
            world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
            if(block.removedByPlayer(world, player, x,y,z, true))
            {
                block.onBlockDestroyedByPlayer(world, x,y,z, meta);
            }
            // callback to the tool
            ItemStack itemstack = player.getCurrentEquippedItem();
            if (itemstack != null)
            {
                itemstack.func_150999_a(world, block, x, y, z, player);

                if (itemstack.stackSize == 0)
                {
                    player.destroyCurrentEquippedItem();
                }
            }
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(2, x,y,z, Minecraft.getMinecraft().objectMouseOver.sideHit));
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List par2List, boolean b)
    {
    	 if(this.drillMode) 
    	 {
    		 	if(stack.stackTagCompound != null && stack.stackTagCompound.getBoolean(drill))
    		 		par2List.add(EnumColor.DARK_GREEN + GCCoreUtil.translate("gui.pickaxe.drillmodeon"));
    		 	else par2List.add(EnumColor.DARK_RED + GCCoreUtil.translate("gui.pickaxe.drillmodeoff"));
    	 }
     	
    }
    
  
    
    
}
