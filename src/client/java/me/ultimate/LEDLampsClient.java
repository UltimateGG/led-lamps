package me.ultimate;

import me.ultimate.block.ModBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;


public class LEDLampsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRendererFactories.register(ModBlockEntities.LAMP_BLOCK_ENTITY, LampBlockEntityRenderer::new);
	}
}