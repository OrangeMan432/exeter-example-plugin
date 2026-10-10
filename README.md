# Exeter Example Plugin

Minimal [Exeter client](https://github.com/OrangeMan432/exeter) plugin showing the
three things plugins can add: a toggleable module (`ExampleModule`), a HUD element
(`ExampleHud`) and a window (`ExampleWindow`), all registered from `ExamplePlugin`.
Drop the built jar into `config/exeter/plugins/` (or install it from the client's
Plugins window) and it loads on startup.

## Building

- JDK 25 (Eclipse Temurin). The Gradle wrapper is in the repo.
- The Minecraft classes come from Fabric Loom; the Exeter API jar downloads
  automatically from the client's dev release:

```sh
./gradlew build        # compile + package build/libs/exeter-example-plugin-1.0.jar
./gradlew format       # format all sources with google-java-format
```

To build against a local client checkout instead of the download:

```sh
./gradlew build -PexeterJar=C:/Users/orang/repos/exeter/build/libs/exeter-1.0.jar
```

The `minecraft_version` in `gradle.properties` must match the client version the
plugin runs against.

## How it works

- `ExamplePlugin extends Plugin`: the client instantiates it from the jar and calls
  `startPlugin()` / `stopPlugin()`. Registration uses the same managers as
  built-in content (`ModuleManager.register`, `WindowsModule` screen).
- Never bundle the game or the Exeter jar: both are `compileOnly`, the client
  supplies them at runtime.

## Releasing

Push a tag and the release workflow attaches the jar:

```sh
git tag v1.0 && git push origin v1.0
```

Then submit the plugin to the
[plugin list](https://github.com/OrangeMan432/exeter-plugin-list) so the client's
Plugins window can find it.

## Workflows

Mirrors the client repo: dev builds (`dev.yml`), tagged releases (`release.yml`),
PR artifacts (`pr-build.yml`) and formatting (`format-check.yml`). The client's
smoke test is intentionally not mirrored: it boots the full game, which is
meaningless for a plugin jar.
