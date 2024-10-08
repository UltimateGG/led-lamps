package me.ultimate.datagen;

import me.ultimate.LEDLamps;
import me.ultimate.block.LampBlock;
import me.ultimate.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.Optional;

import static net.minecraft.data.client.BlockStateModelGenerator.*;
import static net.minecraft.data.client.TexturedModel.makeFactory;


public class LampModelGenerator extends FabricModelProvider {
	private static final Model LAMP_MODEL = getBlockModel("lamp_off", TextureKey.ALL);
	private static final Model LAMP_MODEL_ON = getBlockModel("lamp_on", TextureKey.ALL);

	public LampModelGenerator(FabricDataOutput output) {
		super(output);
	}

	private static Model getBlockModel(String parent, TextureKey... requiredTextureKeys) {
		return new Model(Optional.ofNullable(Identifier.of(LEDLamps.MOD_ID, "block/" + parent)), Optional.empty(), requiredTextureKeys);
	}

	public static TextureMap lamp(Block block) {
		LampBlock lamp = (LampBlock) block;
		return (new TextureMap()).put(TextureKey.ALL, Identifier.of(LEDLamps.MOD_ID, "block/lampoff/" + lamp.getColor().ordinal()));
	}

	public static TextureMap lampOn(Identifier identifier) { // Hacky but yea idk why they dont just pass the block
		DyeColor color = DyeColor.byName(identifier.getPath().split("/")[1].replace("_lamp_on", ""), DyeColor.WHITE);
		return (new TextureMap()).put(TextureKey.ALL, Identifier.of(LEDLamps.MOD_ID, "block/lampon/" + color.ordinal()));
	}

	public static final TexturedModel.Factory TEMPLATE_LAMP = makeFactory(LampModelGenerator::lamp, LAMP_MODEL);

	public final void registerLamp(LampBlock lamp, BlockStateModelGenerator blockStateModelGenerator) {
		Identifier identifier = TEMPLATE_LAMP.upload(lamp, blockStateModelGenerator.modelCollector);
		Identifier identifier2 = blockStateModelGenerator.createSubModel(lamp, "_on", LAMP_MODEL_ON, LampModelGenerator::lampOn);
		blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(lamp).coordinate(createBooleanModelMap(Properties.LIT, identifier2, identifier)));
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		for (LampBlock block : ModBlocks.LAMP_BLOCKS) registerLamp(block, blockStateModelGenerator);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {

	}
}
