package com.aznos.coffee.block.entity.custom;

import com.aznos.coffee.block.entity.ImplementedInventory;
import com.aznos.coffee.block.entity.ModBlockEntities;
import com.aznos.coffee.item.ModItems;
import com.aznos.coffee.screen.custom.BrewerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BrewerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int FLUID_ITEM_SLOT = 2;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private final int DEFAULT_MAX_PROGRESS = 72;

    public static final int TANK_CAPACITY = 1000;
    public static final int WATER_PER_BREW = 200;
    private int waterAmount = 0;

    private boolean isBrewing = false;
    private final int soundTickDelay = 20;
    private int soundTicks = 0;

    public BrewerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BREWER_BE, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch(index) {
                    case 0 -> BrewerBlockEntity.this.progress;
                    case 1 -> BrewerBlockEntity.this.maxProgress;
                    case 2 -> BrewerBlockEntity.this.waterAmount;
                    case 3 -> TANK_CAPACITY;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch(index) {
                    case 0 -> BrewerBlockEntity.this.progress = value;
                    case 1 -> BrewerBlockEntity.this.maxProgress = value;
                    case 2 -> BrewerBlockEntity.this.waterAmount = value;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);

        nbt.putInt("brewer.progress", progress);
        nbt.putInt("brewer.max_progress", maxProgress);
        nbt.putInt("brewer.water_amount", waterAmount);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        progress = nbt.getInt("brewer.progress");
        maxProgress = nbt.getInt("brewer.max_progress");
        waterAmount = nbt.getInt("brewer.water_amount");

        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Brewer");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BrewerScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        handleBucketInsertion();

        if(hasRecipe() && canInsertIntoOutputSlot()) {
            isBrewing = true;
            increaseCraftingProgress();
            markDirty(world, pos, state);

            if(hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            isBrewing = false;
            resetProgress();
        }

        if (isBrewing) {
            soundTicks++;
            if (soundTicks >= soundTickDelay) {
                startBrewingSound(world, pos);
                soundTicks = 0;
            }
        } else {
            soundTicks = 0;
        }
    }

    private void startBrewingSound(World world, BlockPos pos) {
        if(!world.isClient) {
            world.playSound(null, pos, SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }

    private void handleBucketInsertion() {
        ItemStack fluidStack = this.getStack(FLUID_ITEM_SLOT);

        if(fluidStack.getItem() == Items.WATER_BUCKET) {
            if(this.waterAmount + 1000 <= TANK_CAPACITY) {
                this.waterAmount += 1000;

                this.removeStack(FLUID_ITEM_SLOT, 1);
                this.setStack(FLUID_ITEM_SLOT, new ItemStack(Items.BUCKET, 1));
            }
        }
    }

    private boolean hasRecipe() {
        boolean hasCoffeePowder = this.getStack(INPUT_SLOT).getItem() == ModItems.COFFEE_POWDER;
        boolean hasWater = this.waterAmount >= WATER_PER_BREW;

        if(!hasCoffeePowder || !hasWater) return false;

        ItemStack output = new ItemStack(ModItems.COFFEE_CUP);
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.getStack(OUTPUT_SLOT).getMaxCount() >= this.getStack(OUTPUT_SLOT).getCount() + count;
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertIntoOutputSlot() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private void increaseCraftingProgress() {
        this.progress++;
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void craftItem() {
        this.removeStack(INPUT_SLOT, 1);
        this.setStack(OUTPUT_SLOT, new ItemStack(ModItems.COFFEE_CUP, this.getStack(OUTPUT_SLOT).getCount() + 1));

        this.waterAmount -= WATER_PER_BREW;
        if(this.waterAmount < 0) this.waterAmount = 0;
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = DEFAULT_MAX_PROGRESS;
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
