package galaxyspace.systems.SolarSystem.moons.enceladus.render.items;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.moons.enceladus.models.ModelCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererEnceladusCrystal implements IItemRenderer {
	
	private static final ModelCrystal model = new ModelCrystal(); 
	private static ResourceLocation texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/crystal.png");
	
	 @Override 
	 public boolean handleRenderType(ItemStack is, ItemRenderType type) { 
		 switch (type)
	        {
	        case ENTITY:
	            return true;
	        case EQUIPPED:
	            return true;
	        case EQUIPPED_FIRST_PERSON:
	            return true;
	        case INVENTORY:
	            return true;
	        default:
	            return false;
	        } 
	 } 
	 
	 @Override 
	 public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack is, ItemRendererHelper helper) { 
		 return true; 
	 } 
	 
	 @Override 
	 public void renderItem(ItemRenderType type, ItemStack is, Object... data) { 
	 GL11.glPushMatrix(); 
	 
	 switch (type)
        {
        case INVENTORY:
    		GL11.glTranslatef(0.5F, 2.0F, 0.5F); 
    		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F); 
    		GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F); 
    		//GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
    		GL11.glScalef(1.5F, 1.5F, 1.25F);
    		break;
    	case EQUIPPED:
    		GL11.glTranslatef(0.5F, 2.5F, 0.5F); 
    		GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F); 
    		GL11.glRotatef(0F, 0.0F, 1.0F, 0.0F); 
    		GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
    		GL11.glScalef(1.5F, 1.85F, 1.5F);
    		break;
    	case EQUIPPED_FIRST_PERSON:
    		GL11.glTranslatef(1.5F, 2.5F, 0.5F); 
    		GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F); 
    		GL11.glRotatef(0F, 0.0F, 1.0F, 0.0F); 
    		GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
    		GL11.glScalef(1.5F, 1.85F, 1.5F);
    		break;
    	case ENTITY:
    		GL11.glTranslatef(0.0F, 1.0F, 0.0F);
    		//GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
    		break;
    	default:
    		break;
        }
	 
	 Minecraft.getMinecraft().renderEngine.bindTexture(texture); 
	 this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
     GL11.glPopMatrix(); 
	}


}

