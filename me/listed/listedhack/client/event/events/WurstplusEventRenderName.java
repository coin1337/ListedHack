package me.listed.listedhack.client.event.events;

import me.listed.listedhack.client.event.WurstplusEventCancellable;
import net.minecraft.client.entity.AbstractClientPlayer;

public class WurstplusEventRenderName extends WurstplusEventCancellable {
   public AbstractClientPlayer Entity;
   public double X;
   public double Y;
   public double Z;
   public String Name;
   public double DistanceSq;

   public WurstplusEventRenderName(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq) {
      this.Entity = entityIn;
      x = this.X;
      y = this.Y;
      z = this.Z;
      this.Name = name;
      this.DistanceSq = distanceSq;
   }
}
