package not.savage.motd.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerPing;
import lombok.Setter;
import not.savage.motd.core.MessageOfTheDayConfiguration;
import not.savage.motd.core.MessageOfTheDayLoader;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "ShockMOTD",
        name = "ShockMOTD",
        version = "1.0.0-DEV",
        url = "https://supremeventures.ca/discord",
        description = "Powerful, Light-Weight MOTD plugin.",
        dependencies = {
                @Dependency(id = "SVCommonsLib")
        },
        authors = {
                "Savage"
        }
)
public class VelocityMOTDPlugin implements MessageOfTheDayLoader {

    private final ProxyServer server;
    private final Logger logger;

    @Setter private VelocityPlayerCountProvider playerCountProvider;
    private final MessageOfTheDayConfiguration configuration;

    @Inject
    public VelocityMOTDPlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.playerCountProvider = new VelocityPlayerCountProvider(server);
        this.configuration = loadConfiguration(dataDirectory);
        logger.info("Starting ShockMOTD...");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // Do some operation demanding access to the Velocity API here.
        // For instance, we could register an event:
        server.getEventManager().register(this, this);
    }

    @Subscribe
    public void onInfoRequest(ProxyPingEvent event) {
        ServerPing.Builder ping = event.getPing().asBuilder();
        VelocityStructureWrapper wrapper = new VelocityStructureWrapper(ping);
        configuration.applyTo(wrapper, playerCountProvider);
        event.setPing(wrapper.getInfoStructure().build());
    }
}
