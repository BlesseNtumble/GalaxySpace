package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerPortableNuclearReactor;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityPortableNuclearReactor;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

@SideOnly(Side.CLIENT)
public class GuiPortableNuclearReactor extends GuiContainerGC
{
    private static final ResourceLocation fuelGeneratorTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/base_gui.png");
    private GuiElementInfoRegion waterTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    private TileEntityPortableNuclearReactor tileEntity;

    public GuiPortableNuclearReactor(InventoryPlayer par1InventoryPlayer, TileEntityPortableNuclearReactor tileEntity)
    {
        super(new ContainerPortableNuclearReactor(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        this.xSize = 173;
        this.ySize = 205;
    }
    @SuppressWarnings("unchecked")
    @Override
    public void initGui()
    {
        super.initGui();

        this.waterTankRegion.xPosition = (this.width - this.xSize) / 2 + 7;
        this.waterTankRegion.yPosition = (this.height - this.ySize) / 2 + 42;
        this.waterTankRegion.parentWidth = this.width;
        this.waterTankRegion.parentHeight = this.height;
        this.infoRegions.add(this.waterTankRegion);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	int yOffset = -18;
    	 
    	this.fontRendererObj.drawString(EnumColor.WHITE + this.tileEntity.getInventoryName(), 86 - (this.fontRendererObj.getStringWidth(this.tileEntity.getInventoryName()) / 2), 1, 4210752);
          
        String displayText = EnumColor.WHITE + GCCoreUtil.translate("gui.status.generating.name");

        String displayText1 = EnumColor.WHITE + "" + EnergyDisplayHelper.getEnergyDisplayS(this.tileEntity.heatGJperTick - TileEntityPortableNuclearReactor.MIN_GENERATE_GJ_PER_TICK + 1) + "/t";
      
        this.fontRendererObj.drawString(displayText1, 96 - (2 * displayText1.length()), 66 + yOffset, 4210752);
       // this.fontRendererObj.drawString(EnumColor.WHITE + "gJ/t", 75, 76 + yOffset, 4210752);
        
        if (this.tileEntity.waterTank.getFluid() == null && this.tileEntity.heatGJperTick <= 0)
        {
        	displayText = EnumColor.RED + GCCoreUtil.translate("gui.status.nowatergenerator.name");
        }

        if (this.tileEntity.itemCookTime <= 0)
        {
        	displayText = EnumColor.RED + GCCoreUtil.translate("gui.status.nofuelgenerator.name");
        }

        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("gui.message.status.name") + ": " + displayText, 5, 120 + yOffset, 4210752);
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 14, this.ySize - 93 + 5, 4210752);
        
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("gui.message.temperature") + " " + (this.tileEntity.heatTick > 450 ? EnumColor.RED : EnumColor.BRIGHT_GREEN) + this.tileEntity.heatTick + EnumColor.WHITE + " C", 56, 53 + 5, 4210752);
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("gui.message.cooling") + ": " + (this.tileEntity.waterTank.getFluidAmount() <= 0 ? EnumColor.RED + GCCoreUtil.translate("gui.message.no") : EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.message.yes")), 56, 68, 4210752);
       
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("gui.message.fuel.name") + ": " + this.tileEntity.itemCookTime, 56, 78, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.renderEngine.bindTexture(GuiPortableNuclearReactor.fuelGeneratorTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
        
        FluidStack water = this.tileEntity.waterTank.getFluid();

        //Tanks       
		this.drawTexturedModalRect(containerWidth + 5, containerHeight + 40, 192, 66, 20, 42);
	
		//Arrows
		/*this.drawTexturedModalRect(containerWidth + 35, containerHeight + 43, 202, 108, 26, 16);
		this.drawTexturedModalRect(containerWidth + 100, containerHeight + 43, 182, 141, 36, 16);
		if(this.tileEntity.heatGJperTick > 0)
		{
			this.drawTexturedModalRect(containerWidth + 35, containerHeight + 43, 202, 125, 26, 16);
			this.drawTexturedModalRect(containerWidth + 100, containerHeight + 43, 182, 158, 36, 16);
		}*/

		GL11.glPushMatrix();		
	        final int watergraphLevel = this.tileEntity.getScaledWaterLevel(38);
	        if(water != null)
	        {
	        	GL11.glPushMatrix();	        
	 	        GSUtils.drawFluid(water, (this.width - this.xSize) / 2 + 7, this.height / 2 - 60, 16, 38, this.tileEntity.waterTank.getCapacity());
	 			GL11.glPopMatrix();
	        }	      
        GL11.glPopMatrix();
        		
        this.mc.renderEngine.bindTexture(this.fuelGeneratorTexture);
        this.drawTexturedModalRect(containerWidth + 5, containerHeight + 40, 192+20, 66, 20, 42);
        
        // Slots
        for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++)
        {
	        int x = this.inventorySlots.getSlot(i).xDisplayPosition;
	        int y = this.inventorySlots.getSlot(i).yDisplayPosition;
	        
	       /* if(!(this.inventorySlots.getSlot(i).inventory instanceof InventoryPlayer))
	        {*/
		        
		        GL11.glPushMatrix();
		        switch(i)
		        {
		        	case 0:
		        	{
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 192, 26, 20, 21);	        		 
		        		break;
		        	}	 
		        	case 2:
		        	case 3:
		        	case 4:
		        	case 5:
		        	{
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 213, 26, 20, 21);	        		 
		        		break;
		        	}
		        	default: 
		        	{
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 192, 26, 20, 21);
		        		break;
		        	}	        	
		        }
		        GL11.glPopMatrix();
	        //}
        }
        
        List<String> waterTankDesc = new ArrayList<String>();
        int waterLevel = this.tileEntity.waterTank != null && this.tileEntity.waterTank.getFluid() != null ? this.tileEntity.waterTank.getFluid().amount : 0;
        int waterCapacity = this.tileEntity.waterTank != null ? this.tileEntity.waterTank.getCapacity() : 0;
        if(water != null) 
        {
        	waterTankDesc.add(EnumColor.YELLOW + water.getLocalizedName() + ": " + waterLevel + " / " + waterCapacity);
        }
        this.waterTankRegion.tooltipStrings = waterTankDesc;
    }
    
}
