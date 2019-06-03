package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import galaxyspace.core.configs.GSConfigEnergy;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockModernSolarPanel;
import micdoodle8.mods.galacticraft.api.tile.IDisableableMachine;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.api.transmission.tile.IConnector;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.blocks.BlockMulti;
import micdoodle8.mods.galacticraft.core.blocks.BlockMulti.EnumBlockMultiType;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectricalSource;
import micdoodle8.mods.galacticraft.core.inventory.IInventoryDefaults;
import micdoodle8.mods.galacticraft.core.tile.IMultiBlock;
import micdoodle8.mods.galacticraft.core.tile.TileEntityMulti;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
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
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityModernSolarPanel extends TileBaseUniversalElectricalSource implements IMultiBlock, IDisableableMachine, IInventoryDefaults, ISidedInventory, IConnector
{
    @NetworkedField(targetSide = Side.CLIENT)
    public int solarStrength = 0;
    public double angle;
    public float targetAngle;
    public float currentAngle;
    @NetworkedField(targetSide = Side.CLIENT)
    public boolean disabled = false;
    @NetworkedField(targetSide = Side.CLIENT)
    public int disableCooldown = 0;
    private NonNullList<ItemStack> stacks = NonNullList.withSize(2, ItemStack.EMPTY);
    public static final int MAX_GENERATE_WATTS = 450;
    @NetworkedField(targetSide = Side.CLIENT)
    public int generateWatts = 0;

    private boolean initialised = false;
    private boolean initialisedMulti = false;
    private AxisAlignedBB renderAABB;

    /*
     * @param tier: 1 = Basic Solar  2 = Advanced Solar
     */
    public TileEntityModernSolarPanel()
    {
        this.storage.setMaxExtract(TileEntityModernSolarPanel.MAX_GENERATE_WATTS);
        this.storage.setMaxReceive(TileEntityModernSolarPanel.MAX_GENERATE_WATTS);
        this.storage.setCapacity(40000);
           

        this.initialised = true;
        this.tierGC = 1;
    }

    @Override
    public void update()
    {
        if (!this.initialised)
        {
            int metadata = this.getBlockMetadata();
           	this.storage.setCapacity(40000);
              
            this.initialised = true;
        }

        if (!this.initialisedMulti)
        {
            this.initialisedMulti = this.initialiseMultiTiles(this.getPos(), this.world);
        }
        
        if (!this.world.isRemote)        
        	this.receiveEnergyGC(null, this.generateWatts, false);

        super.update();

        if (!this.world.isRemote)
        {

            this.recharge(this.stacks.get(0));
                       
            //this.storage.setCapacity(this.containingItems[1] != null ? 60000 : 40000);
            if (this.disableCooldown > 0)
            {
                this.disableCooldown--;
            }

            if (!this.getDisabled(0) && this.ticks % 20 == 0)
            {
                this.solarStrength = 0;

                if (this.world.isDaytime() && (this.world.provider instanceof IGalacticraftWorldProvider || !this.world.isRaining() && !this.world.isThundering()))
                {
                    double distance = 100.0D;
                    double sinA = -Math.sin((this.currentAngle - 77.5D) * Math.PI / 180.0D);
                    double cosA = Math.abs(Math.cos((this.currentAngle - 77.5D) * Math.PI / 180.0D));

                    for (int x = -1; x <= 1; x++)
                    {
                        for (int z = -1; z <= 1; z++)
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
                                    this.solarStrength++;
                                }
                         
                        }
                    }
                }
            }
        }

        float angle = this.world.getCelestialAngle(1.0F) - 0.7845194F < 0 ? 1.0F - 0.7845194F : -0.7845194F;
        float celestialAngle = (this.world.getCelestialAngle(1.0F) + angle) * 360.0F;

        
        celestialAngle %= 360;

       /* if (this.tierGC == 1)
        {
            if (!this.worldObj.isDaytime() || this.worldObj.isRaining() || this.worldObj.isThundering())
            {
                this.targetAngle = 77.5F + 180.0F;
            }
            else
            {
                this.targetAngle = 77.5F;
            }
        }
        else
        {*/
            if (celestialAngle > 30 && celestialAngle < 150 && !this.world.isRaining())
            {
                float difference = this.targetAngle - celestialAngle;

                this.targetAngle -= difference / 20.0F;
                
            }
            else if (!this.world.isDaytime() || this.world.isRaining() || this.world.isThundering())
            {
                this.targetAngle = 77.5F + 100.0F;
            }
            else if (celestialAngle < 50)
            {
                this.targetAngle = 50;
            }
            else if (celestialAngle > 150)
            {
                this.targetAngle = 150;
            }
        //}

        float difference = this.targetAngle - this.currentAngle;

        this.currentAngle += difference / 20.0F;

        if (!this.world.isRemote)
        {
            if (this.getGenerate() > 0.0F)
            {
                this.generateWatts = Math.min(Math.max(this.getGenerate(), 0), TileEntityModernSolarPanel.MAX_GENERATE_WATTS);
            }
            else
            {
                this.generateWatts = 0;
            }
        }

        this.produce();
    }

    protected boolean initialiseMultiTiles(BlockPos pos, World world)
    {
        //Client can create its own fake blocks and tiles - no need for networking in 1.8+
        if (world.isRemote) this.onCreate(world, pos);
        
        List<BlockPos> positions = new ArrayList<>();
        this.getPositions(pos, positions);
        boolean result = true;
        for (BlockPos vecToAdd : positions)
        {
            TileEntity tile = world.getTileEntity(vecToAdd);
            if (tile instanceof TileEntityMulti)
            {
                ((TileEntityMulti) tile).mainBlockPosition = pos;
            }
            else
            {
                result = false;
            }
        }
        return result;
    }
    
    public int getGenerate()
    {
        if (this.getDisabled(0))
        {
            return 0;
        }

        float angle = this.world.getCelestialAngle(1.0F) - 0.784690560F < 0 ? 1.0F - 0.784690560F : -0.784690560F;
        float celestialAngle = (this.world.getCelestialAngle(1.0F) + angle) * 360.0F;

        celestialAngle %= 360;

        float difference = (180.0F - Math.abs(this.currentAngle % 180 - celestialAngle)) / 180.0F;
        return MathHelper.floor((0.01F * difference * difference * (this.solarStrength * (Math.abs(difference) * 500.0F)) * this.getSolarBoost()) * (2 * GSConfigEnergy.coefficientSolarPanel));
    }

    public float getSolarBoost()
    {
        return (float) (this.world.provider instanceof ISolarLevel ? ((ISolarLevel) this.world.provider).getSolarEnergyMultiplier() : 1.0F);
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
            ((BlockMulti) GCBlocks.fakeBlock).makeFakeBlock(world, positions.get(0), placedPosition, EnumBlockMultiType.SOLAR_PANEL_0.getMeta());
            positions.remove(0);
        }
        ((BlockMulti) GCBlocks.fakeBlock).makeFakeBlock(world, positions, placedPosition, this.getMultiType());
    }

    @Override
    public void getPositions(BlockPos placedPosition, List<BlockPos> positions)
    {
        int buildHeight = this.world.getHeight() - 1;
        int y = placedPosition.getY() + 2; 
        if (y > buildHeight)
        {
            return;
        }
        positions.add(new BlockPos(placedPosition.getX(), y, placedPosition.getZ()));

        y++;
        if (y > buildHeight)
        {
            return;
        }
        for (int x = -1; x < 2; x++)
        {
            for (int z = -1; z < 2; z++)
            {
                positions.add(new BlockPos(placedPosition.getX() + x, y, placedPosition.getZ() + z));
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
                if ((type == EnumBlockMultiType.SOLAR_PANEL_0 || type == EnumBlockMultiType.SOLAR_PANEL_1))
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

        this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.stacks);

        this.initialised = false;
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

        ItemStackHelper.saveAllItems(nbt, this.stacks);
        
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
        if (state.getBlock() instanceof BlockModernSolarPanel)
        {
            return state.getValue(BlockModernSolarPanel.FACING);
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
    public boolean hasCustomName()
    {
        return true;
    }
    
    @Override
    public String getName()
    {
        return GCCoreUtil.translate("tile.modern_solarpanel.name");
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
    public int getSizeInventory()
    {
        return this.stacks.size();
    }

    @Override
    public ItemStack getStackInSlot(int var1)
    {
        return this.stacks.get(var1);
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack itemstack = ItemStackHelper.getAndSplit(this.stacks, index, count);

        if (!itemstack.isEmpty())
        {
            this.markDirty();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        ItemStack oldstack = ItemStackHelper.getAndRemove(this.stacks, index);
        if (!oldstack.isEmpty())
        {
        	this.markDirty();
        }
    	return oldstack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        this.stacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();
    }
    
    @Override
    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.stacks)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
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
		return EnumBlockMultiType.SOLAR_PANEL_0;
	}
}
