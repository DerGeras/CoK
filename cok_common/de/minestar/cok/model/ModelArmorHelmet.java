package de.minestar.cok.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelArmorHelmet extends ModelBiped
{
  
  public ModelArmorHelmet()
  {
    textureWidth = 64;
    textureHeight = 32;
    this.bipedHead = new ModelRenderer(this, 0, 0);
    this.bipedHead.addBox(-4F, -8F, -4F, 12, 5, 8);
    this.bipedHead.setRotationPoint(-2F, -4F, 0F);
    this.bipedHead.setTextureSize(64, 32);
    this.bipedHead.mirror = true;
    setRotation(this.bipedHead, 0F, 0F, 0F);
  }
  
  @Override
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.bipedHead.render(f5);
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
