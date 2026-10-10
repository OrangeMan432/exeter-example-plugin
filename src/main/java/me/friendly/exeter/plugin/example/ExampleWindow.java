package me.friendly.exeter.plugin.example;

import com.mojang.blaze3d.platform.InputConstants;
import me.friendly.api.minecraft.render.RenderMethods;
import me.friendly.api.minecraft.render.font.FontUtil;
import me.friendly.exeter.core.Exeter;
import me.friendly.exeter.module.Module;
import me.friendly.exeter.window.Window;
import me.friendly.exeter.window.WindowButtons;
import net.minecraft.client.input.KeyEvent;

/** Minimal window: static text plus one button that toggles the example module. */
public class ExampleWindow extends Window {
  private static final int BUTTON_HEIGHT = 12;

  public ExampleWindow(int x, int y, int width, int height) {
    super("Example", x, y, width, height);
  }

  private ExampleModule module() {
    Module module = Exeter.getInstance().getModuleManager().getModuleByAlias("example");
    return module instanceof ExampleModule example ? example : null;
  }

  @Override
  protected void renderContent(int mouseX, int mouseY, float partialTicks) {
    FontUtil.drawString("Hello from the example plugin.", x + 5, y + TITLE_HEIGHT + 4, 0xFFEEEEEE);
    int btnY = y + TITLE_HEIGHT + 20;
    buttons.drawButton(0, btnY, mouseX, mouseY, toggled() ? "Disable Module" : "Enable Module");
  }

  private boolean toggled() {
    ExampleModule module = module();
    return module != null && module.isRunning();
  }

  private final WindowButtons buttons = new WindowButtons(this, 1, BUTTON_HEIGHT, id -> true);

  @Override
  protected boolean consumeClick(int mouseX, int mouseY, int button) {
    if (button != InputConstants.MOUSE_BUTTON_LEFT) {
      return false;
    }
    int btnY = y + TITLE_HEIGHT + 20;
    if (buttons.clickButton(0, btnY, mouseX, mouseY)) {
      ExampleModule module = module();
      if (module != null) {
        module.toggle();
      }
      return true;
    }
    return false;
  }

  @Override
  protected boolean consumeKeyPress(KeyEvent event) {
    return false;
  }
}
