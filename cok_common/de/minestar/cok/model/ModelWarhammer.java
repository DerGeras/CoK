package de.minestar.cok.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWarhammer extends ModelBase {
	// fields
	ModelRenderer Shaft;
	ModelRenderer FrontSmall;
	ModelRenderer BackSmall;
	ModelRenderer FrontBig;
	ModelRenderer BackBig;

	public ModelWarhammer() {
		textureWidth = 64;
		textureHeight = 64;

		Shaft = new ModelRenderer(this, 0, 0);
		Shaft.addBox(0F, 0F, 0F, 5, 26, 5);
		Shaft.setRotationPoint(-2.5F, -2F, -2.5F);
		Shaft.setTextureSize(64, 64);
		Shaft.mirror = true;
		setRotation(Shaft, 0F, 0F, 0F);
		FrontSmall = new ModelRenderer(this, 44, 13);
		FrontSmall.addBox(0F, 0F, 0F, 5, 7, 5);
		FrontSmall.setRotationPoint(2.5F, -1F, -2.5F);
		FrontSmall.setTextureSize(64, 64);
		FrontSmall.mirror = true;
		setRotation(FrontSmall, 0F, 0F, 0F);
		BackSmall = new ModelRenderer(this, 44, 0);
		BackSmall.addBox(0F, 0F, 0F, 5, 7, 5);
		BackSmall.setRotationPoint(-7.5F, -1F, -2.5F);
		BackSmall.setTextureSize(64, 64);
		BackSmall.mirror = true;
		setRotation(BackSmall, 0F, 0F, 0F);
		FrontBig = new ModelRenderer(this, 20, 0);
		FrontBig.addBox(0F, 0F, 0F, 3, 11, 9);
		FrontBig.setRotationPoint(7.5F, -3F, -4.5F);
		FrontBig.setTextureSize(64, 64);
		FrontBig.mirror = true;
		setRotation(FrontBig, 0F, 0F, 0F);
		BackBig = new ModelRenderer(this, 20, 22);
		BackBig.addBox(0F, 0F, 0F, 3, 11, 9);
		BackBig.setRotationPoint(-10.5F, -3F, -4.5F);
		BackBig.setTextureSize(64, 64);
		BackBig.mirror = true;
		setRotation(BackBig, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Shaft.render(f5);
		FrontSmall.render(f5);
		BackSmall.render(f5);
		FrontBig.render(f5);
		BackBig.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
