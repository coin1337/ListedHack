package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.WurstplusEventCancellable;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class WurstplusEventRenderEntityModel extends WurstplusEventCancellable {
   public ModelBase modelBase;
   public Entity entity;
   public float limbSwing;
   public float limbSwingAmount;
   public float age;
   public float headYaw;
   public float headPitch;
   public float scale;
   public int stage;

   public WurstplusEventRenderEntityModel(int stage, ModelBase modelBase, Entity entity, float limbSwing, float limbSwingAmount, float age, float headYaw, float headPitch, float scale) {
      this.stage = stage;
      this.modelBase = modelBase;
      this.entity = entity;
      this.limbSwing = limbSwing;
      this.limbSwingAmount = limbSwingAmount;
      this.age = age;
      this.headYaw = headYaw;
      this.headPitch = headPitch;
      this.scale = scale;
   }
}
