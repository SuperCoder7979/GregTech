package gregtech.common.blocks.machines;

import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.internal.IGregTechTileEntity;
import gregtech.api.capability.internal.ITurnable;
import gregtech.api.metatileentity.GregtechTileEntity;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.util.GTResourceLocation;
import gregtech.common.blocks.DelayedStateBlock;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.properties.PropertyString;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.*;

@SuppressWarnings("deprecation")
public class BlockMachine extends DelayedStateBlock {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", Arrays.asList(EnumFacing.VALUES));
    public static final PropertyBool ACTIVE = PropertyBool.create("active");

    public PropertyString META_TYPE;

    public BlockMachine(Collection<String> mteTypeCollection) {
        super(Material.IRON);
        META_TYPE = PropertyString.create("meta_type", mteTypeCollection);
        initBlockState();
        setUnlocalizedName("machine");
        setHardness(6.0f);
        setResistance(8.0f);
        setSoundType(SoundType.METAL);
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
    }

    @SideOnly(Side.CLIENT)
    public void registerStateMapper() {
        ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState blockState) {
                ResourceLocation location = GregTechAPI.METATILEENTITY_REGISTRY.getObject(blockState.getValue(META_TYPE)).getStateLocation();
                Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(blockState.getProperties());
                map.remove(META_TYPE);
                return new ModelResourceLocation(new GTResourceLocation("machines/" + getBlockStateSubFolder() + location.getResourcePath()), getPropertyString(map));
            }
        });
    }

    @SideOnly(Side.CLIENT)
    public void registerItemModel() {
        Streams.stream(GregTechAPI.METATILEENTITY_REGISTRY.getObjectsWithIds())
            .filter(factory -> factory.getBlockClass() == this.getClass())
            .forEach(factory -> {
                ResourceLocation location = GregTechAPI.METATILEENTITY_REGISTRY.getObject(factory.getMetaName()).getStateLocation();
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
                    GregTechAPI.METATILEENTITY_REGISTRY.getIDForObject(factory),
                    new ModelResourceLocation(new GTResourceLocation("machines/" + getBlockStateSubFolder() + location.getResourcePath()), "inventory"));
            });
    }

    protected String getBlockStateSubFolder() {
        return "";
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState()
            .withProperty(META_TYPE, GregTechAPI.METATILEENTITY_REGISTRY.getObjectById(meta).getMetaName());
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        IGregTechTileEntity tileEntity = (IGregTechTileEntity) worldIn.getTileEntity(pos);
        if (tileEntity != null) {
            tileEntity.getMetaTileEntity().onEntityCollidedWithBlock(entityIn);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        IGregTechTileEntity tileEntity = (IGregTechTileEntity) worldIn.getTileEntity(pos);
        if (tileEntity == null) return false;

        IMetaTileEntity metaTileEntity = tileEntity.getMetaTileEntity();
        if (metaTileEntity.isAccessAllowed(playerIn)) {
            if (heldItem.isEmpty()) {
                return metaTileEntity.onRightClick(side, playerIn, hand, hitX, hitY, hitZ);
            } else {
                SimpleItemStack stack = new SimpleItemStack(heldItem);
                if (GregTechAPI.screwdriverList.contains(stack)) {
                    return metaTileEntity.onScrewdriverRightClick(side, playerIn, hand, hitX, hitY, hitZ);
                } else if (GregTechAPI.wrenchList.contains(stack)) {
                    return metaTileEntity.onWrenchRightClick(side, playerIn, hand, hitX, hitY, hitZ);
                } else return metaTileEntity.onRightClick(side, playerIn, hand, hitX, hitY, hitZ);
            }
        }
        return false;
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        IGregTechTileEntity tileEntity = (IGregTechTileEntity) worldIn.getTileEntity(pos);
        if (tileEntity == null) return;
        IMetaTileEntity metaTileEntity = tileEntity.getMetaTileEntity();
        if (metaTileEntity.isAccessAllowed(playerIn)) {
            metaTileEntity.onLeftClick(playerIn);
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        state = this.getActualState(state, world, pos);
        return new ItemStack(MetaBlocks.MACHINE, 1, GregTechAPI.METATILEENTITY_REGISTRY.getIDForObject(GregTechAPI.METATILEENTITY_REGISTRY.getObject(state.getValue(META_TYPE))));
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.BLOCK;
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        IGregTechTileEntity tileEntity = (IGregTechTileEntity) world.getTileEntity(pos);
        if (tileEntity == null) return false;
        return tileEntity.getMetaTileEntity().canConnectRedstone(side);
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        IGregTechTileEntity tileEntity = (IGregTechTileEntity) blockAccess.getTileEntity(pos);
        if (tileEntity == null) return 0;
        return tileEntity.getMetaTileEntity().getOutputRedstoneSignal(side);
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        IGregTechTileEntity tileEntity = (IGregTechTileEntity) worldIn.getTileEntity(pos);
        if (tileEntity == null) return 0;
        return tileEntity.getMetaTileEntity().getComparatorValue();
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }

    @Override
    protected BlockStateContainer createStateContainer() {
        return new BlockStateContainer(this, ACTIVE, FACING, META_TYPE);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity tileentity = worldIn instanceof ChunkCache ? ((ChunkCache) worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);

        if (tileentity == null) return state;
        IMetaTileEntity mte = ((GregtechTileEntity) tileentity).getMetaTileEntity();

        if (mte != null) {
            state = state.withProperty(META_TYPE, mte.getMetaName());
            state = mte.getActualBlockState(mte, state, worldIn, pos);
        }

        return state;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing facing) {
        IGregTechTileEntity tileEntity = (IGregTechTileEntity) world.getTileEntity(pos);
        if (tileEntity == null) {
            return false;
        }
        ITurnable turnable = tileEntity.getMetaTileEntity();
        if (turnable == null) {
            return false;
        }

        if (turnable.isValidFacing(facing)) {
            turnable.setFrontFacing(facing);
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public EnumFacing[] getValidRotations(World world, BlockPos pos) {
        IGregTechTileEntity tileEntity = (IGregTechTileEntity) world.getTileEntity(pos);
        if (tileEntity == null) {
            return null;
        }
        ITurnable turnable = tileEntity.getMetaTileEntity();
        if (turnable == null) {
            return null;
        }

        List<EnumFacing> facingList = new ArrayList<>();
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (turnable.isValidFacing(facing)) {
                facingList.add(facing);
            }
        }

        return facingList.toArray(new EnumFacing[facingList.size()]);
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        return GregTechAPI.METATILEENTITY_REGISTRY.getObject(state.getValue(META_TYPE)).getHarvestTool().getName();
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return GregTechAPI.METATILEENTITY_REGISTRY.getObject(state.getValue(META_TYPE)).getHarvestLevel();
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        IMetaTileEntity tileEntity = GregTechAPI.METATILEENTITY_REGISTRY.getObject(state.getValue(META_TYPE)).constructMetaTileEntity();
        GregtechTileEntity realTileEntity = new GregtechTileEntity();
        realTileEntity.setMetaTileEntity(tileEntity);
        return realTileEntity;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (IMetaTileEntityFactory tileEntityFactory : GregTechAPI.METATILEENTITY_REGISTRY) {
            if (tileEntityFactory.getBlockClass() == this.getClass()) {
                items.add(new ItemStack(this, 1, GregTechAPI.METATILEENTITY_REGISTRY.getIDForObject(tileEntityFactory)));
            }
        }
    }

    public enum ToolClass implements IStringSerializable {

        WRENCH("wrench"),
        AXE("axe"),
        CUTTER("cutter");

        private final String name;

        ToolClass(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

    }

}
