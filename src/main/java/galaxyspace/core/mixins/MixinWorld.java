package galaxyspace.core.mixins;

import galaxyspace.core.events.SetBlockEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(World.class)
public abstract class MixinWorld {

    @Shadow @Final public WorldProvider provider;

    protected MixinWorld(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client) {

    }

    @Inject(at = @At("RETURN"), method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z", cancellable = true)
    public void galaxyspace$setBlockState(BlockPos pos, IBlockState newState, int flags, CallbackInfoReturnable<Boolean> cir) {
        SetBlockEvent event = new SetBlockEvent(this.provider.world, pos, newState, flags);
        MinecraftForge.EVENT_BUS.post(event);

        cir.setReturnValue(!event.isCanceled());
    }
}
