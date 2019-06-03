package galaxyspace.core.client.models;

import org.lwjgl.opengl.GL11;

import micdoodle8.mods.galacticraft.api.item.IHoldableItem;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.wrappers.PlayerGearData;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public abstract class ModelOBJArmor extends ModelBiped {
	public int color = -1;//��������� ����� � ����.

	private boolean usingParachute;
	public abstract void pre();//�� ���� ������.
	public abstract void post();//����� ���� ������.
	public abstract void partHead(Entity entity);//�����: ������.
	public abstract void partBody();//�����: ����.
	public abstract void partRightArm();//�����: ������ ����.
	public abstract void partLeftArm();//�����: ����� ����.
	public abstract void partRightLeg();//�����: ������ ����.
	public abstract void partLeftLeg();//�����: ����� ����.

	public void render(Entity entity, float x, float y, float z, float yaw, float pitch, float parTicks) {
		super.render(entity, x, y, z, yaw, pitch, parTicks);

		GL11.glPushMatrix();

		 if (entity.isSneaking())
         {
             GlStateManager.translate(0.0F, 0.2F, 0.0F);
         }
		 
		if (entity instanceof EntityPlayer) {
			final EntityPlayer player = (EntityPlayer) entity;
			
			{//parachute rendering
				PlayerGearData gearData = ClientProxyCore.playerItemData.get(player.getCommandSenderEntity().getName());
				if (gearData != null) {
					usingParachute = gearData.getParachute() != null;
				} else {
					String id = player.getGameProfile().getName();

					if (!ClientProxyCore.gearDataRequests.contains(id)) {
						GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(
								PacketSimple.EnumSimplePacket.S_REQUEST_GEAR_DATA, GCCoreUtil.getDimensionID(player.world), new Object[] { id }));
						ClientProxyCore.gearDataRequests.add(id);
					}
				}

				if (usingParachute) {
					bipedLeftArm.rotateAngleX += (float) Math.PI;
					bipedLeftArm.rotateAngleZ += (float) Math.PI / 10;
					bipedRightArm.rotateAngleX += (float) Math.PI;
					bipedRightArm.rotateAngleZ -= (float) Math.PI / 10;
				}
			}
			/*
			{// motion on planets
				if (!player.onGround && player.world.provider instanceof IGalacticraftWorldProvider && player.getRidingEntity() == null) {
		            float speedModifier = 0.0001162F;
		            
		            float par1 = x, par2 = y;
		            
		            float angularSwingArm = MathHelper.cos(par1 * (speedModifier / 2));
		            float rightMod = 2;
		            this.bipedRightArm.rotateAngleX -= MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * rightMod * par2 * 0.5F;
		            this.bipedLeftArm.rotateAngleX -= MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
		            this.bipedRightArm.rotateAngleX += -angularSwingArm * 4.0F * par2 * 0.5F;
		            this.bipedLeftArm.rotateAngleX += angularSwingArm * 4.0F * par2 * 0.5F;
		            this.bipedLeftLeg.rotateAngleX -= MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		            this.bipedLeftLeg.rotateAngleX += MathHelper.cos(par1 * 0.1162F * 2 + (float)Math.PI) * 1.4F * par2;
		            this.bipedRightLeg.rotateAngleX -= MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		            this.bipedRightLeg.rotateAngleX += MathHelper.cos(par1 * 0.1162F * 2) * 1.4F * par2;
		        }
			}
			*/
			{// rendering held items (rockets)
				if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof IHoldableItem) {
		            IHoldableItem holdableItem = (IHoldableItem) player.inventory.getCurrentItem().getItem();
	
		            if (holdableItem.shouldHoldLeftHandUp(player)) {
		                this.bipedLeftArm.rotateAngleX = 0;
		                this.bipedLeftArm.rotateAngleZ = 0;
	
		                this.bipedLeftArm.rotateAngleX += (float) Math.PI + 0.3;
		                this.bipedLeftArm.rotateAngleZ += (float) Math.PI / 10;
		            }
	
		            if (holdableItem.shouldHoldRightHandUp(player)) {
		                this.bipedRightArm.rotateAngleX = 0;
		                this.bipedRightArm.rotateAngleZ = 0;
	
		                this.bipedRightArm.rotateAngleX += (float) Math.PI + 0.3;
		                this.bipedRightArm.rotateAngleZ -= (float) Math.PI / 10;
		            }
	
		            if (player.onGround && holdableItem.shouldCrouch(player)) {
		                this.bipedBody.rotateAngleX = 0.5F;
		                this.bipedRightLeg.rotationPointZ = 4.0F;
		                this.bipedLeftLeg.rotationPointZ = 4.0F;
		                this.bipedRightLeg.rotationPointY = 9.0F;
		                this.bipedLeftLeg.rotationPointY = 9.0F;
		                this.bipedHead.rotationPointY = 1.0F;
		                this.bipedHeadwear.rotationPointY = 1.0F;
		            }
		        }
			}
		}
		
		if (entity instanceof EntityZombie || entity instanceof EntitySkeleton || entity instanceof EntityGiantZombie) {
			float f6 = MathHelper.sin(swingProgress * (float)Math.PI);
			float f7 = MathHelper.sin((1.0F - (1.0F - swingProgress) * (1.0F - swingProgress)) * (float)Math.PI);

			bipedRightArm.rotateAngleZ = 0.0F;
			bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
			bipedRightArm.rotateAngleX = -((float)Math.PI / 2F);
			bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			bipedRightArm.rotateAngleZ += MathHelper.cos(z * 0.09F) * 0.05F + 0.05F;
			bipedRightArm.rotateAngleX += MathHelper.sin(z * 0.067F) * 0.05F;

			bipedLeftArm.rotateAngleZ = 0.0F;
			bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
			bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F);
			bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			bipedLeftArm.rotateAngleZ -= MathHelper.cos(z * 0.09F) * 0.05F + 0.05F;
			bipedLeftArm.rotateAngleX -= MathHelper.sin(z * 0.067F) * 0.05F;

			if (entity instanceof EntitySkeleton && !((EntitySkeleton)entity).isChild())
				GL11.glScalef(1.2F, 1.2F, 1.2F);

			else if (entity instanceof EntityGiantZombie)
				GL11.glScalef(6F, 6F, 6F);

		}

		

		if (color != -1) {
			float red = (float)(color >> 16 & 255) / 255F;
			float blue = (float)(color >> 8 & 255) / 255F;
			float green = (float)(color & 255) / 255F;
			GL11.glColor3f(red, blue, green);
		}

		pre();

		float f6 = 2.0F;

		if (entity instanceof EntityPlayer) {
			final EntityPlayer player = (EntityPlayer) entity;
			this.hurt(player, parTicks);
		}
		
		{//partHead
			GL11.glPushMatrix();
			if (isChild) {
				GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
				GL11.glTranslatef(0.0F, 16.0F * parTicks, 0.0F);
			}
			GL11.glTranslatef(bipedHead.rotationPointX * parTicks, bipedHead.rotationPointY * parTicks, bipedHead.rotationPointZ * parTicks);
			GL11.glRotatef(bipedHead.rotateAngleZ * (180F / (float)Math.PI), 0F, 0F, 1F);
			GL11.glRotatef(bipedHead.rotateAngleY * (180F / (float)Math.PI), 0F, 1F, 0F);
			GL11.glRotatef(bipedHead.rotateAngleX * (180F / (float)Math.PI), 1F, 0F, 0F);
			GL11.glRotatef(180F, 1F, 0F, 0F);

			partHead(entity);
			GL11.glPopMatrix();
		}

		if (isChild) {
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, 24.0F * parTicks, 0.0F);
		}

		{//partBody
			GL11.glPushMatrix();
			GL11.glTranslatef(bipedBody.rotationPointX * parTicks, bipedBody.rotationPointY * parTicks, bipedBody.rotationPointZ * parTicks);
			GL11.glRotatef(bipedBody.rotateAngleZ * (180F / (float)Math.PI), 0F, 0F, 1F);
			GL11.glRotatef(bipedBody.rotateAngleY * (180F / (float)Math.PI), 0F, 1F, 0F);
			GL11.glRotatef(bipedBody.rotateAngleX * (180F / (float)Math.PI), 1F, 0F, 0F);
			GL11.glRotatef(180F, 1F, 0F, 0F);
			
			partBody();
			
			GL11.glPopMatrix();
		}

		{//partRightArm
			GL11.glPushMatrix();
			GL11.glTranslatef(bipedRightArm.rotationPointX * parTicks, bipedRightArm.rotationPointY * parTicks, bipedRightArm.rotationPointZ * parTicks);
			GL11.glRotatef(bipedRightArm.rotateAngleZ * (180F / (float)Math.PI), 0F, 0F, 1F);
			GL11.glRotatef(bipedRightArm.rotateAngleY * (180F / (float)Math.PI), 0F, 1F, 0F);
			GL11.glRotatef(bipedRightArm.rotateAngleX * (180F / (float)Math.PI), 1F, 0F, 0F);
			GL11.glRotatef(180F, 1F, 0F, 0F);
			partRightArm();
			GL11.glPopMatrix();
		}

		{//partLeftArm
			GL11.glPushMatrix();
			GL11.glTranslatef(bipedLeftArm.rotationPointX * parTicks, bipedLeftArm.rotationPointY * parTicks, bipedLeftArm.rotationPointZ * parTicks);
			GL11.glRotatef(bipedLeftArm.rotateAngleZ * (180F / (float)Math.PI), 0F, 0F, 1F);
			GL11.glRotatef(bipedLeftArm.rotateAngleY * (180F / (float)Math.PI), 0F, 1F, 0F);
			GL11.glRotatef(bipedLeftArm.rotateAngleX * (180F / (float)Math.PI), 1F, 0F, 0F);
			GL11.glRotatef(180F, 1F, 0F, 0F);
			partLeftArm();
			GL11.glPopMatrix();
		}

		{//partRightLeg
			GL11.glPushMatrix();
			GL11.glTranslatef(bipedRightLeg.rotationPointX * parTicks, bipedRightLeg.rotationPointY * parTicks, bipedRightLeg.rotationPointZ * parTicks);
			GL11.glRotatef(bipedRightLeg.rotateAngleZ * (180F / (float)Math.PI), 0F, 0F, 1F);
			GL11.glRotatef(bipedRightLeg.rotateAngleY * (180F / (float)Math.PI), 0F, 1F, 0F);
			GL11.glRotatef(bipedRightLeg.rotateAngleX * (180F / (float)Math.PI), 1F, 0F, 0F);
			GL11.glRotatef(180F, 1F, 0F, 0F);
			partRightLeg();
			GL11.glPopMatrix();
		}

		{//partLeftLeg
			GL11.glPushMatrix();
			GL11.glTranslatef(bipedLeftLeg.rotationPointX * parTicks, bipedLeftLeg.rotationPointY * parTicks, bipedLeftLeg.rotationPointZ * parTicks);
			GL11.glRotatef(bipedLeftLeg.rotateAngleZ * (180F / (float)Math.PI), 0F, 0F, 1F);
			GL11.glRotatef(bipedLeftLeg.rotateAngleY * (180F / (float)Math.PI), 0F, 1F, 0F);
			GL11.glRotatef(bipedLeftLeg.rotateAngleX * (180F / (float)Math.PI), 1F, 0F, 0F);
			GL11.glRotatef(180F, 1F, 0F, 0F);
			partLeftLeg();
			GL11.glPopMatrix();
		}

		if (isChild) {
			GL11.glPopMatrix();
		}

		post();
		
		GL11.glColor3f(1F, 1F, 1F);
		GL11.glPopMatrix();

	}
	
	private void hurt(EntityPlayer player, float ticks)
	{
		if(player.hurtTime > 0 || player.deathTime > 0)
		{
			GL11.glPushMatrix();
			GL11.glDepthMask(true);
			OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glDisable(GL11.GL_ALPHA_TEST);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glDepthFunc(GL11.GL_EQUAL);
	        
			GL11.glColor4f(player.getBrightness(), 0.0F, 0.0F, 1.0F);
			
			GL11.glDepthFunc(GL11.GL_LEQUAL);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
		}
	}
}
