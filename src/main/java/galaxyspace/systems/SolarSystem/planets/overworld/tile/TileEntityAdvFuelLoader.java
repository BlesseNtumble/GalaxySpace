package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import cpw.mods.fml.relauncher.Side;
import galaxyspace.core.registers.fluids.GSFluids;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSUtils;
import micdoodle8.mods.galacticraft.api.entity.IFuelable;
import micdoodle8.mods.galacticraft.api.tile.ILandingPadAttachable;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric;
import micdoodle8.mods.galacticraft.core.tile.TileEntityMulti;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityAdvFuelLoader extends TileBaseElectricBlockWithInventory implements ISidedInventory, IFluidHandler, ILandingPadAttachable {
   private final int tankCapacity = 12000;
   @NetworkedField(
      targetSide = Side.CLIENT
   )
   public FluidTank fuelTank;
   private ItemStack[] containingItems;
   public IFuelable attachedFuelable;
   private boolean loadedFuelLastTick;

   public TileEntityAdvFuelLoader() {
      this.getClass();
      this.fuelTank = new FluidTank(12000);
      this.containingItems = new ItemStack[2];
      this.loadedFuelLastTick = false;
      this.storage.setMaxExtract(30.0F);
   }

   public int getScaledFuelLevel(int i) {
      double fuelLevel = this.fuelTank.getFluid() == null ? 0.0D : (double)this.fuelTank.getFluid().amount;
      double var10000 = fuelLevel * (double)i;
      this.getClass();
      return (int)(var10000 / 12000.0D);
   }

   @Override
   public void updateEntity()
   {
      super.updateEntity();

      if (!this.getWorld().isRemote) {
         this.loadedFuelLastTick = false;
         FluidStack liquid;
         int amount;
         int used;
         if (this.containingItems[1] != null) {
            if (this.containingItems[1].getItem() instanceof ItemCanisterGeneric) {
               int originalDamage;
               if (this.containingItems[1].getItem() != GCItems.fuelCanister || this.fuelTank.getFluid() != null && this.fuelTank.getFluid().getFluid() != GalacticraftCore.fluidFuel) {
                  if (this.containingItems[1].getItem() == GSItems.HeliumHydrogenCanister && (this.fuelTank.getFluid() == null || this.fuelTank.getFluid().getFluid() == GSFluids.HeliumHydrogen)) {
                     originalDamage = this.containingItems[1].getItemDamage();
                     used = this.fuelTank.fill(new FluidStack(GSFluids.HeliumHydrogen, 1001 - originalDamage), true);
                     if (originalDamage + used == 1001) {
                        this.containingItems[1] = new ItemStack(GCItems.oilCanister, 1, 1001);
                     } else {
                        this.containingItems[1] = new ItemStack(GSItems.HeliumHydrogenCanister, 1, originalDamage + used);
                     }
                  }
               } else {
                  originalDamage = this.containingItems[1].getItemDamage();
                  used = this.fuelTank.fill(new FluidStack(GalacticraftCore.fluidFuel, 1001 - originalDamage), true);
                  if (originalDamage + used == 1001) {
                     this.containingItems[1] = new ItemStack(GCItems.oilCanister, 1, 1001);
                  } else {
                     this.containingItems[1] = new ItemStack(GCItems.fuelCanister, 1, originalDamage + used);
                  }
               }
            } else {
               liquid = FluidContainerRegistry.getFluidForFilledItem(this.containingItems[1]);
               if (liquid != null) {
                  boolean isFuel = GSUtils.testFuel(FluidRegistry.getFluidName(liquid));
                  if (isFuel && (this.fuelTank.getFluid() != null && this.fuelTank.getFluid().isFluidEqual(liquid) || this.fuelTank.getFluid() == null) && (this.fuelTank.getFluid() == null || this.fuelTank.getFluid().amount + liquid.amount <= this.fuelTank.getCapacity())) {
                     this.fuelTank.fill(new FluidStack(liquid.getFluid(), liquid.amount), true);
                     if (FluidContainerRegistry.isBucket(this.containingItems[1]) && FluidContainerRegistry.isFilledContainer(this.containingItems[1])) {
                        amount = this.containingItems[1].stackSize;
                        if (amount > 1) {
                           this.fuelTank.fill(new FluidStack(GSFluids.HeliumHydrogen, (amount - 1) * 1000), true);
                        }

                        this.containingItems[1] = new ItemStack(Items.bucket, amount);
                     } else {
                        --this.containingItems[1].stackSize;
                        if (this.containingItems[1].stackSize == 0) {
                           this.containingItems[1] = null;
                        }
                     }
                  }
               }
            }
         }

         if (this.ticks % 100 == 0) {
            this.attachedFuelable = null;
            ForgeDirection[] var8 = ForgeDirection.VALID_DIRECTIONS;
            used = var8.length;

            for(amount = 0; amount < used; ++amount) {
               ForgeDirection dir = var8[amount];
               TileEntity pad = (new BlockVec3(this)).getTileEntityOnSide(this.getWorldObj(), dir);
               if (pad instanceof TileEntityMulti) {
                  TileEntity mainTile = ((TileEntityMulti)pad).getMainBlockTile();
                  if (mainTile instanceof IFuelable) {
                     this.attachedFuelable = (IFuelable)mainTile;
                     break;
                  }
               } else if (pad instanceof IFuelable) {
                  this.attachedFuelable = (IFuelable)pad;
                  break;
               }
            }
         }

         if (this.fuelTank != null && this.fuelTank.getFluid() != null && this.fuelTank.getFluid().amount > 0) {
            liquid = new FluidStack(this.fuelTank.getFluid().getFluid(), 1);
            if (this.attachedFuelable != null && this.hasEnoughEnergyToRun && !this.disabled) {
               used = this.attachedFuelable.addFuel(liquid, true);
               this.loadedFuelLastTick = used > 0;
               this.fuelTank.drain(used, true);
            }
         }
      }

   }

   @Override
   public void readFromNBT(NBTTagCompound par1NBTTagCompound)
   {
      super.readFromNBT(par1NBTTagCompound);
      this.containingItems = this.readStandardItemsFromNBT(par1NBTTagCompound);
      if (par1NBTTagCompound.hasKey("fuelTank")) {
         this.fuelTank.readFromNBT(par1NBTTagCompound.getCompoundTag("fuelTank"));
      }

   }

   @Override
   public void writeToNBT(NBTTagCompound par1NBTTagCompound)
   {
      super.writeToNBT(par1NBTTagCompound);
      this.writeStandardItemsToNBT(par1NBTTagCompound);
      if (this.fuelTank.getFluid() != null) {
         par1NBTTagCompound.setTag("fuelTank", this.fuelTank.writeToNBT(new NBTTagCompound()));
      }

   }

   @Override
   protected ItemStack[] getContainingItems()
   {
      return this.containingItems;
   }

   @Override
   public String getInventoryName()
   {
      return GCCoreUtil.translate("container.fuelloader.name");
   }
   @Override
   public int getInventoryStackLimit()
   {
      return 1;
   }

   @Override
   public int[] getAccessibleSlotsFromSide(int side)
   {
      return new int[] { 0, 1 };
   }

   @Override
   public boolean canInsertItem(int slotID, ItemStack itemstack, int side)
   {
      return this.isItemValidForSlot(slotID, itemstack);
   }


   @Override
   public boolean canExtractItem(int slotID, ItemStack itemstack, int side)
   {
      if (slotID == 1 && itemstack != null)
      {
         return FluidUtil.isEmptyContainer(itemstack);
      }
      return false;
   }
   @Override
   public boolean hasCustomInventoryName()
   {
      return true;
   }
   @Override
   public boolean isItemValidForSlot(int slotID, ItemStack itemstack)
   {
      return (slotID == 1 && itemstack != null && itemstack.getItem() == GCItems.fuelCanister) || (slotID == 0 ? ItemElectricBase.isElectricItem(itemstack.getItem()) : false);
   }

   @Override
   public boolean canDrain(ForgeDirection from, Fluid fluid)
   {
      return false;
   }

   @Override
   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
   {
      return null;
   }

   @Override
   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
   {
      return null;
   }

   @Override
   public boolean canFill(ForgeDirection from, Fluid fluid)
   {
      return this.fuelTank.getFluid() == null || this.fuelTank.getFluidAmount() < this.fuelTank.getCapacity();
   }

   @Override
   public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
   {
      int used = 0;

      if (from.equals(ForgeDirection.getOrientation(this.getBlockMetadata() + 2).getOpposite()))
      {
         if (FluidUtil.testFuel(FluidRegistry.getFluidName(resource)))
         {
            used = this.fuelTank.fill(resource, doFill);
         }
      }

      return used;
   }

   @Override
   public FluidTankInfo[] getTankInfo(ForgeDirection from)
   {
      return new FluidTankInfo[] { new FluidTankInfo(this.fuelTank) };
   }


   @Override
   public boolean shouldUseEnergy()
   {
      return this.fuelTank.getFluid() != null && this.fuelTank.getFluid().amount > 0 && !this.getDisabled(0) && loadedFuelLastTick;
   }

   @Override
   public boolean canAttachToLandingPad(IBlockAccess world, int x, int y, int z)
   {
      return true;
   }
}
