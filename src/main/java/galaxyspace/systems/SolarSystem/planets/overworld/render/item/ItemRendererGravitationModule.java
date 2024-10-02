package galaxyspace.systems.SolarSystem.planets.overworld.render.item;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ItemRendererGravitationModule implements IItemRenderer{
	
	private static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/gravimodule.obj") ); 
	private static ResourceLocation texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/gravimodule.png");
	
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
    		GL11.glTranslatef(0.5F, 0.2F, 0.5F); 
    		GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F); 
    		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F); 
    		//GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
    		GL11.glScalef(0.5F, 0.5F, 0.5F);
    		break;
    	case EQUIPPED:
    		GL11.glTranslatef(0.5F, 0.5F, 0.5F); 
    		GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F); 
    		GL11.glRotatef(0F, 0.0F, 1.0F, 0.0F); 
    		//GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
    		GL11.glScalef(0.5F, 0.5F, 0.5F);
    		break;
    	case EQUIPPED_FIRST_PERSON:
    		GL11.glTranslatef(0.5F, 0.5F, 0.5F); 
    		GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F); 
    		GL11.glRotatef(-45F, 0.0F, 1.0F, 0.0F); 
    		//GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
    		GL11.glScalef(0.5F, 0.5F, 0.5F);
    		break;
    	case ENTITY:
    		GL11.glTranslatef(0.0F, 1.0F, 0.0F);
    		//GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
    		break;
    	default:
    		break;
        }
	 
	 Minecraft.getMinecraft().renderEngine.bindTexture(texture); 
	 model.renderAll();
	 GL11.glPopMatrix(); 
	}


}
