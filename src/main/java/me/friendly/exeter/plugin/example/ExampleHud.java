package me.friendly.exeter.plugin.example;

import me.friendly.api.minecraft.render.font.FontUtil;
import me.friendly.exeter.module.impl.active.render.Colors;
import me.friendly.exeter.module.impl.toggle.render.hud.HudModule;

/** Minimal HUD element: draws a line of text. */
public class ExampleHud extends HudModule {

  public ExampleHud() {
    super("ExampleHud", new String[] {"examplehud"}, Corner.TOP_LEFT);
    setDescription("Example plugin HUD: a line of text.");
    this.offerProperties();
  }

  @Override
  public int getWidth() {
    return FontUtil.getStringWidth("Example HUD");
  }

  @Override
  public int getHeight() {
    return 9;
  }

  @Override
  public void render(int scaledWidth, int scaledHeight) {
    FontUtil.drawString("Example HUD", getX(), getY(), Colors.getHudMain());
  }
}
