package net.modfest.scatteredshards.client.screen;

import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ShardCreatorGuiDescription extends LightweightGuiDescription {

	public static class Screen extends CottonClientScreen {

		public Screen() {
			super(new ShardCreatorGuiDescription());
		}

		@Override
		public void tick() {
			super.tick();
			//System.out.println("bruh!");
		}

		@Override
		public void removed() {
			super.removed();
			//throw new IllegalStateException("lol");
			System.out.println("removed");
		}
	}

	public ShardCreatorGuiDescription() {
		WGridPanel root = new WGridPanel();
		setRootPanel(root);
		root.setSize(256, 240);
		root.setInsets(Insets.ROOT_PANEL);

		WSprite icon = new WSprite(new Identifier("minecraft:textures/item/redstone.png"));
		root.add(icon, 0, 2, 1, 1);

		WButton button = new WButton(Text.translatable("gui.examplemod.examplebutton"));
		root.add(button, 0, 3, 4, 1);

		WLabel label = new WLabel(Text.literal("Test"), 0xFFFFFF);
		root.add(label, 0, 4, 2, 1);

		root.validate(this);
		System.out.println("joe");
	}
}
