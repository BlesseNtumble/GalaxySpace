/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  micdoodle8.mods.galacticraft.api.vector.Vector3
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.util.ResourceLocation
 */
package galaxyspace.systems.SolarSystem.moons.titan.dimension.sky;

import galaxyspace.core.client.render.sky.SkyProviderBase;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class SkyProviderTitan
extends SkyProviderBase {
    @Override
    protected void rendererSky(Tessellator tessellator, float f10, float ticks) {
    }

    @Override
    protected boolean enableBaseImages() {
        return false;
    }

    @Override
    protected float sunSize() {
        return 6.5f;
    }

    @Override
    protected boolean enableStar() {
        return false;
    }

    @Override
    protected ResourceLocation sunImage() {
        return null;
    }

    @Override
    protected int modeLight() {
        return 2;
    }

    @Override
    protected Vector3 colorSunAura() {
        return null;
    }

    @Override
    protected Vector3 getAtmosphereColor() {
        return null;
    }
}

