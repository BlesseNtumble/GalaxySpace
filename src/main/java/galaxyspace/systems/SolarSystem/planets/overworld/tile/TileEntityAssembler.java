package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.machines.BlockAssembler;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.AssemblyRecipes;
import micdoodle8.mods.galacticraft.api.recipe.ShapedRecipesGC;
import micdoodle8.mods.galacticraft.api.recipe.ShapelessOreRecipeGC;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlock;
import micdoodle8.mods.galacticraft.core.inventory.PersistantInventoryCrafting;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityAssembler extends TileBaseElectricBlock implements ISidedInventory, IPacketReceiver
{
    public static final int PROCESS_TIME_REQUIRED_BASE = 200;
    @NetworkedField(targetSide = Side.CLIENT)
    public int processTimeRequired = PROCESS_TIME_REQUIRED_BASE;
    @NetworkedField(targetSide = Side.CLIENT)
    public int processTicks = 0;
    private ItemStack producingStack = ItemStack.EMPTY;
    private static Random randnum = new Random();
  
    public PersistantInventoryCrafting testCraftMatrix = new PersistantInventoryCrafting();
    
    public TileEntityAssembler()
    {
    	super("tile.assembly_machine.name");
        this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 90 : 75);
        this.inventory = NonNullList.withSize(2 + 4, ItemStack.EMPTY);
        this.setTierGC(1);
    }

       
    @Override
    public void update()
    {
        super.update();

        if (!this.world.isRemote)
        {
            boolean updateInv = false;

            if (this.hasEnoughEnergyToRun)
            {
                if (this.canCompress())
                {
                	//////////
                	int boost_speed = 0, energy_boost = 0;
                	
                	for(int i = 0; i <= 3; i++)
                	{
                		if(this.getInventory().get(2 + i).isItemEqual(new ItemStack(GSItems.UPGRADES, 1, 2)))
                			boost_speed++;
                		if(this.getInventory().get(2 + i).isItemEqual(new ItemStack(GSItems.UPGRADES, 1, 3)))
                			energy_boost++;
                	}
                	
                    this.processTicks += 1 + boost_speed;
                    this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 90 + (40 * boost_speed) - (20 * energy_boost): 75 + (35 * boost_speed) - (15 * energy_boost));
                    /////////////
                    
                    this.processTimeRequired = TileEntityAssembler.PROCESS_TIME_REQUIRED_BASE * 2 / (1 + this.poweredByTierGC);

                    if (this.processTicks >= this.processTimeRequired)
                    {
                    	this.world.playSound(null, this.getPos(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 0.3F, this.world.rand.nextFloat() * 0.1F + 0.9F);
                        this.processTicks = 0;
                        this.compressIntoSlot(1);
                        updateInv = true;
                    }
                }
                else
                {
                    this.processTicks = 0;
                }
            }
           /* else
            {
                this.processTicks = 0;
            }*/

            if (updateInv)
            {
                this.markDirty();
            }
        }        
    }

    private boolean canCompress()
    {
        ItemStack itemstack = this.producingStack;
        if (itemstack.isEmpty())
        {
            return false;
        }
        if (this.getInventory().get(1).isEmpty())
        {
            return true;
        }
        if (!this.getInventory().get(1).isEmpty() && !this.getInventory().get(1).isItemEqual(itemstack))
        {
            return false;
        }
        int contents1 = this.getInventory().get(1).getCount();
        int result = itemstack.getCount();

        result += contents1;
        
        return result <= this.getInventoryStackLimit() && result <= itemstack.getMaxStackSize();
    }

    public void updateInput()
    {
        this.producingStack = AssemblyRecipes.findMatchingRecipe(this.testCraftMatrix, this.world);
    }

    private void compressIntoSlot(int slot)
    {
        if (this.canCompress())
        {
            ItemStack resultItemStack = this.producingStack;

            if (this.getInventory().get(slot).isEmpty())
            {
            	this.getInventory().set(slot, resultItemStack);
            }
            else if (this.getInventory().get(slot).isItemEqual(resultItemStack))
            {
                if (this.getInventory().get(slot).getCount() + resultItemStack.getCount() > resultItemStack.getMaxStackSize())
                {
                	resultItemStack.grow(this.getInventory().get(slot).getCount() - resultItemStack.getMaxStackSize());
                	GCCoreUtil.spawnItem(this.world, this.getPos(), resultItemStack);
                    this.getInventory().get(slot).setCount(resultItemStack.getMaxStackSize());
                }
                else
                {
                	this.getInventory().get(slot).grow(resultItemStack.getCount());
                }
            }

            for (int i = 0; i < this.testCraftMatrix.getSizeInventory(); i++)
            {
            	if (!this.testCraftMatrix.getStackInSlot(i).isEmpty() && this.testCraftMatrix.getStackInSlot(i).getItem() == Items.WATER_BUCKET)
                {
                    this.testCraftMatrix.setInventorySlotContentsNoUpdate(i, new ItemStack(Items.BUCKET));
                }
                else
                {
                    this.testCraftMatrix.decrStackSize(i, 1);
                }
            }

            this.updateInput();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.processTicks = nbt.getInteger("smeltingTicks");
        
        this.inventory = NonNullList.withSize(this.getSizeInventory() - this.testCraftMatrix.getSizeInventory(), ItemStack.EMPTY);
        NBTTagList nbttaglist = nbt.getTagList("Items", 10);
        
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 255;

            if (j >= 0 && j < this.getInventory().size())
            {
                this.getInventory().set(j, new ItemStack(nbttagcompound));
            }
            else if (j < this.getInventory().size() + this.testCraftMatrix.getSizeInventory())
            {
                this.testCraftMatrix.setInventorySlotContents(j - this.getInventory().size(), new ItemStack(nbttagcompound));
            }
        }

        this.updateInput();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("smeltingTicks", this.processTicks);
        NBTTagList var2 = new NBTTagList();
        int var3;

        for (var3 = 0; var3 < this.getInventory().size(); ++var3)
        {
            if (!this.getInventory().get(var3).isEmpty())
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                this.getInventory().get(var3).writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        for (var3 = 0; var3 < this.testCraftMatrix.getSizeInventory(); ++var3)
        {
            if (this.testCraftMatrix.getStackInSlot(var3) != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) (var3 + this.getInventory().size()));
                this.testCraftMatrix.getStackInSlot(var3).writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        nbt.setTag("Items", var2);
        return nbt;
    }

    @Override
    public int getSizeInventory()
    {
        return this.getInventory().size() + this.testCraftMatrix.getSizeInventory();
    }

  
    @Override
    public ItemStack getStackInSlot(int par1)
    {
        if (par1 >= this.getInventory().size())
        {
            return this.testCraftMatrix.getStackInSlot(par1 - this.getInventory().size());
        }

        return this.getInventory().get(par1);
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (par1 >= this.getInventory().size())
        {
            ItemStack result = this.testCraftMatrix.decrStackSize(par1 - this.getInventory().size(), par2);
            if (result != null)
            {
                this.updateInput();
            }
            return result;
        }

        if (!this.getInventory().get(par1).isEmpty())
        {
            ItemStack var3;

            if (this.getInventory().get(par1).getCount() <= par2)
            {
                var3 = this.getInventory().get(par1);
                this.getInventory().set(par1, ItemStack.EMPTY);
                return var3;
            }
            else
            {
                var3 = this.getInventory().get(par1).splitStack(par2);

                if (this.getInventory().get(par1).isEmpty())
                {
                    this.getInventory().set(par1, ItemStack.EMPTY);
                }

                this.markDirty();
                return var3;
            }
        }
        else
        {
        	return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int par1)
    {
        if (par1 >= this.getInventory().size())
        {
            return this.testCraftMatrix.removeStackFromSlot(par1 - this.getInventory().size());
        }

        if (!this.getInventory().get(par1).isEmpty())
        {
            ItemStack var2 = this.getInventory().get(par1);
            this.getInventory().set(par1, ItemStack.EMPTY);
            this.markDirty();
            return var2;
        }
        else
        {
        	return ItemStack.EMPTY;
        }
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack stack)
    {
        if (par1 >= this.getInventory().size())
        {
            this.testCraftMatrix.setInventorySlotContents(par1 - this.getInventory().size(), stack);
            this.updateInput();
        }
        else
        {
            this.getInventory().set(par1, stack);
            
            if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit())
            {
                stack.setCount(this.getInventoryStackLimit());
            }
        }
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityplayer)
    {
    	return this.world.getTileEntity(this.getPos()) == this && entityplayer.getDistanceSq(this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D) <= 64.0D;
    }
    
    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemStack)
    {
        if (slotID == 0)
        {
            return itemStack != null && ItemElectricBase.isElectricItem(itemStack.getItem());
        }
        else if (slotID >= 2)
        {
        	
        	/*if (this.producingStack != null)
        	{
                ItemStack stackInSlot = this.getStackInSlot(slotID);
                return stackInSlot != null && stackInSlot.isItemEqual(itemStack);
        	}*/
        	return this.isItemCompressorInput(itemStack, slotID - 2);
        }

        return false;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
    	if (side == EnumFacing.DOWN)
        {
            return new int[] { 1 };
        }
    	
    	int[] slots = new int[] { 7, 8, 9, 10, 11, 12, 13 };
    	ArrayList<Integer> removeSlots = new ArrayList();
    	
    	for (int i = 2; i < 11; i++)
    	{
			if (removeSlots.contains(i)) continue;
			
    		ItemStack stack1 = this.getStackInSlot(i);
    		if (stack1.isEmpty()) continue;
    		
    		for (int j = i + 1; j < 11; j++)
    		{
    			if (removeSlots.contains(j)) continue;
    			
    			ItemStack stack2 = this.getStackInSlot(j);
    			if (stack2.isEmpty()) continue;
    			
    			if (stack1.isItemEqual(stack2))
    			{
    				if (stack2.getCount() >= stack1.getCount())
    					removeSlots.add(j);
    				else
    					removeSlots.add(i);
    				break;
    			}
    		}
    	}
    	
    	if (removeSlots.size() > 0)
    	{
    		int[] returnSlots = new int[slots.length - removeSlots.size()];
        	int j = 0;
        	for (int i = 0; i < slots.length; i++)
        	{
    			if (i > 0 && removeSlots.contains(slots[i])) { continue; }
    			returnSlots[j] = slots[i];
    			j++;    			
        	}
        	
        	return returnSlots;
    	}
    	
    	return slots;
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack par2ItemStack, EnumFacing par3)
    {
    	return this.isItemValidForSlot(slotID, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack par2ItemStack, EnumFacing par3)
    {
        return slotID == 1;
    }

    @Override
    public boolean shouldUseEnergy()
    {
        return this.processTicks > 0;
    }

    @Override
    public ItemStack getBatteryInSlot()
    {
        return this.getStackInSlot(0);
    }
    
    public boolean isItemCompressorInput(ItemStack stack, int id)
    {
        for (IRecipe recipe : AssemblyRecipes.getRecipeList())
        {
        	
            if (recipe instanceof ShapedRecipesGC)
            {
            	
            	if (id >= ((ShapedRecipesGC) recipe).recipeItems.length) continue;
            	ItemStack itemstack1 = ((ShapedRecipesGC) recipe).recipeItems[id];
                if (stack.getItem() == itemstack1.getItem() && (itemstack1.getItemDamage() == 32767 || stack.getItemDamage() == itemstack1.getItemDamage()))
                {
                	for (int i = 0; i < ((ShapedRecipesGC) recipe).recipeItems.length; i++)
                	{
                		if (i == id) continue;
                        ItemStack itemstack2 = ((ShapedRecipesGC) recipe).recipeItems[i];
                        if (stack.getItem() == itemstack2.getItem() && (itemstack2.getItemDamage() == 32767 || stack.getItemDamage() == itemstack2.getItemDamage()))
                        {
                        	ItemStack is3 = this.getStackInSlot(id + 3);
                        	ItemStack is4 = this.getStackInSlot(i + 3);
                        	return is3.isEmpty() || !is4.isEmpty() && is3.getCount() < is4.getCount();
                        }
                	}
                	return true;
                }
            }
            else if (recipe instanceof ShapelessOreRecipeGC)
            {
            	
                ArrayList<Object> required = new ArrayList<Object>(((ShapelessOreRecipeGC) recipe).getInput());
                
                Iterator<Object> req = required.iterator();

                int match = 0;

                while (req.hasNext())
                {
                    Object next = req.next();

                    if (next instanceof ItemStack)
                    {
                        if ( OreDictionary.itemMatches((ItemStack)next, stack, false)) match++;
                        
                    }
                    else if (next instanceof ArrayList)
                    {
                        Iterator<ItemStack> itr = ((ArrayList<ItemStack>)next).iterator();
                        while (itr.hasNext())
                        {
                            if (OreDictionary.itemMatches(itr.next(), stack, false))
                            {
                            	match++;
                            	break;
                            }
                        }
                    }
                    
                }
                
                
                
                if (match == 0) continue;
                
                if (match == 1) return true;
                
                //Shapeless recipe can go into (match) number of slots
                int slotsFilled = 0;
                for (int i = 3; i < 12; i++)
                {
                	ItemStack inMatrix = this.getStackInSlot(i); 
                	if (!inMatrix.isEmpty() && inMatrix.isItemEqual(stack))
                		slotsFilled++;
                }
                if (slotsFilled < match)
                {
                	return this.getStackInSlot(id + 3).isEmpty();
                }
                
                return randnum.nextInt(match) == 0;
            }
        }

        return false;
    }
    
    @Override
	public EnumFacing getElectricInputDirection() {
		return getFront().rotateY();
	}

	@Override
	public EnumFacing getFront() {
		IBlockState state = this.world.getBlockState(getPos());
		if (state.getBlock() instanceof BlockAssembler) {
			return state.getValue(BlockAssembler.FACING);
		}
		return EnumFacing.NORTH;
	}

}
