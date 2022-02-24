package galaxyspace.systems.SolarSystem.planets.overworld.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.GSItems;
import galaxyspace.core.client.gui.tile.GuiTileBase;
import galaxyspace.core.util.GSUtils;
import galaxyspace.systems.SolarSystem.planets.overworld.inventory.ContainerAdvCircuitFabricator;
import galaxyspace.systems.SolarSystem.planets.overworld.tile.TileEntityAdvCircuitFabricator;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAdvCircuitFabricator extends GuiTileBase
{
   
	private static final ResourceLocation circuitFabricatorTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/circuit_fabricator.png");
    private TileEntityAdvCircuitFabricator tileEntity;
    private GuiElementInfoRegion processInfoRegion = new GuiElementInfoRegion(0, 0, 53, 12, null, 0, 0, this);

    public GuiAdvCircuitFabricator(InventoryPlayer par1InventoryPlayer, TileEntityAdvCircuitFabricator tileEntity)
    {
        super(new ContainerAdvCircuitFabricator(par1InventoryPlayer, tileEntity), 2, 1);
        this.tileEntity = tileEntity;
        this.ySize = 195;
        this.header = 3;
        moduleInfoX = this.inventorySlots.getSlotFromInventory(tileEntity, 7).xPos;
		moduleInfoY = this.inventorySlots.getSlotFromInventory(tileEntity, 7).yPos;
		moduleList = new ItemStack[] {new ItemStack(GSItems.UPGRADES, 1, 2), new ItemStack(GSItems.UPGRADES, 1, 3)};
    }
    
    @Override
    public void initGui()
    {
        super.initGui();

        List<String> desc = new ArrayList<String>();
        desc.add(GCCoreUtil.translate("gui.battery_slot.desc.0"));
        desc.add(GCCoreUtil.translate("gui.battery_slot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 5, (this.height - this.ySize) / 2 + 68, 18, 18, desc, this.width, this.height, this));
        this.processInfoRegion.tooltipStrings = new ArrayList<String>();
        this.processInfoRegion.xPosition = (this.width - this.xSize) / 2 + 87;
        this.processInfoRegion.yPosition = (this.height - this.ySize) / 2 + 19;
        this.processInfoRegion.parentWidth = this.width;
        this.processInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.processInfoRegion);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	super.drawGuiContainerForegroundLayer(par1, par2);
        String displayText;

        if (this.tileEntity.processTicks > 0)
        {
            displayText = EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.status.running.name");
        }
        else
        {
            displayText = EnumColor.ORANGE + GCCoreUtil.translate("gui.status.idle.name");
        }

        String str = GCCoreUtil.translate("gui.message.status.name") + ":";
        this.fontRenderer.drawString(EnumColor.WHITE + str, 115 - this.fontRenderer.getStringWidth(str) / 2, 90, 4210752);
        displayText = this.tileEntity.getGUIstatus(displayText, null, true);
        this.fontRenderer.drawString(displayText, 115 - this.fontRenderer.getStringWidth(displayText) / 2, 100, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        
        this.drawTexturedModalRect(containerWidth + 85, containerHeight + 22, 184, 47, 58, 9);
        this.mc.renderEngine.bindTexture(circuitFabricatorTexture);
        this.drawTexturedModalRect(containerWidth + 30, containerHeight + 22, 0, 0, 131, 61);      
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(this.getTexture());        
        
        int scale = 0;
        if (this.tileEntity.processTicks > 0)
        {
            scale = (this.tileEntity.processTicks * 100) / this.tileEntity.getProcessTimeRequired();
        }       

        List<String> processDesc = new ArrayList<String>();
        processDesc.clear();
        processDesc.add(GCCoreUtil.translate("gui.electric_compressor.desc.0") + ": " + scale + "%");
        this.processInfoRegion.tooltipStrings = processDesc;

        if (this.tileEntity.processTicks > 0)
        {
        	this.mc.renderEngine.bindTexture(circuitFabricatorTexture);
            scale = (this.tileEntity.processTicks * 52) / this.tileEntity.getProcessTimeRequired();
            this.drawTexturedModalRect(containerWidth + 86, containerHeight + 23, 192, 1 + this.tileEntity.processTicks % 9 / 3 * 10, scale, 7);
        }
        
        this.mc.renderEngine.bindTexture(this.getTexture());     
        //Energy
        this.renderEnergyBar(containerWidth + 5, containerHeight + 92, this.tileEntity.getScaledElecticalLevel(54), this.tileEntity.getEnergyStoredGC(), this.tileEntity.getMaxEnergyStoredGC());
        
        GSUtils.renderItemIntoSlot(this.mc.getRenderItem(), new ItemStack(Items.DIAMOND), containerWidth + this.inventorySlots.getSlot(1).xPos, containerHeight + this.inventorySlots.getSlot(1).yPos, new Vec3d(0.4D, 0.4D, 0.4D));
        GSUtils.renderItemIntoSlot(this.mc.getRenderItem(), new ItemStack(GCItems.basicItem, 1, 2), containerWidth + this.inventorySlots.getSlot(2).xPos, containerHeight + this.inventorySlots.getSlot(2).yPos, new Vec3d(0.4D, 0.4D, 0.4D));
        GSUtils.renderItemIntoSlot(this.mc.getRenderItem(), new ItemStack(GCItems.basicItem, 1, 2), containerWidth + this.inventorySlots.getSlot(3).xPos, containerHeight + this.inventorySlots.getSlot(3).yPos, new Vec3d(0.4D, 0.4D, 0.4D));
        GSUtils.renderItemIntoSlot(this.mc.getRenderItem(), new ItemStack(Items.REDSTONE), containerWidth + this.inventorySlots.getSlot(4).xPos, containerHeight + this.inventorySlots.getSlot(4).yPos, new Vec3d(0.4D, 0.4D, 0.4D));
        
        
        GSUtils.renderItemIntoSlot(this.mc.getRenderItem(), new ItemStack(Blocks.AIR), containerWidth + this.inventorySlots.getSlot(5).xPos, containerHeight + this.inventorySlots.getSlot(5).yPos, new Vec3d(1.0D, 1.0D, 1.0D));
        
        if(GalaxySpace.debug) GSUtils.renderDebugGui(this, containerWidth, containerHeight);
    }

	@Override
	protected boolean isModuleSupport() {
		return true;
	}

	@Override
	protected String getName() {
		return tileEntity.getName();
	}

	@Override
	protected Slot getBatterySlot() {
		return inventorySlots.getSlotFromInventory(tileEntity, 0);
	}
}
