package me.ultimate;

import me.ultimate.block.LampBlockEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;


public class LampBlockEntityRenderer implements BlockEntityRenderer<LampBlockEntity> {
	private static final float SIZE = 0.05F;
	private static final float[][] vertices = {
			{ -SIZE, -SIZE, -SIZE}, {1 + SIZE, -SIZE, -SIZE},
			{1 + SIZE, 1 + SIZE, -SIZE}, {-SIZE, 1 + SIZE, -SIZE},
			{-SIZE, -SIZE, 1 + SIZE}, {1 + SIZE, -SIZE, 1 + SIZE},
			{1 + SIZE, 1 + SIZE, 1 + SIZE}, {-SIZE, 1 + SIZE, 1 + SIZE}
	};
	private static final RenderLayer haloLayer = RenderLayer.of(
			"halo",
			VertexFormats.POSITION_COLOR,
			VertexFormat.DrawMode.QUADS,
			512,
			false,
			true,
			RenderLayer.MultiPhaseParameters.builder()
					.program(RenderLayer.LIGHTNING_PROGRAM)
					.transparency(RenderLayer.LIGHTNING_TRANSPARENCY)
					.cull(RenderPhase.DISABLE_CULLING)
					.writeMaskState(RenderPhase.COLOR_MASK)
					.build(true)
	);

	public LampBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

	@Override
	public void render(LampBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		if (!entity.isLit()) return;

		matrices.push();

		MatrixStack.Entry entry = matrices.peek();
		Matrix4f positionMatrix = entry.getPositionMatrix();
		VertexConsumer vx = vertexConsumers.getBuffer(haloLayer);

		// 0x80 = 50% alpha
		int color = 0x80_00_00_00 + entity.getColor().getFireworkColor();

		drawFace(vx, positionMatrix, color, vertices[4], vertices[5], vertices[6], vertices[7]); // Front
		drawFace(vx, positionMatrix, color, vertices[0], vertices[1], vertices[2], vertices[3]); // Back
		drawFace(vx, positionMatrix, color, vertices[0], vertices[4], vertices[7], vertices[3]); // Left
		drawFace(vx, positionMatrix, color, vertices[1], vertices[5], vertices[6], vertices[2]); // Right
		drawFace(vx, positionMatrix, color, vertices[3], vertices[7], vertices[6], vertices[2]); // Top
		drawFace(vx, positionMatrix, color, vertices[0], vertices[1], vertices[5], vertices[4]); // Bottom

		matrices.pop();
	}

	private void drawFace(VertexConsumer vx, Matrix4f positionMatrix, int color, float[] v1, float[] v2, float[] v3, float[] v4) {
		vx.vertex(positionMatrix, v1[0], v1[1], v1[2]).color(color).next();
		vx.vertex(positionMatrix, v2[0], v2[1], v2[2]).color(color).next();
		vx.vertex(positionMatrix, v3[0], v3[1], v3[2]).color(color).next();
		vx.vertex(positionMatrix, v4[0], v4[1], v4[2]).color(color).next();
	}

	@Override
	public boolean rendersOutsideBoundingBox(LampBlockEntity blockEntity) {
		return blockEntity.isLit();
	}

	@Override
	public int getRenderDistance() {
		return 128; // 8 chunks
	}
}
