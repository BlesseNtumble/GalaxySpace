//TODO: CHECK INVISIBILITY FOR PLAYER IN SERVER SIDE INSTANCE (galaxyspace/core/events/GSEventHandler.java line: 496)
//TODO: Have the camera get closer as the pod slows down
package galaxyspace.core.prefab.entity;

import java.util.Map;
import java.util.Random;

import galaxyspace.core.client.render.entity.RenderEntryPod;
import galaxyspace.systems.TCetiSystem.core.configs.TCConfigDimensions;
import micdoodle8.mods.galacticraft.api.entity.ICameraZoomEntity;
import micdoodle8.mods.galacticraft.api.entity.IIgnoreShift;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.entities.EntityLanderBase;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityEntryPod extends EntityLanderBase implements ICameraZoomEntity, IIgnoreShift{

    private Integer groundPosY = null;
    private boolean floating;
    private float cameraZoom = 15.0F;

    public EntityEntryPod(World world) {
        super(world, 0F);
        if(this.dimension == TCConfigDimensions.dimensionIDTauCetiF)
            this.setSize(1.5F, 1.0F);
        else
            this.setSize(1.3F, 2.0F);
    }
    //TODO: Have the rest of everythign work on other planets
    public EntityEntryPod(EntityPlayerMP player)
    {
        super(player, 0F);
        this.setSize(1.3F, 2.0F);
    }

    @Override
    public double getMountedYOffset()
    {
        return (this.dimension == TCConfigDimensions.dimensionIDTauCetiF ? this.height - 1.2D : this.height);
    }

    @Override
    public double getInitialMotionY()
    {
        return -14.5F;
    }

    @Override
    public float getRotateOffset()
    {
        return -20F;
    }

    @Override
    public boolean shouldSpawnParticles()
    {
        return false;
    }

    private int landingPhase = 0; // Track the landing phase

    @Override
    public void tickInAir() {
        super.tickInAir();

        if (this.worldObj.isRemote) {
            if (!this.onGround) {
                if (this.dimension == TCConfigDimensions.dimensionIDTauCetiF) {
                    // Phase-based water landing logic
                    System.out.println(motionY);
                    if(this.riddenByEntity == null && this.isInWater())
                    {
                        System.out.println("Not ridden by entity");
                        this.setPosition(this.posX, 110, this.posZ);
                        landingPhase = 14;
                        this.posY = 110D;
                    }
                    switch (landingPhase) {
                        case 0: // Normal descent
                            RenderEntryPod.setFlotationAngleParam(0);
                            this.motionY -= 0.03D;
                            this.motionY *= 0.99D;
                            System.out.println("Case 0");
                            if (this.posY < 300) this.motionY *= 0.98D;
                            if (this.posY < 200) this.motionY *= 0.97D;
                            if (this.posY < 150) this.motionY *= 0.96D;
                            if (this.isInWater()) {
                                System.out.println("Switching to 1");
                                landingPhase = 1;
                            }
                            break;

                        case 1: // Water deceleration
                            this.motionY *= 0.85D;
                            System.out.println("Case 1");
                            if (Math.abs(this.motionY) < 0.06D) {
                                System.out.println("Switching to 2");
                                landingPhase = 2;
                            }
                            break;

                        case 2: // Floating back up
                            this.motionY += 0.01D; // Dampen upward motion
                            System.out.println("Case 2");
                            if (this.posY >= 115.0D || !this.isInWater()) {
                                System.out.println("Switching to 3");
                                landingPhase = 3;
                            }
                            break;
                        case 3:
                            this.motionY -= 0.012D; // Dampen downward motion
                            System.out.println("Case 3");
                            if(Math.abs(this.motionY) < 0.06D)
                            {
                                System.out.println("Switching to 4");
                                landingPhase = 4;
                            }
                            break;
                        case 4: // Falling down
                            this.motionY -= 0.003D; // Dampen downward motion
                            System.out.println("Case 4");
                            if (this.isInWater()) {
                                System.out.println("Switching to 5");
                                landingPhase = 5;
                            }
                            break;

                        case 5: // Floating back up
                            this.motionY += 0.01D; // Dampen upward motion
                            System.out.println("Case 4");
                            if (this.posY >= 110.0D || !this.isInWater()) {
                                System.out.println("Switching to 6");
                                landingPhase = 6;
                            }
                            break;
                        case 6:
                            this.motionY -= 0.012D; // Dampen downward motion
                            System.out.println("Case 6");
                            if(Math.abs(this.motionY) < 0.06D)
                            {
                                System.out.println("Switching to 7");
                                landingPhase = 7;
                            }
                            break;
                        case 7: // Falling down
                            this.motionY -= 0.003D; // Dampen downward motion
                            System.out.println("Case 7");
                            if (this.isInWater()) {
                                System.out.println("Switching to 8");
                                landingPhase = 8;
                            }
                            break;

                        case 8: // Floating back up
                            this.motionY += 0.01D; // Dampen upward motion
                            System.out.println("Case 8");
                            if (this.posY >= 110.0D || !this.isInWater()) {
                                System.out.println("Switching to 9");
                                landingPhase = 9;
                            }
                            break;
                        case 9:
                            this.motionY -= 0.012D; // Dampen downward motion
                            System.out.println("Case 9");
                            if(Math.abs(this.motionY) < 0.06D)
                            {
                                System.out.println("Switching to 10");
                                landingPhase = 10;
                            }
                            break;
                        case 10: // Falling down
                            this.motionY -= 0.003D; // Dampen downward motion
                            System.out.println("Case 10");
                            if (this.isInWater()) {
                                System.out.println("Switching to 11");
                                landingPhase = 11;
                            }
                            break;

                        case 11: // Floating back up
                            this.motionY += 0.01D; // Dampen upward motion
                            System.out.println("Case 11");
                            if (this.posY >= 110.0D || !this.isInWater()) {
                                System.out.println("Switching to 12");
                                landingPhase = 12;
                            }
                            break;
                        case 12:
                            this.motionY -= 0.012D; // Dampen downward motion
                            System.out.println("Case 12");
                            if(Math.abs(this.motionY) < 0.06D)
                            {
                                System.out.println("Switching to 13");
                                landingPhase = 13;
                            }
                            break;
                        case 13: // Falling down
                            this.motionY -= 0.003D; // Dampen downward motion
                            System.out.println("Case 13");
                            if (this.isInWater()) {
                                System.out.println("Switching to 14");
                                landingPhase = 14;
                            }
                            break;
                        case 14: // Stabilizing and landing
                            this.motionY = 0.0D;
                            System.out.println("Case 14");
                            System.out.println("Landed");
                            this.floating = true;
                            this.onGround = true;
                            break;
                    }


                } else {
                    // Default behavior for non-Tau Ceti F dimensions
                    this.motionY -= 0.014D;
                    landingPhase = 14;
                    if (this.motionY < -0.7F) {
                        this.motionY *= 0.994F;
                    }

                    if (this.posY <= 242.0F) {
                        if (groundPosY == null) {
                            this.groundPosY = this.worldObj.getTopSolidOrLiquidBlock((int) this.posX, (int) this.posZ);
                        }
                        if (this.posY - this.groundPosY > 15.0F) {
                            this.motionY *= 0.95F;
                        } else {
                            this.motionY *= 0.85F;
                        }
                    }
                }
            }
        }
    }
    @Override
    public boolean shouldMove()
    {
        if(super.shouldMove())
            if(this.dimension != TCConfigDimensions.dimensionIDTauCetiF && !this.onGround)
                return true;
            else return this.dimension == TCConfigDimensions.dimensionIDTauCetiF && !floating;
        return false;
    }
    public boolean landed()
    {
        return (floating || this.onGround) && this.motionY == 0.0D;
    }

    public int getLandingPhase()
    {
        return landingPhase;
    }
    @Override
    public String getInventoryName() {
        return GCCoreUtil.translate("container.entry_pod.name");
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public boolean pressKey(int key) {
        return false;
    }

    @Override
    public void updateRiderPosition()
    {
        double offsetXCap = 1.9D;
        double currentXOffset;
        double offsetYCap = 2D;
        double currentYOffset;
        if (this.riddenByEntity != null)
        {

            currentXOffset = (RenderEntryPod.getFlotationAngleParam()/90)*offsetXCap;
            currentYOffset = (RenderEntryPod.getFlotationAngleParam()/90)*offsetYCap;
            this.riddenByEntity.setPosition(this.posX - currentXOffset+(this.dimension == TCConfigDimensions.dimensionIDTauCetiF ? 1.5F : 0), this.posY + this.getMountedYOffset()-currentYOffset + this.riddenByEntity.getYOffset(), this.posZ);
        }
    }
    @Override
    public float getCameraZoom() {
        if(getLandingPhase()>0 && cameraZoom > 6.0F)
        {
            cameraZoom -= 0.018F;
        }

        return cameraZoom;
    }

    @Override
    public boolean defaultThirdPerson() {
        return true;
    }

    @Override
    public Map<Vector3, Vector3> getParticleMap() {
        return null;
    }

    @Override
    public EntityFX getParticle(Random rand, double x, double y, double z, double motX, double motY, double motZ) {
        return null;
    }

    @Override
    public void tickOnGround() {

    }

    @Override
    public void onGroundHit() {
        //if(this.riddenByEntity != null)
        //this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2.0F, true);
    }

    @Override
    public Vector3 getMotionVec()
    {
        if (this.onGround || this.floating)
        {
            landingPhase = 14;
            //this.posY = 110D;
            return new Vector3(0, 0, 0);
        }

        if (this.ticks >= 40 && this.ticks < 45)
        {
            if(this.riddenByEntity != null)
                return new Vector3(0,-1,0 );
            else
                this.motionY = this.getInitialMotionY();
        }

        if (!this.shouldMove())
        {
            //this.posY = 110D;
            return new Vector3(0, 0, 0);
        }

        return new Vector3(this.motionX, this.motionY, this.motionZ);
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity par1Entity)
    {
        return null;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    @Override
    public boolean interactFirst(EntityPlayer player)
    {
        if (this.worldObj.isRemote)
        {
            if (!this.onGround || !this.floating)
            {
                return false;
            }

            if (this.riddenByEntity != null)
            {
                this.riddenByEntity.mountEntity(this);
            }
            player.mountEntity(null);
            return true;
        }

        if (this.riddenByEntity == null && player instanceof EntityPlayerMP)
        {
            GCCoreUtil.openParachestInv((EntityPlayerMP) player, this);
            return true;
        }
        else if (player instanceof EntityPlayerMP)
        {
            if (!this.onGround || !this.floating)
            {
                return false;
            }

            player.mountEntity(null);
            return true;
        }
        else
        {
            return true;
        }
    }

    @Override
    public boolean shouldIgnoreShiftExit()
    {
        return !this.onGround && !this.floating;
    }

    public Integer getGroundPosY()
    {
        return groundPosY;
    }
}