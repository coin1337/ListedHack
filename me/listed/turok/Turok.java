package me.listed.turok;

import me.listed.turok.draw.GL;
import me.listed.turok.task.TurokFont;

public class Turok {
   private String tag;
   private TurokFont font_manager;

   public Turok(String tag) {
      this.tag = tag;
   }

   public void resize(int x, int y, float size) {
      GL.resize(x, y, size);
   }

   public void resize(int x, int y, float size, String tag) {
      GL.resize(x, y, size, "end");
   }

   public TurokFont get_font_manager() {
      return this.font_manager;
   }
}
