package galaxyspace.systems.SolarSystem.planets.overworld.render.item;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;

public class ItemRendererPlasmaGun implements IItemRenderer
{

		protected IModelCustom gunModelObj;
		protected ModelBiped model;
		
		public ItemRendererPlasmaGun(IModelCustom spaceshipModel) 
		{
			this.gunModelObj = spaceshipModel;
		}
		
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
	        		GL11.glTranslatef(1.5F, 1.2F, 1.5F); 
	        		GL11.glRotatef(-30F, 0.0F, 0.0F, 1.0F); 
	        		GL11.glRotatef(0F, 0.0F, 1.0F, 0.0F); 
	        		GL11.glRotatef(0F, 1.0F, 0.0F, 0.0F);
	        		GL11.glScalef(0.8F, 0.8F, 0.8F);
	        		break;
	        	case EQUIPPED:
	        		GL11.glTranslatef(0.5F, 0.5F, 0.5F); 
	        		GL11.glRotatef(75F, 0.0F, 0.0F, 1.0F); 
	        		GL11.glRotatef(-15F, 0.0F, 1.0F, 0.0F); 
	        		GL11.glRotatef(-50F, 1.0F, 0.0F, 0.0F);
	        		GL11.glScalef(1.5F, 1.5F, 1.5F);
	        		break;
	        	case EQUIPPED_FIRST_PERSON:
	        		GL11.glTranslatef(-0.5F, 1.5F, 0.5F); 
	        		GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F); 
	        		GL11.glRotatef(-145F, 0.0F, 1.0F, 0.0F); 
	        		GL11.glRotatef(0F, 1.0F, 0.0F, 0.0F);
	        		GL11.glScalef(1.0F, 1.0F, 1.0F);
	        		break;
	        	case ENTITY:
	        		GL11.glTranslatef(1.0F, 1.0F, 0.0F);
	        		GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
	        		break;
	        	default:
	        		break;
	        }
		 
		 ResourceLocation textures = (new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/plasmaGun.png")); 
		 Minecraft.getMinecraft().renderEngine.bindTexture(textures); 
		 this.gunModelObj.renderAll();
		 //this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		 GL11.glPopMatrix(); 
		 } 

}

