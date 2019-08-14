package galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics;

import java.util.List;

import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class SlotSchematicBooster extends Slot
{
    private final int index;
    private final BlockPos pos;
    private final EntityPlayer player;

    public SlotSchematicBooster(IInventory par2IInventory, int par3, int par4, int par5, BlockPos pos, EntityPlayer player)
    {
        super(par2IInventory, par3, par4, par5);
        this.index = par3;
        this.pos = pos;
        this.player = player;
    }

    @Override
    public void onSlotChanged()
    {
    	if (this.player instanceof EntityPlayerMP)
        {
            int dimID = GCCoreUtil.getDimensionID(this.player.world);
            GCCoreUtil.sendToAllAround(new PacketSimple(EnumSimplePacket.C_SPAWN_SPARK_PARTICLES, dimID, new Object[] { this.pos }), this.player.world, dimID, this.pos, 20);
        }
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        if (par1ItemStack == null)
            return false;

        List<INasaWorkbenchRecipe> recipes = GSRecipeUtil.getBoosterRecipes();
        for (INasaWorkbenchRecipe recipe : recipes)
        {
            if (ItemStack.areItemsEqual(par1ItemStack, recipe.getRecipeInput().get(this.index)))
                return true;
        }
        return false;
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as
     * getInventoryStackLimit(), but 1 in the case of armor slots)
     */
    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }
}
