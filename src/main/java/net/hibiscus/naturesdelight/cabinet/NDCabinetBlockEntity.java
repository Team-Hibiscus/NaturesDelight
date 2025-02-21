//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.hibiscus.naturesdelight.cabinet;

import net.hibiscus.naturesdelight.NaturesDelightBlocksAndItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import vectorwing.farmersdelight.common.registry.ModSounds;
import vectorwing.farmersdelight.common.utility.TextUtils;

public class NDCabinetBlockEntity extends LootableContainerBlockEntity {
  private DefaultedList<ItemStack> contents;
  private ViewerCountManager openersCounter;

  public NDCabinetBlockEntity(BlockPos pos, BlockState state) {
    super(NaturesDelightBlocksAndItems.ND_CABINET_BLOCK_ENTITY_TYPE, pos, state);
    this.contents = DefaultedList.ofSize(27, ItemStack.EMPTY);
    this.openersCounter = new ViewerCountManager() {
      protected void onContainerOpen(World level, BlockPos pos, BlockState state) {
        NDCabinetBlockEntity.this.playSound(state, (SoundEvent)ModSounds.BLOCK_CABINET_OPEN.get());
        NDCabinetBlockEntity.this.updateBlockState(state, true);
      }

      protected void onContainerClose(World level, BlockPos pos, BlockState state) {
        NDCabinetBlockEntity.this.playSound(state, (SoundEvent)ModSounds.BLOCK_CABINET_CLOSE.get());
        NDCabinetBlockEntity.this.updateBlockState(state, false);
      }

      protected void onViewerCountUpdate(World level, BlockPos pos, BlockState sta, int arg1, int arg2) {
      }

      protected boolean isPlayerViewing(PlayerEntity p_155060_) {
        if (p_155060_.currentScreenHandler instanceof GenericContainerScreenHandler) {
          Inventory container = ((GenericContainerScreenHandler)p_155060_.currentScreenHandler).getInventory();
          return container == NDCabinetBlockEntity.this;
        } else {
          return false;
        }
      }
    };
  }

  public void writeNbt(NbtCompound compound, RegistryWrapper.WrapperLookup registries) {
    super.writeNbt(compound, registries);
    if (!this.writeLootTable(compound)) {
      Inventories.writeNbt(compound, this.contents, registries);
    }

  }

  public void readNbt(NbtCompound compound, RegistryWrapper.WrapperLookup registries) {
    super.readNbt(compound, registries);
    this.contents = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
    if (!this.readLootTable(compound)) {
      Inventories.readNbt(compound, this.contents, registries);
    }

  }

  public int size() {
    return 27;
  }

  protected DefaultedList<ItemStack> getHeldStacks() {
    return this.contents;
  }

  protected void setHeldStacks(DefaultedList<ItemStack> itemsIn) {
    this.contents = itemsIn;
  }

  protected Text getContainerName() {
    return TextUtils.getTranslation("container.cabinet", new Object[0]);
  }

  protected ScreenHandler createScreenHandler(int id, PlayerInventory player) {
    return GenericContainerScreenHandler.createGeneric9x3(id, player, this);
  }

  public void onOpen(PlayerEntity pPlayer) {
    if (this.world != null && !this.removed && !pPlayer.isSpectator()) {
      this.openersCounter.openContainer(pPlayer, this.world, this.getPos(), this.getCachedState());
    }

  }

  public void onClose(PlayerEntity pPlayer) {
    if (this.world != null && !this.removed && !pPlayer.isSpectator()) {
      this.openersCounter.closeContainer(pPlayer, this.world, this.getPos(), this.getCachedState());
    }

  }

  public void recheckOpen() {
    if (this.world != null && !this.removed) {
      this.openersCounter.updateViewerCount(this.world, this.getPos(), this.getCachedState());
    }

  }

  void updateBlockState(BlockState state, boolean open) {
    if (this.world != null) {
      this.world.setBlockState(this.getPos(), (BlockState)state.with(NDCabinetBlock.OPEN, open), 3);
    }

  }

  private void playSound(BlockState state, SoundEvent sound) {
    if (this.world != null) {
      Vec3i cabinetFacingVector = ((Direction)state.get(NDCabinetBlock.FACING)).getVector();
      double x = (double)this.pos.getX() + 0.5 + (double)cabinetFacingVector.getX() / 2.0;
      double y = (double)this.pos.getY() + 0.5 + (double)cabinetFacingVector.getY() / 2.0;
      double z = (double)this.pos.getZ() + 0.5 + (double)cabinetFacingVector.getZ() / 2.0;
      this.world.playSound((PlayerEntity)null, x, y, z, sound, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
    }
  }
}
