package net.hibiscus.naturesdelight;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface RichSoilCallback {
  Event<RichSoilCallback> EVENT = EventFactory.createArrayBacked(RichSoilCallback.class,
      (listeners) -> (block, world, pos) -> {
        for (RichSoilCallback listener : listeners) {
          ActionResult result = listener.interact(block, world, pos);

          if (result != ActionResult.PASS) {
            return result;
          }
        }

        return ActionResult.PASS;
      });

  ActionResult interact(Block block, World world, BlockPos blockPos);
}