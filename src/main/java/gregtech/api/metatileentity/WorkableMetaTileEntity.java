package gregtech.api.metatileentity;

import gregtech.api.capability.internal.IWorkable;
import gregtech.api.metatileentity.factory.WorkableMetaTileEntityFactory;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.common.blocks.machines.BlockMachine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public abstract class WorkableMetaTileEntity extends TieredMetaTileEntity implements IWorkable {

    public final RecipeMap<?> recipeMap;
    protected Recipe previousRecipe;

    protected int progressTime;
    protected int maxProgressTime;
    protected int recipeEUt;
    protected List<FluidStack> fluidOutputs;
    protected NonNullList<ItemStack> itemOutputs;

    private boolean isActive;
    private boolean workingEnabled = true;

    public WorkableMetaTileEntity(WorkableMetaTileEntityFactory<WorkableMetaTileEntity> factory) {
        super(factory);
        this.recipeMap = factory.getRecipeMap();
    }

    @Override
    public void onPostTick(long tickTimer) {
        super.onPostTick(tickTimer);
        if (getWorld().isRemote) {
            return;
        }
        if(progressTime == 0) {
            long maxVoltage = Math.max(getInputVoltage(), getOutputVoltage());
            Recipe pickedRecipe = recipeMap.findRecipe(previousRecipe, maxVoltage, importItems, importFluids);
            if(pickedRecipe != null && setupAndConsumeRecipeInputs(pickedRecipe)) {
                if(pickedRecipe.canBeBuffered()) {
                    this.previousRecipe = pickedRecipe;
                } else this.previousRecipe = null;
                setupRecipe(pickedRecipe);
            }
        } else if(workingEnabled) {
            if(getEnergyStored() >= recipeEUt) {
                setEnergyStored(getEnergyStored() - recipeEUt);
                if(++progressTime >= maxProgressTime) {
                    completeRecipe();
                }
            }
        }
    }

    protected boolean setupAndConsumeRecipeInputs(Recipe recipe) {
        int totalEUt = recipe.getEUt() * recipe.getDuration();
        return (totalEUt >= 0 ? getEnergyStored() >= totalEUt : getEnergyStored() - totalEUt <= getEnergyCapacity()) &&
                addItemsToItemHandler(exportItems, true, recipe.getOutputs()) &&
                addFluidsToFluidHandler(exportFluids, true, recipe.getFluidOutputs()) &&
                recipe.matches(true, false, importItems, importFluids);
    }

    protected void setupRecipe(Recipe recipe) {
        this.progressTime = 1;
        setMaxProgress(recipe.getDuration());
        this.recipeEUt = recipe.getEUt();
        this.fluidOutputs = recipe.getFluidOutputs();
        this.itemOutputs = recipe.getOutputs();
        setActive(true);
    }

    protected void completeRecipe() {
        addItemsToItemHandler(exportItems, false, itemOutputs);
        addFluidsToFluidHandler(exportFluids, false, fluidOutputs);
        this.progressTime = 0;
        setMaxProgress(0);
        this.recipeEUt = 0;
        this.fluidOutputs = null;
        this.itemOutputs = null;
        setActive(false);
    }

    @Override
    public int getProgress() {
        return progressTime;
    }

    @Override
    public int getMaxProgress() {
        return maxProgressTime;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgressTime = maxProgress;
        if(!getWorld().isRemote) {
            markDirty();
            holder.writeCustomData(5, buf -> buf.writeInt(maxProgress));
        }
    }

    @Override
    public void increaseProgress(int progress) {
        this.progressTime = Math.min(progressTime + progress, maxProgressTime);
    }

    @Override
    public boolean hasWorkToDo() {
        return progressTime > 0;
    }

    @Override
    public void enableWorking() {
        this.workingEnabled = true;
        markDirty();
    }

    @Override
    public void disableWorking() {
        this.workingEnabled = false;
        markDirty();
    }

    @Override
    public boolean isAllowedToWork() {
        return workingEnabled;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean active) {
        this.isActive = active;
        if(!getWorld().isRemote) {
            markDirty();
            holder.writeCustomData(4, buf -> buf.writeBoolean(active));
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        switch (dataId) {
            case 4:
                this.isActive = buf.readBoolean();
                markBlockForRenderUpdate(); // FIXME this rerenders chunk every time recipe is completed
                break;
            case 5:
                this.maxProgressTime = buf.readInt();
                break;
            default:
                super.receiveCustomData(dataId, buf);
        }
    }

    @Override
    public void writeInitialData(PacketBuffer buf) {
        super.writeInitialData(buf);
        buf.writeBoolean(isActive);
    }

    @Override
    public void receiveInitialData(PacketBuffer buf) {
        super.receiveInitialData(buf);
        this.isActive = buf.readBoolean();
    }

    @Override
    public IBlockState getActualBlockState(IMetaTileEntity metaTileEntity, IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        state = super.getActualBlockState(metaTileEntity, state, worldIn, pos);
        if (metaTileEntity instanceof IWorkable) {
            state = state.withProperty(BlockMachine.ACTIVE, ((IWorkable) metaTileEntity).isActive());
        }
        return state;
    }

    @Override
    public void saveNBTData(NBTTagCompound data) {
        super.saveNBTData(data);
        data.setBoolean("Active", this.isActive);
        data.setBoolean("WorkEnabled", this.workingEnabled);
        if(progressTime > 0) {
            data.setInteger("Progress", progressTime);
            data.setInteger("MaxProgress", maxProgressTime);
            data.setInteger("RecipeEUt", this.recipeEUt);
            NBTTagList itemOutputsList = new NBTTagList();
            for(ItemStack itemOutput : itemOutputs) {
                itemOutputsList.appendTag(itemOutput.writeToNBT(new NBTTagCompound()));
            }
            NBTTagList fluidOutputsList = new NBTTagList();
            for(FluidStack fluidOutput : fluidOutputs) {
                fluidOutputsList.appendTag(fluidOutput.writeToNBT(new NBTTagCompound()));
            }
            data.setTag("ItemOutputs", itemOutputsList);
            data.setTag("FluidOutputs", fluidOutputsList);
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound data) {
        super.loadNBTData(data);
        this.isActive = data.getBoolean("Active");
        this.workingEnabled = data.getBoolean("WorkEnabled");
        this.progressTime = data.getInteger("Progress");
        if (progressTime > 0) {
            this.maxProgressTime = data.getInteger("MaxProgress");
            this.recipeEUt = data.getInteger("RecipeEUt");
            NBTTagList itemOutputsList = data.getTagList("ItemOutputs", Constants.NBT.TAG_COMPOUND);
            this.itemOutputs = NonNullList.create();
            for(int i = 0; i < itemOutputsList.tagCount(); i++) {
                this.itemOutputs.add(new ItemStack(itemOutputsList.getCompoundTagAt(i)));
            }
            NBTTagList fluidOutputsList = data.getTagList("FluidOutputs", Constants.NBT.TAG_COMPOUND);
            this.fluidOutputs = new ArrayList<>();
            for(int i = 0; i < fluidOutputsList.tagCount(); i++) {
                this.fluidOutputs.add(FluidStack.loadFluidStackFromNBT(fluidOutputsList.getCompoundTagAt(i)));
            }
        }
    }

}
