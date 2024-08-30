package me.ultimate.block;

import me.ultimate.LEDLamps;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.ArrayList;


public class ModBlocks {
	public static final DyeColor[] LAMP_COLORS = new DyeColor[] {
			DyeColor.WHITE,
			DyeColor.RED,
			DyeColor.ORANGE,
			DyeColor.YELLOW,
			DyeColor.LIME,
			DyeColor.GREEN,
			DyeColor.LIGHT_BLUE,
			DyeColor.CYAN,
			DyeColor.BLUE,
			DyeColor.PURPLE,
			DyeColor.MAGENTA,
			DyeColor.PINK,
			DyeColor.GRAY
	};

	public static final ArrayList<LampBlock> LAMP_BLOCKS = new ArrayList<>();

	static {
		for (DyeColor color : LAMP_COLORS) {
			LAMP_BLOCKS.add(
					register(new LampBlock(color), color.getName() + "_lamp")
			);
		}
	}

	public static LampBlock register(LampBlock block, String name) {
		Identifier id = Identifier.of(LEDLamps.MOD_ID, name);

		BlockItem blockItem = new BlockItem(block, new Item.Settings());
		Registry.register(Registries.ITEM, id, blockItem);

		return Registry.register(Registries.BLOCK, id, block);
	}

	public static void initialize() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register((itemGroup) -> {
			for (Block block : LAMP_BLOCKS) itemGroup.add(block.asItem());
		});
	}
}
