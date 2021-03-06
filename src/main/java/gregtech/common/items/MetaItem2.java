package gregtech.common.items;

import com.google.common.base.CaseFormat;
import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.items.metaitem.FoodStats;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.RandomPotionEffect;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.W;
import static gregtech.common.items.MetaItems.*;

public class MetaItem2 extends MaterialMetaItem {

    public MetaItem2() {
        super(OrePrefix.toolHeadSword, OrePrefix.toolHeadPickaxe, OrePrefix.toolHeadShovel, OrePrefix.toolHeadAxe,
                OrePrefix.toolHeadHoe, OrePrefix.toolHeadHammer, OrePrefix.toolHeadFile, OrePrefix.toolHeadSaw,
                OrePrefix.toolHeadDrill, OrePrefix.toolHeadChainsaw, OrePrefix.toolHeadWrench, OrePrefix.toolHeadUniversalSpade,
                OrePrefix.toolHeadSense, OrePrefix.toolHeadPlow,  OrePrefix.toolHeadBuzzSaw, OrePrefix.turbineBlade, 
                OrePrefix.wireFine, OrePrefix.gearSmall, OrePrefix.rotor, OrePrefix.stickLong, OrePrefix.springSmall, OrePrefix.spring,
                OrePrefix.gemChipped, OrePrefix.gemFlawed, OrePrefix.gemFlawless, OrePrefix.gemExquisite, OrePrefix.gear,
                null, null, null, null, null);
    }

    @Override
    public void registerSubItems() {
        boolean drinksAlwaysDrinkable = false;

        THERMOS_CAN_DARK_COFFEE = addItem(0, "thermos_can.dark.coffee").addStats(new FoodStats(2, 0.2F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.SPEED, 400, 1, 70), new RandomPotionEffect(MobEffects.HASTE, 400, 1, 70)));
        THERMOS_CAN_DARK_CAFE_AU_LAIT = addItem(1, "thermos_can.dark.cafe.au.lait").setInvisible().addStats(new FoodStats(2, 0.2F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.SPEED, 400, 2, 90), new RandomPotionEffect(MobEffects.HASTE, 400, 2, 90)));
        THERMOS_CAN_COFFEE = addItem(2, "thermos_can.coffee").addStats(new FoodStats(3, 0.4F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.SPEED, 400, 0, 50), new RandomPotionEffect(MobEffects.HASTE, 400, 0, 50)));
        THERMOS_CAN_CAFE_AU_LAIT = addItem(3, "thermos_can.cafe.au.lait").addStats(new FoodStats(3, 0.4F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.SPEED, 400, 1, 70), new RandomPotionEffect(MobEffects.HASTE, 400, 1, 70)));
        THERMOS_CAN_LAIT_AU_CAFE = addItem(4, "thermos_can.lait.au.cafe").setInvisible().addStats(new FoodStats(3, 0.4F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.SPEED, 400, 2, 90), new RandomPotionEffect(MobEffects.HASTE, 400, 2, 90)));
        THERMOS_CAN_DARK_CHOCOLATE_MILK = addItem(5, "thermos_can.dark.chocolate.milk").addStats(new FoodStats(3, 0.4F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.REGENERATION, 50, 1, 60)));
        THERMOS_CAN_CHOCOLATE_MILK = addItem(6, "thermos_can.chocolate.milk").addStats(new FoodStats(3, 0.4F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.REGENERATION, 50, 1, 90)));
        THERMOS_CAN_TEA = addItem(7, "thermos_can.tea").addStats(new FoodStats(2, 0.2F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.SLOWNESS, 300, 0, 50)));
        THERMOS_CAN_SWEET_TEA = addItem(8, "thermos_can.sweet.tea").setInvisible().addStats(new FoodStats(2, 0.2F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm()));
        THERMOS_CAN_ICE_TEA = addItem(9, "thermos_can.ice.tea").addStats(new FoodStats(2, 0.2F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.SLOWNESS, 300, 0, 50)));

        GELLED_TOLUENE = addItem(10, "gelled_toluene");

        ItemStack emptyBottle = new ItemStack(Items.GLASS_BOTTLE);

        BOTTLE_GLEN_MCKENNER = addItem(117, "bottle.glen_mckenner").setInvisible().addStats(new FoodStats(2, 0.1F, true, drinksAlwaysDrinkable, emptyBottle)); //, new int[]{Potion.confusion.id, 400, 1, 90, Potion.resistance.id, 400, 2, 90, Potion.poison.id, 200, 2, 10, Potion.harm.id, 0, 2, 5}));
        BOTTLE_PURPLE_DRINK = addItem(100, "bottle.purple.drink").addStats(new FoodStats(8, 0.2F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.SLOWNESS, 400, 1, 90)));
        BOTTLE_DIABOLO_SAUCE = addItem(122, "bottle.diabolo.sauce").setInvisible().addStats(new FoodStats(2, 0.1F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 3000, 1, 50), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 3000, 0, 80)));
        BOTTLE_DIABLO_SAUCE = addItem(123, "bottle.diablo.sauce").setInvisible().addStats(new FoodStats(2, 0.1F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 4000, 1, 70), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 4000, 0, 90)));
        BOTTLE_SNITCHES_GLITCH_SAUCE = addItem(124, "bottle.snitches.glitch.sauce").setInvisible().addStats(new FoodStats(2, 0.1F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 9999, 2, 999), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 9999, 9, 999)));
        BOTTLE_NOTCHES_BREW = addItem(130, "bottle.notches.brew").setInvisible().addStats(new FoodStats(4, 0.2F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.REGENERATION, 700, 4, 95), new RandomPotionEffect(MobEffects.ABSORPTION, 3000, 1, 95), new RandomPotionEffect(MobEffects.RESISTANCE, 7000, 1, 95), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 7000, 0, 95), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 20)));
        BOTTLE_CAVE_JOHNSONS_GRENADE_JUICE = addItem(135, "bottle.cave.johnsons.grenade.juice").setInvisible().addStats(new FoodStats(0, 0.0F, true, drinksAlwaysDrinkable, emptyBottle));
        BOTTLE_HOLY_WATER = addItem(137, "bottle.holy.water").setUnificationData(OrePrefix.bottle, Materials.HolyWater).addStats(new FoodStats(0, 0.0F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.POISON, 100, 1, 100)));

        FOOD_CHUM = addItem(210, "food.chum").addStats(new FoodStats(5, 0.2F, false, true, null, new RandomPotionEffect(MobEffects.HUNGER, 1000, 4, 100), new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 80)));
        FOOD_CHUM_ON_STICK = addItem(211, "food.chum.on.stick").addStats(new FoodStats(5, 0.2F, false, true, new ItemStack(Items.STICK, 1), new RandomPotionEffect(MobEffects.HUNGER, 1000, 4, 100), new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 80)));

        DYE_INDIGO = addItem(410, "dye.indigo").addOreDict("dyeBlue");
        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
            DYE_ONLY_ITEMS[i] = addItem(414 + i, "dye." + EnumDyeColor.byMetadata(i).getUnlocalizedName()).addOreDict("dye" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, EnumDyeColor.byMetadata(i).getName()));
        }

        PLANK_OAK = addItem(470, "plank.oak").setBurnValue(75);
        PLANK_SPRUCE = addItem(471, "plank.spruce").setBurnValue(75);
        PLANK_BIRCH = addItem(472, "plank.birch").setBurnValue(75);
        PLANK_JUNGLE = addItem(473, "plank.jungle").setBurnValue(75);
        PLANK_ACACIA = addItem(474, "plank.acacia").setBurnValue(75);
        PLANK_DARKOAK = addItem(475, "plank.darkoak").setBurnValue(75);

        SFMIXTURE = addItem(270, "sfmixture");
        MSFMIXTURE = addItem(271, "msfmixture");

        CROP_DROP_PLUMBILIA = addItem(500, "crop.drop.plumbilia");
        CROP_DROP_ARGENTIA = addItem(501, "crop.drop.argentia");
        CROP_DROP_INDIGO = addItem(502, "crop.drop.indigo");
        CROP_DROP_FERRU = addItem(503, "crop.drop.ferru");
        CROP_DROP_AURELIA = addItem(504, "crop.drop.aurelia");
        CROP_DROP_TEALEAF = addItem(505, "crop.drop.tealeaf").addOreDict("cropTea");

        CROP_DROP_OIL_BERRY = addItem(510, "crop.drop.oil_berry");
        CROP_DROP_BOBS_YER_UNCLE_RANKS = addItem(511, "crop.drop.bobs_yer_uncle_ranks");
        CROP_DROP_UUM_BERRY = addItem(512, "crop.drop.uum_berry");
        CROP_DROP_UUA_BERRY = addItem(513, "crop.drop.uua_berry");

        CROP_DROP_MILK_WART = addItem(520, "crop.drop.milk_wart");

        CROP_DROP_COPPON = addItem(530, "crop.drop.coppon");
        CROP_DROP_TINE = addItem(540, "crop.drop.tine").setBurnValue(100);
        CROP_DROP_BAUXITE = addItem(521, "crop.drop.bauxite");
        CROP_DROP_ILMENITE = addItem(522, "crop.drop.ilmenite");
        CROP_DROP_PITCHBLENDE = addItem(523, "crop.drop.pitchblende");
        CROP_DROP_URANINITE = addItem(524, "crop.drop.uraninite");
        CROP_DROP_THORIUM = addItem(526, "crop.drop.thorium");
        CROP_DROP_NICKEL = addItem(527, "crop.drop.nickel");
        CROP_DROP_ZINC = addItem(528, "crop.drop.zinc");
        CROP_DROP_MANGANESE = addItem(529, "crop.drop.manganese");
        CROP_DROP_SCHEELITE = addItem(531, "crop.drop.scheelite");
        CROP_DROP_PLATINUM = addItem(532, "crop.drop.platinum");
        CROP_DROP_IRIDIUM = addItem(533, "crop.drop.iridium");
        CROP_DROP_OSMIUM = addItem(534, "crop.drop.osmium");
        CROP_DROP_NAQUADAH = addItem(535, "crop.drop.naquadah");

        CROP_DROP_CHILLY = addItem(550, "crop.drop.chilly").addOreDict("cropChilipepper").addStats(new FoodStats(1, 0.3F, false, false, null, new RandomPotionEffect(MobEffects.NAUSEA, 200, 1, 40)));
        CROP_DROP_LEMON = addItem(551, "crop.drop.lemon").addOreDict("cropLemon").addStats(new FoodStats(1, 0.3F));
        CROP_DROP_TOMATO = addItem(552, "crop.drop.tomato").addOreDict("cropTomato").addStats(new FoodStats(1, 0.2F));
        CROP_DROP_MTOMATO = addItem(553, "crop.drop.mtomato").addOreDict("cropTomato").addStats(new FoodStats(9, 1.0F, false, false, null, new RandomPotionEffect(MobEffects.REGENERATION, 100, 100, 100)));
        CROP_DROP_GRAPES = addItem(554, "crop.drop.grapes").addOreDict("cropGrape").addStats(new FoodStats(2, 0.3F));
        CROP_DROP_ONION = addItem(555, "crop.drop.onion").addOreDict("cropOnion").addStats(new FoodStats(2, 0.2F));
        CROP_DROP_CUCUMBER = addItem(556, "crop.drop.cucumber").addOreDict("cropCucumber").addStats(new FoodStats(1, 0.2F));
    }

    public void registerRecipes() {

        // Dyes recipes
        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 0))
            .outputs(new ItemStack(Items.DYE, 2, 1))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 1))
            .outputs(new ItemStack(Items.DYE, 2, 12))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 2))
            .outputs(new ItemStack(Items.DYE, 2, 13))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 3))
            .outputs(new ItemStack(Items.DYE, 2, 7))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 4))
            .outputs(new ItemStack(Items.DYE, 2, 1))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 5))
            .outputs(new ItemStack(Items.DYE, 2, 14))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 6))
            .outputs(new ItemStack(Items.DYE, 2, 7))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 7))
            .outputs(new ItemStack(Items.DYE, 2, 9))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 8))
            .outputs(new ItemStack(Items.DYE, 2, 7))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.YELLOW_FLOWER, 1, 0))
            .outputs(new ItemStack(Items.DYE, 2, 11))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0))
            .outputs(new ItemStack(Items.DYE, 3, 11))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.DOUBLE_PLANT, 1, 1))
            .outputs(new ItemStack(Items.DYE, 3, 13))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.DOUBLE_PLANT, 1, 4))
            .outputs(new ItemStack(Items.DYE, 3, 1))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.DOUBLE_PLANT, 1, 5))
            .outputs(new ItemStack(Items.DYE, 3, 9))
            .buildAndRegister();

        // Crops recipes
        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_PLUMBILIA.getStackForm())
            .outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Lead, 1))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_ARGENTIA.getStackForm())
            .outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Silver, 1))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_INDIGO.getStackForm())
            .outputs(DYE_INDIGO.getStackForm())
            .buildAndRegister();

//        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
//            .inputs(CROP_DROP_MILK_WART.getStackForm())
//            .outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Milk))
//            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_COPPON.getStackForm())
            .outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Copper))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_TINE.getStackForm())
            .outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Tin))
            .buildAndRegister();

        RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_COPPON.getStackForm(4))
            .outputs(new ItemStack(Blocks.WOOL, 1, 1))
            .buildAndRegister();

        // Misc
        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.DYE, 1, EnumDyeColor.BROWN.getDyeDamage()))
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Cocoa, 1))
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_TINE.getStackForm())
            .outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Wood, 2))
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.REEDS, 1))
            .outputs(new ItemStack(Items.SUGAR, 1))
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.MELON_BLOCK, 1, 0))
            .outputs(new ItemStack(Items.MELON, 8, 0))
            .chancedOutput(new ItemStack(Items.MELON_SEEDS, 1), 8000)
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.PUMPKIN, 1, 0))
            .outputs(new ItemStack(Items.PUMPKIN_SEEDS, 4, 0))
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.MELON, 1, 0))
            .outputs(new ItemStack(Items.MELON_SEEDS, 1, 0))
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.WHEAT, 1, 0))
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wheat, 1))
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.STICK, 1))
            .outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Wood, 2))
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.WOOL, 1, W))
            .outputs(new ItemStack(Items.STRING, 2))
            .chancedOutput(new ItemStack(Items.STRING, 1), 5000)
            .duration(400)
            .EUt(2)
            .buildAndRegister();
    }

    @Override
    public final ItemStack getContainerItem(ItemStack stack) {
        int damage = stack.getItemDamage();
        if (damage < metaItemOffset) {
            return ItemStack.EMPTY;
        }
        if (damage < metaItemOffset + 100) {
            return THERMOS_CAN_EMPTY.getStackForm();
        }
        if (damage < metaItemOffset + 200) {
            return new ItemStack(Items.GLASS_BOTTLE);
        }
        return ItemStack.EMPTY;
    }
}