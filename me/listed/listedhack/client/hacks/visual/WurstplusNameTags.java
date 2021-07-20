package me.listed.listedhack.client.hacks.visual;

import java.awt.Color;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;
import me.listed.listedhack.client.event.events.WurstplusEventRender;
import me.listed.listedhack.client.event.events.WurstplusEventRenderName;
import me.listed.listedhack.client.guiscreen.settings.WurstplusSetting;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.hacks.chat.WurstplusTotemAnnouncer;
import me.listed.listedhack.client.util.WurstplusDamageUtil;
import me.listed.listedhack.client.util.WurstplusEnemyUtil;
import me.listed.listedhack.client.util.WurstplusFriendUtil;
import me.listed.listedhack.client.util.WurstplusRenderUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

public class WurstplusNameTags extends WurstplusHack {
   WurstplusSetting show_armor = this.create("Armor", "NametagArmor", true);
   WurstplusSetting show_health = this.create("Health", "NametagHealth", true);
   WurstplusSetting show_ping = this.create("Ping", "NametagPing", true);
   WurstplusSetting show_totems = this.create("Totem Count", "NametagTotems", true);
   WurstplusSetting show_invis = this.create("Invis", "NametagInvis", true);
   WurstplusSetting reverse = this.create("Armour Reverse", "NametagReverse", true);
   WurstplusSetting simplify = this.create("Simplify", "NametagSimp", false);
   WurstplusSetting m_scale = this.create("Scale", "NametagScale", 4, 1, 15);
   WurstplusSetting range = this.create("Range", "NametagRange", 75, 1, 250);
   WurstplusSetting r = this.create("R", "NametagR", 255, 0, 255);
   WurstplusSetting g = this.create("G", "NametagG", 255, 0, 255);
   WurstplusSetting b = this.create("B", "NametagB", 255, 0, 255);
   WurstplusSetting a = this.create("A", "NametagA", 0.699999988079071D, 0.0D, 1.0D);
   WurstplusSetting rainbow_mode = this.create("Rainbow", "NametagRainbow", false);
   WurstplusSetting sat = this.create("Saturation", "NametagSatiation", 0.8D, 0.0D, 1.0D);
   WurstplusSetting brightness = this.create("Brightness", "NametagBrightness", 0.8D, 0.0D, 1.0D);
   @EventHandler
   private final Listener<WurstplusEventRenderName> on_render_name = new Listener((event) -> {
      event.cancel();
   }, new Predicate[0]);

   public WurstplusNameTags() {
      super(WurstplusCategory.WURSTPLUS_VISUAL);
      this.name = "Name Tags";
      this.tag = "NameTags";
      this.description = "MORE DETAILED NAMESSSSS";
   }

   public void render(WurstplusEventRender event) {
      Iterator var2 = mc.field_71441_e.field_73010_i.iterator();

      while(true) {
         EntityPlayer player;
         do {
            do {
               do {
                  do {
                     if (!var2.hasNext()) {
                        return;
                     }

                     player = (EntityPlayer)var2.next();
                  } while(player == null);
               } while(player.equals(mc.field_71439_g));
            } while(!player.func_70089_S());
         } while(player.func_82150_aj() && !this.show_invis.get_value(true));

         if (mc.field_71439_g.func_70032_d(player) < (float)this.range.get_value(1)) {
            double x = this.interpolate(player.field_70142_S, player.field_70165_t, event.get_partial_ticks()) - mc.func_175598_ae().field_78725_b;
            double y = this.interpolate(player.field_70137_T, player.field_70163_u, event.get_partial_ticks()) - mc.func_175598_ae().field_78726_c;
            double z = this.interpolate(player.field_70136_U, player.field_70161_v, event.get_partial_ticks()) - mc.func_175598_ae().field_78723_d;
            this.renderNameTag(player, x, y, z, event.get_partial_ticks());
         }
      }
   }

   public void update() {
      if (this.rainbow_mode.get_value(true)) {
         this.cycle_rainbow();
      }

   }

   public void cycle_rainbow() {
      float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0F};
      int color_rgb_o = Color.HSBtoRGB(tick_color[0], (float)this.sat.get_value(1), (float)this.brightness.get_value(1));
      this.r.set_value(color_rgb_o >> 16 & 255);
      this.g.set_value(color_rgb_o >> 8 & 255);
      this.b.set_value(color_rgb_o & 255);
   }

   private void renderNameTag(EntityPlayer player, double x, double y, double z, float delta) {
      double tempY = y + (player.func_70093_af() ? 0.5D : 0.7D);
      Entity camera = mc.func_175606_aa();

      assert camera != null;

      double originalPositionX = camera.field_70165_t;
      double originalPositionY = camera.field_70163_u;
      double originalPositionZ = camera.field_70161_v;
      camera.field_70165_t = this.interpolate(camera.field_70169_q, camera.field_70165_t, delta);
      camera.field_70163_u = this.interpolate(camera.field_70167_r, camera.field_70163_u, delta);
      camera.field_70161_v = this.interpolate(camera.field_70166_s, camera.field_70161_v, delta);
      String displayTag = this.getDisplayTag(player);
      double distance = camera.func_70011_f(x + mc.func_175598_ae().field_78730_l, y + mc.func_175598_ae().field_78731_m, z + mc.func_175598_ae().field_78728_n);
      int width = mc.field_71466_p.func_78256_a(displayTag) / 2;
      double scale = (0.0018D + (double)this.m_scale.get_value(1) * distance * 0.3D) / 1000.0D;
      if (distance <= 8.0D) {
         scale = 0.0245D;
      }

      GlStateManager.func_179094_E();
      RenderHelper.func_74519_b();
      GlStateManager.func_179088_q();
      GlStateManager.func_179136_a(1.0F, -1500000.0F);
      GlStateManager.func_179140_f();
      GlStateManager.func_179109_b((float)x, (float)tempY + 1.4F, (float)z);
      GlStateManager.func_179114_b(-mc.func_175598_ae().field_78735_i, 0.0F, 1.0F, 0.0F);
      float var10001 = mc.field_71474_y.field_74320_O == 2 ? -1.0F : 1.0F;
      GlStateManager.func_179114_b(mc.func_175598_ae().field_78732_j, var10001, 0.0F, 0.0F);
      GlStateManager.func_179139_a(-scale, -scale, scale);
      GlStateManager.func_179097_i();
      GlStateManager.func_179147_l();
      GlStateManager.func_179147_l();
      boolean is_friend = WurstplusFriendUtil.isFriend(player.func_70005_c_());
      boolean is_enemy = WurstplusEnemyUtil.isEnemy(player.func_70005_c_());
      int red = this.r.get_value(1);
      int green = this.g.get_value(1);
      int blue = this.b.get_value(1);
      if (is_friend) {
         red = 157;
         green = 99;
         blue = 255;
      }

      if (is_enemy) {
         red = 255;
         green = 40;
         blue = 7;
      }

      WurstplusRenderUtil.drawRect((float)(-width - 2) - 1.0F, (float)(-(mc.field_71466_p.field_78288_b + 1)) - 1.0F, (float)width + 3.0F, 2.5F, (float)red, (float)green, (float)blue, (float)this.a.get_value(1));
      WurstplusRenderUtil.drawRect((float)(-width - 2), (float)(-(mc.field_71466_p.field_78288_b + 1)), (float)width + 2.0F, 1.5F, 1426063360);
      GlStateManager.func_179084_k();
      ItemStack renderMainHand = player.func_184614_ca().func_77946_l();
      if (renderMainHand.func_77962_s() && (renderMainHand.func_77973_b() instanceof ItemTool || renderMainHand.func_77973_b() instanceof ItemArmor)) {
         renderMainHand.field_77994_a = 1;
      }

      if (!renderMainHand.field_190928_g && renderMainHand.func_77973_b() != Items.field_190931_a) {
         String stackName = renderMainHand.func_82833_r();
         int stackNameWidth = mc.field_71466_p.func_78256_a(stackName) / 2;
         GL11.glPushMatrix();
         GL11.glScalef(0.75F, 0.75F, 0.0F);
         mc.field_71466_p.func_175063_a(stackName, (float)(-stackNameWidth), -(this.getBiggestArmorTag(player) + 18.0F), -1);
         GL11.glScalef(1.5F, 1.5F, 1.0F);
         GL11.glPopMatrix();
      }

      if (this.show_armor.get_value(true)) {
         GlStateManager.func_179094_E();
         int xOffset = -8;
         Iterator var36 = player.field_71071_by.field_70460_b.iterator();

         while(var36.hasNext()) {
            ItemStack stack = (ItemStack)var36.next();
            if (stack != null) {
               xOffset -= 8;
            }
         }

         xOffset -= 8;
         ItemStack renderOffhand = player.func_184592_cb().func_77946_l();
         if (renderOffhand.func_77962_s() && (renderOffhand.func_77973_b() instanceof ItemTool || renderOffhand.func_77973_b() instanceof ItemArmor)) {
            renderOffhand.field_77994_a = 1;
         }

         this.renderItemStack(renderOffhand, xOffset);
         xOffset += 16;
         ItemStack stack2;
         ItemStack armourStack;
         if (this.reverse.get_value(true)) {
            Iterator var39 = player.field_71071_by.field_70460_b.iterator();

            label80:
            while(true) {
               do {
                  if (!var39.hasNext()) {
                     break label80;
                  }

                  stack2 = (ItemStack)var39.next();
               } while(stack2 == null);

               armourStack = stack2.func_77946_l();
               if (armourStack.func_77962_s() && (armourStack.func_77973_b() instanceof ItemTool || armourStack.func_77973_b() instanceof ItemArmor)) {
                  armourStack.field_77994_a = 1;
               }

               this.renderItemStack(armourStack, xOffset);
               xOffset += 16;
            }
         } else {
            for(int i = player.field_71071_by.field_70460_b.size(); i > 0; --i) {
               stack2 = (ItemStack)player.field_71071_by.field_70460_b.get(i - 1);
               armourStack = stack2.func_77946_l();
               if (armourStack.func_77962_s() && (armourStack.func_77973_b() instanceof ItemTool || armourStack.func_77973_b() instanceof ItemArmor)) {
                  armourStack.field_77994_a = 1;
               }

               this.renderItemStack(armourStack, xOffset);
               xOffset += 16;
            }
         }

         this.renderItemStack(renderMainHand, xOffset);
         GlStateManager.func_179121_F();
      }

      mc.field_71466_p.func_175063_a(displayTag, (float)(-width), (float)(-(mc.field_71466_p.field_78288_b - 1)), this.getDisplayColour(player));
      camera.field_70165_t = originalPositionX;
      camera.field_70163_u = originalPositionY;
      camera.field_70161_v = originalPositionZ;
      GlStateManager.func_179126_j();
      GlStateManager.func_179084_k();
      GlStateManager.func_179113_r();
      GlStateManager.func_179136_a(1.0F, 1500000.0F);
      GlStateManager.func_179121_F();
   }

   private void renderItemStack(ItemStack stack, int x) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179086_m(256);
      RenderHelper.func_74519_b();
      mc.func_175599_af().field_77023_b = -150.0F;
      GlStateManager.func_179118_c();
      GlStateManager.func_179126_j();
      GlStateManager.func_179129_p();
      mc.func_175599_af().func_180450_b(stack, x, -29);
      mc.func_175599_af().func_175030_a(mc.field_71466_p, stack, x, -29);
      mc.func_175599_af().field_77023_b = 0.0F;
      RenderHelper.func_74518_a();
      GlStateManager.func_179089_o();
      GlStateManager.func_179141_d();
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
      GlStateManager.func_179097_i();
      this.renderEnchantmentText(stack, x);
      GlStateManager.func_179126_j();
      GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
      GlStateManager.func_179121_F();
   }

   private void renderEnchantmentText(ItemStack stack, int x) {
      int enchantmentY = -37;
      NBTTagList enchants = stack.func_77986_q();
      int percent;
      if (enchants.func_74745_c() > 2 && this.simplify.get_value(true)) {
         mc.field_71466_p.func_175063_a("god", (float)(x * 2), (float)enchantmentY, -3977919);
         enchantmentY -= 8;
      } else {
         for(percent = 0; percent < enchants.func_74745_c(); ++percent) {
            short id = enchants.func_150305_b(percent).func_74765_d("id");
            short level = enchants.func_150305_b(percent).func_74765_d("lvl");
            Enchantment enc = Enchantment.func_185262_c(id);
            if (enc != null) {
               String encName = enc.func_190936_d() ? TextFormatting.RED + enc.func_77316_c(level).substring(11).substring(0, 1).toLowerCase() : enc.func_77316_c(level).substring(0, 1).toLowerCase();
               encName = encName + level;
               mc.field_71466_p.func_175063_a(encName, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY -= 8;
            }
         }
      }

      if (WurstplusDamageUtil.hasDurability(stack)) {
         percent = WurstplusDamageUtil.getRoundedDamage(stack);
         String color;
         if (percent >= 60) {
            color = this.section_sign() + "a";
         } else if (percent >= 25) {
            color = this.section_sign() + "e";
         } else {
            color = this.section_sign() + "c";
         }

         mc.field_71466_p.func_175063_a(color + percent + "%", (float)(x * 2), enchantmentY < -62 ? (float)enchantmentY : -62.0F, -1);
      }

   }

   private float getBiggestArmorTag(EntityPlayer player) {
      float enchantmentY = 0.0F;
      boolean arm = false;
      Iterator var4 = player.field_71071_by.field_70460_b.iterator();

      ItemStack renderOffHand;
      float encY;
      NBTTagList enchants;
      int index;
      short id;
      Enchantment enc;
      while(var4.hasNext()) {
         renderOffHand = (ItemStack)var4.next();
         encY = 0.0F;
         if (renderOffHand != null) {
            enchants = renderOffHand.func_77986_q();

            for(index = 0; index < enchants.func_74745_c(); ++index) {
               id = enchants.func_150305_b(index).func_74765_d("id");
               enc = Enchantment.func_185262_c(id);
               if (enc != null) {
                  encY += 8.0F;
                  arm = true;
               }
            }
         }

         if (encY > enchantmentY) {
            enchantmentY = encY;
         }
      }

      ItemStack renderMainHand = player.func_184614_ca().func_77946_l();
      if (renderMainHand.func_77962_s()) {
         float encY2 = 0.0F;
         NBTTagList enchants2 = renderMainHand.func_77986_q();

         for(int index2 = 0; index2 < enchants2.func_74745_c(); ++index2) {
            short id2 = enchants2.func_150305_b(index2).func_74765_d("id");
            Enchantment enc2 = Enchantment.func_185262_c(id2);
            if (enc2 != null) {
               encY2 += 8.0F;
               arm = true;
            }
         }

         if (encY2 > enchantmentY) {
            enchantmentY = encY2;
         }
      }

      renderOffHand = player.func_184592_cb().func_77946_l();
      if (renderOffHand.func_77962_s()) {
         encY = 0.0F;
         enchants = renderOffHand.func_77986_q();

         for(index = 0; index < enchants.func_74745_c(); ++index) {
            id = enchants.func_150305_b(index).func_74765_d("id");
            enc = Enchantment.func_185262_c(id);
            if (enc != null) {
               encY += 8.0F;
               arm = true;
            }
         }

         if (encY > enchantmentY) {
            enchantmentY = encY;
         }
      }

      return (float)(arm ? 0 : 20) + enchantmentY;
   }

   private String getDisplayTag(EntityPlayer player) {
      String name = player.getDisplayNameString();
      if (!this.show_health.get_value(true)) {
         return name;
      } else {
         float health = player.func_110143_aJ() + player.func_110139_bj();
         if (health <= 0.0F) {
            return name + " - DEAD";
         } else {
            String color;
            if (health > 25.0F) {
               color = this.section_sign() + "5";
            } else if (health > 20.0F) {
               color = this.section_sign() + "a";
            } else if (health > 15.0F) {
               color = this.section_sign() + "2";
            } else if (health > 10.0F) {
               color = this.section_sign() + "6";
            } else if (health > 5.0F) {
               color = this.section_sign() + "c";
            } else {
               color = this.section_sign() + "4";
            }

            String pingStr = "";
            if (this.show_ping.get_value(true)) {
               try {
                  int responseTime = ((NetHandlerPlayClient)Objects.requireNonNull(mc.func_147114_u())).func_175102_a(player.func_110124_au()).func_178853_c();
                  if (responseTime > 150) {
                     pingStr = pingStr + this.section_sign() + "4";
                  } else if (responseTime > 100) {
                     pingStr = pingStr + this.section_sign() + "6";
                  } else {
                     pingStr = pingStr + this.section_sign() + "2";
                  }

                  pingStr = pingStr + responseTime + "ms ";
               } catch (Exception var9) {
               }
            }

            String popStr = " ";
            if (this.show_totems.get_value(true)) {
               try {
                  popStr = popStr + (WurstplusTotemAnnouncer.totem_pop_counter.get(player.func_70005_c_()) == null ? this.section_sign() + "70" : this.section_sign() + "c -" + WurstplusTotemAnnouncer.totem_pop_counter.get(player.func_70005_c_()));
               } catch (Exception var8) {
               }
            }

            if (Math.floor((double)health) == (double)health) {
               name = name + color + " " + (health > 0.0F ? (int)Math.floor((double)health) : "dead");
            } else {
               name = name + color + " " + (health > 0.0F ? (int)health : "dead");
            }

            return pingStr + this.section_sign() + "r" + name + this.section_sign() + "r" + popStr;
         }
      }
   }

   private int getDisplayColour(EntityPlayer player) {
      int colour = -5592406;
      if (WurstplusFriendUtil.isFriend(player.func_70005_c_())) {
         return -11157267;
      } else {
         return WurstplusEnemyUtil.isEnemy(player.func_70005_c_()) ? -49632 : colour;
      }
   }

   private double interpolate(double previous, double current, float delta) {
      return previous + (current - previous) * (double)delta;
   }

   public String section_sign() {
      return "§";
   }
}
