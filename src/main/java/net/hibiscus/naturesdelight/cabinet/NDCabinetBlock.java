//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.hibiscus.naturesdelight.cabinet;

import static net.hibiscus.naturesdelight.NaturesDelightBlocksAndItems.ND_CABINET_BLOCK_ENTITY_TYPE;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class NDCabinetBlock extends BlockWithEntity {
  public static final MapCodec<NDCabinetBlock> CODEC = createCodec(NDCabinetBlock::new);
  public static final DirectionProperty FACING;
  public static final BooleanProperty OPEN;

  public NDCabinetBlock(AbstractBlock.Settings properties) {
    super(properties);
    this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(OPEN, false));
  }

  protected MapCodec<? extends BlockWithEntity> getCodec() {
    return CODEC;
  }

  public ActionResult onUse(BlockState state, World level, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    if (!level.isClient) {
      BlockEntity tile = level.getBlockEntity(pos);
      if (tile instanceof NDCabinetBlockEntity) {
        player.openHandledScreen((NDCabinetBlockEntity)tile);
      }
    }

    return ActionResult.SUCCESS;
  }

  public void onStateReplaced(BlockState state, World level, BlockPos pos, BlockState newState, boolean isMoving) {
    if (state.getBlock() != newState.getBlock()) {
      BlockEntity tileEntity = level.getBlockEntity(pos);
      if (tileEntity instanceof Inventory) {
        ItemScatterer.spawn(level, pos, (Inventory)tileEntity);
        level.updateComparators(pos, this);
      }

      super.onStateReplaced(state, level, pos, newState, isMoving);
    }

  }

  public void scheduledTick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
    BlockEntity tileEntity = level.getBlockEntity(pos);
    if (tileEntity instanceof NDCabinetBlockEntity) {
      ((NDCabinetBlockEntity)tileEntity).recheckOpen();
    }

  }

  public BlockState getPlacementState(ItemPlacementContext context) {
    return (BlockState)this.getDefaultState().with(FACING, context.getHorizontalPlayerFacing().getOpposite());
  }

  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder);
    builder.add(new Property[]{FACING, OPEN});
  }

  public boolean hasComparatorOutput(BlockState state) {
    return true;
  }

  public int getComparatorOutput(BlockState blockState, World level, BlockPos pos) {
    return ScreenHandler.calculateComparatorOutput(level.getBlockEntity(pos));
  }

  public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return ND_CABINET_BLOCK_ENTITY_TYPE.instantiate(pos, state);
  }

  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  public BlockState rotate(BlockState state, BlockRotation rot) {
    return (BlockState)state.with(FACING, rot.rotate((Direction)state.get(FACING)));
  }

  public BlockState mirror(BlockState state, BlockMirror mirrorIn) {
    return state.rotate(mirrorIn.getRotation((Direction)state.get(FACING)));
  }

  static {
    FACING = Properties.HORIZONTAL_FACING;
    OPEN = Properties.OPEN;
  }
}
