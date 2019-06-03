package galaxyspace.systems.SolarSystem.planets.overworld.render.item;

import java.util.function.Function;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableList;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.models.ModelOBJArmor;
import micdoodle8.mods.galacticraft.core.client.model.OBJLoaderGC;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.client.model.obj.OBJModel.OBJBakedModel;

public  class ItemSpaceSuitModel extends ModelOBJArmor {

	public OBJBakedModel G;
	public OBJBakedModel Gru;
	public OBJBakedModel P2;
	public OBJBakedModel L2;
	public OBJBakedModel P3;
	public OBJBakedModel L3;
	
	public OBJBakedModel wing1, wing2, corp;

	private final int partType;
	
	public float color[] = new float[3];
	
	private void updateModels()
    {
		if (this.G == null)
        {
			try
            {
				
				IModel model = OBJLoaderGC.instance.loadModel(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/spacesuit.obj"));
				Function<ResourceLocation, TextureAtlasSprite> spriteFunction = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());

                G = (OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("G"), false), DefaultVertexFormats.ITEM, spriteFunction);
                Gru = (OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("Gru"), false), DefaultVertexFormats.ITEM, spriteFunction);
                // arms
                P2 = (OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("P2"), false), DefaultVertexFormats.ITEM, spriteFunction);
                L2 = (OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("L2"), false), DefaultVertexFormats.ITEM, spriteFunction);
                // legs
                P3 = (OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("P3"), false), DefaultVertexFormats.ITEM, spriteFunction);
                L3 = (OBJBakedModel) model.bake(new OBJModel.OBJState(ImmutableList.of("L3"), false), DefaultVertexFormats.ITEM, spriteFunction);
            
                IModel jetpackmodel = OBJLoaderGC.instance.loadModel(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "models/jetpack.obj"));
                Function<ResourceLocation, TextureAtlasSprite> jetspriteFunction = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());

                wing1 = (OBJBakedModel) jetpackmodel.bake(new OBJModel.OBJState(ImmutableList.of("wing1"), false), DefaultVertexFormats.ITEM, jetspriteFunction);
                wing2 = (OBJBakedModel) jetpackmodel.bake(new OBJModel.OBJState(ImmutableList.of("wing2"), false), DefaultVertexFormats.ITEM, jetspriteFunction);
                corp = (OBJBakedModel) jetpackmodel.bake(new OBJModel.OBJState(ImmutableList.of("corp"), false), DefaultVertexFormats.ITEM, jetspriteFunction);
                
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
            
        }
    }
	/**armorType: 0 - head, 1 - body and arms, 2 - legs, 3 - feet.**/
	public ItemSpaceSuitModel(int armorType) {
		partType = armorType;

		color[0] = 1.0F;
		color[1] = 1.0F;
		color[2] = 1.0F;
		
		this.updateModels();
	}

	@Override
	public void pre() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
	}

	@Override
	public void post() {
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public void partHead(Entity entity) {
		if (partType == 3) {
			if (Minecraft.getMinecraft().player.getActivePotionEffect(MobEffects.INVISIBILITY) != null) return;
			
			GL11.glTranslatef(0F, -1.9F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			/*if(entity instanceof EntityArmorStand) 
			{
				GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
			}*/
			GL11.glScalef(0.75F, 0.78F, 0.75F);
			//Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			GL11.glColor3f(color[0], color[1], color[2]);
			ClientUtil.drawBakedModel(G);
			
			
		}
	}

	@Override
	public void partBody() {
		if (partType == 2 || partType == 5) {
			if (Minecraft.getMinecraft().player.getActivePotionEffect(MobEffects.INVISIBILITY) != null) return;
			
			GL11.glTranslatef(0F, -1.75F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			//Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			GL11.glColor3f(color[0], color[1], color[2]);
			ClientUtil.drawBakedModel(Gru);
		}
		
		if(partType == 5 || partType == 6)
		{
		
			if(partType == 5) GL11.glTranslatef(0F, 1.9F, 0F);
			else GL11.glTranslatef(0F, -0.3F, -0.7F);
			//Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			ClientUtil.drawBakedModel(corp);
			ClientUtil.drawBakedModel(wing2);
			GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
			ClientUtil.drawBakedModel(wing1);
			
			
		//	Minecraft.getMinecraft().renderEngine.bindTexture(textureJet);
		//	modelJet.renderPart("wing1");
		//	modelJet.renderPart("wing2");
		//	modelJet.renderPart("corp");
		}
			
		
	}

	@Override
	public void partRightArm() {
		if (partType == 2 || partType == 5) {
			if (Minecraft.getMinecraft().player.getActivePotionEffect(MobEffects.INVISIBILITY) != null) return;
			
			GL11.glTranslatef(0.375F, -1.6F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			//Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			GL11.glColor3f(color[0], color[1], color[2]);
			ClientUtil.drawBakedModel(P2);
		}
	}

	@Override
	public void partLeftArm() {
		if (partType == 2 || partType == 5) {
			if (Minecraft.getMinecraft().player.getActivePotionEffect(MobEffects.INVISIBILITY) != null) return;
			
			GL11.glTranslatef(-0.375F, -1.6F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			//Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			GL11.glColor3f(color[0], color[1], color[2]);
			ClientUtil.drawBakedModel(L2);
		}
	}

	@Override
	public void partRightLeg() {
		if (partType == 1) {
			if (Minecraft.getMinecraft().player.getActivePotionEffect(MobEffects.INVISIBILITY) != null) return;
			
			GL11.glTranslatef(0.125F, -0.75F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			//Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			GL11.glColor3f(color[0], color[1], color[2]);
			ClientUtil.drawBakedModel(P3);
		}
	}

	@Override
	public void partLeftLeg() {
		if (partType == 1) {
			if (Minecraft.getMinecraft().player.getActivePotionEffect(MobEffects.INVISIBILITY) != null) return;
			
			GL11.glTranslatef(-0.125F, -0.75F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			//Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			GL11.glColor3f(color[0], color[1], color[2]);
			ClientUtil.drawBakedModel(L3);
		}
	}
}