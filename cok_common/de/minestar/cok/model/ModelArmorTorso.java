/*******************************************************************************
 * Clash of Kingdoms Minecraft Mod
 *     Copyright (C) 2013 Geras
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package de.minestar.cok.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelArmorTorso extends ModelBiped
{
  
	private ModelRenderer leftShoulder;
	private ModelRenderer rightShoulder;
	
  public ModelArmorTorso()
  {
    textureWidth = 64;
    textureHeight = 32;
    
    this.bipedBody = new ModelRenderer(this, 0, 16);
    this.bipedBody.addBox(-4F, 0F, -2F, 9, 12, 5);
    this.bipedBody.setRotationPoint(0F, 0F, -0.5F);
    this.bipedBody.setTextureSize(64, 32);
    this.bipedBody.mirror = true;
      setRotation(this.bipedBody, 0F, 0F, 0F);
      this.bipedRightArm = new ModelRenderer(this, 40, 16);
      this.bipedRightArm.addBox(-3F, -2F, -2F, 5, 12, 5);
      this.bipedRightArm.setRotationPoint(-4.5F, 2F, -0.5F);
      this.bipedRightArm.setTextureSize(64, 32);
      this.bipedRightArm.mirror = true;
      setRotation(this.bipedRightArm, 0F, 0F, 0F);
      this.bipedLeftArm.mirror = true;
      this.bipedLeftArm = new ModelRenderer(this, 40, 16);
      this.bipedLeftArm.addBox(-1F, -2F, -2F, 5, 12, 5);
      this.bipedLeftArm.setRotationPoint(4.5F, 2F, -0.5F);
      this.bipedLeftArm.setTextureSize(64, 32);
      this.bipedLeftArm.mirror = true;
      setRotation(this.bipedLeftArm, 0F, 0F, 0F);
      this.bipedLeftArm.mirror = false;
      leftShoulder = new ModelRenderer(this, 0, 0);
      leftShoulder.addBox(0F, 0F, 0F, 6, 1, 6);
      leftShoulder.setRotationPoint(6F, -1.7F, -3F);
      leftShoulder.setTextureSize(64, 32);
      leftShoulder.mirror = true;
      setRotation(leftShoulder, 0F, 0F, 0.4833219F);
      rightShoulder = new ModelRenderer(this, 0, 0);
      rightShoulder.addBox(0F, 0F, 0F, 6, 1, 6);
      rightShoulder.setRotationPoint(-6F, -1.7F, 3F);
      rightShoulder.setTextureSize(64, 32);
      rightShoulder.mirror = true;
      setRotation(rightShoulder, 0F, -3.141593F, 0.4833219F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.bipedBody.render(f5);
    this.bipedRightArm.render(f5);
    this.bipedLeftArm.render(f5);
    leftShoulder.render(f5);
    rightShoulder.render(f5);
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
