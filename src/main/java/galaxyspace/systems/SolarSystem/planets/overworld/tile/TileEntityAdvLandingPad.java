package galaxyspace.systems.SolarSystem.planets.overworld.tile;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.core.registers.blocks.GSBlocks;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import micdoodle8.mods.galacticraft.api.entity.ICargoEntity;
import micdoodle8.mods.galacticraft.api.entity.IDockable;
import micdoodle8.mods.galacticraft.api.entity.IFuelable;
import micdoodle8.mods.galacticraft.api.entity.ILandable;
import micdoodle8.mods.galacticraft.api.entity.ICargoEntity.EnumCargoLoadingState;
import micdoodle8.mods.galacticraft.api.entity.ICargoEntity.RemovalResult;
import micdoodle8.mods.galacticraft.api.tile.IFuelDock;
import micdoodle8.mods.galacticraft.api.tile.ILandingPadAttachable;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.BlockMulti;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.tile.IMultiBlock;
import micdoodle8.mods.galacticraft.core.tile.TileEntityFuelLoader;
import micdoodle8.mods.galacticraft.core.tile.TileEntityMulti;
import micdoodle8.mods.galacticraft.planets.mars.tile.TileEntityLaunchController;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityAdvLandingPad extends TileEntityMulti implements IMultiBlock, IFuelable, IFuelDock, ICargoEntity {
   private IDockable dockedEntity;

   @Override
   public void updateEntity()
   {
      if (!this.getWorldObj().isRemote) {
         List<?> list = this.getWorldObj().getEntitiesWithinAABB(IFuelable.class, AxisAlignedBB.getBoundingBox((double)this.xCoord - 0.5D, (double)this.yCoord, (double)this.zCoord - 0.5D, (double)this.xCoord + 1.5D, (double)this.yCoord + 1.0D, (double)this.zCoord + 1.5D));
         boolean docked = false;
         Iterator var3 = list.iterator();

         while(var3.hasNext()) {
            Object o = var3.next();
            if (o instanceof IDockable && !((Entity)o).isDead) {
               docked = true;
               IDockable fuelable = (IDockable)o;
               if (fuelable != this.dockedEntity && fuelable.isDockValid(this)) {
                  if (fuelable instanceof ILandable) {
                     ((ILandable)fuelable).landEntity(this.xCoord, this.yCoord, this.zCoord);
                  } else {
                     fuelable.setPad(this);
                  }
               }
               break;
            }
         }

         if (!docked) {
            this.dockedEntity = null;
         }
      }

   }
   @Override
   public boolean canUpdate() {
      return true;
   }

   @Override
   public boolean onActivated(EntityPlayer entityPlayer) {
      return false;
   }

   @Override
   public void onCreate(BlockVec3 placedPosition) {
      this.mainBlockPosition = placedPosition;
      this.markDirty();

      for(int x = -2; x < 3; ++x) {
         for(int z = -2; z < 3; ++z) {
            BlockVec3 vecToAdd = new BlockVec3(placedPosition.x + x, placedPosition.y, placedPosition.z + z);
            if (!vecToAdd.equals(placedPosition)) {
               ((BlockMulti)GCBlocks.fakeBlock).makeFakeBlock(this.worldObj, vecToAdd, placedPosition, 2);
            }
         }
      }

   }

   @Override
   public void onDestroy(TileEntity callingBlock) {
      BlockVec3 thisBlock = new BlockVec3(this);
      this.worldObj.func_147480_a(thisBlock.x, thisBlock.y, thisBlock.z, true);

      for(int x = -2; x < 3; ++x) {
         for(int z = -2; z < 3; ++z) {
            if (this.worldObj.isRemote && this.worldObj.rand.nextDouble() < 0.1D) {

               FMLClientHandler.instance().getClient().effectRenderer.addBlockDestroyEffects(thisBlock.x + x, thisBlock.y, thisBlock.z + z, GSBlocks.AdvLandingPad, Block.getIdFromBlock(GSBlocks.AdvLandingPad) >> 12 & 255);
            }

            this.worldObj.func_147480_a(thisBlock.x + x, thisBlock.y, thisBlock.z + z, false);
         }
      }

      if (this.dockedEntity != null) {
         this.dockedEntity.onPadDestroyed();
         this.dockedEntity = null;
      }

   }

   @Override
   public int addFuel(FluidStack liquid, boolean doFill) {
      return this.dockedEntity != null ? this.dockedEntity.addFuel(liquid, doFill) : 0;
   }

   @Override
   public FluidStack removeFuel(int amount) {
      return this.dockedEntity != null ? this.dockedEntity.removeFuel(amount) : null;
   }

   @Override
   public HashSet<ILandingPadAttachable> getConnectedTiles() {
      HashSet<ILandingPadAttachable> connectedTiles = new HashSet();


      for(int x = this.xCoord - 2; x < this.xCoord + 3; ++x) {
         this.testConnectedTile(x, this.zCoord - 2, connectedTiles);
         this.testConnectedTile(x, this.zCoord + 2, connectedTiles);
      }

      for(int z = this.zCoord - 2; z < this.zCoord + 3; ++z) {
         this.testConnectedTile(this.xCoord - 2, z, connectedTiles);
         this.testConnectedTile(this.xCoord + 2, z, connectedTiles);
      }

      return connectedTiles;
   }

   private void testConnectedTile(int x, int z, HashSet<ILandingPadAttachable> connectedTiles) {
      if (this.worldObj.blockExists(x, this.yCoord, z)) {
         TileEntity tile = this.worldObj.getTileEntity(x, this.yCoord, z);
         if (tile instanceof ILandingPadAttachable && ((ILandingPadAttachable)tile).canAttachToLandingPad(this.worldObj, this.xCoord, this.yCoord, this.zCoord)) {
            if (!(tile instanceof TileEntityFuelLoader)) {
               connectedTiles.add((ILandingPadAttachable)tile);
            }

            if (GalacticraftCore.isPlanetsLoaded && tile instanceof TileEntityLaunchController) {
               ((TileEntityLaunchController)tile).setAttachedPad(this);
            }
         }

      }
   }

   @Override
   public EnumCargoLoadingState addCargo(ItemStack stack, boolean doAdd) {
      return this.dockedEntity != null ? this.dockedEntity.addCargo(stack, doAdd) : EnumCargoLoadingState.NOTARGET;
   }

   @Override
   public RemovalResult removeCargo(boolean doRemove) {
      return this.dockedEntity != null ? this.dockedEntity.removeCargo(doRemove) : new RemovalResult(EnumCargoLoadingState.NOTARGET, (ItemStack)null);
   }

   @Override
   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      return AxisAlignedBB.getBoundingBox((double)(this.xCoord - 2), (double)this.yCoord, (double)(this.zCoord - 2), (double)(this.xCoord + 3), (double)this.yCoord + 0.4D, (double)(this.zCoord + 3));
   }

   public boolean isBlockAttachable(IBlockAccess world, int x, int y, int z) {
      TileEntity tile = world.getTileEntity(x, y, z);
      return tile != null && tile instanceof ILandingPadAttachable && !(tile instanceof TileEntityFuelLoader) ? ((ILandingPadAttachable)tile).canAttachToLandingPad(world, this.xCoord, this.yCoord, this.zCoord) : false;
   }

   @Override
   public IDockable getDockedEntity() {
      return this.dockedEntity;
   }

   @Override
   public void dockEntity(IDockable entity) {
      this.dockedEntity = entity;
   }
}
