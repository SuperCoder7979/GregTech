package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.W;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_WORKING;
import static gregtech.api.unification.material.type.Material.MatFlags.NO_UNIFICATION;

public class ProcessingGear implements IOreRegistrationHandler {

    private ProcessingGear() {}

	public static void register() {
        ProcessingGear processing = new ProcessingGear();
		OrePrefix.gear.addProcessingHandler(processing);
		OrePrefix.gearSmall.addProcessingHandler(processing);
	}

	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		if(entry.material instanceof SolidMaterial) {
			SolidMaterial material = (SolidMaterial) entry.material;
			ItemStack stack = simpleStack.asItemStack();
			boolean isSmall = entry.orePrefix == OrePrefix.gearSmall;

			if(material.hasFlag(DustMaterial.MatFlags.SMELT_INTO_FLUID)) {
                RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                        .notConsumable(isSmall ? MetaItems.SHAPE_MOLD_GEAR_SMALL : MetaItems.SHAPE_MOLD_GEAR)
                        .fluidInputs(material.getFluid(L * (isSmall ? 1 : 4)))
                        .outputs(stack)
                        .duration(isSmall ? 20 : 100)
                        .EUt(8)
                        .buildAndRegister();
            }

            if(isSmall) {
			    if(material instanceof MetalMaterial && !material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                    ModHandler.addShapedRecipe("sgear_" + material,
                            stack,
							"h##",
							"#P#",
                            'P', OreDictUnifier.get(OrePrefix.plate, material));
                }
            } else {
			    ModHandler.addShapedRecipe("gear_" + material,
                        stack,
						"RPR",
						"PdP",
						"RPR",
                        'P', OreDictUnifier.get(OrePrefix.plate, material),
                        'R', OreDictUnifier.get(OrePrefix.stick, material));
            }

		}
	}

}
