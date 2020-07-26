package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.ArrayList;

import galaxyspace.core.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockAdvCircuitFabricator;
import micdoodle8.mods.galacticraft.api.recipe.CircuitFabricatorRecipes;
import micdoodle8.mods.galacticraft.api.world.IZeroGDimension;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.items.ItemBasic;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;

public class TileEntityAdvCircuitFabricator extends TileBaseElectricBlockWithInventory implements ISidedInventory
{
    public static final int PROCESS_TIME_REQUIRED = 300;
    @NetworkedField(targetSide = Side.CLIENT)
    public int processTicks = 0;
    private ItemStack producingStack = ItemStack.EMPTY;
    private long ticks;

   
    public TileEntityAdvCircuitFabricator()
    {
    	super("tile.adv_circuit_fabricator.name");
        this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 40 : 20);
        this.inventory = NonNullList.withSize(7 + 4, ItemStack.EMPTY);
    }

    @Override
    public void update()
    {
        super.update();

        this.updateInput();

        if (!this.world.isRemote)
        {
            boolean updateInv = false;

            if (this.hasEnoughEnergyToRun)
            {
                if (this.canCompress())
                {                	
                	//////////
                	int boost_speed = 0;
                	int energy_boost = 0;
                	
                	for(int i = 0; i <= 3; i++)
                	{
                		if(this.getInventory().get(getInventory().size() - i - 1).isItemEqual(new ItemStack(GSItems.UPGRADES, 1, 2)))
                			boost_speed++;
                		if(this.getInventory().get(getInventory().size() - i - 1).isItemEqual(new ItemStack(GSItems.UPGRADES, 1, 3)))
                			energy_boost++;
                	}
                	
                    this.processTicks += 2 + boost_speed;
                    this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 90 + (40 * boost_speed) - (20 * energy_boost): 75 + (35 * boost_speed) - (15 * energy_boost));
                    /////////////
                    
                   // ++this.processTicks;

                    if (this.processTicks >= this.getProcessTimeRequired())
                    {
                        this.world.playSound(null, this.getPos(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 0.3F, this.world.rand.nextFloat() * 0.1F + 0.9F);
                        this.processTicks = 0;
                        this.compressItems();
                        updateInv = true;
                    }
                }
                else
                {
                    this.processTicks = 0;
                }
            }
            else
            {
                this.processTicks = 0;
            }

            if (updateInv)
            {
                this.markDirty();
            }
        }

        this.ticks++;
    }

    public int getProcessTimeRequired()
    {
        return PROCESS_TIME_REQUIRED * 2 / (1 + this.poweredByTierGC);
    }

    public void updateInput()
    {
        this.producingStack = CircuitFabricatorRecipes.getOutputForInput(this.getInventory().subList(1, 6));
    }

    private boolean canCompress()
    {
        if (this.producingStack.isEmpty())
        {
            return false;
        }
        if (this.getInventory().get(6).isEmpty())
        {
            return true;
        }
        if (!this.getInventory().get(6).isEmpty() && !this.getInventory().get(6).isItemEqual(this.producingStack))
        {
            return false;
        }
        int result = this.getInventory().get(6).isEmpty() ? 0 : this.getInventory().get(6).getCount() + this.producingStack.getCount();
        return result <= this.getInventoryStackLimit() && result <= this.producingStack.getMaxStackSize();
    }

    public void compressItems()
    {
        if (this.canCompress())
        {
            ItemStack resultItemStack = this.producingStack.copy();
            if (this.world.provider instanceof IZeroGDimension)
            {
                if (resultItemStack.getItem() == GCItems.basicItem)
                {
                    if (resultItemStack.getItemDamage() == ItemBasic.WAFER_BASIC)
                    {
                        resultItemStack.setCount(5);
                    }
                    else if (resultItemStack.getItemDamage() == 12)  //Solar panels
                    {
                        resultItemStack.setCount(15);
                    }
                    else
                    {
                        resultItemStack.setCount(resultItemStack.getCount() * 2);
                    }
                }
            }

            if (this.getInventory().get(6).isEmpty())
            {
                this.getInventory().set(6, resultItemStack);
            }
            else if (this.getInventory().get(6).isItemEqual(resultItemStack))
            {
                if (this.getInventory().get(6).getCount() + resultItemStack.getCount() > 64)
                {
                    resultItemStack.setCount(this.getInventory().get(6).getCount() + resultItemStack.getCount() - 64);
                    GCCoreUtil.spawnItem(this.world, this.getPos(), resultItemStack);
                    this.getInventory().get(6).setCount(64);
                }
                else
                {
                    this.getInventory().get(6).grow(resultItemStack.getCount());
                }
            }
        }

        for (int i = 1; i < 6; i++)
        {
            this.decrStackSize(i, 1);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.processTicks = nbt.getInteger("smeltingTicks");
       
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("smeltingTicks", this.processTicks);

        return nbt;
    }


    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemStack)
    {
        if (slotID == 0)
        {
            return itemStack != null && ItemElectricBase.isElectricItem(itemStack.getItem());
        }

        if (slotID > 5)
        {
            return false;
        }

        ArrayList<ItemStack> list = CircuitFabricatorRecipes.slotValidItems.get(slotID - 1);

        for (ItemStack test : list)
        {
            if (test.isItemEqual(itemStack))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        if (side == EnumFacing.DOWN)
        {
            return new int[] { 6 };
        }

        //Offer whichever silicon slot has less silicon
        boolean siliconFlag = !this.getInventory().get(2).isEmpty() && (this.getInventory().get(3).isEmpty() || this.getInventory().get(3).getCount() < this.getInventory().get(2).getCount());
        return siliconFlag ? new int[] { 0, 1, 3, 4, 5 } : new int[] { 0, 1, 2, 4, 5 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack par2ItemStack, EnumFacing par3)
    {
        return slotID < 6 && this.isItemValidForSlot(slotID, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack par2ItemStack, EnumFacing par3)
    {
        return slotID == 6;
    }

    @Override
    public boolean shouldUseEnergy()
    {
        return this.processTicks > 0;
    }

    @Override
    public EnumFacing getFront()
    {
        IBlockState state = this.world.getBlockState(getPos()); 
        if (state.getBlock() instanceof BlockAdvCircuitFabricator)
        {
            return (state.getValue(BlockAdvCircuitFabricator.FACING));
        }
        return EnumFacing.NORTH;
    }

    @Override
    public EnumFacing getElectricInputDirection()
    {
        
        return getFront().rotateY();
        
    }
}

