package galaxyspace.systems.SolarSystem.planets.overworld.gui.schematics;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.ContainerSchematicTier1Rocket;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.ContainerSchematicTier2Rocket;
import micdoodle8.mods.galacticraft.api.recipe.ISchematicResultPage;
import micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiSchematicTier1Rocket extends GuiContainer implements ISchematicResultPage {

	private static final ResourceLocation SchematicTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/schematics/schematic_clear.png");


	private int pageIndex;

	public GuiSchematicTier1Rocket(InventoryPlayer par1InventoryPlayer, int x, int y, int z)
    {
        super(new ContainerSchematicTier1Rocket(par1InventoryPlayer, x, y, z));
        this.xSize = 191;
        this.ySize = 250;
    }
	
	@SuppressWarnings("unchecked")
    @Override
    public void initGui()
    {
        super.initGui();
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 140, this.height / 2 - 30 + 27 - 12, 40, 20, GCCoreUtil.translate("gui.button.back.name")));
        ((GuiButton)this.buttonList.get(0)).enabled = false;
        this.buttonList.add(new GuiButton(1, this.width / 2 - 140, this.height / 2 - 30 + 27 + 12, 40, 20, GCCoreUtil.translate("gui.button.next.name")));
     
    }
    @Override
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.enabled)
        {
            SchematicRegistry.flipToNextPage(this.pageIndex);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(EnumColor.WHITE + GCItems.rocketTier1.getItemStackDisplayName(new ItemStack(GCItems.rocketTier1, 1, 0)), 7, -20 + 27, 4210752);
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 25, 221 - 92 + 2 + 27, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(this.SchematicTexture);
        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        
        for (int i = 0; i < this.inventorySlots.inventorySlots.size(); i++) {
			int x = this.inventorySlots.getSlot(i).xDisplayPosition;
			int y = this.inventorySlots.getSlot(i).yDisplayPosition;

			if (i == 0)
			{
				this.drawTexturedModalRect(var5 + x - 9, var6 + y - 9, 192, 21, 34, 34);
			}
//			else if (i == 25)
//			{
//				GL11.glPushMatrix();
//				this.drawTexturedModalRect(var5 + x - 9, var6 + y - 9, 192, 21, 34, 34);
//				GL11.glColor4f(0.8F, 0.0F, 1.0F, 1.0F);
//				this.drawTexturedModalRect(var5 + x - 9, var6 + y - 9, 192, 56, 34, 34);
//				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//				GL11.glPopMatrix();
//			}
			else
				this.drawTexturedModalRect(var5 + x - 2, var6 + y - 2, 192, 0, 20, 21);

		}        
    }

    @Override
    public void setPageIndex(int index)
    {
        this.pageIndex = index;
    }
}
