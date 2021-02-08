package galaxyspace.core.client.gui.entity;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.entities.EntityAstroWolf;
import galaxyspace.core.prefab.inventory.ContainerAstroWolf;
import galaxyspace.core.util.GSUtils;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAstroWolfInventory extends GuiContainer
{
	//private static final Minecraft mc = Minecraft.getMinecraft();
	
    private static final ResourceLocation wolfGui = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/wolf_inventory.png");
    private final EntityAstroWolf wolf;

    private int invX;
    private int invY;
    private final int invWidth = 18;
    private final int invHeight = 18;
    /** The mouse x-position recorded during the last rendered frame. */
    private float mousePosx;
    /** The mouse y-position recorded during the last renderered frame. */
    private float mousePosY;

    public GuiAstroWolfInventory(EntityPlayer player, EntityAstroWolf wolf)
    {
        super(new ContainerAstroWolf(player.inventory, wolf, player));
        this.wolf = wolf;
        this.xSize = 176;
        this.ySize = 210;
        
        
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.buttonList.clear();
        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;
        this.invX = var5 + 151;
        this.invY = var6 + 108;     
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
    	this.drawDefaultBackground();
    	this.mousePosx = (float)mouseX;
        this.mousePosY = (float)mouseY;
    	super.drawScreen(mouseX, mouseY, partialTicks);
    	this.renderHoveredToolTip(mouseX, mouseY);
    }
    
    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	GlStateManager.color(1.0F, 1.0F, 1.0F);
        final int containerWidth = (this.width - this.xSize) / 2;
        final int containerHeight = (this.height - this.ySize) / 2;
        this.mc.renderEngine.bindTexture(wolfGui);
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
        this.drawString(fontRenderer, GCCoreUtil.translate("entity.astro_wolf.name"), containerWidth + 35, containerHeight + 3, 0xFFFFFF);
 
        this.drawString(fontRenderer, GCCoreUtil.translate("item.oxygen_mask.name") + ": " + localeBoolean(!this.wolf.wolfInventory.getStackInSlot(0).isEmpty()), containerWidth + 80, containerHeight + 20, 0xFFFFFF);
        this.drawString(fontRenderer, GCCoreUtil.translate("gui.message.thermal_status.name").substring(0, 7) + ": " + localeBoolean(!this.wolf.wolfInventory.getStackInSlot(1).isEmpty()), containerWidth + 80, containerHeight + 30, 0xFFFFFF);
        
        String oxygen = "";
        if(!this.wolf.wolfInventory.getStackInSlot(2).isEmpty())
        	oxygen = GCCoreUtil.translate("gui.oxygen_storage.desc.1") + ": " + EnumColor.BRIGHT_GREEN + (this.wolf.wolfInventory.getStackInSlot(2).getMaxDamage() - this.wolf.wolfInventory.getStackInSlot(2).getItemDamage());
        else
        	oxygen = GCCoreUtil.translate("gui.oxygen_storage.desc.1") + ": " + EnumColor.DARK_RED + localeBoolean(false);
        	
        this.drawString(fontRenderer, oxygen, containerWidth + 80, containerHeight + 40, 0xFFFFFF);
        
        this.drawString(fontRenderer, this.wolf.getHealth() + "/" + this.wolf.getMaxHealth(), containerWidth + 29, containerHeight + 20, 0xFFFFFF);
        
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GuiInventory.drawEntityOnScreen(containerWidth + 51, containerHeight + 60, 25, (float)(containerWidth + 51) - this.mousePosx, (float)(containerHeight + 75 - 50) - this.mousePosY, this.wolf);
    
        // Slots       
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(wolfGui);
        for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++)
        {
	        int x = this.inventorySlots.getSlot(i).xPos;
	        int y = this.inventorySlots.getSlot(i).yPos;
	        
	        GL11.glPushMatrix();
	        	if(!(this.inventorySlots.getSlot(i).inventory instanceof InventoryPlayer))
	        		this.drawTexturedModalRect(containerWidth + x - 1, containerHeight + y - 1, 176, 0, 18, 18);	        	
	        GL11.glPopMatrix();
        }
        GSUtils.renderDebugGui(this, containerWidth, containerHeight);
        GlStateManager.popMatrix();
    }
    
    private String localeBoolean(boolean bol)
	{
		return bol ? EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.message.yes") : EnumColor.RED + GCCoreUtil.translate("gui.message.no");		
	}
}

