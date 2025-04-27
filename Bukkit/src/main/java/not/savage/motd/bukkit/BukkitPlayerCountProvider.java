package not.savage.motd.bukkit;

import not.savage.motd.core.PlayerCountProvider;
import org.bukkit.Bukkit;

public class BukkitPlayerCountProvider implements PlayerCountProvider {
    @Override
    public int getOnlinePlayerCount() {
        return Bukkit.getOnlinePlayers().size();
    }
}
