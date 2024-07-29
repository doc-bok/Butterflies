package com.bokmcdok.butterflies.client.gui.screens;

import com.bokmcdok.butterflies.client.texture.ButterflyTextures;
import com.bokmcdok.butterflies.world.ButterflyData;
import com.bokmcdok.butterflies.world.CompoundTagId;
import net.minecraft.ChatFormatting;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * The UI for the butterfly book, where players can look up information on
 * butterfly species.
 */
@OnlyIn(Dist.CLIENT)
public class ButterflyBookScreen extends Screen {

    // A cache for the page components.
    private List<FormattedCharSequence> cachedPageComponents = Collections.emptyList();

    // The component that displays the current page.
    private Component pageMsg = CommonComponents.EMPTY;

    // Widget for button to go back a page.
    private PageButton backButton;

    // Widget for button to go forward a page.
    private PageButton forwardButton;

    // Defines what pages are available on this screen.
    private final BookAccess bookAccess;

    // The currently cached page.
    private int cachedPage = -1;

    // The current page being rendered.
    private int currentPage;

    // Whether to play the turn sound.
    private final boolean playTurnSound;

    /**
     * Construction
     * @param stack The item stack we are using.
     */
    public ButterflyBookScreen(ItemStack stack) {
        this(new BookAccess(stack), true);
    }

    /**
     * Get the style at the clicked position.
     * @param x The x-position of mouse cursor.
     * @param y The y-position of mouse cursor.
     * @return The style.
     */
    @Nullable
    public Style getClickedComponentStyleAt(double x, double y) {
        if (this.cachedPageComponents.isEmpty() || this.minecraft == null) {
            return null;
        } else {
            int i = Mth.floor(x - (double)((this.width - 192) / 2) - 36.0D);
            int j = Mth.floor(y - 2.0D - 30.0D);
            if (i >= 0 && j >= 0) {
                int k = Math.min(128 / 9, this.cachedPageComponents.size());
                if (i <= 114 && j < 9 * k + k) {
                    int l = j / 9;
                    if (l < this.cachedPageComponents.size()) {
                        FormattedCharSequence formattedCharSequence = this.cachedPageComponents.get(l);
                        return this.minecraft.font.getSplitter().componentStyleAtWidth(formattedCharSequence, i);
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    /**
     * Handle a component being clicked.
     * @param style The style of the component.
     * @return True if the event was handled.
     */
    @Override
    public boolean handleComponentClicked(Style style) {
        if (style != null) {
            ClickEvent clickEvent = style.getClickEvent();
            if (clickEvent == null) {
                return false;
            } else if (clickEvent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
                String clickEventValue = clickEvent.getValue();

                try {
                    int page = Integer.parseInt(clickEventValue) - 1;
                    return this.forcePage(page);
                } catch (Exception exception) {
                    return false;
                }
            } else {
                boolean flag = super.handleComponentClicked(style);
                if (flag && clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                    this.closeScreen();
                }

                return flag;
            }
        }

        return false;
    }

    /**
     * Handle a key being pressed.
     * @param key The key that was pressed.
     * @param mod1 Unknown (shift/ctrl/alt maybe?).
     * @param mod2 Unknown (shift/ctrl/alt maybe?).
     * @return True if the input was handled.
     */
    @Override
    public boolean keyPressed(int key, int mod1, int mod2) {
        if (super.keyPressed(key, mod1, mod2)) {
            return true;
        } else {
            return switch (key) {
                case 266 -> {
                    this.backButton.onPress();
                    yield true;
                }
                case 267 -> {
                    this.forwardButton.onPress();
                    yield true;
                }
                default -> false;
            };
        }
    }

    /**
     * Handle a mouse click.
     * @param x The x-position of the mouse cursor.
     * @param y The x-position of the mouse cursor.
     * @param button The button that was pressed.
     * @return True if the mouse event was handled.
     */
    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (button == 0) {
            Style style = this.getClickedComponentStyleAt(x, y);
            if (style != null && this.handleComponentClicked(style)) {
                return true;
            }
        }

        return super.mouseClicked(x, y, button);
    }

    /**
     * Render the screen.
     * @param guiGraphics The graphics buffer for the gui.
     * @param x The x position of the cursor.
     * @param y The y position of the cursor.
     * @param unknown Unknown.
     */
    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int x, int y, float unknown) {
        renderBackground(guiGraphics, x, y, unknown);

        int i = (this.width - 192) / 2;
        guiGraphics.blit(ButterflyTextures.BOOK, i, 2, 0, 0, 192, 192);

        if (this.cachedPage != this.currentPage) {
            FormattedText formattedText = this.bookAccess.getPage(this.currentPage);
            this.cachedPageComponents = this.font.split(formattedText, 114);
            this.pageMsg = Component.translatable("book.pageIndicator", this.currentPage + 1, Math.max(this.getNumPages(), 1));
        }

        this.cachedPage = this.currentPage;
        if (this.cachedPage % 2 == 0) {
            int butterflyIndex = bookAccess.getButterflyIndex(cachedPage);
            ButterflyData data = ButterflyData.getEntry(butterflyIndex);
            if (data != null) {
                guiGraphics.blit(data.getScrollTexture(), i, 2, 0, 0, 192, 192);
            }
        } else {
            int cachedPageSize = Math.min(128 / 9, this.cachedPageComponents.size());

            for (int line = 0; line < cachedPageSize; ++line) {
                FormattedCharSequence formattedCharSequence = this.cachedPageComponents.get(line);
                guiGraphics.drawString(this.font, formattedCharSequence, i + 36, 32 + line * 9, 0, false);
            }
        }

        int fontWidth = this.font.width(this.pageMsg);
        guiGraphics.drawString(this.font, this.pageMsg, i - fontWidth + 192 - 44, 18, 0, false);

        Style style = this.getClickedComponentStyleAt(x,y);
        if (style != null) {
            guiGraphics.renderComponentHoverEffect(this.font, style, x, y);
        }

        for(Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, x, y, unknown);
        }
    }

    /**
     * Set the current page.
     * @param page The page to set.
     * @return Whether the page was set.
     */
    public boolean setPage(int page) {
        int i = Mth.clamp(page, 0, this.bookAccess.getPageCount() - 1);
        if (i != this.currentPage) {
            this.currentPage = i;
            this.updateButtonVisibility();
            this.cachedPage = -1;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Close the screen.
     */
    protected void closeScreen() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(null);
        }
    }

    /**
     * Create the menu controls.
     */
    protected void createMenuControls() {
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE,
                (button) -> this.onClose()).bounds(this.width / 2 - 100, 196, 200, 20).build());
    }

    /**
     * Create the page control buttons (forward and backward).
     */
    protected void createPageControlButtons() {
        int i = (this.width - 192) / 2;

        this.forwardButton = this.addRenderableWidget(new PageButton(i + 116, 159, true,
                (button) -> this.pageForward(), this.playTurnSound));

        this.backButton = this.addRenderableWidget(new PageButton(i + 43, 159, false,
                (button) -> this.pageBack(), this.playTurnSound));

        this.updateButtonVisibility();
    }

    /**
     * Force a specific page to be open.
     * @param page The page to set.
     * @return Whether the page was set.
     */
    protected boolean forcePage(int page) {
        return this.setPage(page);
    }

    /**
     * Initialise the gui.
     */
    @Override
    protected void init() {
        this.createMenuControls();
        this.createPageControlButtons();
    }

    /**
     * Move back a page.
     */
    protected void pageBack() {
        if (this.currentPage > 0) {
            --this.currentPage;
        }

        this.updateButtonVisibility();
    }

    /**
     * Move forward a page.
     */
    protected void pageForward() {
        if (this.currentPage < this.getNumPages() - 1) {
            ++this.currentPage;
        }

        this.updateButtonVisibility();
    }

    /**
     * Construction
     * @param access Contains the pages available for this book.
     * @param playTurnSound Whether to play the turn sound.
     */
    private ButterflyBookScreen(BookAccess access, boolean playTurnSound) {
        super(GameNarrator.NO_TITLE);
        this.bookAccess = access;
        this.playTurnSound = playTurnSound;
    }

    /**
     * Get the number of pages for this book.
     * @return The number of pages available in the book access.
     */
    private int getNumPages() {
        return this.bookAccess.getPageCount();
    }

    /**
     * Update the visibility of the buttons.
     */
    private void updateButtonVisibility() {
        this.forwardButton.visible = this.currentPage < this.getNumPages() - 1;
        this.backButton.visible = this.currentPage > 0;
    }

    /**
     * Class that contains a list of all the pages available in this book.
     */
    @OnlyIn(Dist.CLIENT)
    public static class BookAccess {
        private ListTag pages;

        /**
         * Construct access based on an item stack.
         * @param stack The item stack we are using.
         */
        public BookAccess(ItemStack stack) {
            CompoundTag tag = stack.getTag();
            if (tag != null) {
                pages = tag.getList(CompoundTagId.PAGES, 3);
            }
        }

        /**
         * Get the butterfly index for the page.
         * @param page The page to get.
         * @return The index of the butterfly on the page.
         */
        public int getButterflyIndex(int page) {
            if (pages != null) {
                return pages.getInt(page / 2);
            }

            return 0;
        }

        /**
         * Get the specified page
         * @param page The page number to get.
         * @return The formatted text for the page.
         */
        public FormattedText getPage(int page) {
            return page >= 0 && page < this.getPageCount() ? this.getPageRaw(page) : FormattedText.EMPTY;
        }

        /**
         * Get the number of pages available in this book.
         * @return The total page count.
         */
        public int getPageCount() {
            if (pages != null) {
                return pages.size() * 2;
            }

            return 0;
        }

        /**
         * Get the specified page for the book.
         * @param page The page number to get.
         * @return The formatted text for the page.
         */
        @SuppressWarnings("deprecation")
        public FormattedText getPageRaw(int page) {
            if (pages != null) {
                int butterflyIndex = pages.getInt((page - 1) / 2);
                ButterflyData entry = ButterflyData.getEntry(butterflyIndex);
                if (entry != null) {
                    //  Butterfly name
                    MutableComponent component = Component.translatable("entity.butterflies." + entry.entityId());

                    if (entry.type() == ButterflyData.ButterflyType.SPECIAL) {
                        component.withStyle(ChatFormatting.DARK_BLUE);
                    }

                    // Rarity
                    component.append("\n\n");
                    component.append(Component.translatable("gui.butterflies.rarity"));
                    switch (entry.rarity()) {
                        case RARE -> component.append(Component.translatable("gui.butterflies.rarity.rare"));
                        case UNCOMMON -> component.append(Component.translatable("gui.butterflies.rarity.uncommon"));
                        case COMMON -> component.append(Component.translatable("gui.butterflies.rarity.common"));
                        default -> {}
                    }

                    // Size
                    component.append("\n");
                    component.append(Component.translatable("gui.butterflies.size"));
                    switch (entry.size()) {
                        case SMALL -> component.append(Component.translatable("gui.butterflies.size.small"));
                        case MEDIUM -> component.append(Component.translatable("gui.butterflies.size.medium"));
                        case LARGE -> component.append(Component.translatable("gui.butterflies.size.large"));
                        default -> {}
                    }

                    // Speed
                    component.append("\n");
                    component.append(Component.translatable("gui.butterflies.speed"));
                    switch (entry.speed()) {
                        case MODERATE -> component.append(Component.translatable("gui.butterflies.speed.moderate"));
                        case FAST -> component.append(Component.translatable("gui.butterflies.speed.fast"));
                        default -> {}
                    }

                    // Lifespan
                    component.append("\n");
                    component.append(Component.translatable("gui.butterflies.lifespan"));
                    switch (entry.getOverallLifeSpan()) {
                        case SHORT -> component.append(Component.translatable("gui.butterflies.lifespan.short"));
                        case MEDIUM -> component.append(Component.translatable("gui.butterflies.lifespan.average"));
                        case LONG -> component.append(Component.translatable("gui.butterflies.lifespan.long"));
                        default -> {}
                    }

                    // Habitat
                    component.append("\n");
                    component.append(Component.translatable("gui.butterflies.habitat"));
                    switch (entry.habitat()) {
                        case FORESTS -> component.append(Component.translatable("gui.butterflies.habitat.forests"));
                        case FORESTS_AND_PLAINS -> component.append(Component.translatable("gui.butterflies.habitat.forestsandplains"));
                        case JUNGLES -> component.append(Component.translatable("gui.butterflies.habitat.jungles"));
                        case PLAINS -> component.append(Component.translatable("gui.butterflies.habitat.plains"));
                        case ICE -> component.append(Component.translatable("gui.butterflies.habitat.ice"));
                        default -> {}
                    }

                    // Preferred Flower
                    component.append("\n");
                    component.append(Component.translatable("gui.butterflies.preferred_flower"));
                    Component description = BuiltInRegistries.ITEM.get(entry.preferredFlower()).asItem().getDescription();
                    component.append(description);


                    // Fact
                    component.append("\n\n");
                    component.append(Component.translatable("gui.butterflies.fact." + entry.entityId()));

                    return component;
                }
            }

            return null;
        }
    }
}
