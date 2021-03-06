package gregtech.api.metatileentity.factory;

import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.WorkableMetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.common.blocks.machines.BlockMachine;
import net.minecraft.util.ResourceLocation;

public class WorkableMetaTileEntityFactory<T extends WorkableMetaTileEntity> extends TieredMetaTileEntityFactory<T> {

    protected final RecipeMap<?> recipeMap;

    public WorkableMetaTileEntityFactory(BlockMachine.ToolClass toolClass, int harvestLevel, String[] description, Class<T> metaTileEntityClass, ResourceLocation modelLocation, int tier, RecipeMap<?> recipeMap) {
        super(toolClass, harvestLevel, description, metaTileEntityClass, modelLocation, tier);
        this.recipeMap = recipeMap;
    }

    @Override
    public IMetaTileEntity constructMetaTileEntity() {
        try {
            return metaTileEntityClass.getConstructor(WorkableMetaTileEntityFactory.class).newInstance(this);
        } catch (Throwable exception) {
            throw new RuntimeException(exception);
        }
    }

    public RecipeMap<?> getRecipeMap() {
        return recipeMap;
    }
}
