package net.hibiscus.naturesdelight;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import vectorwing.farmersdelight.common.Configuration;

import java.util.ArrayList;
import java.util.List;
public class NaturesDelightVillageStructures {

   public static void init(){
      ServerLifecycleEvents.SERVER_STARTED.register(NaturesDelightVillageStructures::addNewVillageBuilding);
   }

   public static void addNewVillageBuilding(MinecraftServer server) {
      if (!Configuration.GENERATE_VILLAGE_COMPOST_HEAPS.get()) {
         return;
      }

      Registry <StructurePool> templatePools = server.getRegistryManager().getOptional(RegistryKeys.TEMPLATE_POOL).get();
      Registry<StructureProcessorList> processorLists = server.getRegistryManager().getOptional(RegistryKeys.PROCESSOR_LIST).get();

      addBuildingToPool(templatePools, processorLists, Identifier.of("natures_spirit:village/cypress/houses"), NaturesDelight.MOD_ID + ":village/houses/cypress_compost_pile", Identifier.of("natures_spirit", "lime_kaolin"), 3);
      addBuildingToPool(templatePools, processorLists, Identifier.of("natures_spirit:village/cypress/houses"), NaturesDelight.MOD_ID + ":village/houses/cypress_compost_pile", Identifier.of("natures_spirit", "red_kaolin"), 3);
      addBuildingToPool(templatePools, processorLists, Identifier.of("natures_spirit:village/cypress/houses"), NaturesDelight.MOD_ID + ":village/houses/cypress_compost_pile", Identifier.of("natures_spirit", "cyan_kaolin"), 3);
      addBuildingToPool(templatePools, processorLists, Identifier.of("natures_spirit:village/cypress/houses"), NaturesDelight.MOD_ID + ":village/houses/cypress_compost_pile", Identifier.of("natures_spirit", "light_blue_kaolin"), 3);
      addBuildingToPool(templatePools, processorLists, Identifier.of("natures_spirit:village/cypress/houses"), NaturesDelight.MOD_ID + ":village/houses/cypress_compost_pile", Identifier.of("natures_spirit", "crack_10_percent"), 3);
      addBuildingToPool(templatePools, processorLists, Identifier.of("natures_spirit:village/cypress/houses"), NaturesDelight.MOD_ID + ":village/houses/cypress_compost_pile", Identifier.of("natures_spirit", "pink_kaolin"), 3);
      addBuildingToPool(templatePools, processorLists, Identifier.of("natures_spirit:village/cypress/houses"), NaturesDelight.MOD_ID + ":village/houses/cypress_compost_pile", Identifier.of("natures_spirit", "yellow_kaolin"), 3);

      addBuildingToPool(templatePools, processorLists, Identifier.of("natures_spirit:village/wisteria/houses"), NaturesDelight.MOD_ID + ":village/houses/wisteria_compost_pile", Identifier.of("mossify_10_percent"), 3);
      addBuildingToPool(templatePools, processorLists, Identifier.of("natures_spirit:village/coconut/houses"), NaturesDelight.MOD_ID + ":village/houses/coconut_compost_pile", Identifier.of("empty"),4);
      addBuildingToPool(templatePools, processorLists, Identifier.of("natures_spirit:village/adobe/adobe_houses_layer_1_back"), NaturesDelight.MOD_ID + ":village/houses/adobe_compost_pile", Identifier.of("natures_spirit", "fix_button"),3);
   }
   public static void addBuildingToPool(Registry<StructurePool> templatePoolRegistry, Registry<StructureProcessorList> processorListRegistry, Identifier poolRL, String nbtPieceRL, Identifier processor, int weight) {
      StructurePool pool = templatePoolRegistry.get(poolRL);
      if (pool == null) return;

      RegistryEntry <StructureProcessorList> processorHolder = processorListRegistry.entryOf(RegistryKey.of(RegistryKeys.PROCESSOR_LIST, processor));

      SinglePoolElement piece = SinglePoolElement.ofProcessedSingle(nbtPieceRL, processorHolder).apply(StructurePool.Projection.RIGID);

      for (int i = 0; i < weight; i++) {
         pool.elements.add(piece);
      }

      List <Pair <StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList <>(pool.elementCounts);
      listOfPieceEntries.add(new Pair<>(piece, weight));
      pool.elementCounts = listOfPieceEntries;
   }

}
