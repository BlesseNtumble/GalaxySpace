package galaxyspace.core.client.gui.entity;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.core.prefab.entities.EntityAstroWolf;
import galaxyspace.core.prefab.inventory.ContainerAstroWolf;
import galaxyspace.core.util.GSUtils;
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

        GlStateManager.pushMatrix();

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
}

