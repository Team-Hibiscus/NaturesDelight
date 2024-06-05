package net.hibiscus.naturesdelight.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.hibiscus.naturesdelight.NaturesDelightBlocksAndItems;
import net.hibiscus.naturespirit.registration.block_registration.HibiscusMiscBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.RichSoilBlock;

@Mixin(RichSoilBlock.class)
public class RichSoilBlockMixin {
   @Inject(method = "randomTick", at = @At(value = "INVOKE", target = "Ljava/util/function/Supplier;get()Ljava/lang/Object;", ordinal = 2), cancellable = true)
   private void onLanding(BlockState state, ServerWorld level, BlockPos pos, Random rand, CallbackInfo ci, @Local Block aboveBlock) {
         if (aboveBlock == HibiscusMiscBlocks.SHIITAKE_MUSHROOM) {
            level.setBlockState(pos.up(), NaturesDelightBlocksAndItems.SHIITAKE_MUSHROOM_COLONY_BLOCK.getDefaultState());
            ci.cancel();
         }
   }

}
