package me.ultimate;

import me.ultimate.block.LampBlockEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;


public class LampBlockEntityRenderer implements BlockEntityRenderer<LampBlockEntity> {
	private static final float SIZE = 0.05F;
	private static final float SIZE_2 = (SIZE * 2) + 1;
	private static final float RADS_90 = (float) Math.toRadians(90);
	private static final RenderLayer haloLayer = RenderLayer.of(
			"halo",
			VertexFormats.POSITION_COLOR,
			VertexFormat.DrawMode.QUADS,
			512,
			false,
			true,
			RenderLayer.MultiPhaseParameters.builder()
//					.depthTest(RenderLayer.ALWAYS_DEPTH_TEST) // Disable depth test
					.program(RenderLayer.LIGHTNING_PROGRAM)
					.transparency(RenderLayer.TRANSLUCENT_TRANSPARENCY)
					.cull(RenderPhase.DISABLE_CULLING)
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

		// Fix z fighting
		int priority = (entity.getPos().getX() % 2)
				+ (entity.getPos().getY() % 2)
				+ (entity.getPos().getZ() % 2);
		float zFix = (priority * 0.001F) - SIZE;

		positionMatrix.translate(zFix, zFix, zFix);

		// 4d? or 80?
		int color = 0x80_00_00_00 + entity.getColor().getFireworkColor();

		// north
		drawFace(vx, positionMatrix, color);

		// south
		positionMatrix.translate(0, 0, SIZE_2);
		drawFace(vx, positionMatrix, color);

		// west
		positionMatrix.rotateY(RADS_90);
		drawFace(vx, positionMatrix, color);

		// east
		positionMatrix.translate(0, 0, SIZE_2);
		drawFace(vx, positionMatrix, color);

		// down
		positionMatrix.rotateY(RADS_90);
		positionMatrix.rotateX(RADS_90);
		drawFace(vx, positionMatrix, color);

		// up
		positionMatrix.translate(0, 0, -SIZE_2);
		drawFace(vx, positionMatrix, color);

		matrices.pop();
	}

	private void drawFace(VertexConsumer vx, Matrix4f positionMatrix, int color) {
		vx.vertex(positionMatrix, 0, 0, 0).color(color).next();
		vx.vertex(positionMatrix, 0, SIZE_2, 0).color(color).next();
		vx.vertex(positionMatrix, SIZE_2, SIZE_2, 0).color(color).next();
		vx.vertex(positionMatrix, SIZE_2, 0, 0).color(color).next();
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
