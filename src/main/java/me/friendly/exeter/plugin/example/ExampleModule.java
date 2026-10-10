package me.friendly.exeter.plugin.example;

import me.friendly.api.event.Listener;
import me.friendly.api.event.Stage;
import me.friendly.exeter.events.TickEvent;
import me.friendly.exeter.logging.DebugLogger;
import me.friendly.exeter.module.ModuleType;
import me.friendly.exeter.module.ToggleableModule;
import me.friendly.exeter.properties.NumberProperty;
import me.friendly.exeter.properties.Property;

/** Minimal toggleable module: counts ticks while enabled and shows the count. */
public class ExampleModule extends ToggleableModule {

  private final Property<Boolean> announce = new Property<Boolean>(true, "Announce");
  private final NumberProperty<Integer> every =
      new NumberProperty<Integer>(100, 1, 1200, "Every Ticks");

  private int ticks;

  public ExampleModule() {
    super("Example", new String[] {"example"}, 0x55FFFF, ModuleType.MISCELLANEOUS);
    setDescription("Example plugin module: counts ticks.");
    offerProperties(announce, every);
    announce.setDescription("Log the counter each time it wraps.");
    every.setDescription("Ticks per counter wrap.");
    listeners.add(
        new Listener<TickEvent>("example_tick") {
          @Override
          public void call(TickEvent event) {
            if (event.getStage() != Stage.PRE) return;
            ExampleModule.this.onTick();
          }
        });
  }

  @Override
  protected void onEnable() {
    super.onEnable();
    ticks = 0;
    DebugLogger.get().log(getLabel(), DebugLogger.Level.INFO, "enabled");
  }

  @Override
  protected void onDisable() {
    super.onDisable();
    DebugLogger.get().log(getLabel(), DebugLogger.Level.INFO, "disabled");
  }

  @Override
  public String getTag() {
    return String.valueOf(ticks);
  }

  private void onTick() {
    if (minecraft.player == null || minecraft.level == null) return;
    ticks++;
    if (ticks >= every.getValue()) {
      ticks = 0;
      if (announce.getValue()) {
        DebugLogger.get().log(getLabel(), DebugLogger.Level.INFO, "wrapped");
      }
    }
  }
}
