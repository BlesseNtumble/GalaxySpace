package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerFuelGenerator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityFuelGenerator;
import micdoodle8.mods.galacticraft.core.client.gui.container.GuiContainerGC;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

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
    @SuppressWarnings("unchecked")
    @Override
    public void initGui()
    {
        super.initGui();
        List<String> fuelTankDesc = new ArrayList<String>();
        int fuelLevel = this.tileEntity.fuelTank != null && this.tileEntity.fuelTank.getFluid() != null ? this.tileEntity.fuelTank.getFluid().amount : 0;
        int fuelCapacity = this.tileEntity.fuelTank != null ? this.tileEntity.fuelTank.getCapacity() : 0;
        fuelTankDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.message.fuel.name") + ": " + fuelLevel + " / " + fuelCapacity);
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
    	 
    	this.fontRendererObj.drawString(EnumColor.WHITE + this.tileEntity.getInventoryName(), 100 - (this.fontRendererObj.getStringWidth(this.tileEntity.getInventoryName()) / 2), 4, 4210752);
          
        String displayText = EnumColor.WHITE + GCCoreUtil.translate("gui.status.generating.name");
        this.fontRendererObj.drawString(displayText, 122 - this.fontRendererObj.getStringWidth(displayText) / 2, 33, 4210752);

        if (this.tileEntity.heatGJperTick <= 0)
        {
            displayText = EnumColor.WHITE + GCCoreUtil.translate("gui.status.notGenerating.name");
        }
        else if (this.tileEntity.heatGJperTick < TileEntityFuelGenerator.MIN_GENERATE_GJ_PER_TICK)
        {
            displayText = EnumColor.WHITE + GCCoreUtil.translate("gui.status.hullHeat.name") + ": " + (int) (this.tileEntity.heatGJperTick / TileEntityFuelGenerator.MIN_GENERATE_GJ_PER_TICK * 100) + "%";
        }
        else
        {
            displayText = EnergyDisplayHelper.getEnergyDisplayS(this.tileEntity.heatGJperTick - TileEntityFuelGenerator.MIN_GENERATE_GJ_PER_TICK) + "/t";
        }
        //if (this.tileEntity.lavaTank.getFluid() == null || this.tileEntity.lavaTank.getFluidAmount() == 0)
        if (this.tileEntity.fuelTank.getFluid() == null && this.tileEntity.heatGJperTick <= 0)
        {
            displayText = EnumColor.RED + GCCoreUtil.translate("gui.status.nofuelgenerator.name");
        }

        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("gui.message.status.name") + ": " + displayText, 72, 45 + 23 + yOffset, 4210752);
        //this.fontRendererObj.drawString(displayText, 122 - this.fontRendererObj.getStringWidth(displayText) / 2, 45, 4210752);
        //		displayText = "Voltage: " + (int) (this.tileEntity.getVoltage() * 1000.0F);
        //		this.fontRendererObj.drawString(displayText, 122 - this.fontRendererObj.getStringWidth(displayText) / 2, 60, 4210752);
        this.fontRendererObj.drawString(EnumColor.WHITE + GCCoreUtil.translate("container.inventory"), 14, this.ySize - 93 + 2, 4210752);
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
        if(fuel != null) this.drawTexturedModalRect((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 12 + 54 - fuelgraphLevel, fuel.getFluid() == FluidRegistry.LAVA ? 176 : 192, 38 - fuelgraphLevel, 16, fuelgraphLevel);

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
        	if(fuel.getFluid() == FluidRegistry.LAVA) fuelTankDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.message.lava.name") + ": " + fuelLevel + " / " + fuelCapacity);
        	else fuelTankDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.message.fuel.name") + ": " + fuelLevel + " / " + fuelCapacity);
        }
        this.fuelTankRegion.tooltipStrings = fuelTankDesc;

    }
    
}
