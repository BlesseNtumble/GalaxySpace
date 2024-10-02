package galaxyspace.systems.SolarSystem.planets.venus.dimension.sky;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;

public class CloudProviderVenus extends IRenderHandler
{
	private static Minecraft mc;
	private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
	public static int cloudTickCounter = 0;
	
	public CloudProviderVenus()
	{
		mc = FMLClientHandler.instance().getClient();
	}
	
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc)
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
		float f1 = (float)(CloudProviderVenus.mc.renderViewEntity.lastTickPosY + (CloudProviderVenus.mc.renderViewEntity.posY - CloudProviderVenus.mc.renderViewEntity.lastTickPosY) * partialTicks);
		Tessellator tessellator = Tessellator.instance;
		float f2 = 12.0F;
		float f3 = 4.0F;
		mc.renderEngine.bindTexture(locationCloudsPng);
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		mc.theWorld.getCloudColour(partialTicks);

		byte b0 = 10;
		byte b1 = 2;
		float f13 = 9.765625E-4F;

		for (int count = 0; count < 4; ++count)
		{
			GL11.glPushMatrix();
			GL11.glScalef(f2 + count * 2, 1.0F, f2 + count * 2);

			for (int k = 0; k < 2; ++k)
			{
				if (k == 0)
				{
					GL11.glColorMask(false, false, false, false);
				}
				else if (CloudProviderVenus.mc.gameSettings.anaglyph)
				{
					if (EntityRenderer.anaglyphField == 0)
					{
						GL11.glColorMask(false, true, true, true);
					}
					else
					{
						GL11.glColorMask(true, false, false, true);
					}
				}
				else
				{
					GL11.glColorMask(true, true, true, true);
				}

				double d0 = this.cloudTickCounter * (1.0F - count / 5.0F) + count / 5.0F * partialTicks + count * 250000;
				double d1 = (CloudProviderVenus.mc.renderViewEntity.prevPosX + (CloudProviderVenus.mc.renderViewEntity.posX - CloudProviderVenus.mc.renderViewEntity.prevPosX) * partialTicks + d0 * 0.029999999329447746D) / f2;
				double d2 = (CloudProviderVenus.mc.renderViewEntity.prevPosZ + (CloudProviderVenus.mc.renderViewEntity.posZ - CloudProviderVenus.mc.renderViewEntity.prevPosZ) * partialTicks) / f2 + 0.33000001311302185D;
				float f4 = mc.theWorld.provider.getCloudHeight() - f1 + 0.33F + count * 20.0F;
				int i = MathHelper.floor_double(d1 / 2048.0D);
				int j = MathHelper.floor_double(d2 / 2048.0D);
				d1 -= i * 2048;
				d2 -= j * 2048;

				float f5 = 234.0F / (1200.0F + count * 300.0F);
				float f6 = 147.0F / (1200.0F + count * 300.0F);
				float f7 = 9.0F / (1200.0F + count * 300.0F);
				float f8;
				float f9;
				float f10;

				if (CloudProviderVenus.mc.gameSettings.anaglyph)
				{
					f8 = (f5 * 30.0F + f6 * 59.0F + f7 * 11.0F) / 100.0F;
					f9 = (f5 * 30.0F + f6 * 70.0F) / 100.0F;
					f10 = (f5 * 30.0F + f7 * 70.0F) / 100.0F;
					f5 = f8;
					f6 = f9;
					f7 = f10;
				}

				f8 = (float)(d1 * 0.0D);
				f9 = (float)(d2 * 0.0D);
				f10 = 0.00390625F;
				f8 = MathHelper.floor_double(d1) * f10;
				f9 = MathHelper.floor_double(d2) * f10;
				float f11 = (float)(d1 - MathHelper.floor_double(d1));
				float f12 = (float)(d2 - MathHelper.floor_double(d2));

				for (int l = -b1 + 1; l <= b1; ++l)
				{
					for (int i1 = -b1 + 1; i1 <= b1; ++i1)
					{
						tessellator.startDrawingQuads();
						float f14 = l * b0;
						float f15 = i1 * b0;
						float f16 = f14 - f11;
						float f17 = f15 - f12;

						if (f4 > -f3 - 1.0F)
						{
							tessellator.setColorRGBA_F(f5 * 0.7F, f6 * 0.7F, f7 * 0.7F, 0.9F);
							tessellator.setNormal(0.0F, -1.0F, 0.0F);
							tessellator.addVertexWithUV(f16 + 0.0F, f4 + 0.0F, f17 + b0, (f14 + 0.0F) * f10 + f8, (f15 + b0) * f10 + f9);
							tessellator.addVertexWithUV(f16 + b0, f4 + 0.0F, f17 + b0, (f14 + b0) * f10 + f8, (f15 + b0) * f10 + f9);
							tessellator.addVertexWithUV(f16 + b0, f4 + 0.0F, f17 + 0.0F, (f14 + b0) * f10 + f8, (f15 + 0.0F) * f10 + f9);
							tessellator.addVertexWithUV(f16 + 0.0F, f4 + 0.0F, f17 + 0.0F, (f14 + 0.0F) * f10 + f8, (f15 + 0.0F) * f10 + f9);
						}

						if (f4 <= f3 + 1.0F)
						{
							tessellator.setColorRGBA_F(f5, f6, f7, 0.9F);
							tessellator.setNormal(0.0F, 1.0F, 0.0F);
							tessellator.addVertexWithUV(f16 + 0.0F, f4 + f3 - f13, f17 + b0, (f14 + 0.0F) * f10 + f8, (f15 + b0) * f10 + f9);
							tessellator.addVertexWithUV(f16 + b0, f4 + f3 - f13, f17 + b0, (f14 + b0) * f10 + f8, (f15 + b0) * f10 + f9);
							tessellator.addVertexWithUV(f16 + b0, f4 + f3 - f13, f17 + 0.0F, (f14 + b0) * f10 + f8, (f15 + 0.0F) * f10 + f9);
							tessellator.addVertexWithUV(f16 + 0.0F, f4 + f3 - f13, f17 + 0.0F, (f14 + 0.0F) * f10 + f8, (f15 + 0.0F) * f10 + f9);
						}

						tessellator.setColorRGBA_F(f5 * 0.9F, f6 * 0.9F, f7 * 0.9F, 0.9F);
						int j1;

						if (l > -1)
						{
							tessellator.setNormal(-1.0F, 0.0F, 0.0F);

							for (j1 = 0; j1 < b0; ++j1)
							{
								tessellator.addVertexWithUV(f16 + j1 + 0.0F, f4 + 0.0F, f17 + b0, (f14 + j1 + 0.5F) * f10 + f8, (f15 + b0) * f10 + f9);
								tessellator.addVertexWithUV(f16 + j1 + 0.0F, f4 + f3, f17 + b0, (f14 + j1 + 0.5F) * f10 + f8, (f15 + b0) * f10 + f9);
								tessellator.addVertexWithUV(f16 + j1 + 0.0F, f4 + f3, f17 + 0.0F, (f14 + j1 + 0.5F) * f10 + f8, (f15 + 0.0F) * f10 + f9);
								tessellator.addVertexWithUV(f16 + j1 + 0.0F, f4 + 0.0F, f17 + 0.0F, (f14 + j1 + 0.5F) * f10 + f8, (f15 + 0.0F) * f10 + f9);
							}
						}

						if (l <= 1)
						{
							tessellator.setNormal(1.0F, 0.0F, 0.0F);

							for (j1 = 0; j1 < b0; ++j1)
							{
								tessellator.addVertexWithUV(f16 + j1 + 1.0F - f13, f4 + 0.0F, f17 + b0, (f14 + j1 + 0.5F) * f10 + f8, (f15 + b0) * f10 + f9);
								tessellator.addVertexWithUV(f16 + j1 + 1.0F - f13, f4 + f3, f17 + b0, (f14 + j1 + 0.5F) * f10 + f8, (f15 + b0) * f10 + f9);
								tessellator.addVertexWithUV(f16 + j1 + 1.0F - f13, f4 + f3, f17 + 0.0F, (f14 + j1 + 0.5F) * f10 + f8, (f15 + 0.0F) * f10 + f9);
								tessellator.addVertexWithUV(f16 + j1 + 1.0F - f13, f4 + 0.0F, f17 + 0.0F, (f14 + j1 + 0.5F) * f10 + f8, (f15 + 0.0F) * f10 + f9);
							}
						}

						tessellator.setColorRGBA_F(f5 * 0.8F, f6 * 0.8F, f7 * 0.8F, 0.9F);

						if (i1 > -1)
						{
							tessellator.setNormal(0.0F, 0.0F, -1.0F);

							for (j1 = 0; j1 < b0; ++j1)
							{
								tessellator.addVertexWithUV(f16 + 0.0F, f4 + f3, f17 + j1 + 0.0F, (f14 + 0.0F) * f10 + f8, (f15 + j1 + 0.5F) * f10 + f9);
								tessellator.addVertexWithUV(f16 + b0, f4 + f3, f17 + j1 + 0.0F, (f14 + b0) * f10 + f8, (f15 + j1 + 0.5F) * f10 + f9);
								tessellator.addVertexWithUV(f16 + b0, f4 + 0.0F, f17 + j1 + 0.0F, (f14 + b0) * f10 + f8, (f15 + j1 + 0.5F) * f10 + f9);
								tessellator.addVertexWithUV(f16 + 0.0F, f4 + 0.0F, f17 + j1 + 0.0F, (f14 + 0.0F) * f10 + f8, (f15 + j1 + 0.5F) * f10 + f9);
							}
						}

						if (i1 <= 1)
						{
							tessellator.setNormal(0.0F, 0.0F, 1.0F);

							for (j1 = 0; j1 < b0; ++j1)
							{
								tessellator.addVertexWithUV(f16 + 0.0F, f4 + f3, f17 + j1 + 1.0F - f13, (f14 + 0.0F) * f10 + f8, (f15 + j1 + 0.5F) * f10 + f9);
								tessellator.addVertexWithUV(f16 + b0, f4 + f3, f17 + j1 + 1.0F - f13, (f14 + b0) * f10 + f8, (f15 + j1 + 0.5F) * f10 + f9);
								tessellator.addVertexWithUV(f16 + b0, f4 + 0.0F, f17 + j1 + 1.0F - f13, (f14 + b0) * f10 + f8, (f15 + j1 + 0.5F) * f10 + f9);
								tessellator.addVertexWithUV(f16 + 0.0F, f4 + 0.0F, f17 + j1 + 1.0F - f13, (f14 + 0.0F) * f10 + f8, (f15 + j1 + 0.5F) * f10 + f9);
							}
						}

						tessellator.draw();
					}
				}
			}

			GL11.glPopMatrix();
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
}
