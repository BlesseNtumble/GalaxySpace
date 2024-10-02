package galaxyspace.core.client.animations.MCAClientLibrary;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;

public class MCAModelBox extends ModelBox {
   private TexturedQuad[] MCAquadList = new TexturedQuad[6];

   public MCAModelBox(ModelRenderer par1ModelRenderer, int textureX, int textureY, float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ, float scaleFactor) {
      super(par1ModelRenderer, textureX, textureY, posX, posY, posZ, sizeX, sizeY, sizeZ, scaleFactor);
      float endX = posX + (float)sizeX;
      float endY = posY + (float)sizeY;
      float endZ = posZ + (float)sizeZ;
      posX -= scaleFactor;
      posY -= scaleFactor;
      posZ -= scaleFactor;
      endX += scaleFactor;
      endY += scaleFactor;
      endZ += scaleFactor;
      if (par1ModelRenderer.mirror) {
         float tempValueForSwitch = endX;
         endX = posX;
         posX = tempValueForSwitch;
      }

      PositionTextureVertex PTV_back_left_bottom = new PositionTextureVertex(posX, posY, posZ, 0.0F, 0.0F);
      PositionTextureVertex PTV_back_right_bottom = new PositionTextureVertex(endX, posY, posZ, 0.0F, 8.0F);
      PositionTextureVertex PTV_back_right_top = new PositionTextureVertex(endX, endY, posZ, 8.0F, 8.0F);
      PositionTextureVertex PTV_back_left_top = new PositionTextureVertex(posX, endY, posZ, 8.0F, 0.0F);
      PositionTextureVertex PTV_front_left_bottom = new PositionTextureVertex(posX, posY, endZ, 0.0F, 0.0F);
      PositionTextureVertex PTV_front_right_bottom = new PositionTextureVertex(endX, posY, endZ, 0.0F, 8.0F);
      PositionTextureVertex PTV_front_right_top = new PositionTextureVertex(endX, endY, endZ, 8.0F, 8.0F);
      PositionTextureVertex PTV_front_left_top = new PositionTextureVertex(posX, endY, endZ, 8.0F, 0.0F);
      this.MCAquadList[0] = new TexturedQuad(new PositionTextureVertex[]{PTV_back_right_top, PTV_front_right_top, PTV_front_right_bottom, PTV_back_right_bottom}, textureX + sizeZ + sizeX, textureY + sizeZ, textureX + sizeZ + sizeX + sizeZ, textureY + sizeZ + sizeY, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
      this.MCAquadList[1] = new TexturedQuad(new PositionTextureVertex[]{PTV_front_left_top, PTV_back_left_top, PTV_back_left_bottom, PTV_front_left_bottom}, textureX, textureY + sizeZ, textureX + sizeZ, textureY + sizeZ + sizeY, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
      this.MCAquadList[2] = new TexturedQuad(new PositionTextureVertex[]{PTV_front_right_bottom, PTV_front_left_bottom, PTV_back_left_bottom, PTV_back_right_bottom}, textureX + sizeZ + sizeX, textureY, textureX + sizeZ + sizeX + sizeX, textureY + sizeZ, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
      this.MCAquadList[3] = new TexturedQuad(new PositionTextureVertex[]{PTV_back_right_top, PTV_back_left_top, PTV_front_left_top, PTV_front_right_top}, textureX + sizeZ, textureY, textureX + sizeZ + sizeX, textureY + sizeZ, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
      this.MCAquadList[4] = new TexturedQuad(new PositionTextureVertex[]{PTV_back_left_top, PTV_back_right_top, PTV_back_right_bottom, PTV_back_left_bottom}, textureX + sizeZ + sizeX + sizeZ, textureY + sizeZ, textureX + sizeZ + sizeX + sizeZ + sizeX, textureY + sizeZ + sizeY, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
      this.MCAquadList[5] = new TexturedQuad(new PositionTextureVertex[]{PTV_front_right_top, PTV_front_left_top, PTV_front_left_bottom, PTV_front_right_bottom}, textureX + sizeZ, textureY + sizeZ, textureX + sizeZ + sizeX, textureY + sizeZ + sizeY, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
      if (par1ModelRenderer.mirror) {
         for(int j1 = 0; j1 < this.MCAquadList.length; ++j1) {
            this.MCAquadList[j1].flipFace();
         }
      }

   }

   @SideOnly(Side.CLIENT)
   @Override
   public void render(Tessellator par1Tessellator, float textureX) {
      for(int i = 0; i < this.MCAquadList.length; ++i) {
         this.MCAquadList[i].draw(par1Tessellator, textureX);
      }

   }
}
