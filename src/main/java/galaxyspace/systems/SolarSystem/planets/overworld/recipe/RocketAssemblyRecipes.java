/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.IRecipe
 *  net.minecraft.item.crafting.ShapedRecipes
 *  net.minecraft.world.World
 *  net.minecraftforge.oredict.OreDictionary
 *  net.minecraftforge.oredict.ShapelessOreRecipe
 */
package galaxyspace.systems.SolarSystem.planets.overworld.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RocketAssemblyRecipes {
    public static final RocketAssemblyRecipes instance = new RocketAssemblyRecipes();
    private static List<IRecipe> recipes = new ArrayList<IRecipe>();

    public static ShapedRecipes addRecipe(ItemStack output, Object ... inputList) {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;
        if (inputList[i] instanceof String[]) {
            String[] astring;
            for (String s1 : astring = (String[])inputList[i++]) {
                ++k;
                j = s1.length();
                s = s + s1;
            }
        } else {
            while (inputList[i] instanceof String) {
                String s2 = (String)inputList[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }
        HashMap<Character, ItemStack> hashmap = new HashMap<Character, ItemStack>();
        while (i < inputList.length) {
            Character character = (Character)inputList[i];
            ItemStack itemstack1 = null;
            if (inputList[i + 1] instanceof Item) {
                itemstack1 = new ItemStack((Item)inputList[i + 1]);
            } else if (inputList[i + 1] instanceof Block) {
                itemstack1 = new ItemStack((Block)inputList[i + 1], 1, Short.MAX_VALUE);
            } else if (inputList[i + 1] instanceof ItemStack) {
                itemstack1 = (ItemStack)inputList[i + 1];
            }
            hashmap.put(character, itemstack1);
            i += 2;
        }
        ItemStack[] aitemstack = new ItemStack[j * k];
        for (int i1 = 0; i1 < j * k; ++i1) {
            char c0 = s.charAt(i1);
            aitemstack[i1] = hashmap.containsKey(Character.valueOf(c0)) ? ((ItemStack)hashmap.get(Character.valueOf(c0))).copy() : null;
        }
        ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack, output);
        recipes.add((IRecipe)shapedrecipes);
        return shapedrecipes;
    }

    public static void addShapelessRecipe(ItemStack par1ItemStack, Object ... par2ArrayOfObj) {
        ArrayList<Object> arraylist = new ArrayList<Object>();
        for (Object object1 : par2ArrayOfObj) {
            if (object1 instanceof ItemStack) {
                arraylist.add(((ItemStack)object1).copy());
                continue;
            }
            if (object1 instanceof Item) {
                arraylist.add(new ItemStack((Item)object1));
                continue;
            }
            if (object1 instanceof String) {
                arraylist.add(object1);
                continue;
            }
            if (!(object1 instanceof Block)) {
                throw new RuntimeException("Invalid shapeless compressor recipe!");
            }
            arraylist.add(new ItemStack((Block)object1));
        }
        recipes.add((IRecipe)new ShapelessOreRecipe(par1ItemStack, arraylist.toArray()));
    }

    public static ItemStack findMatchingRecipe(IInventory inventory, World par2World) {
        int j;
        int i = 0;
        ItemStack itemstack = null;
        ItemStack itemstack1 = null;
        for (j = 0; j < inventory.getSizeInventory(); ++j) {
            ItemStack itemstack2 = inventory.getStackInSlot(j);
            if (itemstack2 == null) continue;
            if (i == 0) {
                itemstack = itemstack2;
            }
            if (i == 1) {
                itemstack1 = itemstack2;
            }
            ++i;
        }
        if (i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && itemstack.getItem().isRepairable()) {
            int k = itemstack.getItem().getMaxDamage() - itemstack.getItemDamageForDisplay();
            int l = itemstack.getItem().getMaxDamage() - itemstack1.getItemDamageForDisplay();
            int i1 = k + l + itemstack.getItem().getMaxDamage() * 5 / 100;
            int j1 = itemstack.getItem().getMaxDamage() - i1;
            if (j1 < 0) {
                j1 = 0;
            }
            return new ItemStack(itemstack.getItem(), 1, j1);
        }
        for (j = 0; j < recipes.size(); ++j) {
            IRecipe irecipe = recipes.get(j);
            if (irecipe instanceof ShapedRecipes && RocketAssemblyRecipes.matches((ShapedRecipes)irecipe, inventory, par2World)) {
                return irecipe.getRecipeOutput().copy();
            }
            if (!(irecipe instanceof ShapelessOreRecipe) || !RocketAssemblyRecipes.matchesShapeless((ShapelessOreRecipe)irecipe, inventory, par2World)) continue;
            return irecipe.getRecipeOutput().copy();
        }
        return null;
    }

    private static boolean matches(ShapedRecipes recipe, IInventory inventory, World par2World) {
        for (int i = 0; i <= 3 - recipe.recipeWidth; ++i) {
            for (int j = 0; j <= 3 - recipe.recipeHeight; ++j) {
                if (RocketAssemblyRecipes.checkMatch(recipe, inventory, i, j, true)) {
                    return true;
                }
                if (!RocketAssemblyRecipes.checkMatch(recipe, inventory, i, j, false)) continue;
                return true;
            }
        }
        return false;
    }

    private static boolean checkMatch(ShapedRecipes recipe, IInventory inventory, int par2, int par3, boolean par4) {
        for (int k = 0; k < 3; ++k) {
            for (int l = 0; l < 3; ++l) {
                int i1 = k - par2;
                int j1 = l - par3;
                ItemStack itemstack = null;
                if (i1 >= 0 && j1 >= 0 && i1 < recipe.recipeWidth && j1 < recipe.recipeHeight) {
                    itemstack = par4 ? recipe.recipeItems[recipe.recipeWidth - i1 - 1 + j1 * recipe.recipeWidth] : recipe.recipeItems[i1 + j1 * recipe.recipeWidth];
                }
                ItemStack itemstack1 = null;
                if (k >= 0 && l < 3) {
                    int k2 = k + l * 3;
                    itemstack1 = inventory.getStackInSlot(k2);
                }
                if (itemstack1 == null && itemstack == null) continue;
                if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null) {
                    return false;
                }
                if (itemstack.getItem() != itemstack1.getItem()) {
                    return false;
                }
                if (itemstack.getItemDamage() == Short.MAX_VALUE || itemstack.getItemDamage() == itemstack1.getItemDamage()) continue;
                return false;
            }
        }
        return true;
    }

    private static boolean matchesShapeless(ShapelessOreRecipe recipe, IInventory var1, World par2World) {
        ArrayList required = new ArrayList(recipe.getInput());
        for (int x = 0; x < var1.getSizeInventory(); ++x) {
            ItemStack slot = var1.getStackInSlot(x);
            if (slot == null) continue;
            boolean inRecipe = false;
            Iterator req = required.iterator();
            while (req.hasNext()) {
                boolean match = false;
                Object next = req.next();
                if (next instanceof ItemStack) {
                    match = OreDictionary.itemMatches((ItemStack)((ItemStack)next), (ItemStack)slot, (boolean)false);
                } else if (next instanceof ArrayList) {
                    Iterator itr = ((ArrayList)next).iterator();
                    while (itr.hasNext() && !match) {
                        match = OreDictionary.itemMatches((ItemStack)((ItemStack)itr.next()), (ItemStack)slot, (boolean)false);
                    }
                }
                if (!match) continue;
                inRecipe = true;
                required.remove(next);
                break;
            }
            if (inRecipe) continue;
            return false;
        }
        return required.isEmpty();
    }

    public static List<IRecipe> getRecipeList() {
        return recipes;
    }
    public void removeRecipe(ItemStack stack)
    {
        int index = 0;

        for(IRecipe recipes : getRecipeList())
        {
            if(recipes.getRecipeOutput().isItemEqual(stack))
            {
                index = getRecipeList().indexOf(recipes);
            }
        }

        getRecipeList().remove(index);
    }
}

