package me.ultimate.datagen;

import me.ultimate.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;


public class LootTableProvider extends FabricBlockLootTableProvider {
	protected LootTableProvider(FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	@Override
	public void generate() {
		for (Block block : ModBlocks.LAMP_BLOCKS) this.addDrop(block);
	}
}
