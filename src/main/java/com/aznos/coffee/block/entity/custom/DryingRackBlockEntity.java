package com.aznos.coffee.block.entity.custom;

import com.aznos.coffee.block.entity.ModBlockEntities;
import com.aznos.coffee.components.ModDataComponentTypes;
import com.aznos.coffee.item.ModItems;
import com.aznos.coffee.item.custom.RawCoffeeBeanItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DryingRackBlockEntity extends BlockEntity implements Inventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public DryingRackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DRYING_RACK_BE, pos, state);
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < size(); i++) {
            ItemStack stack = getStack(i);
            if(!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        markDirty();
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        markDirty();
        ItemStack stack = inventory.get(slot);
        stack.decrement(amount);
        return inventory.set(slot, stack);
    }

    @Override
    public ItemStack removeStack(int slot) {
        markDirty();
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        markDirty();
        inventory.set(slot, stack.copyWithCount(1));
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
    }

    public static void tick(World world, BlockPos pos, BlockState state, DryingRackBlockEntity blockEntity) {
        if(!world.isClient && world.getTime() % 40 == 0) { // 2 seconds
            ItemStack stack = blockEntity.getStack(0);
            if(!stack.isEmpty() && stack.getItem() instanceof RawCoffeeBeanItem) {
                Integer levelObj = stack.get(ModDataComponentTypes.DEHYDRATION_LEVEL);
                int level = (levelObj != null) ? levelObj : 0;

                if(level < 100) {
                    level++;
                    stack.set(ModDataComponentTypes.DEHYDRATION_LEVEL, level);
                }

                if (level >= 25 && level < 50) {
                    blockEntity.setStack(0, new ItemStack(ModItems.RAW_COFFEE_BEAN_STAGE1));
                    markDirty(world, pos, state);
                    world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
                } else if (level >= 50 && level < 75) {
                    blockEntity.setStack(0, new ItemStack(ModItems.RAW_COFFEE_BEAN_STAGE2));
                    markDirty(world, pos, state);
                    world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
                } else if (level >= 75 && level < 100) {
                    blockEntity.setStack(0, new ItemStack(ModItems.RAW_COFFEE_BEAN_STAGE3));
                    markDirty(world, pos, state);
                    world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
                } else if (level == 100) {
                    blockEntity.setStack(0, new ItemStack(ModItems.DEHYDRATED_COFFEE_BEAN));
                    markDirty(world, pos, state);
                    world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
                }
            }
        }
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
