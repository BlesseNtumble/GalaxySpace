package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import galaxyspace.api.tile.ITileEffects;
import galaxyspace.core.configs.GSConfigEnergy;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockPanelController;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockSingleSolarPanel;
import micdoodle8.mods.galacticraft.api.tile.IDisableableMachine;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.api.transmission.tile.IConnector;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectricalSource;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldProviderHell;
import net.minecraftforge.fml.relauncher.Side;

public class TileEntityPanelController extends TileBaseUniversalElectricalSource implements ITileEffects, IDisableableMachine, ISidedInventory, IConnector{

    public static final float MIN_GENERATE_GJ_PER_TICK = 1;
    
	private Map<BlockPos, Integer> panels = new HashMap<BlockPos, Integer>();

	@NetworkedField(targetSide = Side.CLIENT)
    public float heatGJperTick = 0;
	
	private boolean visible = false;
		
	public TileEntityPanelController()
    {
		super("tile.panel_controller.name");
		this.storage.setCapacity(5000);
		this.storage.setMaxExtract(2000);
		this.inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    }
	
	@Override
	public void update() {
		
		if (this.heatGJperTick - MIN_GENERATE_GJ_PER_TICK > 0)
        {
            this.receiveEnergyGC(null, (this.heatGJperTick - MIN_GENERATE_GJ_PER_TICK), false);
        }
		
		super.update();
		panels.clear();
		int distance = 21; // +1 for module
		int modules = 1;
		
		int xMin = 0, xMax = 0;
		int zMin = 0, zMax = 0;
		
		/*if(this.world.getBlockState(this.getPos().offset(getFront())).getBlock() instanceof BlockSingleSolarPanel)
		{*/		
			switch (this.getFront())
			{
				case EAST:					
					xMin = 1; xMax = distance - 1;
					zMin = -2; zMax = 3;
					
					for(int x = 0; x <= distance; x++)
						for(int z = -2; z <= 2; z++)
						{
							if(x <= distance - 1) 
							{
								BlockPos pos = new BlockPos(this.getPos().getX() + x, this.getPos().getY(), this.getPos().getZ() + z);
								
								if(this.world.getBlockState(pos).getBlock() instanceof BlockSingleSolarPanel)
								{		
									if(this.world.canBlockSeeSky(pos.up()))
									{
										
										panels.put(pos, ((BlockSingleSolarPanel)world.getBlockState(pos).getBlock()).getTier());																										
									}
								}
							}
						}
					
					break;
				case NORTH:
					xMin = -2; xMax = 3;
					zMin = -distance + 1; zMax = 0;
					
					for(int x = -2; x <= 2; x++)
						for(int z = zMin; z <= zMax - 1; z++)
						{
							if(z >= zMin) 
							{
								BlockPos pos = new BlockPos(this.getPos().getX() + x, this.getPos().getY(), this.getPos().getZ() + z);
								
								if(this.world.getBlockState(pos).getBlock() instanceof BlockSingleSolarPanel)
								{		
									if(this.world.canBlockSeeSky(pos.up()))
									{
							
										panels.put(pos, ((BlockSingleSolarPanel)world.getBlockState(pos).getBlock()).getTier());																	
									}
								}
							}
						}
					break;
				case SOUTH:					
					xMin = -2; xMax = 3;
					zMin = 1; zMax = distance - 1;
					
					for(int x = -2; x <= 2; x++)
						for(int z = 0; z <= distance; z++)
						{
							if(z <= distance - 1) 
							{
								BlockPos pos = new BlockPos(this.getPos().getX() + x, this.getPos().getY(), this.getPos().getZ() + z);
								
								if(this.world.getBlockState(pos).getBlock() instanceof BlockSingleSolarPanel)
								{		
									if(this.world.canBlockSeeSky(pos.up()))
									{							
										panels.put(pos, ((BlockSingleSolarPanel)world.getBlockState(pos).getBlock()).getTier());																		
									}
								}
							}
						}
					break;
				case WEST:
					xMin = -distance + 1; xMax = 0;
					zMin = -2; zMax = 3;
					
					for(int x = -distance; x <= 0; x++)
						for(int z = -2; z <= 2; z++)
						{
							if(x >= -distance + 1) 
							{
								BlockPos pos = new BlockPos(this.getPos().getX() + x, this.getPos().getY(), this.getPos().getZ() + z);
								
								if(this.world.getBlockState(pos).getBlock() instanceof BlockSingleSolarPanel)
								{		
									if(this.world.canBlockSeeSky(pos.up()))
									{
										panels.put(pos, ((BlockSingleSolarPanel)world.getBlockState(pos).getBlock()).getTier());
									}
								}
							}
						}
					
					break;
				default:
					break;					
			}
		//}	
		
		if(world.isRemote && this.visible)
		{
				
				for(int x = xMin; x <= xMax; x++)
					for(int z = zMin; z <= zMax; z++)				
					{
						if(x == xMin || x == xMax|| z == zMin || z == zMax)
						world.spawnParticle(EnumParticleTypes.REDSTONE, this.getPos().getX() + x,
						this.getPos().getY(),
						this.getPos().getZ() + z, 0.0D, 0.0D,
						0.0D);
					}
		}
		

		if (!this.world.isRemote)
        {
			this.produce();

		
			if (this.world.isDaytime() && (this.world.provider instanceof IGalacticraftWorldProvider || !this.world.isRaining() && !this.world.isThundering()))
			{
				float angle = this.world.getCelestialAngle(1.0F) - 0.784690560F < 0 ? 1.0F - 0.784690560F : -0.784690560F;
				float celestialAngle = (this.world.getCelestialAngle(1.0F) + angle) * 360.0F;

				celestialAngle %= 360;
				float difference = (180.0F - Math.abs(celestialAngle % 180 - celestialAngle)) / 180.0F;

				
				int t2_count = 0;
				for(Entry<BlockPos, Integer> panel : panels.entrySet())
				{
					//GalaxySpace.debug(panel.getKey() + " | " + ((BlockSingleSolarPanel)world.getBlockState(panel.getKey()).getBlock()).getTier() + "");
					if(panel.getValue() == 2)
						t2_count++; 
				}
				
				//GalaxySpace.debug(t2_count + "");
				
				this.heatGJperTick = MathHelper.floor((0.01F * difference * difference * (9 * (Math.abs(difference) * 500.0F)) * this.getSolarBoost() + (1 * t2_count * 0.5F)) * (panels.size() * GSConfigEnergy.coefficientSolarPanel) / 5);
						//(panels.size() + 2 * GSConfigEnergy.coefficientSolarPanel) * getSolarBoost();
			}
			else
			{
				this.heatGJperTick = 0;
			}

        }
		
		
	}
	
	public float getSolarBoost()
	{
		if(this.world.provider instanceof WorldProviderHell) 
			return 0.0F;
		
		return (float) (this.world.provider instanceof ISolarLevel ? ((ISolarLevel) this.world.provider).getSolarEnergyMultiplier() : 1.0F);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items", 10);

		this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(par1NBTTagCompound, this.getInventory());
	}

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        ItemStackHelper.saveAllItems(par1NBTTagCompound, this.getInventory());        
        return par1NBTTagCompound;
    }
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.world.getTileEntity(this.getPos()) == this	&& par1EntityPlayer.getDistanceSq(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D,	this.getPos().getZ() + 0.5D) <= 64.0D;
	} 


	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return null;
	}

	@Override
	public boolean canInsertItem(int slotID, ItemStack itemstack, EnumFacing direction) {
		return this.isItemValidForSlot(slotID, itemstack);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}

	@Override
    public float receiveElectricity(EnumFacing from, float energy, int tier, boolean doReceive)
    {
        return 0;
    }
	
	@Override
	public void setDisabled(int index, boolean disabled) {
		
	}

	@Override
	public boolean getDisabled(int index) {
		return false;
	}
	
	@Override
    public EnumSet<EnumFacing> getElectricalInputDirections()
    {
        return EnumSet.noneOf(EnumFacing.class);
    }

    @Override
    public EnumSet<EnumFacing> getElectricalOutputDirections()
    {
        return EnumSet.of(this.getElectricOutputDirection());
    }
    
    public EnumFacing getFront()
    {
        IBlockState state = this.world.getBlockState(getPos()); 
        if (state.getBlock() instanceof BlockPanelController)
        {
            return state.getValue(BlockPanelController.FACING);
        }
        return EnumFacing.NORTH;
    }
    
    @Override
    public EnumFacing getElectricOutputDirection()
    {
        return getFront().rotateY().rotateY();
    }
    
    @Override
    public boolean canConnect(EnumFacing direction, NetworkType type)
    {
        if (direction == null)
        {
            return false;
        } 
        if (type == NetworkType.POWER)
        {
            return direction == this.getElectricOutputDirection();
        }
        return false;
    }

	@Override
	public void setEffectsVisible(boolean shouldRender) {	
		this.visible = shouldRender;
	}

	@Override
	public boolean getEffectsVisible() {
		return this.visible;
	}
}
