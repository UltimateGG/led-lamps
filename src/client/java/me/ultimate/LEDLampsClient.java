package me.ultimate;

import me.ultimate.block.LampBlock;
import me.ultimate.block.ModBlockEntities;
import me.ultimate.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;

import java.util.HashMap;


public class LEDLampsClient implements ClientModInitializer {
	private final HashMap<DyeColor, Integer> colorMap = new HashMap<>();

	@Override
	public void onInitializeClient() {
		BlockEntityRendererFactories.register(ModBlockEntities.LAMP_BLOCK_ENTITY, LampBlockEntityRenderer::new);

		colorMap.put(DyeColor.WHITE, 0xFFFFFF);
		colorMap.put(DyeColor.RED, 0xFFCC0000);
		colorMap.put(DyeColor.ORANGE, 0xFFFF9900);
		colorMap.put(DyeColor.YELLOW, 0xFFFFFF00);
		colorMap.put(DyeColor.LIME, 0xFF55FF55);
		colorMap.put(DyeColor.GREEN, 0xFF22AA22);
		colorMap.put(DyeColor.LIGHT_BLUE, 0x3399FFFF);
		colorMap.put(DyeColor.CYAN, 0xFF00FFFF);
		colorMap.put(DyeColor.BLUE, 0xFF1111DD);
		colorMap.put(DyeColor.PURPLE, 0xFF9900FF);
		colorMap.put(DyeColor.MAGENTA, /*0xFFFF55FF*/0xFF_B51AB5);
		colorMap.put(DyeColor.PINK, 0xFFFF99BB);
		colorMap.put(DyeColor.GRAY, 0xFF555555);

		ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) ->
			colorMap.getOrDefault(((LampBlock)state.getBlock()).getColor(), 0xFFFFFF),
			ModBlocks.LAMP_BLOCKS.toArray(new Block[0]));

		ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
			colorMap.getOrDefault(((LampBlock)((BlockItem) stack.getItem()).getBlock()).getColor(), 0xFFFFFF),
			ModBlocks.LAMP_BLOCKS.stream().map(Block::asItem).toList().toArray(new Item[0]));
	}
}