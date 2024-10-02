package galaxyspace.systems.SolarSystem.planets.overworld.render.item;

import org.lwjgl.opengl.GL11;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.planets.overworld.models.ModelJetPack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ItemRendererJetPack implements IItemRenderer
{
		private ModelJetPack model;
		
		public static final IModelCustom modelJet = AdvancedModelLoader.loadModel(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/jetpack1.obj"));
		public static final ResourceLocation textureJet = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/armor/jetpack1.png");
		

		public ItemRendererJetPack() 
		{
            this.model = new ModelJetPack();
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
	        		GL11.glTranslatef(-0.1F, 0.1F, 0.5F); 
	        		GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F); 
	        		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F); 
	        		GL11.glRotatef(0F, 1.0F, 0.0F, 0.0F);
	        		GL11.glScalef(1.2F, 1.2F, 1.2F);
	        		break;
	        	case EQUIPPED:
	        		GL11.glTranslatef(1.0F, 0.0F, -0.1F); 
	        		GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F); 
	        		GL11.glRotatef(0F, 0.0F, 1.0F, 0.0F); 
	        		GL11.glRotatef(-45F, 1.0F, 0.0F, 0.0F);
	        		GL11.glScalef(1.5F, 1.5F, 1.5F);
	        		break;
	        	case EQUIPPED_FIRST_PERSON:
	        		GL11.glTranslatef(0.5F, 1.5F, 0.5F); 
	        		GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F); 
	        		GL11.glRotatef(-85F, 0.0F, 1.0F, 0.0F); 
	        		GL11.glRotatef(0F, 1.0F, 0.0F, 0.0F);
	        		GL11.glScalef(1.0F, 1.0F, 1.0F);
	        		break;
	        	case ENTITY:
	        		GL11.glTranslatef(0.0F, 1.0F, 0.0F);	        		
	        		break;
	        	default:
	        		break;
	        }
		 
		 //ResourceLocation textures = (new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/model/armor/jetpack.png")); 
		 //Minecraft.getMinecraft().renderEngine.bindTexture(textures); 
		 //this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		 Minecraft.getMinecraft().renderEngine.bindTexture(textureJet);
		 modelJet.renderPart("wing1");
		 modelJet.renderPart("wing2");
		 modelJet.renderPart("corp");
		 GL11.glPopMatrix(); 
		 } 

}