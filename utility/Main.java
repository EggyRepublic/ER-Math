package utility;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("Enabling ER Math");
		getLogger().info("ER Math, by EggyRepublic 2020");
	}

	@Override
	public void onDisable() {
		getLogger().info("Disabling ER Math");
		getLogger().info("tank you, com again!");
		getLogger().info("ER Math, by EggyRepublic");
	}
}