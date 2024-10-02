package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import cpw.mods.fml.relauncher.Side;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.AssemberRecipes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlock;
import micdoodle8.mods.galacticraft.core.inventory.PersistantInventoryCrafting;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class TileEntityAssemblyMachine extends TileBaseElectricBlock implements IInventory, ISidedInventory, IPacketReceiver {
   public static final int PROCESS_TIME_REQUIRED_BASE = 200;
   @NetworkedField(
      targetSide = Side.CLIENT
   )
   public int processTimeRequired = 200;
   @NetworkedField(
      targetSide = Side.CLIENT
   )
   public int processTicks = 0;
   private ItemStack producingStack = null;
   private static Random randnum = new Random();
   private ItemStack[] containingItems = new ItemStack[2];
   public PersistantInventoryCrafting testCraftMatrix = new PersistantInventoryCrafting();

   public TileEntityAssemblyMachine() {
      this.storage.setMaxExtract(ConfigManagerCore.hardMode ? 90.0F : 75.0F);
      this.setTierGC(1);
   }

   @Override
   public void updateEntity() {
      super.updateEntity();
      if (!this.worldObj.isRemote) {
         boolean updateInv = false;
         if (this.hasEnoughEnergyToRun) {
            if (this.canCompress()) {
               ++this.processTicks;
               this.processTimeRequired = 400 / (1 + this.poweredByTierGC);
               if (this.processTicks >= this.processTimeRequired) {
                  this.worldObj.playSoundEffect((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, "random.anvil_land", 0.2F, 0.5F);
                  this.processTicks = 0;
                  this.compressIntoSlot(1);
                  updateInv = true;
               }
            } else {
               this.processTicks = 0;
            }
         } else {
            this.processTicks = 0;
         }

         if (updateInv) {
            this.markDirty();
         }
      }

   }

   @Override
   public void openInventory() {
   }

   @Override
   public void closeInventory() {
   }

   private boolean canCompress() {
      ItemStack itemstack = this.producingStack;
      if (itemstack == null) {
         return false;
      } else if (this.containingItems[1] == null) {
         return true;
      } else if (this.containingItems[1] != null && !this.containingItems[1].isItemEqual(itemstack)) {
         return false;
      } else {
         int result = this.containingItems[1] == null ? 0 : this.containingItems[1].stackSize + itemstack.stackSize;
         return result <= this.getInventoryStackLimit() && result <= itemstack.getMaxStackSize();
      }
   }


   public void updateInput() {
      this.producingStack = AssemberRecipes.instance.findMatchingRecipe(this.testCraftMatrix, this.worldObj);
   }

   private void compressIntoSlot(int slot) {
      if (this.canCompress()) {
         ItemStack resultItemStack = this.producingStack;
         int i;
         if (this.containingItems[slot] == null) {
            this.containingItems[slot] = resultItemStack.copy();
         } else if (this.containingItems[slot].isItemEqual(resultItemStack)) {
            if (this.containingItems[slot].stackSize + resultItemStack.stackSize > 64) {
               for(i = 0; i < this.containingItems[slot].stackSize + resultItemStack.stackSize - 64; ++i) {
                  float var = 0.7F;
                  double dx = (double)(this.worldObj.rand.nextFloat() * var) + (double)(1.0F - var) * 0.5D;
                  double dy = (double)(this.worldObj.rand.nextFloat() * var) + (double)(1.0F - var) * 0.5D;
                  double dz = (double)(this.worldObj.rand.nextFloat() * var) + (double)(1.0F - var) * 0.5D;
                  EntityItem entityitem = new EntityItem(this.worldObj, (double)this.xCoord + dx, (double)this.yCoord + dy, (double)this.zCoord + dz, new ItemStack(resultItemStack.getItem(), 1, resultItemStack.getItemDamage()));
                  entityitem.delayBeforeCanPickup = 10;
                  this.worldObj.spawnEntityInWorld(entityitem);
               }
               this.containingItems[slot].stackSize = 64;
            }

            this.containingItems[slot].stackSize += resultItemStack.stackSize;
         }

         for(i = 0; i < this.testCraftMatrix.getSizeInventory(); ++i) {
            this.testCraftMatrix.decrStackSize(i, 1);
         }

         this.updateInput();
      }

   }

   @Override
   public void readFromNBT(NBTTagCompound par1NBTTagCompound)
   {
      super.readFromNBT(par1NBTTagCompound);
      this.processTicks = par1NBTTagCompound.getInteger("smeltingTicks");
      NBTTagList var2 = par1NBTTagCompound.getTagList("Items", 10);
      this.containingItems = new ItemStack[this.getSizeInventory() - this.testCraftMatrix.getSizeInventory()];

      for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
         NBTTagCompound var4 = var2.getCompoundTagAt(var3);
         int var5 = var4.getByte("Slot") & 255;
         if (var5 < this.containingItems.length) {
            this.containingItems[var5] = ItemStack.loadItemStackFromNBT(var4);
         } else if (var5 < this.containingItems.length + this.testCraftMatrix.getSizeInventory()) {
            this.testCraftMatrix.setInventorySlotContents(var5 - this.containingItems.length, ItemStack.loadItemStackFromNBT(var4));
         }
      }

      this.updateInput();
   }

   @Override
   public void writeToNBT(NBTTagCompound par1NBTTagCompound)
   {
      super.writeToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("smeltingTicks", this.processTicks);
      NBTTagList var2 = new NBTTagList();

      int var3;
      NBTTagCompound var4;
      for(var3 = 0; var3 < this.containingItems.length; ++var3) {
         if (this.containingItems[var3] != null) {
            var4 = new NBTTagCompound();
            var4.setByte("Slot", (byte)var3);
            this.containingItems[var3].writeToNBT(var4);
            var2.appendTag(var4);
         }
      }

      for(var3 = 0; var3 < this.testCraftMatrix.getSizeInventory(); ++var3) {
         if (this.testCraftMatrix.getStackInSlot(var3) != null) {
            var4 = new NBTTagCompound();
            var4.setByte("Slot", (byte)(var3 + this.containingItems.length));
            this.testCraftMatrix.getStackInSlot(var3).writeToNBT(var4);
            var2.appendTag(var4);
         }
      }

      par1NBTTagCompound.setTag("Items", var2);
   }

   @Override
   public int getSizeInventory()
   {
      return this.containingItems.length + this.testCraftMatrix.getSizeInventory();
   }
   @Override
   public ItemStack getStackInSlot(int par1)
   {
      if (par1 >= this.containingItems.length)
      {
         return this.testCraftMatrix.getStackInSlot(par1 - this.containingItems.length);
      }

      return this.containingItems[par1];
   }

   @Override
   public ItemStack decrStackSize(int par1, int par2)
   {
      if (par1 >= this.containingItems.length)
      {
         ItemStack result = this.testCraftMatrix.decrStackSize(par1 - this.containingItems.length, par2);
         if (result != null)
         {
            this.updateInput();
         }
         return result;
      }

      if (this.containingItems[par1] != null)
      {
         ItemStack var3;

         if (this.containingItems[par1].stackSize <= par2)
         {
            var3 = this.containingItems[par1];
            this.containingItems[par1] = null;
            return var3;
         }
         else
         {
            var3 = this.containingItems[par1].splitStack(par2);

            if (this.containingItems[par1].stackSize == 0)
            {
               this.containingItems[par1] = null;
            }

            return var3;
         }
      }
      else
      {
         return null;
      }
   }

   @Override
   public ItemStack getStackInSlotOnClosing(int par1)
   {
      if (par1 >= this.containingItems.length)
      {
         return this.testCraftMatrix.getStackInSlotOnClosing(par1 - this.containingItems.length);
      }

      if (this.containingItems[par1] != null)
      {
         ItemStack var2 = this.containingItems[par1];
         this.containingItems[par1] = null;
         return var2;
      }
      else
      {
         return null;
      }
   }

   @Override
   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
   {
      if (par1 >= this.containingItems.length)
      {
         this.testCraftMatrix.setInventorySlotContents(par1 - this.containingItems.length, par2ItemStack);
         this.updateInput();
      }
      else
      {
         this.containingItems[par1] = par2ItemStack;

         if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
         {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
         }
      }
   }

   @Override
   public String getInventoryName() {
      return GCCoreUtil.translate("tile.AssemblyMachine.name");
   }

   @Override
   public int getInventoryStackLimit()
   {
      return 64;
   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer entityplayer)
   {
      return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && entityplayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
   }

   @Override
   public boolean hasCustomInventoryName()
   {
      return true;
   }

   @Override
   public boolean isItemValidForSlot(int slotID, ItemStack itemStack) {
      if (slotID == 0) {
         return itemStack != null && ItemElectricBase.isElectricItem(itemStack.getItem());
      } else if (slotID >= 2) {
         if (this.producingStack == null) {
            return isItemCompressorInput(itemStack);
         } else {
            ItemStack stackInSlot = this.getStackInSlot(slotID);
            return stackInSlot != null && stackInSlot.isItemEqual(itemStack);
         }
      } else {
         return false;
      }
   }

   @Override
   public int[] getAccessibleSlotsFromSide(int side) {
      if (side == 0) {
         return new int[]{1};
      } else {
         int[] slots = new int[]{0, 2, 3, 4, 5, 6, 7, 8, 9, 10};
         ArrayList<Integer> removeSlots = new ArrayList();


         for(int i = 2; i < 11; ++i) {
            if (!removeSlots.contains(i)) {
               ItemStack stack1 = this.getStackInSlot(i);
               if (stack1 != null && stack1.stackSize > 0) {
                  for(int j = i + 1; j < 11; ++j) {
                     if (!removeSlots.contains(j)) {
                        ItemStack stack2 = this.getStackInSlot(j);
                        if (stack2 != null && stack1.isItemEqual(stack2)) {
                           if (stack2.stackSize >= stack1.stackSize) {
                              removeSlots.add(j);
                           } else {
                              removeSlots.add(i);
                           }
                           break;
                        }
                     }
                  }
               }
            }
         }

         if (removeSlots.size() <= 0) {
            return slots;
         } else {
            int[] returnSlots = new int[slots.length - removeSlots.size()];


            for(int j = 0; j < slots.length; ++j) {
               if (j <= 0 || !removeSlots.contains(slots[j])) {
                  returnSlots[j] = slots[j];
                  ++j;
               }
            }

            return returnSlots;
         }
      }
   }

   @Override
   public boolean canInsertItem(int slotID, ItemStack par2ItemStack, int par3)
   {
      return this.isItemValidForSlot(slotID, par2ItemStack);
   }

   @Override
   public boolean canExtractItem(int slotID, ItemStack par2ItemStack, int par3)
   {
      return slotID == 1;
   }

   @Override
   public boolean shouldUseEnergy() {
      return this.processTicks > 0;
   }

   @Override
   public ForgeDirection getElectricInputDirection() {
      return ForgeDirection.getOrientation((this.getBlockMetadata() & 3) + 2);
   }

   @Override
   public ItemStack getBatteryInSlot() {
      return this.getStackInSlot(0);
   }

   public static boolean isItemCompressorInput(ItemStack stack) {
      Iterator var1 = AssemberRecipes.instance.getRecipeList().iterator();

      while(true) {
         while(var1.hasNext()) {
            IRecipe recipe = (IRecipe)var1.next();
            int match;
            if (recipe instanceof ShapedRecipes) {
               ItemStack[] var8 = ((ShapedRecipes)recipe).recipeItems;
               int var9 = var8.length;

               for(match = 0; match < var9; ++match) {
                  ItemStack itemstack1 = var8[match];
                  if (stack.getItem() == itemstack1.getItem() && (itemstack1.getItemDamage() == 32767 || stack.getItemDamage() == itemstack1.getItemDamage())) {
                     return true;
                  }
               }
            } else if (recipe instanceof ShapelessOreRecipe) {
               ArrayList<Object> required = new ArrayList(((ShapelessOreRecipe)recipe).getInput());
               Iterator<Object> req = required.iterator();
               match = 0;

               while(true) {
                  while(req.hasNext()) {
                     Object next = req.next();
                     if (next instanceof ItemStack) {
                        if (OreDictionary.itemMatches((ItemStack)next, stack, false)) {
                           ++match;
                        }
                     } else if (next instanceof ArrayList) {
                        Iterator itr = ((ArrayList)next).iterator();

                        while(itr.hasNext()) {
                           if (OreDictionary.itemMatches((ItemStack)itr.next(), stack, false)) {
                              ++match;
                              break;
                           }
                        }
                     }
                  }

                  if (match != 0) {
                     if (match == 1) {
                        return true;
                     }

                     return randnum.nextInt(match) == 0;
                  }
                  break;
               }
            }
         }

         return false;
      }
   }
}
