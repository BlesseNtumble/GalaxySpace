/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package galaxyspace.systems.SolarSystem.moons.moon.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.ItemStack;

public class AlienRecipes {
    private static final AlienRecipes base = new AlienRecipes();
    private Map tradeList = new LinkedHashMap();
    private Map stackList = new LinkedHashMap();
    private List<TradeRecipe> recipes = new ArrayList<TradeRecipe>();

    public static AlienRecipes getInstance() {
        return base;
    }

    public void addRecipe(ItemStack result, ItemStack component1, ItemStack component2) {
        this.recipes.add(new TradeRecipe(result, component1, component2));
    }

    public List<TradeRecipe> getRecipes() {
        return this.recipes;
    }

    public ItemStack getResult(ItemStack component1, ItemStack component2) {
        for (TradeRecipe recipes : this.recipes) {
            if (component2 == null && recipes.getFirstComponent().isItemEqual(component1)) {
                return recipes.getResult();
            }
            if (component2 == null || !recipes.getFirstComponent().isItemEqual(component1) || !recipes.getSecondComponent().isItemEqual(component2)) continue;
            return recipes.getResult();
        }
        return null;
    }

    public ItemStack[] getComponents(ItemStack result) {
        for (TradeRecipe recipes : this.recipes) {
            if (result == null || !recipes.getResult().isItemEqual(result)) continue;
            ItemStack[] components = new ItemStack[]{recipes.getFirstComponent(), recipes.getSecondComponent()};
            return components;
        }
        return new ItemStack[]{null, null};
    }

    public ItemStack getTradeCont(ItemStack stack) {
        Map.Entry entry;
        Iterator iterator = this.tradeList.entrySet().iterator();
        do {
            if (iterator.hasNext()) continue;
            return null;
        } while (!this.func_151397_a(stack, (ItemStack)(entry = (Map.Entry) iterator.next()).getKey()));
        return (ItemStack)entry.getKey();
    }

    public ItemStack getTradeResult(ItemStack stack) {
        Map.Entry entry;
        Iterator iterator = this.tradeList.entrySet().iterator();
        do {
            if (iterator.hasNext()) continue;
            return null;
        } while (!this.func_151397_a(stack, (ItemStack)(entry = (Map.Entry) iterator.next()).getKey()));
        return (ItemStack)entry.getValue();
    }

    public ItemStack getTwoSlot(ItemStack stack) {
        Map.Entry entry;
        Iterator iterator = this.stackList.entrySet().iterator();
        do {
            if (iterator.hasNext()) continue;
            return null;
        } while (!this.func_151397_a(stack, (ItemStack)(entry = (Map.Entry) iterator.next()).getKey()));
        return (ItemStack)entry.getValue();
    }

    private boolean func_151397_a(ItemStack p_151397_1_, ItemStack p_151397_2_) {
        return p_151397_2_.getItem() == p_151397_1_.getItem() && (p_151397_2_.getItemDamage() == Short.MAX_VALUE || p_151397_2_.getItemDamage() == p_151397_1_.getItemDamage());
    }

    public Map getTwoList() {
        return this.stackList;
    }

    public Map getTradeList() {
        return this.tradeList;
    }

    public class TradeRecipe {
        private ItemStack slot1;
        private ItemStack slot2;
        private ItemStack result;

        public TradeRecipe(ItemStack result, ItemStack component1, ItemStack component2) {
            this.slot1 = component1;
            this.slot2 = component2;
            this.result = result;
        }

        public ItemStack getResult() {
            return this.result;
        }

        public ItemStack getFirstComponent() {
            return this.slot1;
        }

        public ItemStack getSecondComponent() {
            return this.slot2;
        }
    }
}

