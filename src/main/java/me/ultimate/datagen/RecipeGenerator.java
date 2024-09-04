package me.ultimate.datagen;

import me.ultimate.block.LampBlock;
import me.ultimate.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.DyeColor;

import java.util.function.Consumer;


public class RecipeGenerator extends FabricRecipeProvider {
	public RecipeGenerator(FabricDataOutput output) {
		super(output);
	}

	private Item dyeToItem(DyeColor color) {
		return switch (color) {
			case WHITE -> Items.WHITE_DYE;
			case ORANGE -> Items.ORANGE_DYE;
			case MAGENTA -> Items.MAGENTA_DYE;
			case LIGHT_BLUE -> Items.LIGHT_BLUE_DYE;
			case YELLOW -> Items.YELLOW_DYE;
			case LIME -> Items.LIME_DYE;
			case PINK -> Items.PINK_DYE;
			case GRAY -> Items.GRAY_DYE;
			case LIGHT_GRAY -> Items.LIGHT_GRAY_DYE;
			case CYAN -> Items.CYAN_DYE;
			case PURPLE -> Items.PURPLE_DYE;
			case BLUE -> Items.BLUE_DYE;
			case BROWN -> Items.BROWN_DYE;
			case GREEN -> Items.GREEN_DYE;
			case RED -> Items.RED_DYE;
			case BLACK -> Items.BLACK_DYE;
			default -> throw new IllegalStateException("Unmapped item value for dye color: " + color);
		};
	}

	@Override
	public void generate(Consumer<RecipeJsonProvider> exporter) {
		for (LampBlock block : ModBlocks.LAMP_BLOCKS) {
			Item dyeItem = dyeToItem(block.getColor());

			ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, block, 4)
				.group("led_lamps")
				.pattern("gdg")
				.pattern("gdg")
				.pattern("grg")
				.input('g', Items.GLASS)
				.input('d', dyeItem)
				.input('r', Items.REDSTONE)
				.criterion(FabricRecipeProvider.hasItem(Items.GLASS),
						FabricRecipeProvider.conditionsFromItem(Items.GLASS))
				.criterion(FabricRecipeProvider.hasItem(dyeItem),
						FabricRecipeProvider.conditionsFromItem(dyeItem))
				.criterion(FabricRecipeProvider.hasItem(Items.REDSTONE),
						FabricRecipeProvider.conditionsFromItem(Items.REDSTONE))
				.offerTo(exporter);
		}
	}
}
