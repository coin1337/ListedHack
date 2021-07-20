package me.listed.listedhack.client.manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import me.listed.listedhack.client.event.events.WurstplusEventRender;
import me.listed.listedhack.client.hacks.ClickGUI;
import me.listed.listedhack.client.hacks.WurstplusCategory;
import me.listed.listedhack.client.hacks.WurstplusDiscordRPC;
import me.listed.listedhack.client.hacks.WurstplusHack;
import me.listed.listedhack.client.hacks.WurstplusManager;
import me.listed.listedhack.client.hacks.chat.WurstplusAnnouncer;
import me.listed.listedhack.client.hacks.chat.WurstplusAntiFemboy;
import me.listed.listedhack.client.hacks.chat.WurstplusArmorAlert;
import me.listed.listedhack.client.hacks.chat.WurstplusAutoCope;
import me.listed.listedhack.client.hacks.chat.WurstplusAutoDox;
import me.listed.listedhack.client.hacks.chat.WurstplusAutoEz;
import me.listed.listedhack.client.hacks.chat.WurstplusAutoFax;
import me.listed.listedhack.client.hacks.chat.WurstplusAutoSus;
import me.listed.listedhack.client.hacks.chat.WurstplusAutoToxic;
import me.listed.listedhack.client.hacks.chat.WurstplusBetterChat;
import me.listed.listedhack.client.hacks.chat.WurstplusChatMods;
import me.listed.listedhack.client.hacks.chat.WurstplusChatSuffix;
import me.listed.listedhack.client.hacks.chat.WurstplusClearChat;
import me.listed.listedhack.client.hacks.chat.WurstplusDeathCoords;
import me.listed.listedhack.client.hacks.chat.WurstplusEntityAlert;
import me.listed.listedhack.client.hacks.chat.WurstplusPearlAlert;
import me.listed.listedhack.client.hacks.chat.WurstplusPlayerAlert;
import me.listed.listedhack.client.hacks.chat.WurstplusTotemAnnouncer;
import me.listed.listedhack.client.hacks.combat.WurstplusAntiCrystal;
import me.listed.listedhack.client.hacks.combat.WurstplusAuto32k;
import me.listed.listedhack.client.hacks.combat.WurstplusAutoAnvil;
import me.listed.listedhack.client.hacks.combat.WurstplusAutoArmour;
import me.listed.listedhack.client.hacks.combat.WurstplusAutoBed;
import me.listed.listedhack.client.hacks.combat.WurstplusAutoCrystal;
import me.listed.listedhack.client.hacks.combat.WurstplusAutoGapple;
import me.listed.listedhack.client.hacks.combat.WurstplusAutoMend;
import me.listed.listedhack.client.hacks.combat.WurstplusAutoMine;
import me.listed.listedhack.client.hacks.combat.WurstplusAutoPiston;
import me.listed.listedhack.client.hacks.combat.WurstplusAutoTotem;
import me.listed.listedhack.client.hacks.combat.WurstplusAutoTrap;
import me.listed.listedhack.client.hacks.combat.WurstplusAutoWeb;
import me.listed.listedhack.client.hacks.combat.WurstplusBowSpam;
import me.listed.listedhack.client.hacks.combat.WurstplusCevBreaker;
import me.listed.listedhack.client.hacks.combat.WurstplusCriticals;
import me.listed.listedhack.client.hacks.combat.WurstplusEAOffhand;
import me.listed.listedhack.client.hacks.combat.WurstplusHoleFill;
import me.listed.listedhack.client.hacks.combat.WurstplusKillAura;
import me.listed.listedhack.client.hacks.combat.WurstplusOffhand;
import me.listed.listedhack.client.hacks.combat.WurstplusQuiver;
import me.listed.listedhack.client.hacks.combat.WurstplusSelfTrap;
import me.listed.listedhack.client.hacks.combat.WurstplusSurround;
import me.listed.listedhack.client.hacks.combat.WurstplusWebfill;
import me.listed.listedhack.client.hacks.exploit.WurstplusAntiDesync;
import me.listed.listedhack.client.hacks.exploit.WurstplusAntiHunger;
import me.listed.listedhack.client.hacks.exploit.WurstplusAntiWeakness;
import me.listed.listedhack.client.hacks.exploit.WurstplusAutoDupe;
import me.listed.listedhack.client.hacks.exploit.WurstplusAutoMount;
import me.listed.listedhack.client.hacks.exploit.WurstplusBuildHeight;
import me.listed.listedhack.client.hacks.exploit.WurstplusCoordExploit;
import me.listed.listedhack.client.hacks.exploit.WurstplusEntityControl;
import me.listed.listedhack.client.hacks.exploit.WurstplusEntityMine;
import me.listed.listedhack.client.hacks.exploit.WurstplusEntitySpeed;
import me.listed.listedhack.client.hacks.exploit.WurstplusFastMine;
import me.listed.listedhack.client.hacks.exploit.WurstplusFastUse;
import me.listed.listedhack.client.hacks.exploit.WurstplusListedDupe;
import me.listed.listedhack.client.hacks.exploit.WurstplusNoHandshake;
import me.listed.listedhack.client.hacks.exploit.WurstplusNoSwing;
import me.listed.listedhack.client.hacks.exploit.WurstplusPacketMine;
import me.listed.listedhack.client.hacks.exploit.WurstplusPortalGodMode;
import me.listed.listedhack.client.hacks.exploit.WurstplusQueueSkip;
import me.listed.listedhack.client.hacks.exploit.WurstplusReach;
import me.listed.listedhack.client.hacks.exploit.WurstplusXCarry;
import me.listed.listedhack.client.hacks.movement.WurstPlusAnchor;
import me.listed.listedhack.client.hacks.movement.WurstplusAntiLevitation;
import me.listed.listedhack.client.hacks.movement.WurstplusAutoWalk;
import me.listed.listedhack.client.hacks.movement.WurstplusBurrowBypass;
import me.listed.listedhack.client.hacks.movement.WurstplusElytraFly;
import me.listed.listedhack.client.hacks.movement.WurstplusIceSpeed;
import me.listed.listedhack.client.hacks.movement.WurstplusInstantBurrow;
import me.listed.listedhack.client.hacks.movement.WurstplusJesus;
import me.listed.listedhack.client.hacks.movement.WurstplusLongJump;
import me.listed.listedhack.client.hacks.movement.WurstplusNoFall;
import me.listed.listedhack.client.hacks.movement.WurstplusNoPush;
import me.listed.listedhack.client.hacks.movement.WurstplusNoSlow;
import me.listed.listedhack.client.hacks.movement.WurstplusNoVoid;
import me.listed.listedhack.client.hacks.movement.WurstplusParkour;
import me.listed.listedhack.client.hacks.movement.WurstplusReverseStep;
import me.listed.listedhack.client.hacks.movement.WurstplusScaffold;
import me.listed.listedhack.client.hacks.movement.WurstplusSprint;
import me.listed.listedhack.client.hacks.movement.WurstplusStep;
import me.listed.listedhack.client.hacks.movement.WurstplusStrafe;
import me.listed.listedhack.client.hacks.movement.WurstplusVClip;
import me.listed.listedhack.client.hacks.movement.WurstplusVelocity;
import me.listed.listedhack.client.hacks.movement.WurstplusYaw;
import me.listed.listedhack.client.hacks.other.WurstplusAntiAfk;
import me.listed.listedhack.client.hacks.other.WurstplusAutoCrash;
import me.listed.listedhack.client.hacks.other.WurstplusAutoGroom;
import me.listed.listedhack.client.hacks.other.WurstplusAutoLog;
import me.listed.listedhack.client.hacks.other.WurstplusAutoNomadHut;
import me.listed.listedhack.client.hacks.other.WurstplusAutoRat;
import me.listed.listedhack.client.hacks.other.WurstplusAutoReplenish;
import me.listed.listedhack.client.hacks.other.WurstplusAutoRespawn;
import me.listed.listedhack.client.hacks.other.WurstplusAutoSuicide;
import me.listed.listedhack.client.hacks.other.WurstplusFakePlayer;
import me.listed.listedhack.client.hacks.other.WurstplusMiddleClickEnemies;
import me.listed.listedhack.client.hacks.other.WurstplusMiddleClickFriends;
import me.listed.listedhack.client.hacks.other.WurstplusMiddleClickPearl;
import me.listed.listedhack.client.hacks.other.WurstplusPingSpoof;
import me.listed.listedhack.client.hacks.other.WurstplusPingbypass;
import me.listed.listedhack.client.hacks.other.WurstplusStatic;
import me.listed.listedhack.client.hacks.other.WurstplusStopEXP;
import me.listed.listedhack.client.hacks.other.WurstplusSwing;
import me.listed.listedhack.client.hacks.other.WurstplusTimer;
import me.listed.listedhack.client.hacks.other.WurstplusTpsSync;
import me.listed.listedhack.client.hacks.visual.WurstplusAlwaysNight;
import me.listed.listedhack.client.hacks.visual.WurstplusAnimations;
import me.listed.listedhack.client.hacks.visual.WurstplusAntifog;
import me.listed.listedhack.client.hacks.visual.WurstplusCapes;
import me.listed.listedhack.client.hacks.visual.WurstplusChams;
import me.listed.listedhack.client.hacks.visual.WurstplusChildEsp;
import me.listed.listedhack.client.hacks.visual.WurstplusCityEsp;
import me.listed.listedhack.client.hacks.visual.WurstplusFreecam;
import me.listed.listedhack.client.hacks.visual.WurstplusFuckedDetector;
import me.listed.listedhack.client.hacks.visual.WurstplusFullBright;
import me.listed.listedhack.client.hacks.visual.WurstplusHighlight;
import me.listed.listedhack.client.hacks.visual.WurstplusHoleESP;
import me.listed.listedhack.client.hacks.visual.WurstplusNameTags;
import me.listed.listedhack.client.hacks.visual.WurstplusNoBob;
import me.listed.listedhack.client.hacks.visual.WurstplusNoRender;
import me.listed.listedhack.client.hacks.visual.WurstplusShulkerPreview;
import me.listed.listedhack.client.hacks.visual.WurstplusSkyColour;
import me.listed.listedhack.client.hacks.visual.WurstplusSmallShield;
import me.listed.listedhack.client.hacks.visual.WurstplusTracers;
import me.listed.listedhack.client.hacks.visual.WurstplusViewmodleChanger;
import me.listed.turok.draw.RenderHelp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class WurstplusModuleManager {
   public static ArrayList<WurstplusHack> array_hacks = new ArrayList();
   public static Minecraft mc = Minecraft.func_71410_x();

   public WurstplusModuleManager() {
      this.add_hack(new ClickGUI());
      this.add_hack(new WurstplusChatSuffix());
      this.add_hack(new WurstplusPlayerAlert());
      this.add_hack(new WurstplusTotemAnnouncer());
      this.add_hack(new WurstplusClearChat());
      this.add_hack(new WurstplusChatMods());
      this.add_hack(new WurstplusAutoEz());
      this.add_hack(new WurstplusAnnouncer());
      this.add_hack(new WurstplusBetterChat());
      this.add_hack(new WurstplusAutoFax());
      this.add_hack(new WurstplusAntiFemboy());
      this.add_hack(new WurstplusAutoToxic());
      this.add_hack(new WurstplusPearlAlert());
      this.add_hack(new WurstplusAutoCope());
      this.add_hack(new WurstplusAutoDox());
      this.add_hack(new WurstplusDeathCoords());
      this.add_hack(new WurstplusArmorAlert());
      this.add_hack(new WurstplusAutoSus());
      this.add_hack(new WurstplusEntityAlert());
      this.add_hack(new WurstplusCriticals());
      this.add_hack(new WurstplusKillAura());
      this.add_hack(new WurstplusSurround());
      this.add_hack(new WurstplusVelocity());
      this.add_hack(new WurstplusAutoCrystal());
      this.add_hack(new WurstplusHoleFill());
      this.add_hack(new WurstplusAutoTrap());
      this.add_hack(new WurstplusSelfTrap());
      this.add_hack(new WurstplusAutoArmour());
      this.add_hack(new WurstplusAuto32k());
      this.add_hack(new WurstplusWebfill());
      this.add_hack(new WurstplusAutoWeb());
      this.add_hack(new WurstplusAutoBed());
      this.add_hack(new WurstplusOffhand());
      this.add_hack(new WurstplusAutoGapple());
      this.add_hack(new WurstplusAutoTotem());
      this.add_hack(new WurstplusAutoMine());
      this.add_hack(new WurstplusAutoAnvil());
      this.add_hack(new WurstplusBowSpam());
      this.add_hack(new WurstplusAntiCrystal());
      this.add_hack(new WurstplusAutoPiston());
      this.add_hack(new WurstplusQuiver());
      this.add_hack(new WurstplusCevBreaker());
      this.add_hack(new WurstplusEAOffhand());
      this.add_hack(new WurstplusAutoMend());
      this.add_hack(new WurstplusXCarry());
      this.add_hack(new WurstplusNoSwing());
      this.add_hack(new WurstplusPortalGodMode());
      this.add_hack(new WurstplusPacketMine());
      this.add_hack(new WurstplusEntityMine());
      this.add_hack(new WurstplusBuildHeight());
      this.add_hack(new WurstplusCoordExploit());
      this.add_hack(new WurstplusNoHandshake());
      this.add_hack(new WurstplusQueueSkip());
      this.add_hack(new WurstplusAutoDupe());
      this.add_hack(new WurstplusFastUse());
      this.add_hack(new WurstplusAntiDesync());
      this.add_hack(new WurstplusAntiHunger());
      this.add_hack(new WurstplusAntiWeakness());
      this.add_hack(new WurstplusAutoMount());
      this.add_hack(new WurstplusEntityControl());
      this.add_hack(new WurstplusEntitySpeed());
      this.add_hack(new WurstplusReach());
      this.add_hack(new WurstplusListedDupe());
      this.add_hack(new WurstplusStrafe());
      this.add_hack(new WurstplusStep());
      this.add_hack(new WurstplusSprint());
      this.add_hack(new WurstPlusAnchor());
      this.add_hack(new WurstplusNoVoid());
      this.add_hack(new WurstplusScaffold());
      this.add_hack(new WurstplusElytraFly());
      this.add_hack(new WurstplusNoFall());
      this.add_hack(new WurstplusNoPush());
      this.add_hack(new WurstplusJesus());
      this.add_hack(new WurstplusLongJump());
      this.add_hack(new WurstplusNoSlow());
      this.add_hack(new WurstplusAutoWalk());
      this.add_hack(new WurstplusParkour());
      this.add_hack(new WurstplusReverseStep());
      this.add_hack(new WurstplusVClip());
      this.add_hack(new WurstplusYaw());
      this.add_hack(new WurstplusAntiLevitation());
      this.add_hack(new WurstplusInstantBurrow());
      this.add_hack(new WurstplusBurrowBypass());
      this.add_hack(new WurstplusIceSpeed());
      this.add_hack(new WurstplusHighlight());
      this.add_hack(new WurstplusHoleESP());
      this.add_hack(new WurstplusShulkerPreview());
      this.add_hack(new WurstplusViewmodleChanger());
      this.add_hack(new WurstplusAntifog());
      this.add_hack(new WurstplusNameTags());
      this.add_hack(new WurstplusFuckedDetector());
      this.add_hack(new WurstplusTracers());
      this.add_hack(new WurstplusSkyColour());
      this.add_hack(new WurstplusChams());
      this.add_hack(new WurstplusCapes());
      this.add_hack(new WurstplusAlwaysNight());
      this.add_hack(new WurstplusCityEsp());
      this.add_hack(new WurstplusChildEsp());
      this.add_hack(new WurstplusFullBright());
      this.add_hack(new WurstplusFreecam());
      this.add_hack(new WurstplusNoRender());
      this.add_hack(new WurstplusNoBob());
      this.add_hack(new WurstplusAnimations());
      this.add_hack(new WurstplusSmallShield());
      this.add_hack(new WurstplusMiddleClickFriends());
      this.add_hack(new WurstplusStopEXP());
      this.add_hack(new WurstplusAutoReplenish());
      this.add_hack(new WurstplusAutoNomadHut());
      this.add_hack(new WurstplusMiddleClickPearl());
      this.add_hack(new WurstplusFastMine());
      this.add_hack(new WurstplusPingbypass());
      this.add_hack(new WurstplusPingSpoof());
      this.add_hack(new WurstplusAutoSuicide());
      this.add_hack(new WurstplusFakePlayer());
      this.add_hack(new WurstplusTimer());
      this.add_hack(new WurstplusAutoCrash());
      this.add_hack(new WurstplusAutoRat());
      this.add_hack(new WurstplusTpsSync());
      this.add_hack(new WurstplusAutoRespawn());
      this.add_hack(new WurstplusAutoGroom());
      this.add_hack(new WurstplusMiddleClickEnemies());
      this.add_hack(new WurstplusAutoLog());
      this.add_hack(new WurstplusSwing());
      this.add_hack(new WurstplusStatic());
      this.add_hack(new WurstplusAntiAfk());
      this.add_hack(new WurstplusDiscordRPC());
      this.add_hack(new WurstplusManager());
      array_hacks.sort(Comparator.comparing(WurstplusHack::get_name));
   }

   public void add_hack(WurstplusHack module) {
      array_hacks.add(module);
   }

   public ArrayList<WurstplusHack> get_array_hacks() {
      return array_hacks;
   }

   public ArrayList<WurstplusHack> get_array_active_hacks() {
      ArrayList<WurstplusHack> actived_modules = new ArrayList();
      Iterator var2 = this.get_array_hacks().iterator();

      while(var2.hasNext()) {
         WurstplusHack modules = (WurstplusHack)var2.next();
         if (modules.is_active()) {
            actived_modules.add(modules);
         }
      }

      return actived_modules;
   }

   public Vec3d process(Entity entity, double x, double y, double z) {
      return new Vec3d((entity.field_70165_t - entity.field_70142_S) * x, (entity.field_70163_u - entity.field_70137_T) * y, (entity.field_70161_v - entity.field_70136_U) * z);
   }

   public Vec3d get_interpolated_pos(Entity entity, double ticks) {
      return (new Vec3d(entity.field_70142_S, entity.field_70137_T, entity.field_70136_U)).func_178787_e(this.process(entity, ticks, ticks, ticks));
   }

   public void render(RenderWorldLastEvent event) {
      mc.field_71424_I.func_76320_a("wurstplus");
      mc.field_71424_I.func_76320_a("setup");
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179103_j(7425);
      GlStateManager.func_179097_i();
      GlStateManager.func_187441_d(1.0F);
      Vec3d pos = this.get_interpolated_pos(mc.field_71439_g, (double)event.getPartialTicks());
      WurstplusEventRender event_render = new WurstplusEventRender(RenderHelp.INSTANCE, pos);
      event_render.reset_translation();
      mc.field_71424_I.func_76319_b();
      Iterator var4 = this.get_array_hacks().iterator();

      while(var4.hasNext()) {
         WurstplusHack modules = (WurstplusHack)var4.next();
         if (modules.is_active()) {
            mc.field_71424_I.func_76320_a(modules.get_tag());
            modules.render(event_render);
            mc.field_71424_I.func_76319_b();
         }
      }

      mc.field_71424_I.func_76320_a("release");
      GlStateManager.func_187441_d(1.0F);
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
      GlStateManager.func_179126_j();
      GlStateManager.func_179089_o();
      RenderHelp.release_gl();
      mc.field_71424_I.func_76319_b();
      mc.field_71424_I.func_76319_b();
   }

   public void update() {
      Iterator var1 = this.get_array_hacks().iterator();

      while(var1.hasNext()) {
         WurstplusHack modules = (WurstplusHack)var1.next();
         if (modules.is_active()) {
            modules.update();
         }
      }

   }

   public void render() {
      Iterator var1 = this.get_array_hacks().iterator();

      while(var1.hasNext()) {
         WurstplusHack modules = (WurstplusHack)var1.next();
         if (modules.is_active()) {
            modules.render();
         }
      }

   }

   public void bind(int event_key) {
      if (event_key != 0) {
         Iterator var2 = this.get_array_hacks().iterator();

         while(var2.hasNext()) {
            WurstplusHack modules = (WurstplusHack)var2.next();
            if (modules.get_bind(0) == event_key) {
               modules.toggle();
            }
         }

      }
   }

   public WurstplusHack get_module_with_tag(String tag) {
      WurstplusHack module_requested = null;
      Iterator var3 = this.get_array_hacks().iterator();

      while(var3.hasNext()) {
         WurstplusHack module = (WurstplusHack)var3.next();
         if (module.get_tag().equalsIgnoreCase(tag)) {
            module_requested = module;
         }
      }

      return module_requested;
   }

   public ArrayList<WurstplusHack> get_modules_with_category(WurstplusCategory category) {
      ArrayList<WurstplusHack> module_requesteds = new ArrayList();
      Iterator var3 = this.get_array_hacks().iterator();

      while(var3.hasNext()) {
         WurstplusHack modules = (WurstplusHack)var3.next();
         if (modules.get_category().equals(category)) {
            module_requesteds.add(modules);
         }
      }

      return module_requesteds;
   }
}
