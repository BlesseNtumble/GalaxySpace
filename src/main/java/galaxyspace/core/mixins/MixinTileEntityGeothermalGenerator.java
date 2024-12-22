package galaxyspace.core.mixins;

import galaxyspace.api.block.IEnergyGeyser;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.planets.GalacticraftPlanets;
import micdoodle8.mods.galacticraft.planets.venus.VenusBlocks;
import micdoodle8.mods.galacticraft.planets.venus.VenusModule;
import micdoodle8.mods.galacticraft.planets.venus.tile.TileEntityGeothermalGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TileEntityGeothermalGenerator.class, remap = false)
public class MixinTileEntityGeothermalGenerator {

    @Shadow
    private boolean validSpout;

    /**
     * @author ViTold
     * @reason
     */
    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lmicdoodle8/mods/galacticraft/core/energy/tile/TileBaseUniversalElectricalSource;update()V", shift = At.Shift.AFTER), cancellable = true)
    public void update(CallbackInfo ci) {
        TileEntityGeothermalGenerator te = (TileEntityGeothermalGenerator) (Object) this;
        if (te.ticks % 20 == 0) {
            BlockPos below = te.getPos().down();
            IBlockState stateBelow = te.getWorld().getBlockState(below);

            boolean lastValidSpout = this.validSpout;//ReflectionHelper.getPrivateValue(TileEntityGeothermalGenerator.class, te, "validSpout");
            boolean validSpoutHook = false;
            //ReflectionHelper.setPrivateValue(TileEntityGeothermalGenerator.class, te, validSpoutHook, "validSpout");
            validSpout = validSpoutHook;
            if (stateBelow.getBlock() instanceof IEnergyGeyser) {
                BlockPos pos1 = below.down();
                for (; te.getPos().getY() - pos1.getY() < 20; pos1 = pos1.down()) {
                    IBlockState state = te.getWorld().getBlockState(pos1);
                    IEnergyGeyser geyser = (IEnergyGeyser) stateBelow.getBlock();
                    boolean work = geyser.isWorkGeyser(te.getWorld(), stateBelow, below);

                    if (work) {
                        validSpoutHook = true;
                        //ReflectionHelper.setPrivateValue(TileEntityGeothermalGenerator.class, te, validSpoutHook, "validSpout");
                        validSpout = validSpoutHook;
                        break;
                    } else if (!state.getBlock().isAir(te.getWorld().getBlockState(pos1), te.getWorld(), pos1)) {
                        // Not valid
                        break;
                    }
                }
            } else if (stateBelow.getBlock() == VenusBlocks.spout) {
                for (BlockPos pos1 = below.down(); te.getPos().getY() - pos1.getY() < 20; pos1 = pos1.down()) {
                    IBlockState state = te.getWorld().getBlockState(pos1);
                    if (state.getBlock() == VenusModule.sulphuricAcid.getBlock()) {
                        this.validSpout = true;
                        break;
                    }

                    if (!state.getBlock().isAir(te.getWorld().getBlockState(pos1), te.getWorld(), pos1)) {
                        break;
                    }
                }
            }

            if (te.getWorld().isRemote && this.validSpout != lastValidSpout) {
                // Update active texture
                IBlockState state = te.getWorld().getBlockState(te.getPos());
                te.getWorld().notifyBlockUpdate(te.getPos(), state, state, 3);
            }

        }

        if (!te.getWorld().isRemote) {
            te.recharge((ItemStack) te.getInventory().get(0));
            if (te.disableCooldown > 0) {
                --te.disableCooldown;
            }

            te.generateWatts = Math.min(Math.max(_1_12_2$getGenerate(te, this.validSpout), 0), 200);
        } else if (te.generateWatts > 0 && te.ticks % ((int) (200.0F / (float) (te.generateWatts + 1)) * 5 + 1) == 0) {
            double posX = (double) te.getPos().getX() + 0.5;
            double posY = (double) te.getPos().getY() + 1.0;
            double posZ = (double) te.getPos().getZ() + 0.5;
            GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX - 0.25, posY, posZ - 0.25), new Vector3(0.0, 0.025, 0.0), new Object[0]);
            GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX - 0.25, posY, posZ), new Vector3(0.0, 0.025, 0.0), new Object[0]);
            GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX - 0.25, posY, posZ + 0.25), new Vector3(0.0, 0.025, 0.0), new Object[0]);
            GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX, posY, posZ - 0.25), new Vector3(0.0, 0.025, 0.0), new Object[0]);
            GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX, posY, posZ), new Vector3(0.0, 0.025, 0.0), new Object[0]);
            GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX, posY, posZ + 0.25), new Vector3(0.0, 0.025, 0.0), new Object[0]);
            GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX + 0.25, posY, posZ - 0.25), new Vector3(0.0, 0.025, 0.0), new Object[0]);
            GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX + 0.25, posY, posZ), new Vector3(0.0, 0.025, 0.0), new Object[0]);
            GalacticraftPlanets.spawnParticle("acidExhaust", new Vector3(posX + 0.25, posY, posZ + 0.25), new Vector3(0.0, 0.025, 0.0), new Object[0]);
        }

        te.produce();
        ci.cancel();

    }

    @Unique
    private static int _1_12_2$getGenerate(TileEntityGeothermalGenerator te, boolean valid) {
        if (te.getDisabled(0)) {
            return 0;
        }

        if (!valid) {
            return 0;
        }

        int diff = TileEntityGeothermalGenerator.MAX_GENERATE_GJ_PER_TICK - TileEntityGeothermalGenerator.MIN_GENERATE_GJ_PER_TICK;
        return (int) Math.floor((Math.sin(te.ticks / 50.0F) * 0.5F + 0.5F) * diff + TileEntityGeothermalGenerator.MIN_GENERATE_GJ_PER_TICK);
    }

}
