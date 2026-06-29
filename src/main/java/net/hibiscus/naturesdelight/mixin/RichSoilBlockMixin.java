package net.hibiscus.naturesdelight.mixin;

import net.hibiscus.naturesdelight.NaturesDelightBlocksAndItems;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.RichSoilBlock;

@Mixin(RichSoilBlock.class)
public class RichSoilBlockMixin {
   @Inject(method = "convertMushroomToColony", at = @At(value = "HEAD"), cancellable = true)
   private void convertMushroomToColony(BlockState targetState, BlockPos targetPos, ServerWorld level, CallbackInfoReturnable<Boolean> cir) {
      if (targetState.isOf(NSMiscBlocks.SHIITAKE_MUSHROOM)) {
         level.setBlockState(targetPos, NaturesDelightBlocksAndItems.SHIITAKE_MUSHROOM_COLONY_BLOCK.getDefaultState());
         cir.setReturnValue(true);
      }
   }

}
