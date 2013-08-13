package de.minestar.cok.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCrossbow extends ModelBase
{
  //fields
    ModelRenderer Shaft;
    ModelRenderer Trigger;
    ModelRenderer FrontLeft0;
    ModelRenderer FrontLeft1;
    ModelRenderer FrontLeft2;
    ModelRenderer FrontLeft3;
    ModelRenderer FrontRight0;
    ModelRenderer FrontRight1;
    ModelRenderer FrontRight2;
    ModelRenderer FrontRight3;
    ModelRenderer Bolt;
  
  public ModelCrossbow()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Shaft = new ModelRenderer(this, 0, 0);
      Shaft.addBox(0F, 0F, 0F, 3, 2, 14);
      Shaft.setRotationPoint(-1.5F, 0F, -7F);
      Shaft.setTextureSize(64, 32);
      Shaft.mirror = true;
      setRotation(Shaft, 0F, 0F, 0F);
      Trigger = new ModelRenderer(this, 0, 0);
      Trigger.addBox(0F, 0F, 0F, 2, 2, 1);
      Trigger.setRotationPoint(-1F, 2F, 3F);
      Trigger.setTextureSize(64, 32);
      Trigger.mirror = true;
      setRotation(Trigger, 0F, 0F, 0F);
      FrontLeft0 = new ModelRenderer(this, 0, 0);
      FrontLeft0.addBox(0F, 0F, 0F, 1, 1, 2);
      FrontLeft0.setRotationPoint(1.5F, 0F, -7F);
      FrontLeft0.setTextureSize(64, 32);
      FrontLeft0.mirror = true;
      setRotation(FrontLeft0, 0F, 0F, 0F);
      FrontLeft1 = new ModelRenderer(this, 0, 0);
      FrontLeft1.addBox(0F, 0F, 0F, 1, 1, 2);
      FrontLeft1.setRotationPoint(2.5F, 0F, -6F);
      FrontLeft1.setTextureSize(64, 32);
      FrontLeft1.mirror = true;
      setRotation(FrontLeft1, 0F, 0F, 0F);
      FrontLeft2 = new ModelRenderer(this, 0, 0);
      FrontLeft2.addBox(0F, 0F, 0F, 1, 1, 2);
      FrontLeft2.setRotationPoint(3.5F, 0F, -5F);
      FrontLeft2.setTextureSize(64, 32);
      FrontLeft2.mirror = true;
      setRotation(FrontLeft2, 0F, 0F, 0F);
      FrontLeft3 = new ModelRenderer(this, 0, 0);
      FrontLeft3.addBox(0F, 0F, 0F, 1, 1, 2);
      FrontLeft3.setRotationPoint(4.5F, 0F, -4F);
      FrontLeft3.setTextureSize(64, 32);
      FrontLeft3.mirror = true;
      setRotation(FrontLeft3, 0F, 0F, 0F);
      FrontRight0 = new ModelRenderer(this, 0, 0);
      FrontRight0.addBox(0F, 0F, 0F, 1, 1, 2);
      FrontRight0.setRotationPoint(-2.5F, 0F, -7F);
      FrontRight0.setTextureSize(64, 32);
      FrontRight0.mirror = true;
      setRotation(FrontRight0, 0F, 0F, 0F);
      FrontRight1 = new ModelRenderer(this, 0, 0);
      FrontRight1.addBox(0F, 0F, 0F, 1, 1, 2);
      FrontRight1.setRotationPoint(-3.5F, 0F, -6F);
      FrontRight1.setTextureSize(64, 32);
      FrontRight1.mirror = true;
      setRotation(FrontRight1, 0F, 0F, 0F);
      FrontRight2 = new ModelRenderer(this, 0, 0);
      FrontRight2.addBox(0F, 0F, 0F, 1, 1, 2);
      FrontRight2.setRotationPoint(-4.5F, 0F, -5F);
      FrontRight2.setTextureSize(64, 32);
      FrontRight2.mirror = true;
      setRotation(FrontRight2, 0F, 0F, 0F);
      FrontRight3 = new ModelRenderer(this, 0, 0);
      FrontRight3.addBox(0F, 0F, 0F, 1, 1, 2);
      FrontRight3.setRotationPoint(-5.5F, 0F, -4F);
      FrontRight3.setTextureSize(64, 32);
      FrontRight3.mirror = true;
      setRotation(FrontRight3, 0F, 0F, 0F);
      Bolt = new ModelRenderer(this, 0, 0);
      Bolt.addBox(0F, 0F, 0F, 1, 1, 9);
      Bolt.setRotationPoint(-0.5F, -1F, -6F);
      Bolt.setTextureSize(64, 32);
      Bolt.mirror = true;
      setRotation(Bolt, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Shaft.render(f5);
    Trigger.render(f5);
    FrontLeft0.render(f5);
    FrontLeft1.render(f5);
    FrontLeft2.render(f5);
    FrontLeft3.render(f5);
    FrontRight0.render(f5);
    FrontRight1.render(f5);
    FrontRight2.render(f5);
    FrontRight3.render(f5);
    Bolt.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  @Override
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
