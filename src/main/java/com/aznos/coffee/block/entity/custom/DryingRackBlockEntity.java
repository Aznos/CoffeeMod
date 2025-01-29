package com.aznos.coffee.block.entity.custom;

import com.aznos.coffee.block.entity.ModBlockEntities;
import com.aznos.coffee.components.ModDataComponentTypes;
import com.aznos.coffee.item.ModItems;
import com.aznos.coffee.item.custom.RawCoffeeBeanItem;
import net.minecraft.block.*;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class DryingRackBlockEntity extends BlockEntity implements Inventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    private static final List<Block> HEAT_SOURCES = Arrays.asList(
            Blocks.FIRE, Blocks.SOUL_FIRE, Blocks.LAVA, Blocks.CAMPFIRE,
            Blocks.SOUL_CAMPFIRE, Blocks.FURNACE, Blocks.BLAST_FURNACE,
            Blocks.SMOKER, Blocks.MAGMA_BLOCK
    );

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

                int drynessChange = 1;
                boolean isRaining = world.isRaining();
                boolean skyExposed = world.isSkyVisible(pos.up());

                if(isRaining && skyExposed) {
                    drynessChange = -1;
                } else {
                    if(skyExposed && !isRaining) {
                        drynessChange += 1;
                    }

                    if(isNearHeatSource(world, pos)) {
                        drynessChange += 1;
                    }
                }

                level = MathHelper.clamp(level + drynessChange, 0, 100);
                ItemStack newStack;
                if(level >= 100) {
                    newStack = new ItemStack(ModItems.DEHYDRATED_COFFEE_BEAN);
                } else if(level >= 75) {
                    newStack = new ItemStack(ModItems.RAW_COFFEE_BEAN_STAGE3);
                } else if(level >= 50) {
                    newStack = new ItemStack(ModItems.RAW_COFFEE_BEAN_STAGE2);
                } else if(level >= 25) {
                    newStack = new ItemStack(ModItems.RAW_COFFEE_BEAN_STAGE1);
                } else {
                    newStack = new ItemStack(ModItems.RAW_COFFEE_BEAN);
                }

                newStack.set(ModDataComponentTypes.DEHYDRATION_LEVEL, level);
                blockEntity.setStack(0, newStack);

                markDirty(world, pos, state);
                world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
            }
        }
    }

    private static boolean isNearHeatSource(World world, BlockPos pos) {
        int radius = 2;
        for(BlockPos checkPos : BlockPos.iterate(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
            BlockState blockState = world.getBlockState(checkPos);
            Block blockAt = blockState.getBlock();

            if(blockAt == Blocks.FIRE || blockAt == Blocks.SOUL_FIRE || blockAt == Blocks.LAVA || blockAt == Blocks.MAGMA_BLOCK) {
                return true;
            }

            if(blockAt instanceof CampfireBlock) {
                boolean lit = blockState.get(CampfireBlock.LIT);
                if (lit) return true;
            }

            if(blockAt instanceof AbstractFurnaceBlock) {
                boolean lit = blockState.get(AbstractFurnaceBlock.LIT);
                if(lit) return true;
            }
        }

        return false;
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
