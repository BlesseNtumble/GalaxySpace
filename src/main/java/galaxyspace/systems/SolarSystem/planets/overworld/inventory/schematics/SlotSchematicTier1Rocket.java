package galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics;

import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SlotSchematicTier1Rocket extends Slot
{
    private final int index;
    private final int x, y, z;
    private final EntityPlayer player;

    public SlotSchematicTier1Rocket(IInventory par2IInventory, int par3, int par4, int par5, int x, int y, int z, EntityPlayer player)
    {
        super(par2IInventory, par3, par4, par5);
        this.index = par3;
        this.x = x;
        this.y = y;
        this.z = z;
        this.player = player;
    }

    @Override
    public void onSlotChanged()
    {
        if (this.player instanceof EntityPlayerMP)
        {
            final Object[] toSend = { this.x, this.y, this.z };

            for (int var12 = 0; var12 < this.player.worldObj.playerEntities.size(); ++var12)
            {
                final EntityPlayerMP var13 = (EntityPlayerMP) this.player.worldObj.playerEntities.get(var12);

                if (var13.dimension == this.player.worldObj.provider.dimensionId)
                {
                    final double var14 = this.x - var13.posX;
                    final double var16 = this.y - var13.posY;
                    final double var18 = this.z - var13.posZ;

                    if (var14 * var14 + var16 * var16 + var18 * var18 < 20 * 20)
                    {
                        GalacticraftCore.packetPipeline.sendTo(new PacketSimple(EnumSimplePacket.C_SPAWN_SPARK_PARTICLES, new Object[] { this.x, this.y, this.z }), var13);
                    }
                }
            }
        }
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
    	if (par1ItemStack == null)
            return false;

    	List<INasaWorkbenchRecipe> recipes = GSRecipeUtil.getRocketT1Recipes();
    	for (INasaWorkbenchRecipe recipe : recipes)
        {
            //TODO: Check to see that chest item validation works, if not, just eh, undo it
            if (index >= 15 && index <= 17 && par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.chest))
                return true;
            ItemStack stack = recipe.getRecipeInput().get(this.index);

            if (stack != null && par1ItemStack.isItemEqual(stack)) {
                return true;
            }
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

