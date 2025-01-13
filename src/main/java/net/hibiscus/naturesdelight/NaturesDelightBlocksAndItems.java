package net.hibiscus.naturesdelight;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.impl.biome.modification.BuiltInRegistryKeys;
import net.hibiscus.naturespirit.config.NSConfig;
import net.hibiscus.naturespirit.registration.NSItemGroups;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.hibiscus.naturespirit.registration.NSRegistryHelper;
import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BlockEntityType.Builder;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.block.entity.CabinetBlockEntity;
import vectorwing.farmersdelight.common.item.FuelBlockItem;
import vectorwing.farmersdelight.common.item.MushroomColonyItem;
import vectorwing.farmersdelight.common.registry.ModCreativeTabs;
import vectorwing.farmersdelight.common.registry.ModEffects;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.ArrayList;

public class NaturesDelightBlocksAndItems {
   public static final ArrayList<Block> cabinets = new ArrayList<>();

   public static final  Block DESERT_TURNIP_CRATE_BLOCK = registerBlock("desert_turnip_crate",
           new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
   public static final  Block SHIITAKE_MUSHROOM_COLONY_BLOCK = registerBlock("shiitake_mushroom_colony",
           new MushroomColonyBlock(NSMiscBlocks.SHIITAKE_MUSHROOM.asItem().getRegistryEntry(), AbstractBlock.Settings.copy(NSMiscBlocks.SHIITAKE_MUSHROOM)));
   public static final  Item DESERT_TURNIP_CRATE_ITEM = registerItem("desert_turnip_crate",
           new BlockItem(DESERT_TURNIP_CRATE_BLOCK, ModItems.basicItem()), NSItemGroups.NS_ITEM_GROUP, NSMiscBlocks.DESERT_TURNIP_BLOCK.asItem());
   public static final  Item SHIITAKE_MUSHROOM_COLONY_ITEM = registerItem("shiitake_mushroom_colony",
           new MushroomColonyItem(SHIITAKE_MUSHROOM_COLONY_BLOCK, ModItems.basicItem()), NSItemGroups.NS_ITEM_GROUP, NSMiscBlocks.SHIITAKE_MUSHROOM.asItem());

   public static final Block MANAKISH_BLOCK = registerBlock("manakish", new ManakishBlock(AbstractBlock.Settings.copy(Blocks.CAKE)));
   public static final Item MANAKISH_ITEM = registerItem("manakish", new BlockItem(MANAKISH_BLOCK, ModItems.basicItem()), NSItemGroups.NS_ITEM_GROUP, NSMiscBlocks.WHOLE_PIZZA);

   public static final FoodComponent MANAKISH_SLICE = (new FoodComponent.Builder()).nutrition(3).saturationModifier(0.3f).snack().build();
   public static final Item MANAKISH_SLICE_ITEM = registerItem("manakish_slice", new Item(ModItems.foodItem(MANAKISH_SLICE)), NSItemGroups.NS_ITEM_GROUP, MANAKISH_ITEM);
   public static final FoodComponent ALFREDO_PASTA = (new FoodComponent.Builder())
           .nutrition(12).saturationModifier(0.8f)
           .statusEffect(new StatusEffectInstance(ModEffects.NOURISHMENT, FoodValues.SHORT_DURATION, 0), 1.0F).build();
   public static final Item ALFREDO_PASTA_ITEM = registerItem("alfredo_pasta", new Item(ModItems.bowlFoodItem(ALFREDO_PASTA)), NSItemGroups.NS_ITEM_GROUP, MANAKISH_SLICE_ITEM);
   public static final FoodComponent TURNIP_TAGINE = (new FoodComponent.Builder())
           .nutrition(12).saturationModifier(0.70f)
           .statusEffect(new StatusEffectInstance(ModEffects.COMFORT, FoodValues.MEDIUM_DURATION, 0), 1.0F).build();
   public static final Item TURNIP_TAGINE_ITEM = registerItem("turnip_tagine", new Item(ModItems.bowlFoodItem(TURNIP_TAGINE)), NSItemGroups.NS_ITEM_GROUP, ALFREDO_PASTA_ITEM);
   public static final Item COCONUT_SAUCE_ITEM = registerItem("coconut_sauce", new Item(ModItems.bowlFoodItem(FoodValues.TOMATO_SAUCE)), NSItemGroups.NS_ITEM_GROUP, TURNIP_TAGINE_ITEM);
   public static final FoodComponent SWEET_AND_SAVORY_SAUTE = (new FoodComponent.Builder())
           .nutrition(14).saturationModifier(0.80f)
           .statusEffect(new StatusEffectInstance(ModEffects.NOURISHMENT, FoodValues.LONG_DURATION, 0), 1.0F).build();
   public static final Item SWEET_AND_SAVORY_SAUTE_ITEM = registerItem("sweet_and_savory_saute", new Item(ModItems.bowlFoodItem(SWEET_AND_SAVORY_SAUTE)), NSItemGroups.NS_ITEM_GROUP, COCONUT_SAUCE_ITEM);
   public static final FoodComponent FAFARU = (new FoodComponent.Builder())
           .nutrition(14).saturationModifier(0.8f)
           .statusEffect(new StatusEffectInstance(ModEffects.COMFORT, FoodValues.LONG_DURATION, 0), 1.0F).build();
   public static final Item FAFARU_ITEM = registerItem("fafaru", new Item(ModItems.bowlFoodItem(FAFARU)), NSItemGroups.NS_ITEM_GROUP, SWEET_AND_SAVORY_SAUTE_ITEM);
   public static final FoodComponent COCONUT_PANCAKES = (new FoodComponent.Builder())
           .nutrition(10).saturationModifier(0.6f)
           .statusEffect(new StatusEffectInstance(ModEffects.NOURISHMENT, FoodValues.SHORT_DURATION, 0), 1.0F).build();
   public static final Item COCONUT_PANCAKES_ITEM = registerItem("coconut_pancakes", new Item(ModItems.bowlFoodItem(COCONUT_PANCAKES)), NSItemGroups.NS_ITEM_GROUP, FAFARU_ITEM);
   public static final FoodComponent COCONUT_BREAD = (new FoodComponent.Builder())
           .nutrition(8).saturationModifier(0.6f).build();
   public static final Item COCONUT_BREAD_ITEM = registerItem("coconut_bread", new Item(ModItems.foodItem(COCONUT_BREAD)), NSItemGroups.NS_ITEM_GROUP, COCONUT_PANCAKES_ITEM);
   public static final FoodComponent COCADA = (new FoodComponent.Builder())
           .nutrition(5).saturationModifier(0.7f).statusEffect(new StatusEffectInstance(StatusEffects.SPEED, FoodValues.BRIEF_DURATION, 0), 0.5F).build();
   public static final Item COCADA_ITEM = registerItem("cocada", new Item(ModItems.foodItem(COCADA)), NSItemGroups.NS_ITEM_GROUP, COCONUT_BREAD_ITEM);

   public static void registerBlocksAndItems() {
      for (WoodSet woodSet : NSRegistryHelper.WoodHashMap.values()) {
         Block block = registerBlock(woodSet.getName() + "_cabinet", new CabinetBlock(AbstractBlock.Settings.copy(Blocks.BARREL)));
         registerItem(woodSet.getName() + "_cabinet", new FuelBlockItem(block, ModItems.basicItem(), 300), NSItemGroups.NS_ITEM_GROUP, woodSet.getPlanks().asItem());
         cabinets.add(block);
      }
      registerBlockEntity("cabinet", Builder.create(CabinetBlockEntity::new, cabinets.toArray(new Block[0])));
   }
   public static Block registerBlock(String name, Block block) {
      return Registry.register(Registries.BLOCK, Identifier.of(NaturesDelight.MOD_ID, name), block);
   }
   public static Item registerItem(String name, Item item) {
      return Registry.register(Registries.ITEM, Identifier.of(NaturesDelight.MOD_ID, name), item);
   }
   public static Item registerItem(String name, Item item, RegistryKey<ItemGroup> group, Item itemBefore) {
      Item item1 = registerItem(name, item);
      if (NSConfig.creative_tab) ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.addAfter(itemBefore, item1));
      ItemGroupEvents.modifyEntriesEvent(RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(FarmersDelight.MODID, "farmersdelight"))).register(entries -> entries.add(item1));
      return item1;
   }
   public static <T extends BlockEntity> void registerBlockEntity(String name, BlockEntityType.Builder <T> factory) {
      Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(NaturesDelight.MOD_ID, name), factory.build());
   }
}
