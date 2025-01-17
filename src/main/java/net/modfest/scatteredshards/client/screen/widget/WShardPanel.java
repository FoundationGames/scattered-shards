package net.modfest.scatteredshards.client.screen.widget;

import com.mojang.datafixers.util.Either;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.modfest.scatteredshards.ScatteredShards;
import net.modfest.scatteredshards.api.shard.Shard;
import net.modfest.scatteredshards.api.shard.ShardType;
import net.modfest.scatteredshards.client.screen.widget.scalable.WScaledLabel;
import net.modfest.scatteredshards.client.screen.widget.scalable.WScaledText;
import net.modfest.scatteredshards.client.screen.widget.scalable.WShardIcon;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class WShardPanel extends WPlainPanel {
	//private static final Identifier TEXTURE = ScatteredShards.id("textures/gui/view.png");

	public static final IntSupplier WHITE = () -> 0xFFFFFF;
	public static final Style HINT_STYLE = Style.EMPTY.withFont(new Identifier("minecraft:alt"));

	private Shard shard = Shard.MISSING_SHARD.copy();
	private ShardType shardType;

	private final WDynamicSprite backing = new WDynamicSprite(() -> shardType.getFrontTexture());
	private final WShardIcon icon = new WShardIcon(2.0f);
	private final WScaledLabel name = new WScaledLabel(() -> shard.name(), 1.4f)
			.setShadow(true)
			.setHorizontalAlignment(HorizontalAlignment.CENTER);
	private final WScaledLabel typeDescription = new WScaledLabel(() -> shardType.getDescription(), 0.5f)
			.setShadow(true)
			.setHorizontalAlignment(HorizontalAlignment.CENTER)
			.setColor(() -> shardType.textColor());
	private final WScaledLabel source = new WScaledLabel(shard::source, 0.9f)
			.setShadow(true)
			.setHorizontalAlignment(HorizontalAlignment.CENTER);
	private final WScaledText lore = new WScaledText(shard::lore, 0.8f)
			.setShadow(true)
			.setHorizontalAlignment(HorizontalAlignment.CENTER);
	private final WScaledText hint = new WScaledText(shard::hint, 0.8f)
			.setShadow(true)
			.setHorizontalAlignment(HorizontalAlignment.CENTER);

	/**
	 * Sets the shardType displayed to a static value. Note: Prevents the shardType from being updated if the configured shard is mutated!
	 */
	public WShardPanel setType(ShardType value) {
		this.shardType = value;
		backing.setImage(value::getFrontTexture);
		typeDescription.setText(value::getDescription);
		typeDescription.setColor(value::textColor);
		return this;
	}

	/**
	 * Sets the icon displayed to a static value. Note: Prevents shard icon from being updated if the configured shard is mutated!
	 */
	public WShardPanel setIcon(Either<ItemStack, Identifier> icon) {
		this.icon.setIcon(icon);
		return this;
	}

	public WShardPanel setName(Supplier<Text> text, IntSupplier color) {
		this.name.setText(text);
		this.name.setColor(color);
		return this;
	}

	public WShardPanel setSource(Supplier<Text> text, IntSupplier color) {
		this.source.setText(text);
		this.source.setColor(color);
		return this;
	}

	public WShardPanel setLore(Supplier<Text> text, IntSupplier color) {
		this.lore.setText(() -> text.get().copy().formatted(Formatting.ITALIC));
		this.lore.setColor(color);
		return this;
	}

	public WShardPanel setHint(Supplier<Text> text, IntSupplier color) {
		this.hint.setText(() -> text.get().copy().fillStyle(HINT_STYLE));
		this.hint.setColor(color);
		this.hint.setHover(text);
		return this;
	}

	public WShardPanel setShard(Shard shard) {
		this.shard = shard;

		setType(shard.getShardType());
		icon.setIcon(shard::icon);
		setName(shard::name, WHITE);
		setSource(shard::source, WHITE);
		setLore(shard::lore, WHITE);
		setHint(shard::hint, WHITE);

		return this;
	}

	public WShardPanel() {
		this.shardType = ShardType.MISSING;
		this.width = 114;
		this.height = 200;
		this.setInsets(Insets.ROOT_PANEL);
		
		add(name, 0, 0, getWidth(), 18);
		add(typeDescription, 0, 16, getWidth(), 16);
		add(source, 0, 25, getWidth(), 16);
		
		int cardScale = 2;
		int cardX = ((this.getWidth()) / 2) - (12 * cardScale);
		add(backing, cardX, 40, 24*cardScale, 32*cardScale);
		
		add(icon, cardX + (4 * cardScale), 40 + (4 * cardScale), 16 * cardScale, 16 * cardScale);

		
		add(lore, 0, 113, getWidth(), 32);
		
		//TODO: Add divider image
		add(new WSprite(ScatteredShards.id("textures/gui/divider.png")), cardX, 145, 24 * cardScale, 1);
		
		add(hint, 0, 149, getWidth(), 32);
	}
	
	@Override
	public void layout() {
		// We are already perfectly laid out from the constructor.
	}
	
	@Override
	protected void expandToFit(WWidget w, Insets insets) {
		// Do not expand to fit anything.
		return;
	}
	
	@Override
	public void addPainters() {
		this.setBackgroundPainter((context, left, top, panel) -> {
			ScreenDrawing.drawGuiPanel(context, left, top, panel.getWidth(), panel.getHeight());
			ScreenDrawing.drawBeveledPanel(context, left+4, top+4, panel.getWidth()-8, panel.getHeight()-8);
			context.fillGradient(left+5, top+5, left+5+panel.getWidth()-10, top+5+panel.getHeight()-10, 0xFF_777777, 0xFF_555555);
		});
	}

	public WShardPanel(Shard shard) {
		setShard(shard);
	}

	public Shard getShard() {
		return shard;
	}
	
	@Override
	public boolean canResize() {
		return false;
	}
}
