package not.savage.motd.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import lombok.Getter;
import not.savage.motd.core.MessageOfTheDayConfiguration;
import not.savage.motd.core.MessageOfTheDayLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitMOTDPlugin extends JavaPlugin implements MessageOfTheDayLoader {

    @Getter private static BukkitMOTDPlugin instance;
    @Getter private MessageOfTheDayConfiguration configuration;
    @Getter private ProtocolInfoListener listener;

    @Override
    public void onEnable() {
        instance = this;
        configuration = loadConfiguration(getDataFolder().toPath());
        listener = new ProtocolInfoListener(this, configuration, new BukkitPlayerCountProvider());
        ProtocolLibrary.getProtocolManager().addPacketListener(listener);
    }

    public void reload() {
        configuration = loadConfiguration(getDataFolder().toPath());
        listener.setConfig(configuration);
    }
}
