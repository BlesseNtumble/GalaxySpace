/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  codechicken.nei.PositionedStack
 *  codechicken.nei.api.API
 *  codechicken.nei.api.IConfigureNEI
 *  codechicken.nei.recipe.ICraftingHandler
 *  codechicken.nei.recipe.IUsageHandler
 *  micdoodle8.mods.galacticraft.core.blocks.GCBlocks
 *  micdoodle8.mods.galacticraft.core.items.GCItems
 *  micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems
 *  micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks
 *  micdoodle8.mods.galacticraft.planets.mars.items.MarsItems
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.IRecipe
 *  net.minecraft.item.crafting.ShapedRecipes
 *  net.minecraftforge.oredict.ShapelessOreRecipe
 */
package galaxyspace.core.client.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;

import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.nei.AssemblyMachineRecipeHandler;
import galaxyspace.systems.SolarSystem.planets.overworld.nei.BodyRecipeHandler;
import galaxyspace.systems.SolarSystem.planets.overworld.nei.BoosterRecipeHandler;
import galaxyspace.systems.SolarSystem.planets.overworld.nei.ConeRecipeHandler;
import galaxyspace.systems.SolarSystem.planets.overworld.nei.EngineRecipeHandler;
import galaxyspace.systems.SolarSystem.planets.overworld.nei.FinsRecipeHandler;
import galaxyspace.systems.SolarSystem.planets.overworld.nei.OxTankRecipeHandler;
import galaxyspace.systems.SolarSystem.planets.overworld.nei.PortNuclearRecipeHandler;
import galaxyspace.systems.SolarSystem.planets.overworld.nei.RecyclerRecipeHandler;
import galaxyspace.systems.SolarSystem.planets.overworld.nei.RocketAssemblyRecipeHandler;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.AssemberRecipes;
import galaxyspace.systems.SolarSystem.planets.overworld.recipe.RocketAssemblyRecipes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.planets.asteroids.items.AsteroidsItems;
import micdoodle8.mods.galacticraft.planets.mars.blocks.MarsBlocks;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class NEIGalaxySpaceConfig
implements IConfigureNEI {
    private static HashMap<HashMap<Integer, PositionedStack>, PositionedStack> partCone = new HashMap();
    private static HashMap<HashMap<Integer, PositionedStack>, PositionedStack> partBody = new HashMap();
    private static HashMap<HashMap<Integer, PositionedStack>, PositionedStack> partEngine = new HashMap();
    private static HashMap<HashMap<Integer, PositionedStack>, PositionedStack> partBooster = new HashMap();
    private static HashMap<HashMap<Integer, PositionedStack>, PositionedStack> partFins = new HashMap();
    private static HashMap<HashMap<Integer, PositionedStack>, PositionedStack> OxTank = new HashMap();
    private static HashMap<HashMap<Integer, PositionedStack>, PositionedStack> PortNuclearReactor = new HashMap();
    private static HashMap<HashMap<Integer, PositionedStack>, PositionedStack> rocketAssemblyRecipes = new HashMap();
    private static HashMap<HashMap<Integer, PositionedStack>, PositionedStack> assemblyMachineRecipes = new HashMap();
    private static HashMap<HashMap<Integer, PositionedStack>, PositionedStack> circuitFabricatorRecipes = new HashMap();
    private static HashMap<ArrayList<PositionedStack>, PositionedStack> rocketBenchRecipes = new HashMap();
  

    public void loadConfig() {
        this.registerRecipes();
        API.registerRecipeHandler((ICraftingHandler)new ConeRecipeHandler());
        API.registerUsageHandler((IUsageHandler)new ConeRecipeHandler());
        API.registerRecipeHandler((ICraftingHandler)new BodyRecipeHandler());
        API.registerUsageHandler((IUsageHandler)new BodyRecipeHandler());
        API.registerRecipeHandler((ICraftingHandler)new EngineRecipeHandler());
        API.registerUsageHandler((IUsageHandler)new EngineRecipeHandler());
        API.registerRecipeHandler((ICraftingHandler)new BoosterRecipeHandler());
        API.registerUsageHandler((IUsageHandler)new BoosterRecipeHandler());
        API.registerRecipeHandler((ICraftingHandler)new FinsRecipeHandler());
        API.registerUsageHandler((IUsageHandler)new FinsRecipeHandler());
        API.registerRecipeHandler((ICraftingHandler)new OxTankRecipeHandler());
        API.registerUsageHandler((IUsageHandler)new OxTankRecipeHandler());
        API.registerRecipeHandler((ICraftingHandler)new PortNuclearRecipeHandler());
        API.registerUsageHandler((IUsageHandler)new PortNuclearRecipeHandler());
        API.registerRecipeHandler((ICraftingHandler)new AssemblyMachineRecipeHandler());
        API.registerUsageHandler((IUsageHandler)new AssemblyMachineRecipeHandler());
        API.registerRecipeHandler((ICraftingHandler)new RocketAssemblyRecipeHandler());
        API.registerUsageHandler((IUsageHandler)new RocketAssemblyRecipeHandler());
        API.registerRecipeHandler((ICraftingHandler)new RecyclerRecipeHandler());
        API.registerUsageHandler((IUsageHandler)new RecyclerRecipeHandler());
        API.registerRecipeHandler((ICraftingHandler)new GSCircuitFabricatorRecipeHandler());
        API.registerUsageHandler((IUsageHandler)new GSCircuitFabricatorRecipeHandler());
        API.registerRecipeHandler((ICraftingHandler)new RocketT2RecipeHandler());
        API.registerUsageHandler((IUsageHandler)new RocketT2RecipeHandler());
        API.hideItem((ItemStack)new ItemStack(GSBlocks.AdvLandingPadFull));
    }

    public String getName() {
        return "GalaxySpace NEI Plugin";
    }

    public String getVersion() {
        return "3.0.12";
    }

    public void registerPartConeRecipe(HashMap<Integer, PositionedStack> input, PositionedStack output) {
        partCone.put(input, output);
    }

    public static Set<Map.Entry<HashMap<Integer, PositionedStack>, PositionedStack>> getPartConeRecipes() {
        return partCone.entrySet();
    }

    public void registerPartBodyRecipe(HashMap<Integer, PositionedStack> input, PositionedStack output) {
        partBody.put(input, output);
    }

    public static Set<Map.Entry<HashMap<Integer, PositionedStack>, PositionedStack>> getPartBodyRecipes() {
        return partBody.entrySet();
    }

    public void registerPartEngineRecipe(HashMap<Integer, PositionedStack> input, PositionedStack output) {
        partEngine.put(input, output);
    }

    public static Set<Map.Entry<HashMap<Integer, PositionedStack>, PositionedStack>> getPartEngineRecipes() {
        return partEngine.entrySet();
    }

    public void registerPartBoosterRecipe(HashMap<Integer, PositionedStack> input, PositionedStack output) {
        partBooster.put(input, output);
    }

    public static Set<Map.Entry<HashMap<Integer, PositionedStack>, PositionedStack>> getPartBoosterRecipes() {
        return partBooster.entrySet();
    }

    public void registerPartFinsRecipe(HashMap<Integer, PositionedStack> input, PositionedStack output) {
        partFins.put(input, output);
    }

    public static Set<Map.Entry<HashMap<Integer, PositionedStack>, PositionedStack>> getPartFinsRecipes() {
        return partFins.entrySet();
    }

    public void registerOxTankRecipe(HashMap<Integer, PositionedStack> input, PositionedStack output) {
        OxTank.put(input, output);
    }

    public static Set<Map.Entry<HashMap<Integer, PositionedStack>, PositionedStack>> getOxTankRecipes() {
        return OxTank.entrySet();
    }

    public void registerPortNuclearRecipe(HashMap<Integer, PositionedStack> input, PositionedStack output) {
        PortNuclearReactor.put(input, output);
    }

    public static Set<Map.Entry<HashMap<Integer, PositionedStack>, PositionedStack>> getPortNuclearRecipes() {
        return PortNuclearReactor.entrySet();
    }

    public void registerAssemblyMachineRecipe(HashMap<Integer, PositionedStack> input, PositionedStack output) {
        assemblyMachineRecipes.put(input, output);
    }

    public static Set<Map.Entry<HashMap<Integer, PositionedStack>, PositionedStack>> getAssemblyMachineRecipes() {
        return assemblyMachineRecipes.entrySet();
    }

    public void registerRocketAssemblyRecipe(HashMap<Integer, PositionedStack> input, PositionedStack output) {
        rocketAssemblyRecipes.put(input, output);
    }

    public static Set<Map.Entry<HashMap<Integer, PositionedStack>, PositionedStack>> getRocketAssemblyRecipes() {
        return rocketAssemblyRecipes.entrySet();
    }

    public void registerCircuitFabricatorRecipe(HashMap<Integer, PositionedStack> input, PositionedStack output) {
        circuitFabricatorRecipes.put(input, output);
    }

    public static Set<Map.Entry<HashMap<Integer, PositionedStack>, PositionedStack>> getCircuitFabricatorRecipes() {
        return circuitFabricatorRecipes.entrySet();
    }

    public void registerRocketBenchRecipe(ArrayList<PositionedStack> input, PositionedStack output) {
        rocketBenchRecipes.put(input, output);
        
    }

    public static Set<Map.Entry<ArrayList<PositionedStack>, PositionedStack>> getRocketBenchRecipes() {
        return rocketBenchRecipes.entrySet();
    }

    public void registerRecipes() {
        this.addAssemblyMachineRecipes();
        this.addRocketAssemblyRecipes();
        this.addConeRecipes();
        this.addBodyRecipes();
        this.addEngineRecipes();
        this.addBoosterRecipes();
        this.addFinsRecipes();
        this.addOxTankRecipes();
        this.addPortNuclearRecipes();
        this.addRocketT1Recipes();
        this.addRocketT2Recipes();
        HashMap<Integer, PositionedStack> input1 = new HashMap<Integer, PositionedStack>();
        input1 = new HashMap();
        input1.put(0, new PositionedStack((Object)new ItemStack(Items.diamond), 10, 22));
        input1.put(1, new PositionedStack((Object)new ItemStack(GSItems.BasicItems, 1, 6), 69, 51));
        input1.put(2, new PositionedStack((Object)new ItemStack(GSItems.BasicItems, 1, 6), 69, 69));
        input1.put(3, new PositionedStack((Object)new ItemStack(Items.redstone), 117, 51));
        input1.put(4, new PositionedStack((Object)new ItemStack(GCItems.basicItem, 1, 14), 140, 25));
        this.registerCircuitFabricatorRecipe(input1, new PositionedStack((Object)new ItemStack(GSItems.BasicItems, 1, 7), 147, 91));
    }

    private void addConeRecipes() {
        for (int i = 0; i <= 3; ++i) {
            ItemStack plate = i == 0 ? new ItemStack(AsteroidsItems.basicItem, 1, 0) : new ItemStack(GSItems.HeavyDutyPlates, 1, i - 1);
            HashMap<Integer, PositionedStack> input1 = new HashMap<Integer, PositionedStack>();
            input1.put(0, new PositionedStack((Object)plate, 35, 25));
            input1.put(1, new PositionedStack((Object)new ItemStack(GSBlocks.FutureGlass, 1, 0), 35, 41));
            input1.put(2, new PositionedStack((Object)new ItemStack(GCBlocks.screen, 1, 0), 35, 57));
            input1.put(3, new PositionedStack((Object)new ItemStack(GCItems.partBuggy, 1, 1), 35, 73));
            input1.put(4, new PositionedStack((Object)new ItemStack(GCItems.oxTankHeavy, 1, 0), 35, 89));
            if (i < 1) {
                input1.put(5, new PositionedStack((Object)new ItemStack(GSItems.RocketModules, 1, 1), 35, 105));
            } else {
                input1.put(5, new PositionedStack((Object)new ItemStack(GSItems.RocketModules, 1, 2), 35, 105));
            }
            input1.put(6, new PositionedStack((Object)plate, 51, 41));
            input1.put(7, new PositionedStack((Object)new ItemStack(GCItems.oxygenVent, 1, 0), 51, 57));
            input1.put(8, new PositionedStack((Object)new ItemStack(GCItems.oxygenFan, 1, 0), 51, 73));
            input1.put(9, new PositionedStack((Object)new ItemStack(GCBlocks.sealableBlock, 1, 14), 51, 89));
            input1.put(10, new PositionedStack((Object)new ItemStack(GCItems.rocketEngine, 1, 0), 51, 105));
            input1.put(11, new PositionedStack((Object)plate, 67, 59));
            input1.put(12, new PositionedStack((Object)plate, 67, 75));
            input1.put(13, new PositionedStack((Object)plate, 67, 91));
            input1.put(14, new PositionedStack((Object)plate, 67, 107));
            input1.put(15, new PositionedStack((Object)plate, 19, 41));
            input1.put(16, new PositionedStack((Object)new ItemStack(GCItems.oxygenVent, 1, 0), 19, 57));
            input1.put(17, new PositionedStack((Object)new ItemStack(GCItems.oxygenFan, 1, 0), 19, 73));
            input1.put(18, new PositionedStack((Object)new ItemStack(GCBlocks.sealableBlock, 1, 14), 19, 89));
            input1.put(19, new PositionedStack((Object)new ItemStack(GCItems.rocketEngine, 1, 0), 19, 105));
            input1.put(20, new PositionedStack((Object)plate, 3, 59));
            input1.put(21, new PositionedStack((Object)plate, 3, 75));
            input1.put(22, new PositionedStack((Object)plate, 3, 91));
            input1.put(23, new PositionedStack((Object)plate, 3, 107));
            this.registerPartConeRecipe(input1, new PositionedStack((Object)new ItemStack(GSItems.RocketParts, 1, i * 5), 129, 113));
        }
    }

    private void addBodyRecipes() {
        for (int i = 0; i <= 3; ++i) {
            HashMap<Integer, PositionedStack> input1 = new HashMap<Integer, PositionedStack>();
            ItemStack plate = i == 0 ? new ItemStack(AsteroidsItems.basicItem, 1, 0) : new ItemStack(GSItems.HeavyDutyPlates, 1, i - 1);
            input1.put(0, new PositionedStack((Object)plate, 13, 25));
            input1.put(1, new PositionedStack((Object)plate, 13, 41));
            input1.put(2, new PositionedStack((Object)new ItemStack(GCBlocks.spinThruster, 1, 0), 13, 57));
            input1.put(3, new PositionedStack((Object)plate, 13, 73));
            input1.put(4, new PositionedStack((Object)plate, 13, 89));
            input1.put(5, new PositionedStack((Object)plate, 13, 105));
            input1.put(6, new PositionedStack((Object)new ItemStack(GCItems.basicItem, 1, 20), 29, 25));
            input1.put(7, new PositionedStack((Object)new ItemStack(GCItems.basicItem, 1, 19), 29, 41));
            input1.put(8, new PositionedStack((Object)plate, 29, 57));
            input1.put(9, new PositionedStack((Object)new ItemStack(GSItems.RocketModules, 1, 3), 29, 73));
            input1.put(10, new PositionedStack((Object)new ItemStack(GSItems.RocketModules, 1, 3), 29, 89));
            input1.put(11, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 1), 29, 105));
            input1.put(12, new PositionedStack((Object)new ItemStack(GCItems.basicItem, 1, 14), 45, 25));
            input1.put(13, new PositionedStack((Object)new ItemStack(GCItems.oxygenConcentrator, 1, 0), 45, 41));
            input1.put(14, new PositionedStack((Object)plate, 45, 57));
            input1.put(15, new PositionedStack((Object)new ItemStack(GSItems.RocketModules, 1, 3), 45, 73));
            input1.put(16, new PositionedStack((Object)new ItemStack(GSItems.RocketModules, 1, 3), 45, 89));
            input1.put(17, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 1), 45, 105));
            input1.put(18, new PositionedStack((Object)plate, 61, 25));
            input1.put(19, new PositionedStack((Object)plate, 61, 41));
            input1.put(20, new PositionedStack((Object)new ItemStack(GCBlocks.spinThruster, 1, 0), 61, 57));
            input1.put(21, new PositionedStack((Object)plate, 61, 73));
            input1.put(22, new PositionedStack((Object)plate, 61, 89));
            input1.put(23, new PositionedStack((Object)plate, 61, 105));
            this.registerPartBodyRecipe(input1, new PositionedStack((Object)new ItemStack(GSItems.RocketParts, 1, i * 5 + 1), 129, 113));
        }
    }

    private void addEngineRecipes() {
        for (int i = 0; i <= 3; ++i) {
            HashMap<Integer, PositionedStack> input1 = new HashMap<Integer, PositionedStack>();
            ItemStack plate = i == 0 ? new ItemStack(AsteroidsItems.basicItem, 1, 0) : new ItemStack(GSItems.HeavyDutyPlates, 1, i - 1);
            input1.put(0, new PositionedStack((Object)plate, 5, 25));
            input1.put(1, new PositionedStack((Object)new ItemStack(GSItems.RocketParts, 1, i * 5 + 3), 5, 73));
            input1.put(2, new PositionedStack((Object)new ItemStack(MarsBlocks.hydrogenPipe, 1, 0), 21, 25));
            input1.put(3, new PositionedStack((Object)plate, 21, 41));
            input1.put(4, new PositionedStack((Object)plate, 21, 57));
            input1.put(5, new PositionedStack((Object)new ItemStack(MarsBlocks.hydrogenPipe, 1, 0), 21, 73));
            input1.put(6, new PositionedStack((Object)new ItemStack(GSItems.RocketParts, 1, i * 5 + 3), 21, 89));
            input1.put(7, new PositionedStack((Object)plate, 37, 25));
            input1.put(8, new PositionedStack((Object)new ItemStack(GCItems.fuelCanister, 1, 1), 37, 41));
            input1.put(9, new PositionedStack((Object)new ItemStack(GCItems.fuelCanister, 1, 1), 37, 57));
            input1.put(10, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 1), 37, 73));
            input1.put(11, new PositionedStack((Object)new ItemStack(MarsBlocks.hydrogenPipe, 1, 0), 53, 25));
            input1.put(12, new PositionedStack((Object)plate, 53, 41));
            input1.put(13, new PositionedStack((Object)plate, 53, 57));
            input1.put(14, new PositionedStack((Object)new ItemStack(MarsBlocks.hydrogenPipe, 1, 0), 53, 73));
            input1.put(15, new PositionedStack((Object)new ItemStack(GSItems.RocketParts, 1, i * 5 + 3), 53, 89));
            input1.put(16, new PositionedStack((Object)plate, 69, 25));
            input1.put(17, new PositionedStack((Object)new ItemStack(GSItems.RocketParts, 1, i * 5 + 3), 69, 73));
            this.registerPartEngineRecipe(input1, new PositionedStack((Object)new ItemStack(GSItems.RocketParts, 1, i * 5 + 2), 129, 113));
        }
    }

    private void addBoosterRecipes() {
        for (int i = 0; i <= 3; ++i) {
            HashMap<Integer, PositionedStack> input1 = new HashMap<Integer, PositionedStack>();
            ItemStack plate = i == 0 ? new ItemStack(AsteroidsItems.basicItem, 1, 0) : new ItemStack(GSItems.HeavyDutyPlates, 1, i - 1);
            input1.put(1, new PositionedStack((Object)plate, 13, 57));
            input1.put(2, new PositionedStack((Object)plate, 13, 73));
            input1.put(3, new PositionedStack((Object)plate, 13, 89));
            input1.put(4, new PositionedStack((Object)plate, 29, 41));
            input1.put(5, new PositionedStack((Object)new ItemStack(GCItems.fuelCanister, 1, 1), 29, 57));
            input1.put(6, new PositionedStack((Object)new ItemStack(MarsBlocks.hydrogenPipe, 1, 0), 29, 73));
            input1.put(7, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 1), 29, 89));
            input1.put(8, new PositionedStack((Object)plate, 45, 57));
            input1.put(9, new PositionedStack((Object)plate, 45, 73));
            input1.put(10, new PositionedStack((Object)plate, 45, 89));
            this.registerPartBoosterRecipe(input1, new PositionedStack((Object)new ItemStack(GSItems.RocketParts, 1, i * 5 + 3), 129, 113));
        }
    }

    private void addFinsRecipes() {
        for (int i = 0; i <= 3; ++i) {
            HashMap<Integer, PositionedStack> input1 = new HashMap<Integer, PositionedStack>();
            ItemStack plate = i == 0 ? new ItemStack(AsteroidsItems.basicItem, 1, 0) : new ItemStack(GSItems.HeavyDutyPlates, 1, i - 1);
            input1.put(0, new PositionedStack((Object)plate, 13, 73));
            input1.put(1, new PositionedStack((Object)plate, 13, 89));
            input1.put(2, new PositionedStack((Object)plate, 29, 41));
            input1.put(3, new PositionedStack((Object)plate, 29, 57));
            input1.put(4, new PositionedStack((Object)plate, 29, 73));
            input1.put(5, new PositionedStack((Object)plate, 29, 89));
            input1.put(6, new PositionedStack((Object)plate, 45, 41));
            input1.put(7, new PositionedStack((Object)plate, 45, 57));
            input1.put(8, new PositionedStack((Object)plate, 45, 73));
            input1.put(9, new PositionedStack((Object)plate, 45, 89));
            input1.put(10, new PositionedStack((Object)plate, 61, 73));
            input1.put(11, new PositionedStack((Object)plate, 61, 89));
            this.registerPartFinsRecipe(input1, new PositionedStack((Object)new ItemStack(GSItems.RocketParts, 1, i * 5 + 4), 129, 113));
        }
    }

    private void addOxTankRecipes() {
        HashMap<Integer, PositionedStack> input1 = new HashMap<Integer, PositionedStack>();
        input1.put(0, new PositionedStack((Object)new ItemStack(Blocks.wool, 1, 14), 13, 41));
        input1.put(1, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 6), 13, 57));
        input1.put(2, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 6), 13, 73));
        input1.put(3, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 6), 13, 89));
        input1.put(4, new PositionedStack((Object)new ItemStack(GCBlocks.oxygenPipe, 1, 0), 29, 41));
        input1.put(5, new PositionedStack((Object)new ItemStack(GCItems.oxygenConcentrator, 1, 0), 29, 57));
        input1.put(6, new PositionedStack((Object)new ItemStack(GCItems.oxTankLight, 1, GCItems.oxTankLight.getMaxDamage()), 29, 73));
        input1.put(7, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 6), 29, 89));
        input1.put(8, new PositionedStack((Object)new ItemStack(Blocks.wool, 1, 14), 45, 41));
        input1.put(9, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 6), 45, 57));
        input1.put(10, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 6), 45, 73));
        input1.put(11, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 6), 45, 89));
        this.registerOxTankRecipe(input1, new PositionedStack((Object)new ItemStack(GSItems.OxygenTankEPPTier1, 1, GSItems.OxygenTankEPPTier1.getMaxDamage()), 129, 113));
    }

    private void addPortNuclearRecipes() {
        HashMap<Integer, PositionedStack> input1 = new HashMap<Integer, PositionedStack>();
        int x = 3;
        int y = 25;
        input1.put(0, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 0), x, y));
        input1.put(1, new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 1), x, y + 16));
        input1.put(2, new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 1), x, y + 32));
        input1.put(3, new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 1), x, y + 48));
        input1.put(4, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 0), x, y + 64));
        input1.put(5, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 6), x + 16, y));
        input1.put(6, new PositionedStack((Object)new ItemStack(GSItems.HeavyDutyPlates, 1, 1), x + 16, y + 16));
        input1.put(7, new PositionedStack((Object)new ItemStack(GSItems.HeavyDutyPlates, 1, 1), x + 16, y + 32));
        input1.put(8, new PositionedStack((Object)new ItemStack(GSItems.HeavyDutyPlates, 1, 1), x + 16, y + 48));
        input1.put(9, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 6), x + 16, y + 64));
        input1.put(10, new PositionedStack((Object)new ItemStack(GSBlocks.FluidTank, 1, 0), x + 32, y));
        input1.put(11, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 8), x + 32, y + 16));
        input1.put(12, new PositionedStack((Object)new ItemStack(GSItems.BasicItems, 1, 0), x + 32, y + 32));
        input1.put(13, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 8), x + 32, y + 48));
        input1.put(15, new PositionedStack((Object)new ItemStack(GSBlocks.FuelGenerator, 1, 0), x + 32, y + 64));
        input1.put(16, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 6), x + 48, y));
        input1.put(17, new PositionedStack((Object)new ItemStack(GSItems.HeavyDutyPlates, 1, 1), x + 48, y + 16));
        input1.put(18, new PositionedStack((Object)new ItemStack(GSItems.HeavyDutyPlates, 1, 1), x + 48, y + 32));
        input1.put(19, new PositionedStack((Object)new ItemStack(GSItems.HeavyDutyPlates, 1, 1), x + 48, y + 48));
        input1.put(20, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 6), x + 48, y + 64));
        input1.put(21, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 0), x + 64, y));
        input1.put(22, new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 1), x + 64, y + 16));
        input1.put(23, new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 1), x + 64, y + 32));
        input1.put(24, new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 1), x + 64, y + 48));
        input1.put(25, new PositionedStack((Object)new ItemStack(AsteroidsItems.basicItem, 1, 0), x + 64, y + 64));
        this.registerPortNuclearRecipe(input1, new PositionedStack((Object)new ItemStack(GSBlocks.PortableNuclearReactor, 1, 0), 129, 113));
    }

    private void addAssemblyMachineRecipes() {
        for (int i = 0; i < AssemberRecipes.instance.getRecipeList().size(); ++i) {
            int j;
            IRecipe recipe;
            HashMap<Integer, PositionedStack> input1 = new HashMap<Integer, PositionedStack>();
            IRecipe rec = AssemberRecipes.instance.getRecipeList().get(i);
            if (rec instanceof ShapedRecipes) {
                recipe = (ShapedRecipes)rec;
                for (j = 0; j < ((ShapedRecipes) recipe).recipeItems.length; ++j) {
                    if (((ShapedRecipes) recipe).recipeItems[j] == null) continue;
                    ItemStack stack = ((ShapedRecipes) recipe).recipeItems[j];
                    input1.put(j, new PositionedStack((Object)stack, 21 + j % 3 * 18, 26 + j / 3 * 18));
                }
            } else if (rec instanceof ShapelessOreRecipe) {
                recipe = (ShapelessOreRecipe)rec;
                for (j = 0; j < ((ShapelessOreRecipe) recipe).getInput().size(); ++j) {
                    Object obj = ((ShapelessOreRecipe) recipe).getInput().get(j);
                    input1.put(j, new PositionedStack(obj, 21 + j % 3 * 18, 26 + j / 3 * 18));
                }
            }
            this.registerAssemblyMachineRecipe(input1, new PositionedStack((Object)rec.getRecipeOutput(), 140, 44));
        }
    }

    private void addRocketAssemblyRecipes() {
        for (int i = 0; i < RocketAssemblyRecipes.getRecipeList().size(); ++i) {
            IRecipe recipe;
            HashMap<Integer, PositionedStack> input1 = new HashMap<Integer, PositionedStack>();
            IRecipe rec = RocketAssemblyRecipes.getRecipeList().get(i);
            if (rec instanceof ShapedRecipes) {
                recipe = (ShapedRecipes)rec;
                for (int j = 0; j < ((ShapedRecipes) recipe).recipeItems.length; ++j) {
                    ItemStack stack = ((ShapedRecipes) recipe).recipeItems[j];
                    input1.put(j, new PositionedStack((Object)stack, 21 + j % 3 * 18, 26 + j / 3 * 18));
                }
            } else if (rec instanceof ShapelessOreRecipe) {
                recipe = (ShapelessOreRecipe)rec;
                input1.put(0, new PositionedStack(((ShapelessOreRecipe) recipe).getInput().get(0), 116, 62));
                input1.put(1, new PositionedStack(((ShapelessOreRecipe) recipe).getInput().get(1), 76, 62));
                input1.put(2, new PositionedStack(((ShapelessOreRecipe) recipe).getInput().get(2), 44, 62));
                input1.put(3, new PositionedStack(((ShapelessOreRecipe) recipe).getInput().get(3), 9, 62));
                input1.put(4, new PositionedStack(((ShapelessOreRecipe) recipe).getInput().get(4), 9, 33));
                input1.put(5, new PositionedStack(((ShapelessOreRecipe) recipe).getInput().get(5), 9, 91));
                input1.put(6, new PositionedStack(((ShapelessOreRecipe) recipe).getInput().get(6), 38, 33));
                input1.put(7, new PositionedStack(((ShapelessOreRecipe) recipe).getInput().get(7), 38, 91));
            }
            this.registerRocketAssemblyRecipe(input1, new PositionedStack((Object)rec.getRecipeOutput(), 154, 62));
        }
    }
    private void addRocketT1Recipes() {

        //Tier 1


        int changeY = 25;
        int changeX = -15;
        ArrayList<PositionedStack> input1 = new ArrayList<PositionedStack>();
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.partNoseCone), 30, 17));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.heavyPlatingTier1, 1, 3), 21, 35));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.heavyPlatingTier1, 1, 3), 21, 53));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.heavyPlatingTier1, 1, 3), 21, 71));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.heavyPlatingTier1, 1, 3), 21, 89));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.heavyPlatingTier1, 1, 3), 21, 107));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.heavyPlatingTier1, 1, 3), 39, 35));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.heavyPlatingTier1, 1, 3), 39, 53));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.heavyPlatingTier1, 1, 3), 39, 71));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.rocketEngine), 30, 125));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.partFins), 3, 107));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.partFins), 3, 125));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.partFins), 57, 107));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.partFins), 57, 125));
        input1.add(new PositionedStack((Object)new ItemStack(GSItems.RocketModules, 1, 1), 67, 25));
        input1.add(new PositionedStack((Object)new ItemStack(GSItems.RocketModules, 1, 3), 67, 42));
        input1.add(new PositionedStack((Object)new ItemStack(GSItems.RocketModules, 1, 3), 67, 59));
        this.registerRocketBenchRecipe(input1, new PositionedStack((Object)new ItemStack(GCItems.rocketTier1, 1, 0), 129, 107));
        ArrayList<PositionedStack> input2 = new ArrayList<PositionedStack>(input1);
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 90, 10));
        this.registerRocketBenchRecipe(input2, new PositionedStack((Object)new ItemStack(GCItems.rocketTier1, 1, 1), 129, 107));
        input2 = new ArrayList<PositionedStack>(input1);
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 116, 10));
        this.registerRocketBenchRecipe(input2, new PositionedStack((Object)new ItemStack(GCItems.rocketTier1, 1, 1), 129, 107));
        input2 = new ArrayList<PositionedStack>(input1);
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 142, 10));
        this.registerRocketBenchRecipe(input2, new PositionedStack((Object)new ItemStack(GCItems.rocketTier1, 1, 1), 129, 107));
        input2 = new ArrayList<PositionedStack>(input1);
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 90, 10));
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 116, 10));
        this.registerRocketBenchRecipe(input2, new PositionedStack((Object)new ItemStack(GCItems.rocketTier1, 1, 2), 129, 107));
        input2 = new ArrayList<PositionedStack>(input1);
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 116, 10));
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 142, 10));
        this.registerRocketBenchRecipe(input2, new PositionedStack((Object)new ItemStack(GCItems.rocketTier1, 1, 2), 129, 107));
        input2 = new ArrayList<PositionedStack>(input1);
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 90, 10));
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 142, 10));
        this.registerRocketBenchRecipe(input2, new PositionedStack((Object)new ItemStack(GCItems.rocketTier1, 1, 2), 129, 107));
        input2 = new ArrayList<PositionedStack>(input1);
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 90, 10));
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 116, 10));
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 142, 10));
        this.registerRocketBenchRecipe(input2, new PositionedStack((Object)new ItemStack(GCItems.rocketTier1, 1, 3), 129, 107));
    }

    private void addRocketT2Recipes() {

        //Tier 2


        int changeY = 25;
        int changeX = -15;
        ArrayList<PositionedStack> input1 = new ArrayList<PositionedStack>();
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.partNoseCone), 30, 17));
        input1.add(new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 3), 21, 35));
        input1.add(new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 3), 21, 53));
        input1.add(new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 3), 21, 71));
        input1.add(new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 3), 21, 89));
        input1.add(new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 3), 21, 107));
        input1.add(new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 3), 39, 35));
        input1.add(new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 3), 39, 53));
        input1.add(new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 3), 39, 71));
        input1.add(new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 3), 39, 89));
        input1.add(new PositionedStack((Object)new ItemStack(MarsItems.marsItemBasic, 1, 3), 39, 107));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.rocketEngine), 30, 125));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.rocketEngine, 1, 1), 3, 89));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.rocketEngine, 1, 1), 57, 89));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.partFins), 3, 107));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.partFins), 3, 125));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.partFins), 57, 107));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.partFins), 57, 125));
        input1.add(new PositionedStack((Object)new ItemStack(GSItems.RocketModules, 1, 1), 67, 25));
        input1.add(new PositionedStack((Object)new ItemStack(GSItems.RocketModules, 1, 3), 67, 42));
        input1.add(new PositionedStack((Object)new ItemStack(GSItems.RocketModules, 1, 3), 67, 59));
        input1.add(new PositionedStack((Object)new ItemStack(GCItems.rocketTier1, 1, 0), 129, 50));
        this.registerRocketBenchRecipe(input1, new PositionedStack((Object)new ItemStack(MarsItems.spaceship, 1, 0), 129, 107));
        ArrayList<PositionedStack> input2 = new ArrayList<PositionedStack>(input1);
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 90, 10));
        this.registerRocketBenchRecipe(input2, new PositionedStack((Object)new ItemStack(MarsItems.spaceship, 1, 1), 129, 107));
        input2 = new ArrayList<PositionedStack>(input1);
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 116, 10));
        this.registerRocketBenchRecipe(input2, new PositionedStack((Object)new ItemStack(MarsItems.spaceship, 1, 1), 129, 107));
        input2 = new ArrayList<PositionedStack>(input1);
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 142, 10));
        this.registerRocketBenchRecipe(input2, new PositionedStack((Object)new ItemStack(MarsItems.spaceship, 1, 1), 129, 107));
        input2 = new ArrayList<PositionedStack>(input1);
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 90, 10));
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 116, 10));
        this.registerRocketBenchRecipe(input2, new PositionedStack((Object)new ItemStack(MarsItems.spaceship, 1, 2), 129, 107));
        input2 = new ArrayList<PositionedStack>(input1);
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 116, 10));
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 142, 10));
        this.registerRocketBenchRecipe(input2, new PositionedStack((Object)new ItemStack(MarsItems.spaceship, 1, 2), 129, 107));
        input2 = new ArrayList<PositionedStack>(input1);
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 90, 10));
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 142, 10));
        this.registerRocketBenchRecipe(input2, new PositionedStack((Object)new ItemStack(MarsItems.spaceship, 1, 2), 129, 107));
        input2 = new ArrayList<PositionedStack>(input1);
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 90, 10));
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 116, 10));
        input2.add(new PositionedStack((Object)new ItemStack((Block)Blocks.chest), 142, 10));
        this.registerRocketBenchRecipe(input2, new PositionedStack((Object)new ItemStack(MarsItems.spaceship, 1, 3), 129, 107));

    }
}

