package net.thestig294.tutorialmod.screen;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.thestig294.tutorialmod.block.entity.GemPolishingStationBlockEntity;

public class GemPolishingScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final GemPolishingStationBlockEntity blockEntity;

    public GemPolishingScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(2));
    }

    public GemPolishingScreenHandler(int syncId, PlayerInventory playerInventory,
                                     BlockEntity blockEntity, PropertyDelegate propertyDelegate){
        super(ModScreenHandlers.GEM_POLISHING_SCREEN_HANDLER, syncId);
        checkSize(((Inventory) blockEntity), 2);
        this.inventory = ((Inventory) blockEntity);
        this.propertyDelegate = propertyDelegate;
        this.blockEntity = ((GemPolishingStationBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory, 0, 80, 11));
        this.addSlot(new Slot(inventory, 1, 80, 59));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(propertyDelegate);
    }

    public boolean isCrafting(){
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress(){
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);
//        Width in pixels of the arrow texture
        int progressArrowSize = 26;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

//    Basically implements shift-clicking!
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

//            Shift-clicking items OUT the machine block
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
//                Shift-clicking items IN the machine block
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)){
                return ItemStack.EMPTY;
            }

//            If shift-clicking completely cleared the item, set the item type to "empty" in the ItemStack
            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
//                Otherwise, we need to update the inventory that changed so that its data is saved!
//                Basically, whenever an inventory or item slot is changed in any way, you need to call:
                slot.markDirty();
//                otherwise the change in the inventory might not be saved!
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

//    These 2 functions almost always have the same implementation, you just have to play around with the slot positions
//    for custom GUIs that mess with the hotbar or player's inventory!

//    They just add the physical UI of the player's inventory and hotbar
    private void addPlayerInventory(PlayerInventory playerInventory){
        for (int i = 0; i < 3; ++i){
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory){
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
