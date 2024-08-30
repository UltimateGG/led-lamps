package me.ultimate.block;

import me.ultimate.LEDLamps;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModBlockEntities {
	public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(LEDLamps.MOD_ID, path), blockEntityType);
	}

	public static final BlockEntityType<LampBlockEntity> LAMP_BLOCK_ENTITY = register(
			"lamp_block_entity",
			FabricBlockEntityTypeBuilder.create(LampBlockEntity::new, ModBlocks.LAMP_BLOCKS.toArray(new Block[0])).build()
	);

	public static void initialize() { }
}
