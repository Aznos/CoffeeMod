package com.aznos.coffee.block.entity.custom;

import com.aznos.coffee.block.entity.ModBlockEntities;
import com.aznos.coffee.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DryingRackBlockEntity extends BlockEntity implements Inventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private int dehydrationLevel = 0;

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

    public static void tick(World world, BlockPos pos, BlockState state, DryingRackBlockEntity be) {
        if(!world.isClient) {
            ItemStack stack = be.getStack(0);
            if(!stack.isEmpty() && stack.getItem() == ModItems.RAW_COFFEE_BEAN) {
                if(world.getTime() % 40 == 0) { // every 40 ticks
                    if(be.dehydrationLevel < 100) {
                        be.dehydrationLevel++;
                    }
                }
            }
        }
    }
}
