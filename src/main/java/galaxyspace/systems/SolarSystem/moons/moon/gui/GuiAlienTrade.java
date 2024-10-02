/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC
 *  micdoodle8.mods.galacticraft.core.util.EnumColor
 *  micdoodle8.mods.galacticraft.core.util.GCCoreUtil
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package galaxyspace.systems.SolarSystem.moons.moon.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.moons.moon.inventory.ContainerAlienTrade;
import galaxyspace.systems.SolarSystem.moons.moon.recipe.AlienRecipes;
import java.util.ArrayList;
import java.util.List;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class GuiAlienTrade
extends GuiContainerGC {
    private GuiButton next;
    private GuiButton prev;
    private static final ResourceLocation batteryBoxTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/base_gui.png");
    int page = 0;

    public GuiAlienTrade(InventoryPlayer par1InventoryPlayer) {
        super((Container)new ContainerAlienTrade(par1InventoryPlayer));
        this.ySize = 220;
    }

    public void initGui() {
        super.initGui();
        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        this.next = new GuiButton(1, containerWidth + 80, containerHeight + 24, 10, 20, ">");
        this.buttonList.add(this.next);
        this.prev = new GuiButton(2, containerWidth + 10, containerHeight + 24, 10, 20, "<");
        this.buttonList.add(this.prev);
        this.next.enabled = false;
    }

    public void updateScreen() {
        super.updateScreen();
        this.next.enabled = this.page < AlienRecipes.getInstance().getRecipes().size() - 1;
        this.prev.enabled = this.page > 0;
    }

    protected void actionPerformed(GuiButton button) {
        if (button == this.next) {
            ++this.page;
        }
        if (button == this.prev) {
            --this.page;
        }
    }

    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);
        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        GL11.glPushMatrix();
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        List<AlienRecipes.TradeRecipe> map = AlienRecipes.getInstance().getRecipes();
        for (AlienRecipes.TradeRecipe entry : map) {
            list.add(entry.getResult());
        }
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable((int)2896);
        GL11.glEnable((int)32826);
        GL11.glEnable((int)2903);
        GL11.glEnable((int)2896);
        ItemStack result = (ItemStack)list.get(this.page);
        ItemStack[] components = AlienRecipes.getInstance().getComponents(result);
        GuiAlienTrade.itemRender.zLevel = 100.0f;
        itemRender.renderItemIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), components[0], containerWidth + 30, containerHeight + 24);
        itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), components[0], containerWidth + 30, containerHeight + 24);
        if (components[1] != null) {
            itemRender.renderItemIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), components[1], containerWidth + 52, containerHeight + 24);
            itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), components[1], containerWidth + 52, containerHeight + 24);
        }
        itemRender.renderItemIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), result, containerWidth + 135, containerHeight + 24);
        itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), result, containerWidth + 135, containerHeight + 24);
        GuiAlienTrade.itemRender.zLevel = 0.0f;
        if (this.func_146978_c(30, 24, 16, 16, par1, par2)) {
            this.renderToolTip(components[0], par1, par2);
        } else if (components[1] != null && this.func_146978_c(52, 24, 16, 16, par1, par2)) {
            this.renderToolTip(components[1], par1, par2);
        } else if (this.func_146978_c(135, 24, 16, 16, par1, par2)) {
            this.renderToolTip(result, par1, par2);
        }
        GL11.glDisable((int)2896);
        GL11.glPopMatrix();
        RenderHelper.enableStandardItemLighting();
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate((String)"gui.trade"), this.xSize / 2 - this.fontRendererObj.getStringWidth(GCCoreUtil.translate((String)"gui.trade")) / 2, 1, 0x404040);
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        this.mc.renderEngine.bindTexture(batteryBoxTexture);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(containerWidth + 88, containerHeight + 44, 192, 108, 36, 17);
        for (int i = 0; i < this.inventorySlots.inventorySlots.size(); ++i) {
            int x = this.inventorySlots.getSlot((int)i).xDisplayPosition;
            int y = this.inventorySlots.getSlot((int)i).yDisplayPosition;
            GL11.glPushMatrix();
            switch (i) {
                default: 
            }
            this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 192, 26, 20, 21);
            GL11.glPopMatrix();
        }
    }
}

