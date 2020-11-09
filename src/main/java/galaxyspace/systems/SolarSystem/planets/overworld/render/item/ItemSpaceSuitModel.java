package galaxyspace.systems.SolarSystem.planets.overworld.render.item;

import java.util.function.Function;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableList;

import asmodeuscore.core.client.render.ModelOBJArmor;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.core.client.model.OBJLoaderGC;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.client.model.obj.OBJModel.OBJBakedModel;

public  class ItemSpaceSuitModel extends ModelOBJArmor {

	public static OBJBakedModel G;
	public static OBJBakedModel Gru;
	public static OBJBakedModel P2;
	public static OBJBakedModel L2;
	public static OBJBakedModel P3;
	public static OBJBakedModel L3;
	
	public static OBJBakedModel wing1, wing2, corp;

	private final int partType;
	
	public float color[] = new float[3];
	
	public static int helmetList, bodyList, leftArmList, rightArmList, leftLegList, rightLegList, jetpackList;
	
	static {

		updateModels();
		
		int displayLists = GLAllocation.generateDisplayLists(7);
		helmetList = displayLists;
		bodyList = displayLists + 1;
		leftArmList = displayLists + 2;
		rightArmList = displayLists + 3;
		leftLegList = displayLists + 4;
		rightLegList = displayLists + 5;
		jetpackList = displayLists + 6;
		
		GL11.glPushMatrix();
			GL11.glNewList(helmetList, GL11.GL_COMPILE);
			ClientUtil.drawBakedModel(G);
			GL11.glEndList();
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
			GL11.glNewList(bodyList, GL11.GL_COMPILE);
			ClientUtil.drawBakedModel(Gru);
			GL11.glEndList();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
			GL11.glNewList(leftArmList, GL11.GL_COMPILE);
			ClientUtil.drawBakedModel(L2);
			GL11.glEndList();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
			GL11.glNewList(rightArmList, GL11.GL_COMPILE);
			ClientUtil.drawBakedModel(P2);
			GL11.glEndList();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
			GL11.glNewList(leftLegList, GL11.GL_COMPILE);
			ClientUtil.drawBakedModel(L3);
			GL11.glEndList();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
			GL11.glNewList(rightLegList, GL11.GL_COMPILE);
			ClientUtil.drawBakedModel(P3);
			GL11.glEndList();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
			GL11.glNewList(jetpackList, GL11.GL_COMPILE);
			ClientUtil.drawBakedModel(corp);
			ClientUtil.drawBakedModel(wing2);
			GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
			ClientUtil.drawBakedModel(wing1);	
			GL11.glEndList();
		GL11.glPopMatrix();
	}
	
	private static void updateModels()
    {
		if (G == null)
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
			
			GL11.glTranslatef(0F, -1.9F, 0F);
			//GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			if(entity != null && entity instanceof EntityArmorStand) 
			{
				float i = ((EntityArmorStand)entity).rotationYaw;
				//GalaxySpace.debug(i + "");			
				if(i == 0)
					GL11.glRotatef(i + 180, 0.0F, 1.0F, 0.0F);
				if(i == 90 || i == -90)
					GL11.glRotatef(i, 0.0F, 1.0F, 0.0F);
				if(i == -45 || i == 135)
					GL11.glRotatef(i - 90, 0.0F, 1.0F, 0.0F);
				if(i == 45 || i == -135)
					GL11.glRotatef(i + 90, 0.0F, 1.0F, 0.0F);
				//else if(i < 45 && i > 0)
			}
			else
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.78F, 0.75F);
			GL11.glColor3f(color[0], color[1], color[2]);
			
			GL11.glCallList(this.helmetList);
			
		}
	}

	@Override
	public void partBody() {
		if (partType == 2 || partType == 5) {
			
			GL11.glTranslatef(0F, -1.75F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			GL11.glColor3f(color[0], color[1], color[2]);
			GL11.glCallList(this.bodyList);
		}
		
		if(partType == 5 || partType == 6)
		{
		
			if(partType == 5) GL11.glTranslatef(0F, 1.9F, 0F);
			else GL11.glTranslatef(0F, -0.3F, -0.7F);
				
			GL11.glCallList(this.jetpackList);
		}
			
		
	}

	@Override
	public void partRightArm() {
		if (partType == 2 || partType == 5) {
			
			GL11.glTranslatef(0.375F, -1.6F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			GL11.glColor3f(color[0], color[1], color[2]);
			GL11.glCallList(this.rightArmList);
		}
	}

	@Override
	public void partLeftArm() {
		if (partType == 2 || partType == 5) {
			
			GL11.glTranslatef(-0.375F, -1.6F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			GL11.glColor3f(color[0], color[1], color[2]);
			GL11.glCallList(this.leftArmList);
		}
	}

	@Override
	public void partRightLeg() {
		if (partType == 1) {
			
			GL11.glTranslatef(0.125F, -0.75F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			GL11.glColor3f(color[0], color[1], color[2]);
			GL11.glCallList(this.rightLegList);
		}
	}

	@Override
	public void partLeftLeg() {
		if (partType == 1) {
			
			GL11.glTranslatef(-0.125F, -0.75F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			GL11.glColor3f(color[0], color[1], color[2]);
			GL11.glCallList(this.leftLegList);
		}
	}
}