package net.thestig294.tutorialmod.compat;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.thestig294.tutorialmod.TutorialMod;
import net.thestig294.tutorialmod.block.ModBlocks;

import java.util.LinkedList;
import java.util.List;

public class GemPolishingCategory implements DisplayCategory<BasicDisplay> {
    public static final Identifier TEXTURE =
            new Identifier(TutorialMod.MOD_ID, "textures/gui/gem_polishing_station_gui.png");
    public static final CategoryIdentifier<GemPolishingDisplay> GEM_POLISHING =
            CategoryIdentifier.of(TutorialMod.MOD_ID, "gem_polishing");

    @Override
    public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier() {
        return GEM_POLISHING;
    }

    @Override
    public Text getTitle() {
        return Text.literal("Gem Polishing Station");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.GEM_POLISHING_STATION.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(BasicDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 87, bounds.getCenterY() -35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 175, 82)));

//        Takes the first item in the recipe (the input item), and displays it at x = 80, y = 11, according to the
//        gem polishing station ui image!
//        (From the GemPolishingStationScreenHandler this.addSlot calls! The actual gem polishing station UI creates an
//        item slot in the exact same place!)
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 80, startPoint.y + 11))
                .entries(display.getInputEntries().get(0)));
//        If your recipe takes 2 ingredients, you expand like so:
//        widgets.add(Widgets.createSlot(new Point(startPoint.x + 80, startPoint.y + 11)).entries(display.getInputEntries().get(1)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 80, startPoint.y + 59))
                .entries(display.getOutputEntries().get(0)));

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return DisplayCategory.super.getDisplayHeight();
    }
}
