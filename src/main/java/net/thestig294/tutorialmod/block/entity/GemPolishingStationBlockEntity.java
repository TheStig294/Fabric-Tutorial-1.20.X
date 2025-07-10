package net.thestig294.tutorialmod.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thestig294.tutorialmod.item.ModItems;
import net.thestig294.tutorialmod.screen.GemPolishingScreenHandler;
import org.jetbrains.annotations.Nullable;

// Look at all the entities that extend the BlockEntity class to see all vanilla block entities!
// (Ctrl-H on the "BlockEntity" class!)
public class GemPolishingStationBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

//    The "PropertyDelegate" keeps track of how much progress has been made with crafting with this machine block!
//    It is essentially the Minecraft version of an ent:NetworkVar()!
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 27;

    public GemPolishingStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GEM_POLISHING_STATION_BLOCK_ENTITY, pos, state);
//        This is responsible for syncing the crafting progress between the client and server!
//        (also our first use of a Java anonymous class!)
//
//        (Pretty much ENT:SetupDataTables() from Gmod!)
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
//                You can shove a switch statement on a return call in Java??? Whoa...
                return switch (index) {
//                    Hey look... You can use lambda function syntax to skip the whole C-like "break;" spam
//                    in switch statements! Yipeee!
                    case 0 -> GemPolishingStationBlockEntity.this.progress;
                    case 1 -> GemPolishingStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> GemPolishingStationBlockEntity.this.progress = value;
                    case 1 -> GemPolishingStationBlockEntity.this.maxProgress = value;
                }
            }

//            We have 2 different values in the PropertyDelegate we want to synchronise between the client and server!
            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
//        Allows us to send the position of the block between the client and server!
        packetByteBuf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
//        You should make a proper text translation in practice...
        return Text.literal("Gem Polishing Station");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("gem_polishing_station.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("gem_polishing_station.progress");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GemPolishingScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient){
            return;
        }

        if (isOutputSlotEmptyOrReceivable()) {
            if (this.hasRecipe()) {
                this.increaseCraftProgress();
                markDirty(world, pos, state);

                if (hasCraftingFinished()) {
                    this.craftItem();
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
            }
        } else {
            this.resetProgress();
            markDirty(world, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void craftItem() {
        this.removeStack(INPUT_SLOT, 1);
        ItemStack result = new ItemStack(ModItems.RUBY);

        this.setStack(OUTPUT_SLOT, new ItemStack(result.getItem(), getStack(OUTPUT_SLOT).getCount() + result.getCount()));
//        Maybe the above 2 line can be replaced with this???
//        this.getStack(OUTPUT_SLOT).setCount( this.getStack(OUTPUT_SLOT).getCount() + 1);
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean hasRecipe() {
//        Hard-coded... but sure...
//        (Look at the AbstractFurnaceBlockEntity for how to add "processable" item tags for your machine block!)
        ItemStack result = new ItemStack(ModItems.RUBY);
        boolean hasInput = getStack(INPUT_SLOT).getItem() == ModItems.RAW_RUBY;

        return hasInput && canInsertAmountIntoOutputSlot(result) && canInsertItemIntoOutputSlot(result.getItem());
    }

//    Have to make sure the output slot is free before crafting! (either same item result or empty!)
    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }


}
