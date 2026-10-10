package me.friendly.exeter.plugin.example;

import me.friendly.exeter.core.Exeter;
import me.friendly.exeter.module.ModuleManager;
import me.friendly.exeter.module.impl.toggle.client.WindowsModule;
import me.friendly.exeter.plugin.Plugin;

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
    window = new ExampleWindow(460, 20, 240, 120);
    registerWindow(window);
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
    if (window != null) {
      unregisterWindow(window.getTitle());
      window = null;
    }
  }

  /**
   * Registers through WindowsModule when the client supports it (reflection keeps
   * this building against older client jars); otherwise adds to the live screen
   * directly, which works until the screen rebuilds.
   */
  private static void registerWindow(me.friendly.exeter.window.Window window) {
    Object windows = windows();
    try {
      windows
          .getClass()
          .getMethod("registerWindow", me.friendly.exeter.window.Window.class)
          .invoke(windows, window);
      return;
    } catch (ReflectiveOperationException e) {
      // Old client: fall through to the direct add below.
    }
    me.friendly.exeter.window.WindowScreen screen =
        me.friendly.exeter.module.impl.toggle.client.WindowsModule.getScreen();
    if (screen != null) {
      screen.addWindow(window);
    }
  }

  private static void unregisterWindow(String title) {
    Object windows = windows();
    try {
      windows.getClass().getMethod("unregisterWindow", String.class).invoke(windows, title);
      return;
    } catch (ReflectiveOperationException e) {
      // Old client: fall through to the direct remove below.
    }
    me.friendly.exeter.window.WindowScreen screen =
        me.friendly.exeter.module.impl.toggle.client.WindowsModule.getScreen();
    if (screen != null) {
      screen.getWindows().removeIf(window -> window.getTitle().equalsIgnoreCase(title));
    }
  }

  private static WindowsModule windows() {
    return (WindowsModule)
        Exeter.getInstance().getModuleManager().getModuleByAlias("windows");
  }
}
