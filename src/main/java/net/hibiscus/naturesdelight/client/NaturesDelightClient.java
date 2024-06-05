package net.hibiscus.naturesdelight.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.hibiscus.naturesdelight.NaturesDelightBlocksAndItems;
import net.minecraft.client.render.RenderLayer;

public class NaturesDelightClient implements ClientModInitializer {
   @Override public void onInitializeClient() {
      BlockRenderLayerMap.INSTANCE.putBlock(NaturesDelightBlocksAndItems.SHIITAKE_MUSHROOM_COLONY_BLOCK, RenderLayer.getCutout());
   }
}
