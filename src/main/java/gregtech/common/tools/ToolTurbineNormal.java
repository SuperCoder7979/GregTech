package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolTurbineNormal extends ToolTurbine {

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 2.5F;
    }
}
