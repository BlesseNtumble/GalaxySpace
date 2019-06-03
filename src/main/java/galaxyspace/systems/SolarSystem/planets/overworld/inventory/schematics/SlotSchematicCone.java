package galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
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

public class SlotSchematicCone extends Slot
{
    private final int index;
    private final BlockPos pos;
    private final EntityPlayer player;

    public SlotSchematicCone(IInventory par2IInventory, int par3, int par4, int par5, BlockPos pos, EntityPlayer player)
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
        switch (this.index)
        {
        	case 1: return par1ItemStack.getItem() == AsteroidsItems.basicItem && par1ItemStack.getItemDamage() == 5 || par1ItemStack.getItem() == MarsItems.marsItemBasic && par1ItemStack.getItemDamage() == 3 || par1ItemStack.getItem() == GSItems.HDP;
        	case 2: return par1ItemStack.getItem() == AsteroidsItems.basicItem && par1ItemStack.getItemDamage() == 5 || par1ItemStack.getItem() == MarsItems.marsItemBasic && par1ItemStack.getItemDamage() == 3 || par1ItemStack.getItem() == GSItems.HDP;
        	case 3: return par1ItemStack.getItem() == Item.getItemFromBlock(GSBlocks.FUTURE_GLASS_BASIC);
        	case 4: return par1ItemStack.getItem() == AsteroidsItems.basicItem && par1ItemStack.getItemDamage() == 5 || par1ItemStack.getItem() == MarsItems.marsItemBasic && par1ItemStack.getItemDamage() == 3 || par1ItemStack.getItem() == GSItems.HDP;
        	case 5: return par1ItemStack.getItem() == AsteroidsItems.basicItem && par1ItemStack.getItemDamage() == 5 || par1ItemStack.getItem() == MarsItems.marsItemBasic && par1ItemStack.getItemDamage() == 3 || par1ItemStack.getItem() == GSItems.HDP;
        	case 6: return par1ItemStack.getItem() == AsteroidsItems.basicItem && par1ItemStack.getItemDamage() == 5 || par1ItemStack.getItem() == MarsItems.marsItemBasic && par1ItemStack.getItemDamage() == 3 || par1ItemStack.getItem() == GSItems.HDP;
        	case 7: return par1ItemStack.getItem() == AsteroidsItems.basicItem && par1ItemStack.getItemDamage() == 5 || par1ItemStack.getItem() == MarsItems.marsItemBasic && par1ItemStack.getItemDamage() == 3 || par1ItemStack.getItem() == GSItems.HDP;
        	case 8: return par1ItemStack.getItem() == AsteroidsItems.basicItem && par1ItemStack.getItemDamage() == 5 || par1ItemStack.getItem() == MarsItems.marsItemBasic && par1ItemStack.getItemDamage() == 3 || par1ItemStack.getItem() == GSItems.HDP;
        	case 9: return par1ItemStack.getItem() == GCItems.oxygenVent;
        	case 10: return par1ItemStack.getItem() == GCItems.oxygenFan;
        	case 11: return par1ItemStack.getItem() == Item.getItemFromBlock(GCBlocks.sealableBlock) && par1ItemStack.getItemDamage() == 14;
        	case 12: return par1ItemStack.getItem() == GCItems.rocketEngine;
        	case 13: return par1ItemStack.getItem() == Item.getItemFromBlock(GCBlocks.screen);
        	case 14: return par1ItemStack.getItem() == GCItems.partBuggy && par1ItemStack.getItemDamage() == 1;
        	case 15: return par1ItemStack.getItem() == GCItems.oxTankHeavy && par1ItemStack.getItemDamage() == 0;
        	case 16: return par1ItemStack.getItem() == GSItems.ROCKET_MODULES;
        	case 17: return par1ItemStack.getItem() == GCItems.oxygenVent;
        	case 18: return par1ItemStack.getItem() == GCItems.oxygenFan;
        	case 19: return par1ItemStack.getItem() == Item.getItemFromBlock(GCBlocks.sealableBlock) && par1ItemStack.getItemDamage() == 14;
        	case 20: return par1ItemStack.getItem() == GCItems.rocketEngine;
        	case 21: return par1ItemStack.getItem() == AsteroidsItems.basicItem && par1ItemStack.getItemDamage() == 5 || par1ItemStack.getItem() == MarsItems.marsItemBasic && par1ItemStack.getItemDamage() == 3 || par1ItemStack.getItem() == GSItems.HDP;
        	case 22: return par1ItemStack.getItem() == AsteroidsItems.basicItem && par1ItemStack.getItemDamage() == 5 || par1ItemStack.getItem() == MarsItems.marsItemBasic && par1ItemStack.getItemDamage() == 3 || par1ItemStack.getItem() == GSItems.HDP;
        	case 23: return par1ItemStack.getItem() == AsteroidsItems.basicItem && par1ItemStack.getItemDamage() == 5 || par1ItemStack.getItem() == MarsItems.marsItemBasic && par1ItemStack.getItemDamage() == 3 || par1ItemStack.getItem() == GSItems.HDP;
        	case 24: return par1ItemStack.getItem() == AsteroidsItems.basicItem && par1ItemStack.getItemDamage() == 5 || par1ItemStack.getItem() == MarsItems.marsItemBasic && par1ItemStack.getItemDamage() == 3 || par1ItemStack.getItem() == GSItems.HDP;
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
