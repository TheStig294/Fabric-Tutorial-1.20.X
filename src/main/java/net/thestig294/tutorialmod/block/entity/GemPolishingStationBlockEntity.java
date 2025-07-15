package net.thestig294.tutorialmod.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thestig294.tutorialmod.recipe.GemPolishingRecipe;
import net.thestig294.tutorialmod.screen.GemPolishingScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

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

    public ItemStack getRenderStack() {
        if(this.getStack(OUTPUT_SLOT).isEmpty()){
            return this.getStack(INPUT_SLOT);
        } else {
            return this.getStack(OUTPUT_SLOT);
        }
    }

//    Basically SendFullStateUpdate() for updating how a block entity looks...
    @Override
    public void markDirty() {
        assert world != null;
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        super.markDirty();
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
        Optional<RecipeEntry<GemPolishingRecipe>> recipe = getCurrentRecipe();

        this.removeStack(INPUT_SLOT, 1);

//        *Don't just suppress this error in practice!*
//        Need to figure out the best way to handle having a nil recipe in this case, perhaps just returning early is best?
        //noinspection OptionalGetWithoutIsPresent
        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().value().getResult(null).getItem(),
                getStack(OUTPUT_SLOT).getCount() + recipe.get().value().getResult(null).getCount()));
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<RecipeEntry<GemPolishingRecipe>> recipe = getCurrentRecipe();

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(recipe.get().value().getResult(null))
                && canInsertItemIntoOutputSlot(recipe.get().value().getResult(null).getItem());
    }

    private Optional<RecipeEntry<GemPolishingRecipe>> getCurrentRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size());

        for (int i = 0; i < this.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }

        assert getWorld() != null;
        return getWorld().getRecipeManager().getFirstMatch(GemPolishingRecipe.Type.INSTANCE, inv, getWorld());
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

// * <p>Block entity's data, unlike block states, are not automatically synced. Block
// * entities declare when and which data to sync. In general, block entities need to
// * sync states observable from the clients without specific interaction (such as opening
// * a container). {@link #toUpdatePacket} and {@link #toInitialChunkDataNbt} control
// * which data is sent to the client. To sync the block entity to the client, call
// * {@code serverWorld.getChunkManager().markForUpdate(this.getPos());}.

    /**
     * {@return the packet to send to nearby players when the block entity's observable
     * state changes, or {@code null} to not send the packet}
     *
     * <p>If the data returned by {@link #toInitialChunkDataNbt initial chunk data} is suitable
     * for updates, the following shortcut can be used to create an update packet: {@code
     * BlockEntityUpdateS2CPacket.create(this)}. The NBT will be passed to {@link #readNbt}
     * on the client.
     *
     * <p>"Observable state" is a state that clients can observe without specific interaction.
     * For example, {@link CampfireBlockEntity}'s cooked items are observable states,
     * but chests' inventories are not observable states, since the player must first open
     * that chest before they can see the contents.
     *
     * <p>To sync block entity data using this method, use {@code
     * serverWorld.getChunkManager().markForUpdate(this.getPos());}.
     *
     * @see #toInitialChunkDataNbt
     */
    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    /**
     * {@return the serialized state of this block entity that is observable by clients}
     *
     * <p>This is sent alongside the initial chunk data, as well as when the block
     * entity implements {@link #toUpdatePacket} and decides to use the default
     * {@link net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket}.
     *
     * <p>"Observable state" is a state that clients can observe without specific interaction.
     * For example, {@link CampfireBlockEntity}'s cooked items are observable states,
     * but chests' inventories are not observable states, since the player must first open
     * that chest before they can see the contents.
     *
     * <p>To send all NBT data of this block entity saved to disk, return {@link #createNbt}.
     *
     * @see #toUpdatePacket
     */
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
