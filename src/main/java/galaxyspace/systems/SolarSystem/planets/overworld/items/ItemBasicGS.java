/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.eventhandler.Event
 *  cpw.mods.fml.common.eventhandler.Event$Result
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  micdoodle8.mods.galacticraft.core.util.EnumColor
 *  micdoodle8.mods.galacticraft.core.util.GCCoreUtil
 *  net.minecraft.block.Block
 *  net.minecraft.block.IGrowable
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.util.IIcon
 *  net.minecraft.util.StatCollector
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.player.BonemealEvent
 */
package galaxyspace.systems.SolarSystem.planets.overworld.items;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.core.registers.potions.GSPotions;
import galaxyspace.core.util.GSAttributePlayer;
import galaxyspace.core.util.GSCreativeTabs;
import java.util.List;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class ItemBasicGS
extends Item {
    public static String[] names = new String[]{"ModuleSmallCanister", "PartSolarFlares", "SolarFlares", "ModuleElectrolyse", "DolomiteCrystal", "DolomiteMeal", "Sapphire", "WaferModern", "Hematite", "Sulfur", "UnknowCrystal", "Antiradiation", "SchematicBox", "VolcanicStone", "ModuleExpander", "ModulePressure", "UraniumFragments", "AncientRelic", "GuideBook", "UraniumRod"};
    protected IIcon[] icons = new IIcon[names.length];

    public ItemBasicGS() {
        this.setUnlocalizedName("BasicItems");
        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
        this.setMaxDamage(0);
    }

    @SideOnly(value=Side.CLIENT)
    public CreativeTabs getCreativeTab() {
        return GSCreativeTabs.GSItemsTab;
    }

    @SideOnly(value=Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean b) {
        int n = is.getItemDamage();
        if (n == 5) {
            list.add(GCCoreUtil.translate((String)"gui.bonemeal.desc"));
        } else if (n == 12) {
            list.add(GCCoreUtil.translate((String)"gui.schematicbox.desc"));
        } else if (n == 17) {
            if (!GSConfigCore.enableGenAncientAmulet) {
                list.add(EnumColor.DARK_RED + "Disabled");
            }
            list.add(GCCoreUtil.translate((String)"gui.ancient_item"));
            list.add(GCCoreUtil.translate((String)"gui.ancient_relic"));
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        int i = 0;
        for (String name : names) {
            this.icons[i++] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":basic/" + name);
        }
    }

    public IIcon getIconFromDamage(int damage) {
        if (this.icons.length > damage) {
            return this.icons[damage];
        }
        return super.getIconFromDamage(damage);
    }

    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        int i = 0;
        while (true) {
            if (i >= names.length) break;
            par3List.add(new ItemStack(par1, 1, i));
            ++i;
        }
    }

    public String getUnlocalizedName(ItemStack par1ItemStack) {
        if (this.icons.length > par1ItemStack.getItemDamage()) {
            return "item." + names[par1ItemStack.getItemDamage()];
        }
        return "unnamed";
    }

    public int getMetadata(int par1) {
        return par1;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            if (stack.getItemDamage() == 11) {
                IAttributeInstance lvl = player.getEntityAttribute(GSAttributePlayer.RADIATION_LVL);
                if (!(lvl.getAttributeValue() > 0.0)) {
                    player.addChatComponentMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal((String)"gui.message.younothaveradiation")));
                    return stack;
                }
                player.addPotionEffect(new PotionEffect(GSPotions.antiradiation.id, 200));
                player.inventory.consumeInventoryItem((Item)this);
                return stack;
            }
            if (stack.getItemDamage() == 12) {
                for (int i = 0; i < 5; ++i) {
                    if (player.inventory.getFirstEmptyStack() != -1) {
                        player.inventory.addItemStackToInventory(new ItemStack(GSItems.Schematics, 1, i));
                        continue;
                    }
                    world.spawnEntityInWorld((Entity)new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(GSItems.Schematics, 1, i)));
                }
                player.inventory.consumeInventoryItem((Item)this);
            }
        }
        return super.onItemRightClick(stack, world, player);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        if (stack.getItemDamage() == 5 && ItemBasicGS.applyBonemeal(stack, world, x, y, z, player)) {
            if (!world.isRemote) {
                world.playAuxSFX(2005, x, y, z, 0);
            }
            return true;
        }
        return false;
    }

    public static boolean applyBonemeal(ItemStack stack, World world, int x, int y, int z, EntityPlayer player) {
        IGrowable igrowable;
        Block block = world.getBlock(x, y, z);
        BonemealEvent event = new BonemealEvent(player, world, block, x, y, z);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            return false;
        }
        if (event.getResult() == Event.Result.ALLOW) {
            if (!world.isRemote) {
                --stack.stackSize;
            }
            return true;
        }
        if (block instanceof IGrowable && (igrowable = (IGrowable)block).func_149851_a(world, x, y, z, world.isRemote)) {
            if (!world.isRemote) {
                if (igrowable.func_149852_a(world, world.rand, x, y, z)) {
                    igrowable.func_149853_b(world, world.rand, x, y, z);
                }
                --stack.stackSize;
            }
            return true;
        }
        return false;
    }
}

