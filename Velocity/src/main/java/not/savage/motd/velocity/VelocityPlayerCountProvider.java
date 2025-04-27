package not.savage.motd.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import lombok.NonNull;
import not.savage.motd.core.PlayerCountProvider;

public class VelocityPlayerCountProvider implements PlayerCountProvider {

    private final ProxyServer server;

    public VelocityPlayerCountProvider(@NonNull final ProxyServer server) {
        this.server = server;
    }

    @Override
    public int getOnlinePlayerCount() {
        return server.getPlayerCount();
    }
}
