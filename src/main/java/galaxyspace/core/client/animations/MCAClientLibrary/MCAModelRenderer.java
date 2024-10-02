package galaxyspace.core.client.animations.MCAClientLibrary;

import galaxyspace.core.client.animations.MCACommonLibrary.Utils;
import galaxyspace.core.client.animations.MCACommonLibrary.math.Matrix4f;
import galaxyspace.core.client.animations.MCACommonLibrary.math.Quaternion;
import galaxyspace.core.client.animations.MCACommonLibrary.math.Vector3f;
import java.nio.FloatBuffer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class MCAModelRenderer extends ModelRenderer {
   private int DDStextureOffsetX;
   private int DDStextureOffsetY;
   private boolean DDScompiled;
   private int DDSdisplayList;
   private Matrix4f rotationMatrix = new Matrix4f();
   private Matrix4f prevRotationMatrix = new Matrix4f();
   private float defaultRotationPointX;
   private float defaultRotationPointY;
   private float defaultRotationPointZ;
   private Matrix4f defaultRotationMatrix = new Matrix4f();
   private Quaternion defaultRotationAsQuaternion;

   public MCAModelRenderer(ModelBase par1ModelBase, String par2Str, int xTextureOffset, int yTextureOffset) {
      super(par1ModelBase, par2Str);
      this.setTextureSize(par1ModelBase.textureWidth, par1ModelBase.textureHeight);
      this.setTextureOffset(xTextureOffset, yTextureOffset);
   }

   @Override
   public ModelRenderer setTextureOffset(int par1, int par2) {
      this.DDStextureOffsetX = par1;
      this.DDStextureOffsetY = par2;
      return this;
   }

   @Override
   public ModelRenderer addBox(String par1Str, float par2, float par3, float par4, int par5, int par6, int par7) {
      par1Str = this.boxName + "." + par1Str;
      this.cubeList.add((new MCAModelBox(this, this.DDStextureOffsetX, this.DDStextureOffsetY, par2, par3, par4, par5, par6, par7, 0.0F)).func_78244_a(par1Str));
      return this;
   }

   @Override
   public ModelRenderer addBox(float par1, float par2, float par3, int par4, int par5, int par6) {
      this.cubeList.add(new MCAModelBox(this, this.DDStextureOffsetX, this.DDStextureOffsetY, par1, par2, par3, par4, par5, par6, 0.0F));
      return this;
   }

   @Override
   public void addBox(float par1, float par2, float par3, int par4, int par5, int par6, float par7) {
      this.cubeList.add(new MCAModelBox(this, this.DDStextureOffsetX, this.DDStextureOffsetY, par1, par2, par3, par4, par5, par6, par7));
   }

   @Override
   public void render(float par1) {
      if (!this.isHidden && this.showModel) {
         if (!this.DDScompiled) {
            this.DDScompileDisplayList(par1);
         }

         GL11.glTranslatef(this.offsetX, this.offsetY, this.offsetZ);
         int i;
         if (this.rotationMatrix.isEmptyRotationMatrix()) {
            if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
               GL11.glCallList(this.DDSdisplayList);
               if (this.childModels != null) {
                  for(i = 0; i < this.childModels.size(); ++i) {
                     ((ModelRenderer)this.childModels.get(i)).render(par1);
                  }
               }
            } else {
               GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
               GL11.glCallList(this.DDSdisplayList);
               if (this.childModels != null) {
                  for(i = 0; i < this.childModels.size(); ++i) {
                     ((ModelRenderer)this.childModels.get(i)).render(par1);
                  }
               }

               GL11.glTranslatef(-this.rotationPointX * par1, -this.rotationPointY * par1, -this.rotationPointZ * par1);
            }
         } else {
            GL11.glPushMatrix();
            GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
            FloatBuffer buf = Utils.makeFloatBuffer(this.rotationMatrix.intoArray());
            GL11.glMultMatrix(buf);
            GL11.glCallList(this.DDSdisplayList);
            if (this.childModels != null) {
               for(i = 0; i < this.childModels.size(); ++i) {
                  ((ModelRenderer)this.childModels.get(i)).render(par1);
               }
            }

            GL11.glPopMatrix();
         }

         GL11.glTranslatef(-this.offsetX, -this.offsetY, -this.offsetZ);
         this.prevRotationMatrix = this.rotationMatrix;
      }

   }

   @Override
   public void renderWithRotation(float par1) {
   }

   @Override
   public void postRender(float par1) {
      if (!this.isHidden && this.showModel) {
         if (!this.DDScompiled) {
            this.DDScompileDisplayList(par1);
         }

         if (this.rotationMatrix.equals(this.prevRotationMatrix)) {
            if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F) {
               GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
            }
         } else {
            GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
            GL11.glMultMatrix(FloatBuffer.wrap(this.rotationMatrix.intoArray()));
         }
      }

   }

   public void setInitialRotationPoint(float par1, float par2, float par3) {
      this.defaultRotationPointX = par1;
      this.defaultRotationPointY = par2;
      this.defaultRotationPointZ = par3;
      this.setRotationPoint(par1, par2, par3);
   }

   @Override
   public void setRotationPoint(float par1, float par2, float par3) {
      this.rotationPointX = par1;
      this.rotationPointY = par2;
      this.rotationPointZ = par3;
   }


   public void resetRotationPoint() {
      this.rotationPointX = this.defaultRotationPointX;
      this.rotationPointY = this.defaultRotationPointY;
      this.rotationPointZ = this.defaultRotationPointZ;
   }

   public Vector3f getPositionAsVector() {
      return new Vector3f(this.rotationPointX, this.rotationPointY, this.rotationPointZ);
   }

   public void setInitialRotationMatrix(Matrix4f matrix) {
      this.defaultRotationMatrix = matrix;
      this.setRotationMatrix(matrix);
      this.defaultRotationAsQuaternion = Utils.getQuaternionFromMatrix(this.rotationMatrix);
   }

   public void setRotationMatrix(Matrix4f matrix) {
      this.rotationMatrix.m00 = matrix.m00;
      this.rotationMatrix.m01 = matrix.m01;
      this.rotationMatrix.m02 = matrix.m02;
      this.rotationMatrix.m03 = matrix.m03;
      this.rotationMatrix.m10 = matrix.m10;
      this.rotationMatrix.m11 = matrix.m11;
      this.rotationMatrix.m12 = matrix.m12;
      this.rotationMatrix.m13 = matrix.m13;
      this.rotationMatrix.m20 = matrix.m20;
      this.rotationMatrix.m21 = matrix.m21;
      this.rotationMatrix.m22 = matrix.m22;
      this.rotationMatrix.m23 = matrix.m23;
      this.rotationMatrix.m30 = matrix.m30;
      this.rotationMatrix.m31 = matrix.m31;
      this.rotationMatrix.m32 = matrix.m32;
      this.rotationMatrix.m33 = matrix.m33;
   }

   public void resetRotationMatrix() {
      this.setRotationMatrix(this.defaultRotationMatrix);
   }

   public Matrix4f getRotationMatrix() {
      return this.rotationMatrix;
   }

   public Quaternion getDefaultRotationAsQuaternion() {
      return this.defaultRotationAsQuaternion;
   }

   public void DDScompileDisplayList(float par1) {
      this.DDSdisplayList = GLAllocation.generateDisplayLists(1);
      GL11.glNewList(this.DDSdisplayList, 4864);
      Tessellator tessellator = Tessellator.instance;

      for(int i = 0; i < this.cubeList.size(); ++i) {
         ((ModelBox)this.cubeList.get(i)).render(tessellator, par1);
      }

      GL11.glEndList();
      this.DDScompiled = true;
   }
}
