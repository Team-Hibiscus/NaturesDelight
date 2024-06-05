package net.hibiscus.naturesdelight;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.function.Supplier;

public class ManakishBlock extends PieBlock {
   public ManakishBlock(Settings properties) {
      super(properties, null);
   }

   public ItemStack getPieSliceItem() {
      return new ItemStack(NaturesDelightBlocksAndItems.MANAKISH_SLICE_ITEM);
   }
   public static final IntProperty BITES = IntProperty.of("bites", 0, 1);

   protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D);

   public int getMaxBites() {
      return 2;
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView level, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }
}
