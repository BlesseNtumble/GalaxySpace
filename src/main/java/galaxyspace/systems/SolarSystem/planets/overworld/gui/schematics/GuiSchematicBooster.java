package galaxyspace.systems.SolarSystem.planets.overworld.gui.schematics;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.ContainerSchematicBooster;
import micdoodle8.mods.galacticraft.api.recipe.ISchematicResultPage;
import micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiSchematicBooster extends GuiContainerGC implements ISchematicResultPage
{
    private static final ResourceLocation SchematicTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/schematics/schematic_clear.png");
    private static final ResourceLocation BoosterTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/schematics/schematic_parts.png");

    private int pageIndex;

    public GuiSchematicBooster(InventoryPlayer par1InventoryPlayer, int x, int y, int z)
    {
        super(new ContainerSchematicBooster(par1InventoryPlayer, x, y, z));
        this.xSize = 191;
        this.ySize = 250;
    }

	@SuppressWarnings("unchecked")
    @Override
    public void initGui()
    {
        super.initGui();
        this.buttonList.clear();
        GuiButton back;
        this.buttonList.add(back = new GuiButton(0, this.width / 2 - 140, this.height / 2 - 30 + 27 - 12, 40, 20, GCCoreUtil.translate("gui.button.back.name")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 140, this.height / 2 - 30 + 27 + 12, 40, 20, GCCoreUtil.translate("gui.button.next.name")));
      
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.enabled)
        {
            switch (par1GuiButton.id)
            {
            case 0:
                SchematicRegistry.flipToLastPage(this.pageIndex);
                break;
            case 1:
                SchematicRegistry.flipToNextPage(this.pageIndex);
                break;
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("item.RocketBooster.name"), 7, -20 + 27, 4210752);
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 25, 221 - 92 + 2 + 27, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;      
    	
        
   	
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.renderEngine.bindTexture(this.SchematicTexture);	        
	        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
	        
	        for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++)
	        {
		        int x = this.inventorySlots.getSlot(i).xDisplayPosition;
		        int y = this.inventorySlots.getSlot(i).yDisplayPosition;

		        if(i > 0) this.drawTexturedModalRect(var5 + x - 2, var6 + y - 2, 192, 0, 20, 21);
		        else this.drawTexturedModalRect(var5 + x - 9, var6 + y - 9, 192, 21, 34, 34);
		        	
		       
	        }
	        
	       this.mc.renderEngine.bindTexture(this.BoosterTexture);
	       this.drawTexturedModalRect(var5 + 125, var6 + 40, 151, 0, 48, 68);        

        
     }

    @Override
    public void setPageIndex(int index)
    {
        this.pageIndex = index;
    }
}

