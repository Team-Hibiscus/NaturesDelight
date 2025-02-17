package net.hibiscus.naturesdelight;

import net.fabricmc.api.ModInitializer;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.util.ActionResult;

public class NaturesDelight implements ModInitializer {

   public static final String MOD_ID = "natures_delight";
   @Override public void onInitialize() {
      NaturesDelightBlocksAndItems.registerBlocksAndItems();
      NaturesDelightVillageStructures.init();
   }
}
