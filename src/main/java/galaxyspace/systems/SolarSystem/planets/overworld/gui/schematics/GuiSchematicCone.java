package galaxyspace.systems.SolarSystem.planets.overworld.gui.schematics;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.registers.items.GSItems;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.schematics.ContainerSchematicCone;
import micdoodle8.mods.galacticraft.api.recipe.ISchematicResultPage;
import micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GuiSchematicCone extends GuiContainerGC implements ISchematicResultPage
{
    private static final ResourceLocation SchematicTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/schematics/schematic_clear.png");
    private static final ResourceLocation ConeTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/schematics/schematic_parts.png");

    private int pageIndex;

    public GuiSchematicCone(InventoryPlayer par1InventoryPlayer, BlockPos pos)
    {
        super(new ContainerSchematicCone(par1InventoryPlayer, pos));
        this.xSize = 191;
        this.ySize = 250;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.buttonList.clear();
        GuiButton back;
        this.buttonList.add(back = new GuiButton(0, this.width / 2 - 140, this.height / 2 - 30 + 27 - 12, 40, 20, GCCoreUtil.translate("gui.button.back.name")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 140, this.height / 2 - 30 + 27 + 12, 40, 20, GCCoreUtil.translate("gui.button.next.name")));
        back.enabled = true; 
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.enabled)
        {
            switch (par1GuiButton.id)
            {
            case 0:
                SchematicRegistry.flipToLastPage(this, this.pageIndex);
                break;
            case 1:
                SchematicRegistry.flipToNextPage(this, this.pageIndex);
                break;
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	this.fontRenderer.drawString(EnumColor.WHITE + GSItems.ROCKET_PARTS.getItemStackDisplayName(new ItemStack(GSItems.ROCKET_PARTS, 1, 0)), 7, -20 + 27, 4210752);
        this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 25, 221 - 92 + 2 + 27, 4210752);
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
		        int x = this.inventorySlots.getSlot(i).xPos;
		        int y = this.inventorySlots.getSlot(i).yPos;
		        
		       
		        if(i > 0) this.drawTexturedModalRect(var5 + x - 2, var6 + y - 2, 192, 0, 20, 21);
		        else this.drawTexturedModalRect(var5 + x - 9, var6 + y - 9, 192, 21, 34, 34);
		        	
		       
	        }
/*
	        for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++)
	        {
	        	int x = this.inventorySlots.getSlot(i).xPos;
		        int y = this.inventorySlots.getSlot(i).yPos;
	        	this.fontRenderer.drawString(EnumColor.WHITE + "" + this.inventorySlots.getSlot(i).getSlotIndex(), var5 + x + 5, var6 + y + 5, 4210752);
	        }
	        */
	        this.mc.renderEngine.bindTexture(this.ConeTexture);
	        this.drawTexturedModalRect(var5 + 126, var6 + 40, 0, 0, 48, 58);        

        
     }

    @Override
    public void setPageIndex(int index)
    {
        this.pageIndex = index;
    }
}

