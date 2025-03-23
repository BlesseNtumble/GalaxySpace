package galaxyspace.core.mixins;

import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.planets.mars.entities.EntityCreeperBoss;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Mixin(value = EntityCreeperBoss.class, remap = false)
public class MixinEntityCreeperBoss {

    /**
     * @author ViTold
     * @reason Normal rolling loot
     */
    @Overwrite
    public ItemStack getGuaranteedLoot(Random rand)
    {
        List<ItemStack> stackList = new LinkedList<>();
        stackList.addAll(GalacticraftRegistry.getDungeonLoot(2));

        return stackList.get(rand.nextInt(stackList.size())).copy();
    }
}
