package galaxyspace.systems.BarnardsSystem.moons.barnarda_c1.recipes;

import galaxyspace.systems.BarnardsSystem.core.BRBlocks;
import galaxyspace.systems.BarnardsSystem.core.BRItems;
import galaxyspace.systems.SolarSystem.planets.overworld.items.ItemBasicGS;
import galaxyspace.systems.SolarSystem.planets.overworld.recipes.RecyclerRecipes;
import micdoodle8.mods.galacticraft.api.recipe.CircuitFabricatorRecipes;
import micdoodle8.mods.galacticraft.core.GCItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Arrays;

public class CraftingRecipesBarnarda_C1 {

    public static void loadRecipes() {
        addBlockRecipes();
        addItemRecipes();
        addBlockSmelting();
        addCircuitFabricator();
        addRecycler();
    }

    private static void addBlockRecipes() {

    }

    private static void addItemRecipes() {

    }

    private static void addBlockSmelting() {
        GameRegistry.addSmelting(new ItemStack(BRBlocks.BARNARDA_C1_BLOCKS, 1, 5), new ItemStack(Items.IRON_INGOT, 1, 0), 1.0F);
        GameRegistry.addSmelting(new ItemStack(BRBlocks.BARNARDA_C1_BLOCKS, 1, 6), new ItemStack(GCItems.basicItem, 1, 3), 1.0F);
        GameRegistry.addSmelting(new ItemStack(BRBlocks.BARNARDA_C1_BLOCKS, 1, 7), new ItemStack(GCItems.basicItem, 1, 4), 1.0F);

    }

    private static void addCircuitFabricator() {
        ItemStack modernWafers = ItemBasicGS.BasicItems.WAFER_MODERN.getItemStack();

        CircuitFabricatorRecipes.addRecipe(modernWafers,
                Arrays.asList(

                        new ItemStack(Items.DIAMOND),
                        new ItemStack(BRItems.BASIC, 1, 3),
                        new ItemStack(BRItems.BASIC, 1, 3),
                        new ItemStack(Items.REDSTONE),
                        new ItemStack(GCItems.basicItem, 1, 14)
                ));
    }

    private static void addRecycler() {
        //RecyclerRecipes.recycling().addNewRecipe(new ItemStack(BRItems.BASIC, 1, 3),  new ItemStack(GCItems.itemBasicMoon, 1, 2), null);

    }
}
