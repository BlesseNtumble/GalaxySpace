/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe
 *  micdoodle8.mods.galacticraft.core.items.GCItems
 *  micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe
 *  micdoodle8.mods.galacticraft.planets.mars.items.MarsItems
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 */
package galaxyspace.systems.SolarSystem.planets.overworld.recipe.schematic;

import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.util.GSRecipeUtil;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class SchematicTier1Recipe {
    public static void registerRecipeWorkBench() {
        HashMap<Integer, ItemStack> input = new HashMap<Integer, ItemStack>();
        input.put(1, new ItemStack(GCItems.partNoseCone));
        input.put(2, new ItemStack(GCItems.heavyPlatingTier1, 1, 0));
        input.put(3, new ItemStack(GCItems.heavyPlatingTier1, 1, 0));
        input.put(4, new ItemStack(GCItems.heavyPlatingTier1, 1, 0));
        input.put(5, new ItemStack(GCItems.heavyPlatingTier1, 1, 0));
        input.put(6, new ItemStack(GCItems.heavyPlatingTier1, 1, 0));
        input.put(7, new ItemStack(GCItems.heavyPlatingTier1, 1, 0));
        input.put(8, new ItemStack(GCItems.heavyPlatingTier1, 1, 0));
        input.put(9, new ItemStack(GCItems.heavyPlatingTier1, 1, 0));
        input.put(10, new ItemStack(GCItems.partFins));
        input.put(11, new ItemStack(GCItems.partFins));
        input.put(12, new ItemStack(GCItems.rocketEngine, 1, 0));
        input.put(13, new ItemStack(GCItems.partFins));
        input.put(14, new ItemStack(GCItems.partFins));
        input.put(15, null);
        input.put(16, null);
        input.put(17, null);
        input.put(18, new ItemStack(GSItems.RocketModules, 1, 0));
        input.put(19, new ItemStack(GSItems.RocketModules, 1, 3));
        input.put(20, new ItemStack(GSItems.RocketModules, 1, 4));
        GSRecipeUtil.addT1RocketRecipe((INasaWorkbenchRecipe)new NasaWorkbenchRecipe(new ItemStack(GCItems.rocketTier1, 1, 0), input));
        HashMap<Integer, ItemStack> input2 = new HashMap<Integer, ItemStack>(input);
        input2 = new HashMap(input);
        input2.put(15, new ItemStack((Block)Blocks.chest));
        input2.put(16, null);
        input2.put(17, null);
        GSRecipeUtil.addT1RocketRecipe((INasaWorkbenchRecipe)new NasaWorkbenchRecipe(new ItemStack(GCItems.rocketTier1, 1, 1), input2));
        input2 = new HashMap(input);
        input2.put(15, null);
        input2.put(16, new ItemStack((Block)Blocks.chest));
        input2.put(17, null);
        GSRecipeUtil.addT1RocketRecipe((INasaWorkbenchRecipe)new NasaWorkbenchRecipe(new ItemStack(GCItems.rocketTier1, 1, 1), input2));
        input2 = new HashMap(input);
        input2.put(15, null);
        input2.put(16, null);
        input2.put(17, new ItemStack((Block)Blocks.chest));
        GSRecipeUtil.addT1RocketRecipe((INasaWorkbenchRecipe)new NasaWorkbenchRecipe(new ItemStack(GCItems.rocketTier1, 1, 1), input2));
        input2 = new HashMap(input);
        input2.put(15, new ItemStack((Block)Blocks.chest));
        input2.put(16, new ItemStack((Block)Blocks.chest));
        input2.put(17, null);
        GSRecipeUtil.addT1RocketRecipe((INasaWorkbenchRecipe)new NasaWorkbenchRecipe(new ItemStack(GCItems.rocketTier1, 1, 2), input2));
        input2 = new HashMap(input);
        input2.put(15, new ItemStack((Block)Blocks.chest));
        input2.put(16, null);
        input2.put(17, new ItemStack((Block)Blocks.chest));
        GSRecipeUtil.addT1RocketRecipe((INasaWorkbenchRecipe)new NasaWorkbenchRecipe(new ItemStack(GCItems.rocketTier1, 1, 2), input2));
        input2 = new HashMap(input);
        input2.put(15, null);
        input2.put(16, new ItemStack((Block)Blocks.chest));
        input2.put(17, new ItemStack((Block)Blocks.chest));
        GSRecipeUtil.addT1RocketRecipe((INasaWorkbenchRecipe)new NasaWorkbenchRecipe(new ItemStack(GCItems.rocketTier1, 1, 2), input2));
        input2 = new HashMap(input);
        input2.put(15, new ItemStack((Block)Blocks.chest));
        input2.put(16, new ItemStack((Block)Blocks.chest));
        input2.put(17, new ItemStack((Block)Blocks.chest));
        GSRecipeUtil.addT1RocketRecipe((INasaWorkbenchRecipe)new NasaWorkbenchRecipe(new ItemStack(GCItems.rocketTier1, 1, 3), input2));
    }
}

