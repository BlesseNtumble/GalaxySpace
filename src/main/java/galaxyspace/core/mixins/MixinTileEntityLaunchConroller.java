package galaxyspace.core.mixins;

import galaxyspace.core.GSBlocks;
import galaxyspace.systems.SolarSystem.planets.overworld.blocks.BlockAdvancedLandingPadFull;
import micdoodle8.mods.galacticraft.api.entity.IDockable;
import micdoodle8.mods.galacticraft.api.prefab.entity.EntityAutoRocket;
import micdoodle8.mods.galacticraft.api.tile.IFuelDock;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.BlockLandingPadFull;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.world.ChunkLoadingCallback;
import micdoodle8.mods.galacticraft.planets.mars.ConfigManagerMars;
import micdoodle8.mods.galacticraft.planets.mars.network.PacketSimpleMars;
import micdoodle8.mods.galacticraft.planets.mars.tile.TileEntityLaunchController;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeChunkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = TileEntityLaunchController.class, remap = false)
public class MixinTileEntityLaunchConroller {

    @Shadow
    private ForgeChunkManager.Ticket chunkLoadTicket;

    private List<BlockPos> connectedPads = new ArrayList();
    @Shadow
    private boolean frequencyCheckNeeded;


    @Inject(at = @At("RETURN"), method = "canAttachToLandingPad", cancellable = true)
    public void galaxyspace$canAttachToLandingPad(IBlockAccess world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        TileEntity tile = world.getTileEntity(pos);
        cir.setReturnValue(tile instanceof IFuelDock);
    }

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lmicdoodle8/mods/galacticraft/core/energy/tile/TileBaseElectricBlockWithInventory;update()V", shift = At.Shift.AFTER), cancellable = true)
    public void update(CallbackInfo ci) {
        TileEntityLaunchController te = (TileEntityLaunchController) (Object) this;

        if (!te.getWorld().isRemote) {
            te.controlEnabled = te.launchSchedulingEnabled && te.hasEnoughEnergyToRun && !te.getDisabled(0);
            if (this.frequencyCheckNeeded) {
                te.checkDestFrequencyValid();
                this.frequencyCheckNeeded = false;
            }

            if (te.requiresClientUpdate) {
                te.requiresClientUpdate = false;
            }

            if (te.ticks % 40 == 0) {
                te.setFrequency(te.frequency);
                te.setDestinationFrequency(te.destFrequency);
            }

            if (te.ticks % 20 == 0 && this.chunkLoadTicket != null) {
                for(int i = 0; i < this.connectedPads.size(); ++i) {
                    BlockPos coords = (BlockPos)this.connectedPads.get(i);
                    Block block = te.getWorld().getBlockState(coords).getBlock();
                    TileEntity tile = te.getWorld().getTileEntity(coords);

                    if (block != GCBlocks.landingPadFull || block != GSBlocks.ADVANCED_LANDING_PAD) {
                        this.connectedPads.remove(i);
                        ForgeChunkManager.unforceChunk(this.chunkLoadTicket, new ChunkPos(coords.getX() >> 4, coords.getZ() >> 4));
                    }
                }


            }
        } else if (te.frequency == -1 && te.destFrequency == -1) {
            GalacticraftCore.packetPipeline.sendToServer(new PacketSimpleMars(PacketSimpleMars.EnumSimplePacketMars.S_UPDATE_ADVANCED_GUI, GCCoreUtil.getDimensionID(te.getWorld()), new Object[]{5, te.getPos(), 0}));
        }
        ci.cancel();
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void onTicketLoaded(ForgeChunkManager.Ticket ticket, boolean placed) {
        TileEntityLaunchController lc = (TileEntityLaunchController) (Object) this;
        if (!lc.getWorld().isRemote && ConfigManagerMars.launchControllerChunkLoad) {
            if (ticket == null) {
                return;
            }

            if (this.chunkLoadTicket == null) {
                this.chunkLoadTicket = ticket;
            }

            NBTTagCompound nbt = this.chunkLoadTicket.getModData();
            nbt.setInteger("ChunkLoaderTileX", lc.getPos().getX());
            nbt.setInteger("ChunkLoaderTileY", lc.getPos().getY());
            nbt.setInteger("ChunkLoaderTileZ", lc.getPos().getZ());

            for(int x = -3; x <= 3; ++x) {
                for(int z = -3; z <= 3; ++z) {
                    TileEntity tile = lc.getWorld().getTileEntity(lc.getPos().add(x, 0, z));
                    Block blockID = lc.getWorld().getBlockState(lc.getPos().add(x, 0, z)).getBlock();
                    //System.out.println((blockID instanceof BlockLandingPadFull || blockID instanceof BlockAdvancedLandingPadFull) + " | " + (lc.getPos().getX() + x >> 4 != lc.getPos().getX() >> 4 || lc.getPos().getZ() + z >> 4 != lc.getPos().getZ() >> 4));
                    //System.out.println((lc.getPos().getX() + x >> 4) + " | " + (lc.getPos().getX() >> 4) + " || " + (lc.getPos().getZ() + z >> 4) + " | "  + (lc.getPos().getZ() >> 4));
                    //System.out.println(new BlockPos(lc.getPos().getX() + x, lc.getPos().getY(), lc.getPos().getZ() + z));
                    if ((blockID instanceof BlockLandingPadFull || blockID instanceof BlockAdvancedLandingPadFull)) { //&& (lc.getPos().getX() + x >> 4 != lc.getPos().getX() >> 4 || lc.getPos().getZ() + z >> 4 != lc.getPos().getZ() >> 4)) {
                        this.connectedPads.add(new BlockPos(lc.getPos().getX() + x, lc.getPos().getY(), lc.getPos().getZ() + z));
                        if (placed) {
                            ChunkLoadingCallback.forceChunk(this.chunkLoadTicket, lc.getWorld(), lc.getPos().getX() + x, lc.getPos().getY(), lc.getPos().getZ() + z, lc.getOwnerName());
                        } else {
                            ChunkLoadingCallback.addToList(lc.getWorld(), lc.getPos().getX(), lc.getPos().getY(), lc.getPos().getZ(), lc.getOwnerName());
                        }
                    }
                }
            }
            System.out.println(this.connectedPads.toString());
            ChunkLoadingCallback.forceChunk(this.chunkLoadTicket, lc.getWorld(), lc.getPos().getX(), lc.getPos().getY(), lc.getPos().getZ(), lc.getOwnerName());
        }

    }

    @Inject(at = @At("HEAD"), method = "updateRocketOnDockSettings", cancellable = true)
    public void updateRocketOnDockSettings(CallbackInfo ci) {
        TileEntityLaunchController te = (TileEntityLaunchController) (Object) this;

        if (te.attachedDock instanceof IFuelDock) {
            IFuelDock pad = (IFuelDock)te.attachedDock;
            IDockable rocket = pad.getDockedEntity();
            if (rocket instanceof EntityAutoRocket) {
                ((EntityAutoRocket)rocket).updateControllerSettings(pad);
            }
            ci.cancel();
        }

    }
}
