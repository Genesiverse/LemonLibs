# LemonLibs
Genesiverse Main Library API

# Usage

Make sure to (soft-)depend: `LemonLibs` to your  plugin.yml beforehand and check if LemonLibs is a loaded plugin via #Bukkit.getPluginManager();

```java

public class YourPlugin extends JavaPlugin {

  private LemonAPI api;

  @Override
  public void onEnable() {

    api = getServer().getServicesManager().load(LemonAPI.class);

  }

  public LemonAPI getLemonAPI() {

    return api;

  }

}

```
