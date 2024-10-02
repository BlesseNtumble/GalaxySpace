/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  micdoodle8.mods.galacticraft.api.recipe.ISchematicResultPage
 *  micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry
 *  micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC
 *  micdoodle8.mods.galacticraft.core.util.EnumColor
 *  micdoodle8.mods.galacticraft.core.util.GCCoreUtil
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package galaxyspace.systems.SolarSystem.planets.overworld.gui.schematics;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.ContainerSchematicFins;
import micdoodle8.mods.galacticraft.api.recipe.ISchematicResultPage;
import micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiSchematicFins
extends GuiContainerGC
implements ISchematicResultPage {
    private static final ResourceLocation SchematicTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/schematics/schematic_clear.png");
    private static final ResourceLocation FinsTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/schematics/schematic_parts.png");
    private int pageIndex;

    public GuiSchematicFins(InventoryPlayer par1InventoryPlayer, int x, int y, int z) {
        super((Container)new ContainerSchematicFins(par1InventoryPlayer, x, y, z));
        this.xSize = 191;
        this.ySize = 250;
    }

    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        GuiButton back = new GuiButton(0, this.width / 2 - 140, this.height / 2 - 30 + 27 - 12, 40, 20, GCCoreUtil.translate((String)"gui.button.back.name"));
        this.buttonList.add(back);
        this.buttonList.add(new GuiButton(1, this.width / 2 - 140, this.height / 2 - 30 + 27 + 12, 40, 20, GCCoreUtil.translate((String)"gui.button.next.name")));
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            switch (par1GuiButton.id) {
                case 0: {
                    SchematicRegistry.flipToLastPage((int)this.pageIndex);
                    break;
                }
                case 1: {
                    SchematicRegistry.flipToNextPage((int)this.pageIndex);
                }
            }
        }
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate((String)"item.RocketStabilizer.name"), 7, 7, 0x404040);
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate((String)"container.inventory"), 25, 158, 0x404040);
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.renderEngine.bindTexture(SchematicTexture);
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        for (int i = 0; i < this.inventorySlots.inventorySlots.size(); ++i) {
            int x = this.inventorySlots.getSlot((int)i).xDisplayPosition;
            int y = this.inventorySlots.getSlot((int)i).yDisplayPosition;
            if (i > 0) {
                this.drawTexturedModalRect(var5 + x - 2, var6 + y - 2, 192, 0, 20, 21);
                continue;
            }
            this.drawTexturedModalRect(var5 + x - 9, var6 + y - 9, 192, 21, 34, 34);
        }
        this.mc.renderEngine.bindTexture(FinsTexture);
        this.drawTexturedModalRect(var5 + 126, var6 + 37, 199, 0, 52, 64);
    }

    public void setPageIndex(int index) {
        this.pageIndex = index;
    }
}

