package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSUtils;
import galaxyspace.core.util.GSUtils.FluidType;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerFuelGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityFuelGenerator;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFuelGenerator extends GuiContainerGC
{
    private static final ResourceLocation fuelGeneratorTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/geothermal_generator.png");
    private GuiElementInfoRegion fuelTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height, this);
    private TileEntityFuelGenerator tileEntity;

    public GuiFuelGenerator(InventoryPlayer par1InventoryPlayer, TileEntityFuelGenerator tileEntity)
    {
        super(new ContainerFuelGenerator(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        List<String> fuelTankDesc = new ArrayList<String>();
        int fuelLevel = this.tileEntity.fuelTank != null && this.tileEntity.fuelTank.getFluid() != null ? this.tileEntity.fuelTank.getFluid().amount : 0;
        int fuelCapacity = this.tileEntity.fuelTank != null ? this.tileEntity.fuelTank.getCapacity() : 0;
       
        this.fuelTankRegion.tooltipStrings = fuelTankDesc;
        this.fuelTankRegion.xPosition = (this.width - this.xSize) / 2 + 7;
        this.fuelTankRegion.yPosition = (this.height - this.ySize) / 2 + 28;
        this.fuelTankRegion.parentWidth = this.width;
        this.fuelTankRegion.parentHeight = this.height;
        this.infoRegions.add(this.fuelTankRegion);
    }
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	int yOffset = -18;
    	 
        this.fontRenderer.drawString(EnumColor.WHITE + this.tileEntity.getName(), 100 - (this.fontRenderer.getStringWidth(this.tileEntity.getName()) / 2), 4, 4210752);
        
        String displayText = EnumColor.WHITE + GCCoreUtil.translate("gui.status.generating.name");
        this.fontRenderer.drawString(displayText, 122 - this.fontRenderer.getStringWidth(displayText) / 2, 33, 4210752);

        if (this.tileEntity.heatGJperTick <= 0)
        {
            displayText = EnumColor.WHITE + GCCoreUtil.translate("gui.status.not_generating.name");
        }
        /*else if (this.tileEntity.heatGJperTick < TileEntityFuelGenerator.MIN_GENERATE_GJ_PER_TICK)
        {
            displayText = EnumColor.WHITE + GCCoreUtil.translate("gui.status.hullheat.name") + ": " + (int) (this.tileEntity.heatGJperTick / TileEntityFuelGenerator.MIN_GENERATE_GJ_PER_TICK * 100) + "%";
        }*/
        else
        {
            displayText = EnergyDisplayHelper.getEnergyDisplayS(this.tileEntity.heatGJperTick - TileEntityFuelGenerator.MIN_GENERATE_GJ_PER_TICK) + "/t";
        }
        //if (this.tileEntity.lavaTank.getFluid() == null || this.tileEntity.lavaTank.getFluidAmount() == 0)
        if (this.tileEntity.fuelTank.getFluid() == null && this.tileEntity.heatGJperTick <= 0)
        {
            displayText = EnumColor.RED + GCCoreUtil.translate("gui.status.nofuelgenerator.name");
        }

        this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("gui.message.status.name") + ": " + displayText, 72, 45 + 23 + yOffset, 4210752);
        //this.fontRendererObj.drawString(displayText, 122 - this.fontRendererObj.getStringWidth(displayText) / 2, 45, 4210752);
        //		displayText = "Voltage: " + (int) (this.tileEntity.getVoltage() * 1000.0F);
        //		this.fontRendererObj.drawString(displayText, 122 - this.fontRendererObj.getStringWidth(displayText) / 2, 60, 4210752);
        this.fontRenderer.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 14, this.ySize - 93 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.renderEngine.bindTexture(GuiFuelGenerator.fuelGeneratorTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
        
        FluidStack fuel = this.tileEntity.fuelTank.getFluid();
        
        this.drawTexturedModalRect(containerWidth + 30, containerHeight + 33, 176, 58, 24, 16);       
		this.drawTexturedModalRect(containerWidth + 5, containerHeight + 26, 200, 38, 20, 42);
		
        final int fuelgraphLevel = this.tileEntity.getScaledFuelLevel(38);
        //if(fuel != null) this.drawTexturedModalRect((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 12 + 54 - fuelgraphLevel, fuel.getFluid() == FluidRegistry.LAVA ? 176 : 192, 38 - fuelgraphLevel, 16, fuelgraphLevel);
      
     
        
        if(fuel != null)
        {
        	GL11.glPushMatrix();	
        	displayGauge(7, 8, tileEntity.getScaledFuelLevel(38), tileEntity.fuelTank.getFluid(), 0);
 	        //GSUtils.drawFluid(fluid, (this.width - this.xSize) / 2 + 148, this.height / 2 - 62, 16, 38, this.tileEntity.waterTank.getCapacity());
 			GL11.glPopMatrix();
        }
        
        drawTexturedModalRect(containerWidth + 7, containerHeight + 12, 176, -16, 16, 54);
        
        // Slots
        for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++)
        {
	        int x = this.inventorySlots.getSlot(i).xPos;
	        int y = this.inventorySlots.getSlot(i).yPos;
	        
	       /* if(!(this.inventorySlots.getSlot(i).inventory instanceof InventoryPlayer))
	        {*/
		        
		        GL11.glPushMatrix();
		        switch(i)
		        {
		        	case 0:
		        	{
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 176, 38, 20, 21);	        		 
		        		break;
		        	}	        	
		        	default: 
		        	{
		        		this.drawTexturedModalRect(containerWidth + x - 2, containerHeight + y - 2, 176, 38, 20, 21);
		        		break;
		        	}	        	
		        }
		        GL11.glPopMatrix();
	        //}
        }
        
        List<String> fuelTankDesc = new ArrayList<String>();
        int fuelLevel = this.tileEntity.fuelTank != null && this.tileEntity.fuelTank.getFluid() != null ? this.tileEntity.fuelTank.getFluid().amount : 0;
        int fuelCapacity = this.tileEntity.fuelTank != null ? this.tileEntity.fuelTank.getCapacity() : 0;
        if(fuel != null) 
        {
        	 if(this.tileEntity.fuelTank.getFluid() != null) 
             	fuelTankDesc.add(EnumColor.YELLOW + this.tileEntity.fuelTank.getFluid().getLocalizedName() + ": " + fuelLevel + " / " + fuelCapacity);
            
        	//if(fuel.getFluid() == FluidRegistry.LAVA) fuelTankDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.message.lava.name") + ": " + fuelLevel + " / " + fuelCapacity);
        	//else fuelTankDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.message.fuel.name") + ": " + fuelLevel + " / " + fuelCapacity);
        }
        this.fuelTankRegion.tooltipStrings = fuelTankDesc;

        if(GalaxySpace.debug) GSUtils.renderDebugGui(this, containerWidth, containerHeight);
    }
    
    public void displayGauge(int xPos, int yPos, int scale, FluidStack fluid, int side /*0-left, 1-right*/)
	{
		if(fluid == null)
		{
			return;
		}

		int guiWidth = (width - xSize) / 2;
		int guiHeight = (height - ySize) / 2;

		int start = 0;

		while(true)
		{
			int renderRemaining;

			if(scale > 16)
			{
				renderRemaining = 16;
				scale -= 16;
			}
			else {
				renderRemaining = scale;
				scale = 0;
			}

			mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			drawTexturedModalRect(guiWidth + xPos, guiHeight + yPos + 58 - renderRemaining - start, GSUtils.getFluidTexture(fluid.getFluid(), FluidType.STILL), 16, 16 - (16 - renderRemaining));
			start+=16;

			if(renderRemaining == 0 || scale == 0)
			{
				break;
			}
		}

		mc.renderEngine.bindTexture(this.fuelGeneratorTexture);
		//drawTexturedModalRect(guiWidth + xPos, guiHeight + yPos + 4, 176, -16, 16, 54);
	}
    
}
