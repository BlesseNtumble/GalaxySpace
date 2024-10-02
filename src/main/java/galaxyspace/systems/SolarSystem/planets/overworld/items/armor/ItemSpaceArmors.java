/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.FMLClientHandler
 *  cpw.mods.fml.common.Optional$Interface
 *  cpw.mods.fml.common.Optional$InterfaceList
 *  cpw.mods.fml.common.Optional$Method
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  micdoodle8.mods.galacticraft.api.item.IArmorGravity
 *  micdoodle8.mods.galacticraft.api.item.IItemElectric
 *  micdoodle8.mods.galacticraft.core.client.gui.overlay.OverlaySensorGlasses
 *  micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper
 *  micdoodle8.mods.galacticraft.core.util.GCCoreUtil
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntitySkeleton
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.EnumAction
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor$ArmorMaterial
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.util.StatCollector
 *  net.minecraft.world.World
 *  net.minecraftforge.common.ISpecialArmor
 *  net.minecraftforge.common.ISpecialArmor$ArmorProperties
 *  org.lwjgl.input.Keyboard
 *  thaumcraft.api.IGoggles
 *  thaumcraft.api.IVisDiscountGear
 *  thaumcraft.api.aspects.Aspect
 *  thaumcraft.api.nodes.IRevealer
 */
package galaxyspace.systems.SolarSystem.planets.overworld.items.armor;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.api.item.IItemPressurized;
import galaxyspace.api.item.IItemRadiation;
import galaxyspace.api.item.IJetpack;
import galaxyspace.api.item.IModificationItem;
import galaxyspace.core.client.models.ModelOBJArmor;
import galaxyspace.core.prefab.items.ItemElectricArmor;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.render.item.ItemSpaceSuitModel;
import java.util.List;
import micdoodle8.mods.galacticraft.api.item.IArmorGravity;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.client.gui.overlay.OverlaySensorGlasses;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import org.lwjgl.input.Keyboard;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;

@Optional.InterfaceList(value={@Optional.Interface(iface="thaumcraft.api.IGoggles", modid="Thaumcraft"), @Optional.Interface(iface="thaumcraft.api.nodes.IRevealer", modid="Thaumcraft"), @Optional.Interface(iface="thaumcraft.api.IVisDiscountGear", modid="Thaumcraft")})
public class ItemSpaceArmors
extends ItemElectricArmor
implements IModificationItem,
IJetpack,
IArmorGravity,
IItemPressurized,
IItemRadiation,
ISpecialArmor,
IRevealer,
IGoggles,
IVisDiscountGear {
    private int tier = 1;
    public static String mod_count = "modification_count";
    public static boolean[] pressedKey = new boolean[3];
    public static String[] suit_buttons = new String[]{"helmet_button", "chest_button", "legs_button", "boots_button"};
    private boolean[] key_handler = new boolean[4];
    boolean test = false;
    public float transferMax = 200.0f;
    private float jumpCharge;

    public ItemSpaceArmors(ItemArmor.ArmorMaterial material, int proxyIndex, int armorIndex, String assetSuffix) {
        super(material, proxyIndex, armorIndex, assetSuffix);
        this.setTextureName(GalaxySpace.ASSET_PREFIX + ":armors/" + assetSuffix);
        this.setNoRepair();
    }

    public CreativeTabs getCreativeTab() {
        return GSCreativeTabs.GSArmorTab;
    }

    @Override
    public boolean canFly(ItemStack stack, EntityPlayer player) {
        return stack.hasTagCompound() && stack.stackTagCompound.getBoolean("jetpack") && stack.getTagCompound().getBoolean(suit_buttons[1]) && stack.getItemDamage() < stack.getMaxDamage();
    }

    public void needText(EntityPlayer player, String text) {
        player.addChatComponentMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.DARK_RED + StatCollector.translateToLocal((String)("gui.message." + text))));
    }

    public void installText(EntityPlayer player, String text) {
        player.addChatComponentMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal((String)("gui.message.install." + text))));
    }

    public int getArmorType(ItemStack stack) {
        int i = EntityLiving.getArmorPosition((ItemStack)stack) - 1;
        return i;
    }

    @SideOnly(value=Side.CLIENT)
    public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks, boolean hasScreen, int mouseX, int mouseY) {
        if (!stack.hasTagCompound()) {
            stack.stackTagCompound = new NBTTagCompound();
        }
        if (this.getArmorType(stack) == 3 && stack.stackTagCompound.getBoolean("sensor") && this.getElectricityStored(stack) > 2.0f && stack.getTagCompound().getBoolean(suit_buttons[0])) {
            OverlaySensorGlasses.renderSensorGlassesMain((ItemStack)stack, (EntityPlayer)player, (ScaledResolution)resolution, (float)partialTicks, (boolean)hasScreen, (int)mouseX, (int)mouseY);
            OverlaySensorGlasses.renderSensorGlassesValueableBlocks((ItemStack)stack, (EntityPlayer)player, (ScaledResolution)resolution, (float)partialTicks, (boolean)hasScreen, (int)mouseX, (int)mouseY);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack is, int armorSlot) {
        ItemSpaceSuitModel armorModel = armorSlot == 1 && is.hasTagCompound() && is.stackTagCompound.getBoolean("jetpack") ? new ItemSpaceSuitModel(5) : new ItemSpaceSuitModel(armorSlot);
        if (is.getItem() instanceof ItemSpaceArmors) {
            armorModel = (ItemSpaceSuitModel) ItemSpaceArmors.fillingArmorModel(armorModel, entityLiving);
            if (this.hasColor(is) && armorModel instanceof ModelOBJArmor) {
                ((ModelOBJArmor)armorModel).color = this.getColor(is);
            }
        }
        return armorModel;
    }

    public static ModelBiped fillingArmorModel(ModelBiped model, EntityLivingBase entityLiving) {
        if (model == null) {
            return model;
        }
        model.bipedLeftLeg.showModel = false;
        model.bipedRightLeg.showModel = false;
        model.bipedLeftArm.showModel = false;
        model.bipedRightArm.showModel = false;
        model.bipedBody.showModel = false;
        model.bipedHeadwear.showModel = false;
        model.bipedHead.showModel = false;
        model.isSneak = entityLiving.isSneaking();
        model.isRiding = entityLiving.isRiding();
        model.isChild = entityLiving.isChild();
        ItemStack held_item = entityLiving.getEquipmentInSlot(0);
        if (held_item != null) {
            EntityPlayer player;
            model.heldItemRight = 1;
            if (entityLiving instanceof EntityPlayer && (player = (EntityPlayer)entityLiving).getItemInUseCount() > 0) {
                EnumAction enumaction = held_item.getItemUseAction();
                if (enumaction == EnumAction.bow) {
                    model.aimedBow = true;
                } else if (enumaction == EnumAction.block) {
                    model.heldItemRight = 3;
                }
            }
        } else {
            model.heldItemRight = 0;
        }
        if (entityLiving instanceof EntitySkeleton) {
            model.aimedBow = ((EntitySkeleton)entityLiving).getSkeletonType() == 1;
        }
        return model;
    }

    @SideOnly(value=Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer player, List par2List, boolean b) {
        par2List.addAll(FMLClientHandler.instance().getClient().fontRenderer.listFormattedStringToWidth(GCCoreUtil.translate((String)"gui.spacesuit.desc"), 350));
        if (this.getArmorType(par1ItemStack) == 3) {
            par2List.addAll(FMLClientHandler.instance().getClient().fontRenderer.listFormattedStringToWidth(EnumChatFormatting.DARK_GREEN + GCCoreUtil.translate((String)"gui.spacesuit_helmet.desc"), 250));
        }
        String color = "";
        float joules = this.getElectricityStored(par1ItemStack);
        color = joules <= this.getMaxElectricityStored(par1ItemStack) / 3.0f ? color + EnumChatFormatting.DARK_RED : (joules > this.getMaxElectricityStored(par1ItemStack) * 2.0f / 3.0f ? color + EnumChatFormatting.DARK_GREEN : color + EnumChatFormatting.GOLD);
        par2List.add("");
        par2List.add(EnumChatFormatting.DARK_GREEN + GCCoreUtil.translate((String)"gui.module.caninstall"));
        par2List.add(EnumChatFormatting.GRAY + GCCoreUtil.translate((String)"gui.module.available_modules") + " " + par1ItemStack.getTagCompound().getInteger(mod_count));
        par2List.add("");
        par2List.add(EnumChatFormatting.AQUA + GCCoreUtil.translate((String)"gui.module.list"));
        if (Keyboard.isKeyDown((int)FMLClientHandler.instance().getClient().gameSettings.keyBindSneak.getKeyCode())) {
            for (ItemModule s : GSUtils.getListModule()) {
                if (s.getEquipmentSlot() != -1 && s.getEquipmentSlot() != this.armorType || !par1ItemStack.getTagCompound().hasKey(s.getName())) continue;
                par2List.addAll(FMLClientHandler.instance().getClient().fontRenderer.listFormattedStringToWidth(this.check(par1ItemStack, s.getName()) + GCCoreUtil.translate((String)("gui.module." + s.getName())), 150));
            }
        } else {
            par2List.add(EnumChatFormatting.DARK_AQUA + GCCoreUtil.translateWithFormat((String)"itemDesc.shift.name", (Object[])new Object[]{GameSettings.getKeyDisplayString((int)FMLClientHandler.instance().getClient().gameSettings.keyBindSneak.getKeyCode())}));
        }
        par2List.add("");
        par2List.add(color + EnergyDisplayHelper.getEnergyDisplayS((float)joules) + "/" + EnergyDisplayHelper.getEnergyDisplayS((float)this.getMaxElectricityStored(par1ItemStack)));
    }

    private String check(ItemStack stack, String nbt) {
        if (stack.hasTagCompound() && stack.stackTagCompound.getBoolean(nbt)) {
            return EnumChatFormatting.DARK_GREEN + "-";
        }
        return EnumChatFormatting.DARK_RED + "-";
    }

    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        this.setElectricity(itemStack, 0.0f);
        if (!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if (!stack.getTagCompound().hasKey(mod_count)) {
            stack.getTagCompound().setInteger(mod_count, 1 + this.tier);
        }
        switch (this.getArmorType(stack)) {
            case 3: {
                if (stack.getTagCompound().hasKey(suit_buttons[0])) break;
                stack.getTagCompound().setBoolean(suit_buttons[0], false);
                break;
            }
            case 2: {
                if (stack.getTagCompound().hasKey(suit_buttons[1])) break;
                stack.getTagCompound().setBoolean(suit_buttons[1], false);
                break;
            }
            case 1: {
                if (stack.getTagCompound().hasKey(suit_buttons[2])) break;
                stack.getTagCompound().setBoolean(suit_buttons[2], false);
                break;
            }
            case 0: {
                if (stack.getTagCompound().hasKey(suit_buttons[3])) break;
                stack.getTagCompound().setBoolean(suit_buttons[3], false);
                break;
            }
        }
    }

    private void pressed() {
        Minecraft mc = Minecraft.getMinecraft();
        ItemSpaceArmors.pressedKey[0] = mc.gameSettings.keyBindJump.getIsKeyPressed();
        ItemSpaceArmors.pressedKey[1] = mc.gameSettings.keyBindForward.getIsKeyPressed();
        ItemSpaceArmors.pressedKey[2] = mc.gameSettings.keyBindSneak.getIsKeyPressed();
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (world.isRemote) {
            this.pressed();
        }
        if (itemStack.hasTagCompound()) {
            for (ItemModule modules : GSUtils.getListModule()) {
                if (this.getArmorType(itemStack) != 3 - modules.getEquipmentSlot() || !itemStack.getTagCompound().getBoolean(modules.getName())) continue;
                modules.onUpdate(world, player, itemStack, itemStack.getTagCompound().getBoolean(suit_buttons[3 - this.getArmorType(itemStack)]));
                if (!itemStack.getTagCompound().getBoolean(suit_buttons[3 - this.getArmorType(itemStack)]) || modules.getDischargeCount() <= 0 || !(this.getElectricityStored(itemStack) >= (float)modules.getDischargeCount()) || player.ticksExisted % 20 != 0) continue;
                this.discharge(itemStack, modules.getDischargeCount(), true);
            }
            if (this.getArmorType(itemStack) == 0 && itemStack.getTagCompound().getBoolean(suit_buttons[3])) {
                if (pressedKey[0]) {
                    if (itemStack.stackTagCompound.getBoolean("jump") && this.getElectricityStored(itemStack) >= 5.0f) {
                        if (player.onGround) {
                            this.jumpCharge = 1.0f;
                        }
                        if (player.motionY >= 0.0 && this.jumpCharge > 0.0f && !player.isInWater()) {
                            player.fallDistance = 0.3f;
                            this.discharge(itemStack, 5.0f, true);
                            player.motionY += (double)(this.jumpCharge * 0.25f);
                            this.jumpCharge = (float)((double)this.jumpCharge * 0.4);
                        }
                    } else if (this.jumpCharge < 1.0f) {
                        this.jumpCharge = 0.0f;
                    }
                }
            }
        }
    }

    @Override
    public float getMaxElectricityStored(ItemStack stack) {
        boolean energy = stack.hasTagCompound() && stack.getTagCompound().hasKey("energy");
        switch (this.getArmorType(stack)) {
            case 0: {
                return energy ? 100000.0f : 25000.0f;
            }
            case 1: {
                return energy ? 150000.0f : 50000.0f;
            }
            case 2: {
                return energy ? 250000.0f : 75000.0f;
            }
            case 3: {
                return energy ? 50000.0f : 20000.0f;
            }
        }
        return 10000.0f;
    }

    public int gravityOverrideIfLow(EntityPlayer player) {
        if (player.getCurrentArmor(0) != null && !player.getCurrentArmor(0).hasTagCompound()) {
            player.getCurrentArmor((int)0).stackTagCompound = new NBTTagCompound();
        }
        return player.getCurrentArmor(0) != null && player.getCurrentArmor((int)0).stackTagCompound.getBoolean("gravity") && this.getElectricityStored(player.getCurrentArmor(0)) > 0.0f ? 100 : 0;
    }

    public int gravityOverrideIfHigh(EntityPlayer p) {
        return 0;
    }

    public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source.isUnblockable()) {
            return new ISpecialArmor.ArmorProperties(0, 0.0, 0);
        }
        return new ISpecialArmor.ArmorProperties(0, 0.15, 25);
    }

    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return this.damageReduceAmount;
    }

    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        this.discharge(stack, 5.0f, true);
    }

    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(par1, 1, par1.getMaxDamage()));
    }

    @Override
    public void consumeFuel(ItemStack stack, int fuel) {
        stack.setItemDamage(stack.getItemDamage() + 1);
    }

    @Override
    public void decrementFuel(ItemStack stack) {
        if (stack.getItem() instanceof IItemElectric) {
            ((IItemElectric)stack.getItem()).discharge(stack, 1.0f, true);
        }
    }

    @Override
    public int getFuel(ItemStack stack) {
        return stack.getTagCompound().getInteger("jetpack:fuel");
    }

    @Override
    public boolean isActivated(ItemStack stack) {
        return stack.getTagCompound().getBoolean("jetpack:activated");
    }

    @Override
    public void switchState(ItemStack stack, boolean state) {
        if (stack != null) {
            stack.getTagCompound().setBoolean("jetpack:activated", state);
        }
    }

    @Override
    public int getFireStreams(ItemStack stack) {
        return 2;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Optional.Method(modid="Thaumcraft")
    public boolean showNodes(ItemStack stack, EntityLivingBase arg1) {
        return this.armorType == 0 && stack.hasTagCompound() && stack.stackTagCompound.hasKey("thaumvision");
    }

    @Optional.Method(modid="Thaumcraft")
    public boolean showIngamePopups(ItemStack stack, EntityLivingBase arg1) {
        return this.armorType == 0 && stack.hasTagCompound() && stack.stackTagCompound.hasKey("thaumvision");
    }

    @Optional.Method(modid="Thaumcraft")
    public int getVisDiscount(ItemStack stack, EntityPlayer entityPlayer, Aspect aspect) {
        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("thaumvision")) {
            return 20;
        }
        return 0;
    }

    @Override
    public GSUtils.Module_Type getType(ItemStack stack) {
        return GSUtils.Module_Type.SPACESUIT;
    }

    @Override
    public int getModificationCount(ItemStack stack) {
        return 2;
    }
}

