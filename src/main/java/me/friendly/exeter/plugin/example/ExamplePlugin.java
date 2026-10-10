package me.friendly.exeter.plugin.example;

import me.friendly.exeter.core.Exeter;
import me.friendly.exeter.module.ModuleManager;
import me.friendly.exeter.module.impl.toggle.client.WindowsModule;
import me.friendly.exeter.plugin.Plugin;
import me.friendly.exeter.window.WindowScreen;

/**
 * Entry point, loaded from the plugins folder because it extends {@link Plugin}.
 * Registers one module, one HUD element and one window, then cleans them up on stop.
 */
public class ExamplePlugin extends Plugin {
  private ExampleModule module;
  private ExampleHud hud;
  private ExampleWindow window;

  public ExamplePlugin() {
    super("Example");
  }

  @Override
  public void startPlugin() {
    ModuleManager modules = Exeter.getInstance().getModuleManager();
    module = new ExampleModule();
    modules.register(module);
    hud = new ExampleHud();
    modules.register(hud);
    WindowScreen screen = WindowsModule.getScreen();
    if (screen != null) {
      window = new ExampleWindow(460, 20, 240, 120);
      screen.addWindow(window);
    }
  }

  @Override
  public void stopPlugin() {
    ModuleManager modules = Exeter.getInstance().getModuleManager();
    if (module != null) {
      if (module.isRunning()) {
        module.toggle();
      }
      modules.unregister(module);
      module = null;
    }
    if (hud != null) {
      if (hud.isRunning()) {
        hud.toggle();
      }
      modules.unregister(hud);
      hud = null;
    }
    WindowScreen screen = WindowsModule.getScreen();
    if (screen != null && window != null) {
      screen.getWindows().remove(window);
      window = null;
    }
  }
}
