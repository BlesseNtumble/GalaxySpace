package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import asmodeuscore.core.utils.Utils;
import galaxyspace.core.GSItems;
import galaxyspace.core.configs.GSConfigEnergy;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockWindGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS;
import micdoodle8.mods.galacticraft.api.tile.IDisableableMachine;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.api.transmission.tile.IConnector;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.blocks.BlockMulti;
import micdoodle8.mods.galacticraft.core.blocks.BlockMulti.EnumBlockMultiType;
import micdoodle8.mods.galacticraft.core.dimension.WorldProviderSpaceStation;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectricalSource;
import micdoodle8.mods.galacticraft.core.tile.IMultiBlock;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityWindGenerator extends TileBaseUniversalElectricalSource implements IMultiBlock, IDisableableMachine, IConnector
{
    //@NetworkedField(targetSide = Side.CLIENT)
    //public int solarStrength = 0;
    public float angle;
    public float targetAngle;
    public float currentAngle;
    @NetworkedField(targetSide = Side.CLIENT)
    public boolean disabled = false;
    @NetworkedField(targetSide = Side.CLIENT)
    public int disableCooldown = 0;
    public static final int MAX_GENERATE_WATTS = 450;
    @NetworkedField(targetSide = Side.CLIENT)
    public float generateWatts = 0;
    private AxisAlignedBB renderAABB;

    private boolean advanced;
    

    public TileEntityWindGenerator()
    {
    	this(false);
    }
    
    public TileEntityWindGenerator(boolean adv)
    {
    	super(adv ? "tile.adv_wind_generator.name" : "tile.wind_generator.name");
        this.storage.setMaxExtract(this.MAX_GENERATE_WATTS);
        this.storage.setMaxReceive(this.MAX_GENERATE_WATTS);
        this.inventory = NonNullList.withSize(2, ItemStack.EMPTY);
        this.advanced = adv;
    }
    
    public boolean isAdvanced() {
    	return this.advanced;
    }

    @Override
    public void update()
    {      
        this.receiveEnergyGC(null, this.generateWatts, false);

        super.update();

        
        if (!this.world.isRemote)
        {
        	if(Utils.isDustStorm(getWorld()) && !this.advanced)
        		return;
        	
        	this.recharge(this.getInventory().get(0));

            if (this.disableCooldown > 0)
            {
                this.disableCooldown--;
            }

            if (!this.getDisabled(0) && this.ticks % 20 == 0 && getFanType() >= 0)
            {
                //this.solarStrength = 0;

                if ((this.world.provider instanceof IGalacticraftWorldProvider))
                {
                    double distance = 100.0D;
                    double sinA = -Math.sin((this.currentAngle - 77.5D) * Math.PI / 180.0D);
                    double cosA = Math.abs(Math.cos((this.currentAngle - 77.5D) * Math.PI / 180.0D));

                    for (int x = -1; x <= 2; x++)
                    {
                        for (int z = -1; z <= 2; z++)
                        {                          
                        	if (this.world.canBlockSeeSky(new BlockPos(this.getPos().getX() + x, this.getPos().getY() + 2, this.getPos().getZ() + z)))
                            {
                        		 boolean valid = true;

                                 BlockVec3 blockVec = new BlockVec3(this).translate(x, 3, z);
                                 for (double d = 0.0D; d < distance; d++)
                                 {
                                     BlockVec3 blockAt = blockVec.clone().translate((int) (d * sinA), (int) (d * cosA), 0);
                                     IBlockState state = blockAt.getBlockState(this.world);

                                     if (state == null)
                                     {
                                         break;
                                     }
                                     
                                     if (state.getBlock().isOpaqueCube(state))
                                     {
                                         valid = false;
                                         break;
                                     }
                                 }

                                 if (valid)
                                 {
                                     //this.solarStrength++;
                                 }
                            }
                        }
                    }
                }
            }
        }

        if (!this.world.isRemote)
        {
            if (this.getGenerate() > 0.0F && this.world.canBlockSeeSky(this.getPos().up()))
            {
                this.generateWatts = Math.min(Math.max(this.getGenerate(), 0), this.MAX_GENERATE_WATTS);
                
                if(this.getInventory().get(1).getItem() == GSItems.BASIC) {
	                if(!this.getInventory().get(1).hasTagCompound()) 
	                	this.getInventory().get(1).setTagCompound(new NBTTagCompound());
	    			
	    			if(!this.getInventory().get(1).getTagCompound().hasKey("destroyedLvl")) 
	    				this.getInventory().get(1).getTagCompound().setInteger("destroyedLvl", 0);		
	    			
	                if(ticks % 20 == 0) {
		                if(this.getInventory().get(1).getTagCompound().getInteger("destroyedLvl") < ItemBasicGS.FANS_DURABILITY[getFanType()])
		                	this.getInventory().get(1).getTagCompound().setInteger("destroyedLvl", this.getInventory().get(1).getTagCompound().getInteger("destroyedLvl") + 1);
		    			else {
		    				this.getInventory().get(1).splitStack(1);
		    				this.world.scheduleBlockUpdate(getPos(), getBlockType(), 0, 0);
		    			}
	                }
                }
            }
            else
            {
                this.generateWatts = 0;
            }
        }

        this.produce();
    }

    public float getGenerate()
    {
    	if (this.getDisabled(0))
        {
            return 0;
        }
    	
    	if(getFanType() < 0)
    		return 0;

    	float watts = MathHelper.floor((10.0F + 5.0F * Math.round(this.getPos().getY() / 15.0F) * (getFanType() + 1)) * this.getWindBoost()) * (GSConfigEnergy.coefficientWindTurbine) ;
    	if(this.advanced)
    		watts *= 2;
    	return watts;
    }

    public float getWindBoost()
    {
    	if(this.world.provider instanceof IGalacticraftWorldProvider && !(this.world.provider instanceof WorldProviderSpaceStation))
    		if(this.world.canBlockSeeSky(this.getPos().up()))
    			return ((IGalacticraftWorldProvider) this.world.provider).getWindLevel();
    	
    	if(this.world.provider instanceof WorldProviderSurface)
    		return this.world.isThundering() ? 3.0F : 1.0F ;
    	
        return 0.0F;
    }

    @Override
    public boolean onActivated(EntityPlayer entityPlayer)
    {
        return false;
    }
    
    @Override
    public void onCreate(World world, BlockPos placedPosition)
    {
    	List<BlockPos> positions = new LinkedList<>();
        this.getPositions(placedPosition, positions);
        if (positions.size() > 0)
        {
            ((BlockMulti) GCBlocks.fakeBlock).makeFakeBlock(world, positions.get(0), placedPosition, EnumBlockMultiType.DISH_LARGE.getMeta());
            positions.remove(0);
        }
        ((BlockMulti) GCBlocks.fakeBlock).makeFakeBlock(world, positions, placedPosition, this.getMultiType());
    }

    @Override
    public void getPositions(BlockPos placedPosition, List<BlockPos> positions)
    {
        int buildHeight = this.world.getHeight() - 1;
        int y = placedPosition.getY() + 1; 
        if (y > buildHeight)
        {
            return;
        }
       
     

       
        if(this.advanced) {
        	positions.add(new BlockPos(placedPosition.getX(), y, placedPosition.getZ()));
        	positions.add(new BlockPos(placedPosition.getX(), ++y, placedPosition.getZ()));
        	for (int yy = 0; yy < 3; yy++)
	        	for (int x = -1; x < 2; x++)
	        		for (int z = -1; z < 2; z++)
	        			positions.add(new BlockPos(placedPosition.getX() + x, y + yy + 1, placedPosition.getZ() + z));
        }
        
        if(!this.advanced) {
        	positions.add(new BlockPos(placedPosition.getX(), y, placedPosition.getZ()));
            positions.add(new BlockPos(placedPosition.getX(), ++y, placedPosition.getZ()));
            positions.add(new BlockPos(placedPosition.getX(), ++y, placedPosition.getZ()));
            
	        switch (this.getBlockMetadata()) {
	        	case 0:	
	        		for (int x = -1; x < 2; x++)
	                {
	                    for (int z = -1; z < 2; z++)
	                    {
	                        positions.add(new BlockPos(placedPosition.getX() - 1, y + x, placedPosition.getZ() + z));
	                    }
	                }
	        		break;
	        	case 1:	
	        		for (int x = -1; x < 2; x++)
	                {
	                    for (int z = -1; z < 2; z++)
	                    {
	                        positions.add(new BlockPos(placedPosition.getX() + z, y + x, placedPosition.getZ() - 1));
	                    }
	                }
	        		break;
	        	case 2:	
	        		for (int x = -1; x < 2; x++)
	                {
	                    for (int z = -1; z < 2; z++)
	                    {
	                        positions.add(new BlockPos(placedPosition.getX() + 1, y + x, placedPosition.getZ() + z));
	                    }
	                }
	        		break;
	        	case 3:	
	        		for (int x = -1; x < 2; x++)
	                {
	                    for (int z = -1; z < 2; z++)
	                    {
	                        positions.add(new BlockPos(placedPosition.getX() + z, y + x, placedPosition.getZ() + 1));
	                    }
	                }
	        		break;
	        	
	        }
        }
       
    }
    
    @Override
    public void onDestroy(TileEntity callingBlock)
    {
    	final BlockPos thisBlock = getPos();
        List<BlockPos> positions = new ArrayList<>();
        this.getPositions(thisBlock, positions);

        for (BlockPos pos : positions)
        {
            IBlockState stateAt = this.world.getBlockState(pos);

            if (stateAt.getBlock() == GCBlocks.fakeBlock)
            {
                EnumBlockMultiType type = (EnumBlockMultiType) stateAt.getValue(BlockMulti.MULTI_TYPE);
                if ((type == getMultiType()))
                {
                    if (this.world.isRemote && this.world.rand.nextDouble() < 0.1D)
                    {
                        FMLClientHandler.instance().getClient().effectRenderer.addBlockDestroyEffects(pos, GCBlocks.solarPanel.getDefaultState());
                    }

                    this.world.setBlockToAir(pos);
                }
            }
        }

        this.world.destroyBlock(getPos(), true);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.storage.setCapacity(nbt.getFloat("maxEnergy"));
        this.currentAngle = nbt.getFloat("currentAngle");
        this.targetAngle = nbt.getFloat("targetAngle");
        this.setDisabled(0, nbt.getBoolean("disabled"));
        this.disableCooldown = nbt.getInteger("disabledCooldown");
        this.advanced = nbt.getBoolean("advanced");

        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.getInventory());

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setFloat("maxEnergy", this.getMaxEnergyStoredGC());
        nbt.setFloat("currentAngle", this.currentAngle);
        nbt.setFloat("targetAngle", this.targetAngle);
        nbt.setInteger("disabledCooldown", this.disableCooldown);
        nbt.setBoolean("disabled", this.getDisabled(0));
        nbt.setBoolean("advanced", this.advanced);

        ItemStackHelper.saveAllItems(nbt, this.getInventory());
        
        return nbt;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return Constants.RENDERDISTANCE_LONG;
    }
    @Override
    public EnumSet<EnumFacing> getElectricalInputDirections()
    {
        return EnumSet.noneOf(EnumFacing.class);
    }
    
    @Override
    public EnumSet<EnumFacing> getElectricalOutputDirections()
    {
        return EnumSet.of(getFront());
    }
    
    
    public EnumFacing getFront()
    {
        IBlockState state = this.world.getBlockState(getPos()); 
        if (state.getBlock() instanceof BlockWindGenerator)
        {
            return state.getValue(BlockWindGenerator.FACING);
        }
        return EnumFacing.NORTH;
    }

    @Override
    public EnumFacing getElectricOutputDirection()
    {
        return getFront();
    }


    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        if (this.renderAABB == null)
        {
            this.renderAABB = new AxisAlignedBB(getPos().getX() - 1, getPos().getY(), getPos().getZ() - 1, getPos().getX() + 2, getPos().getY() + 4, getPos().getZ() + 2); 
        }
        return this.renderAABB;
    }

    @Override
    public void setDisabled(int index, boolean disabled)
    {
        if (this.disableCooldown == 0)
        {
            this.disabled = disabled;
            this.disableCooldown = 20;
        }
    }

    @Override
    public boolean getDisabled(int index)
    {
        return this.disabled;
    }

    public int getScaledElecticalLevel(int i)
    {
        return (int) Math.floor(this.getEnergyStoredGC() * i / this.getMaxEnergyStoredGC());
    }
    
    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer)
    {
    	return this.world.getTileEntity(this.getPos()) == this && par1EntityPlayer.getDistanceSq(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return new int[] { 0 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack itemstack, EnumFacing side)
    {
        return this.isItemValidForSlot(slotID, itemstack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, EnumFacing side)
    {
        return slotID == 0;
    }

    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemstack)
    {
        return slotID == 0 && ItemElectricBase.isElectricItem(itemstack.getItem());
    }

    @Override
    public boolean canConnect(EnumFacing direction, NetworkType type)
    {
        if (direction == null || type != NetworkType.POWER)
        {
            return false;
        }

        return direction == this.getElectricOutputDirection();
    }

	@Override
	public EnumBlockMultiType getMultiType() {
		return EnumBlockMultiType.DISH_LARGE;
	}
	
	public int getFanType() {
		if(this.getInventory().get(1).isEmpty())
			return -1;
		
		return this.getInventory().get(1).getMetadata()-33;
	}
}
