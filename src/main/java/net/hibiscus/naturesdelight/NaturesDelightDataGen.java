package net.hibiscus.naturesdelight;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.hibiscus.naturespirit.registration.NSRegistryHelper;
import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class NaturesDelightDataGen implements DataGeneratorEntrypoint {
   @Override public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
      FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
      pack.addProvider(NaturesDelightDataGen.NaturesDelightModelGenerator::new);
      pack.addProvider(NaturesDelightDataGen.NaturesDelightLangGenerator::new);
      pack.addProvider(NaturesDelightDataGen.NaturesDelightRecipeGenerator::new);
      pack.addProvider(NaturesDelightDataGen.NaturesDelightBlockLootTableProvider::new);
      NaturesDelightDataGen.NaturesDelightBlockTagGenerator blockTagProvider = pack.addProvider(NaturesDelightBlockTagGenerator::new);
      pack.addProvider((output, registries) -> new NaturesDelightItemTagGenerator(output, registries, blockTagProvider));
      System.out.println("Initialized Data Generator");
   }



   public static class NaturesDelightBlockTagGenerator extends FabricTagProvider.BlockTagProvider {
      public NaturesDelightBlockTagGenerator(FabricDataOutput output, CompletableFuture <RegistryWrapper.WrapperLookup> registriesFuture) {
         super(output, registriesFuture);
      }

      protected void configure(RegistryWrapper.WrapperLookup arg) {
         for(Block block: NaturesDelightBlocksAndItems.cabinets) {
            this.getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(block);
         }
         this.getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(NaturesDelightBlocksAndItems.DESERT_TURNIP_CRATE_BLOCK);
      }
   }

   public static class NaturesDelightItemTagGenerator extends FabricTagProvider.ItemTagProvider {
      public NaturesDelightItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, @Nullable FabricTagProvider.@Nullable BlockTagProvider blockTagProvider) {
         super(output, completableFuture, blockTagProvider);
      }

      protected void configure(RegistryWrapper.WrapperLookup arg) {
         for(Block block: NaturesDelightBlocksAndItems.cabinets) {
            Item item = block.asItem();
            this.getOrCreateTagBuilder(ModTags.WOODEN_CABINETS).add(item);
         }
      }
   }

   public static class NaturesDelightRecipeGenerator extends FabricRecipeProvider {

      public NaturesDelightRecipeGenerator(FabricDataOutput output) {
         super(output);
      }
      public static void offerShapelessRecipe(Consumer <RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input, @Nullable String group, int outputCount) {
         ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, output, outputCount).input(input).group(group).criterion(RecipeProvider.hasItem(input), RecipeProvider.conditionsFromItem(input)).offerTo(exporter, new Identifier("natures_delight", RecipeProvider.convertBetween(output, input)));
      }
      public static void createCabinetRecipe(Consumer <RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input, ItemConvertible input2) {
         ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output).pattern("___").pattern("D D").pattern("___").input('_', input).input('D', input2).criterion(hasItem(input2), conditionsFromItem(input2)).offerTo(exporter, new Identifier(NaturesDelight.MOD_ID, RecipeProvider.getRecipeName(output)));
      }
      protected Identifier getRecipeIdentifier(Identifier identifier) {
         return new Identifier("natures_delight", identifier.getPath());
      }

      public void generate(Consumer<RecipeJsonProvider> exporter) {
         for(Block block: NaturesDelightBlocksAndItems.cabinets) {
            WoodSet woodSet = NSRegistryHelper.WoodHashMap.get(StringUtils.removeEnd(Registries.BLOCK.getId(block).getPath(), "_cabinet"));
            createCabinetRecipe(exporter, block, woodSet.getSlab(), woodSet.getTrapDoor());
         }
      }
   }

   private static class NaturesDelightLangGenerator extends FabricLanguageProvider {
      protected NaturesDelightLangGenerator(FabricDataOutput dataOutput) {
         super(dataOutput);
      }

      public static String capitalizeString(String string) {
         char[] chars = string.toLowerCase().toCharArray();
         boolean found = false;

         for(int i = 0; i < chars.length; ++i) {
            if (!found && Character.isLetter(chars[i])) {
               chars[i] = Character.toUpperCase(chars[i]);
               found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') {
               found = false;
            }
         }

         return String.valueOf(chars);
      }

      private void generateBlockTranslations(Block block, FabricLanguageProvider.TranslationBuilder translationBuilder) {
         String temp = capitalizeString(Registries.BLOCK.getId(block).getPath().replace("_", " "));
         translationBuilder.add(block, temp);
      }

      private void generateItemTranslations(Item item, FabricLanguageProvider.TranslationBuilder translationBuilder) {
         String temp = capitalizeString(Registries.ITEM.getId(item).getPath().replace("_", " "));
         translationBuilder.add(item, temp);
      }

      public void generateTranslations(FabricLanguageProvider.TranslationBuilder translationBuilder) {
         this.generateBlockTranslations(NaturesDelightBlocksAndItems.DESERT_TURNIP_CRATE_BLOCK, translationBuilder);
         this.generateBlockTranslations(NaturesDelightBlocksAndItems.MANAKISH_BLOCK, translationBuilder);
         translationBuilder.add(NaturesDelightBlocksAndItems.MANAKISH_SLICE_ITEM, "Slice of Manakish");
         this.generateItemTranslations(NaturesDelightBlocksAndItems.COCADA_ITEM, translationBuilder);
         this.generateItemTranslations(NaturesDelightBlocksAndItems.ALFREDO_PASTA_ITEM, translationBuilder);
         this.generateItemTranslations(NaturesDelightBlocksAndItems.TURNIP_TAGINE_ITEM, translationBuilder);
         this.generateItemTranslations(NaturesDelightBlocksAndItems.COCONUT_SAUCE_ITEM, translationBuilder);
         this.generateItemTranslations(NaturesDelightBlocksAndItems.SWEET_AND_SAVORY_SAUTE_ITEM, translationBuilder);
         this.generateItemTranslations(NaturesDelightBlocksAndItems.FAFARU_ITEM, translationBuilder);
         this.generateItemTranslations(NaturesDelightBlocksAndItems.COCONUT_PANCAKES_ITEM, translationBuilder);
         this.generateItemTranslations(NaturesDelightBlocksAndItems.COCONUT_BREAD_ITEM, translationBuilder);
         this.generateBlockTranslations(NaturesDelightBlocksAndItems.SHIITAKE_MUSHROOM_COLONY_BLOCK, translationBuilder);
         translationBuilder.add("block.natures_spirit.pizza.farmersdelight.onion", "With Onions");
         translationBuilder.add("block.natures_spirit.pizza.farmersdelight.tomato", "With Tomatoes");
         translationBuilder.add("block.natures_spirit.pizza.farmersdelight.cooked_bacon", "With Cooked Bacon");
         translationBuilder.add("block.natures_spirit.pizza.farmersdelight.cabbage", "With Cabbage");
         translationBuilder.add("block.natures_spirit.pizza.farmersdelight.cabbage_leaf", "With Chopped Cabbage");
         translationBuilder.add("block.natures_spirit.pizza.farmersdelight.cooked_chicken_cuts", "With Chopped Chicken");
         translationBuilder.add("block.natures_spirit.pizza.farmersdelight.cooked_cod_slice", "With Chopped Cod");
         for(Block block: NaturesDelightBlocksAndItems.cabinets) {
            this.generateBlockTranslations(block, translationBuilder);
         }
      }
   }

   private static class NaturesDelightModelGenerator extends FabricModelProvider {

      public NaturesDelightModelGenerator(FabricDataOutput output) {
         super(output);
      }
      public static TextureMap sideFrontOpenTop(Block block) {
         return (new TextureMap()).put(TextureKey.SIDE, TextureMap.getSubId(block, "_side")).put(TextureKey.FRONT, TextureMap.getSubId(block, "_front_open")).put(TextureKey.TOP, TextureMap.getSubId(block, "_top"));
      }
      public static BlockStateSupplier createCabinetBlockState(Block cabinet, Identifier closedId, Identifier openId) {
         return VariantsBlockStateSupplier.create(cabinet).coordinate(BlockStateVariantMap.create(CabinetBlock.OPEN, CabinetBlock.FACING)
                     .register(false, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, closedId).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                     .register(false, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, closedId).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                     .register(false, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, closedId))
                     .register(false, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, closedId).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                     .register(true, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, openId).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                     .register(true, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, openId).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                     .register(true, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, openId))
                     .register(true, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, openId).put(VariantSettings.Y, VariantSettings.Rotation.R180))
         );
      }
      public void registerCabinets(Block cabinet, BlockStateModelGenerator blockStateModelGenerator) {
         Identifier closedId = Models.ORIENTABLE.upload(cabinet, TextureMap.sideFrontTop(cabinet), blockStateModelGenerator.modelCollector);
         Identifier openId = Models.ORIENTABLE.upload(cabinet, "_open", sideFrontOpenTop(cabinet), blockStateModelGenerator.modelCollector);
         blockStateModelGenerator.blockStateCollector.accept(createCabinetBlockState(cabinet, closedId, openId));
      }
      public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
         for(Block block: NaturesDelightBlocksAndItems.cabinets) {
            registerCabinets(block, blockStateModelGenerator);
         }
      }

      public void generateItemModels(ItemModelGenerator itemModelGenerator) {

         itemModelGenerator.register(NaturesDelightBlocksAndItems.COCADA_ITEM, Models.GENERATED);
         itemModelGenerator.register(NaturesDelightBlocksAndItems.ALFREDO_PASTA_ITEM, Models.GENERATED);
         itemModelGenerator.register(NaturesDelightBlocksAndItems.TURNIP_TAGINE_ITEM, Models.GENERATED);
         itemModelGenerator.register(NaturesDelightBlocksAndItems.COCONUT_SAUCE_ITEM, Models.GENERATED);
         itemModelGenerator.register(NaturesDelightBlocksAndItems.SWEET_AND_SAVORY_SAUTE_ITEM, Models.GENERATED);
         itemModelGenerator.register(NaturesDelightBlocksAndItems.FAFARU_ITEM, Models.GENERATED);
         itemModelGenerator.register(NaturesDelightBlocksAndItems.COCONUT_PANCAKES_ITEM, Models.GENERATED);
         itemModelGenerator.register(NaturesDelightBlocksAndItems.COCONUT_BREAD_ITEM, Models.GENERATED);
      }
   }

   private static class NaturesDelightBlockLootTableProvider extends FabricBlockLootTableProvider {
      private static final LootCondition.Builder WITH_SILK_TOUCH_OR_SHEARS;

      protected NaturesDelightBlockLootTableProvider(FabricDataOutput dataOutput) {
         super(dataOutput);
      }

      public void generate() {
         for(Block block: NaturesDelightBlocksAndItems.cabinets) {
            addDrop(block, nameableContainerDrops(block));
         }
         System.out.println("YOUR GAY");
         addDrop(NaturesDelightBlocksAndItems.DESERT_TURNIP_CRATE_BLOCK);
      }

      static {
         WITH_SILK_TOUCH_OR_SHEARS = WITH_SHEARS.or(WITH_SILK_TOUCH);
      }
   }
}
