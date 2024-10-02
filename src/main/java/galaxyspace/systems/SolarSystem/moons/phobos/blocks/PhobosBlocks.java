/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  micdoodle8.mods.galacticraft.api.block.IDetectableResource
 *  micdoodle8.mods.galacticraft.api.block.ITerraformableBlock
 *  micdoodle8.mods.galacticraft.planets.mars.items.MarsItems
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package galaxyspace.systems.SolarSystem.moons.phobos.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import java.util.List;
import java.util.Random;
import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class PhobosBlocks
extends Block
implements ITerraformableBlock,
IDetectableResource {
    public static String[] metadata = new String[]{"PhobosGrunt", "PhobosSubGrunt", "PhobosStone"};
    protected IIcon[] textures = new IIcon[metadata.length];

    public PhobosBlocks() {
        super(Material.rock);
        this.setBlockName("PhobosRock");
        this.setHardness(2.0f);
        this.setHarvestLevel("pickaxe", 2);
        this.setBlockTextureName("dirt");
    }

    @SideOnly(value=Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn() {
        return GSCreativeTabs.GSBlocksTab;
    }

    public boolean isTerraformable(World world, int x, int y, int z) {
        return true;
    }

    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    public int damageDropped(int metadata) {
        if (metadata == 3) {
            return 0;
        }
        return metadata;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
        world.setBlockMetadataWithNotify(x, y, z, is.getItemDamage(), 3);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);
        this.textures[0] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":solarsystem/phobos/phobosgrunt");
        this.textures[1] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":solarsystem/phobos/phobossubgrunt");
        this.textures[2] = iconRegister.registerIcon(GalaxySpace.ASSET_PREFIX + ":solarsystem/phobos/phobosstone");
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta < 0 || meta >= this.textures.length) {
            return this.textures[0];
        }
        return this.textures[meta];
    }

    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < this.textures.length; ++i) {
            list.add(new ItemStack(block, 1, i));
        }
    }

    public Item getItemDropped(int meta, Random random, int par3) {
        return meta == 3 ? MarsItems.marsItemBasic : Item.getItemFromBlock((Block)this);
    }

    public int quantityDropped(int meta, int fortune, Random random) {
        return meta == 3 ? 1 + random.nextInt(2) : 1;
    }

    public boolean isValueable(int metadata) {
        return metadata == 3 || metadata == 4;
    }
}

