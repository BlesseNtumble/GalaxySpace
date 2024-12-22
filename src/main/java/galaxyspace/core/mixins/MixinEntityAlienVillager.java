package galaxyspace.core.mixins;

import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.entities.EntityAlienVillager;
import micdoodle8.mods.galacticraft.core.items.ItemBasic;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipeList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = EntityAlienVillager.class, remap = false)
public class MixinEntityAlienVillager {

    @Shadow
    private MerchantRecipeList buyingList;

    private static final EntityAlienVillager.ITradeList[] DEFAULT_TRADE_LIST_MAP = new EntityAlienVillager.ITradeList[] {
            new EntityAlienVillager.ItemAndEmeraldToItem(new ItemStack(GCItems.schematic, 1, 1), new EntityAlienVillager.PriceInfo(40, 55), ItemBasicGS.BasicItems.SCHEMATIC_BOX.getItemStack()),


            new EntityAlienVillager.ListItemForEmeralds(new ItemStack(GCItems.oxMask, 1, 0), new EntityAlienVillager.PriceInfo(1, 2)),
            new EntityAlienVillager.ListItemForEmeralds(new ItemStack(GCItems.oxTankLight, 1, 235), new EntityAlienVillager.PriceInfo(3, 4)),
            new EntityAlienVillager.ListItemForEmeralds(new ItemStack(GCItems.oxygenGear, 1, 0), new EntityAlienVillager.PriceInfo(3, 4)),
            new EntityAlienVillager.ListItemForEmeralds(new ItemStack(GCItems.fuelCanister, 1, 317), new EntityAlienVillager.PriceInfo(3, 4)),
            new EntityAlienVillager.ListItemForEmeralds(new ItemStack(GCItems.parachute, 1, 0), new EntityAlienVillager.PriceInfo(1, 2)),
            new EntityAlienVillager.ListItemForEmeralds(new ItemStack(GCItems.battery, 1, 58), new EntityAlienVillager.PriceInfo(2, 4)),
            new EntityAlienVillager.ItemAndEmeraldToItem(new ItemStack(GCItems.oilCanister, 1, ItemCanisterGeneric.EMPTY), new EntityAlienVillager.PriceInfo(1, 1), new ItemStack(GCItems.foodItem, 1, 1)), //carrots = also yields a tin!
            new EntityAlienVillager.ListItemForEmeralds(new ItemStack(GCItems.basicItem, 1, ItemBasic.WAFER_BASIC), new EntityAlienVillager.PriceInfo(3, 4)),
            new EntityAlienVillager.ItemAndEmeraldToItem(new ItemStack(GCItems.schematic, 1, 0), new EntityAlienVillager.PriceInfo(3, 5), new ItemStack(GCItems.schematic, 1, 1)), //Exchange buggy and rocket schematics
            new EntityAlienVillager.ItemAndEmeraldToItem(new ItemStack(GCItems.schematic, 1, 1), new EntityAlienVillager.PriceInfo(3, 5), new ItemStack(GCItems.schematic, 1, 0)), //Exchange buggy and rocket schematics
            new EntityAlienVillager.ItemAndEmeraldToItem(new ItemStack(GCItems.basicItem, 2, 3), new EntityAlienVillager.PriceInfo(1, 1), new ItemStack(GCItems.basicItem, 1, 6)), //Compressed Tin - needed to craft a Fuel Loader
            new EntityAlienVillager.ItemAndEmeraldToItem(new ItemStack(GCItems.basicItem, 2, 4), new EntityAlienVillager.PriceInfo(1, 1), new ItemStack(GCItems.basicItem, 1, 7)), //Compressed Copper - needed to craft a Fuel Loader
            new EntityAlienVillager.EmeraldForItems(new ItemStack(Blocks.SAPLING, 1, 3), new EntityAlienVillager.PriceInfo(11, 39)) //The one thing Alien Villagers don't have and can't get is jungle trees...
    };

    /**
     * @author ViTold
     * @reason
     */
    @Overwrite
    private void populateBuyingList(){
        EntityAlienVillager e = (EntityAlienVillager)(Object)this;

        MerchantRecipeList buyingList = this.buyingList;
        if (buyingList == null) {
            buyingList = new MerchantRecipeList();
        }

        for (EntityAlienVillager.ITradeList tradeList : DEFAULT_TRADE_LIST_MAP) {
            tradeList.modifyMerchantRecipeList(buyingList, e.getEntityWorld().rand);
        }

        this.buyingList = buyingList;
    }
}
