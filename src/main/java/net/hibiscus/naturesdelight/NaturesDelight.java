package net.hibiscus.naturesdelight;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;

public class NaturesDelight implements ModInitializer {

   public static final String MOD_ID = "natures_delight";
   @Override public void onInitialize() {
      NaturesDelightBlocksAndItems.registerBlocksAndItems();
      NaturesDelightVillageStructures.init();
      CompostingChanceRegistry.INSTANCE.add(NaturesDelightBlocksAndItems.SHIITAKE_MUSHROOM_COLONY_ITEM, 1.0f);
   }
}
