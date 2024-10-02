package galaxyspace.systems.SolarSystem.planets.overworld.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelGenerator extends ModelBase
{
    ModelRenderer s2;
    ModelRenderer bak;
    ModelRenderer d1;
    ModelRenderer d2;
    ModelRenderer k1;
    ModelRenderer s21;
    ModelRenderer t1;
    ModelRenderer e1;
    ModelRenderer b1;
    ModelRenderer b2;
    ModelRenderer b3;
    ModelRenderer t2;

    public ModelGenerator()
    {
        this( 0.0f );
    }

    public ModelGenerator( float par1 )
    {
        s2 = new ModelRenderer( this, 0, 16 );
        s2.setTextureSize( 128, 64 );
        s2.addBox( -8F, -16F, -8F, 16, 32, 16);
        s2.setRotationPoint( 0F, 8F, 4F );
        bak = new ModelRenderer( this, 64, 31 );
        bak.setTextureSize( 128, 64 );
        bak.addBox( -6.5F, -10F, -6.5F, 13, 20, 13);
        bak.setRotationPoint( -16F, 12F, 4F );
        d1 = new ModelRenderer( this, 48, 14 );
        d1.setTextureSize( 128, 64 );
        d1.addBox( -8F, -1F, -7.5F, 16, 2, 15);
        d1.setRotationPoint( -16F, 6F, 4F );
        d2 = new ModelRenderer( this, 48, 14 );
        d2.setTextureSize( 128, 64 );
        d2.addBox( -8F, -1F, -7.5F, 16, 2, 15);
        d2.setRotationPoint( -16F, 18F, 4F );
        k1 = new ModelRenderer( this, 0, 6 );
        k1.setTextureSize( 128, 64 );
        k1.addBox( -3F, -1F, -3F, 6, 2, 6);
        k1.setRotationPoint( -16F, 1F, 4F );
        s21 = new ModelRenderer( this, 16, 6 );
        s21.setTextureSize( 128, 64 );
        s21.addBox( -6F, -1F, -4F, 12, 2, 8);
        s21.setRotationPoint( -14F, -4F, 4F );
        t1 = new ModelRenderer( this, 0, 24 );
        t1.setTextureSize( 128, 64 );
        t1.addBox( -2F, -2F, -2F, 4, 4, 4);
        t1.setRotationPoint( -16F, -2F, 4F );
        e1 = new ModelRenderer( this, 48, 7 );
        e1.setTextureSize( 128, 64 );
        e1.addBox( -6F, -3F, -0.5F, 12, 6, 1);
        e1.setRotationPoint( 0F, -3F, -4.5F );
        b1 = new ModelRenderer( this, 8, 22 );
        b1.setTextureSize( 128, 64 );
        b1.addBox( -1.5F, -0.5F, -0.5F, 3, 1, 1);
        b1.setRotationPoint( -4.5F, 6.5F, -4.5F );
        b2 = new ModelRenderer( this, 8, 22 );
        b2.setTextureSize( 128, 64 );
        b2.addBox( -1.5F, -0.5F, -0.5F, 3, 1, 1);
        b2.setRotationPoint( -0.5F, 8F, -4.5F );
        b3 = new ModelRenderer( this, 8, 22 );
        b3.setTextureSize( 128, 64 );
        b3.addBox( -1.5F, -0.5F, -0.5F, 3, 1, 1);
        b3.setRotationPoint( 3.5F, 7F, -4.5F );
        t2 = new ModelRenderer( this, 95, 5 );
        t2.setTextureSize( 128, 64 );
        t2.addBox( -1F, -6F, -6F, 2, 12, 12);
        t2.setRotationPoint( -23F, 16F, 4F );
    }

   public void render(float par7)
   {
        s2.rotateAngleX = 0F;
        s2.rotateAngleY = 0F;
        s2.rotateAngleZ = 0F;
        s2.renderWithRotation(par7);

        bak.rotateAngleX = 0F;
        bak.rotateAngleY = 0F;
        bak.rotateAngleZ = 0F;
        bak.renderWithRotation(par7);

        d1.rotateAngleX = 0F;
        d1.rotateAngleY = 0F;
        d1.rotateAngleZ = 0F;
        d1.renderWithRotation(par7);

        d2.rotateAngleX = 0F;
        d2.rotateAngleY = 0F;
        d2.rotateAngleZ = 0F;
        d2.renderWithRotation(par7);

        k1.rotateAngleX = 0F;
        k1.rotateAngleY = 0F;
        k1.rotateAngleZ = 0F;
        k1.renderWithRotation(par7);

        s21.rotateAngleX = 0F;
        s21.rotateAngleY = 0F;
        s21.rotateAngleZ = 0F;
        s21.renderWithRotation(par7);

        t1.rotateAngleX = 0F;
        t1.rotateAngleY = 0F;
        t1.rotateAngleZ = 0F;
        t1.renderWithRotation(par7);

        e1.rotateAngleX = 0F;
        e1.rotateAngleY = 0F;
        e1.rotateAngleZ = 0F;
        e1.renderWithRotation(par7);

        b1.rotateAngleX = 0F;
        b1.rotateAngleY = 0F;
        b1.rotateAngleZ = 0F;
        b1.renderWithRotation(par7);

        b2.rotateAngleX = 0F;
        b2.rotateAngleY = 0F;
        b2.rotateAngleZ = 0F;
        b2.renderWithRotation(par7);

        b3.rotateAngleX = 0F;
        b3.rotateAngleY = 0F;
        b3.rotateAngleZ = 0F;
        b3.renderWithRotation(par7);

        t2.rotateAngleX = 0F;
        t2.rotateAngleY = 0F;
        t2.rotateAngleZ = 0F;
        t2.renderWithRotation(par7);

    }

}
