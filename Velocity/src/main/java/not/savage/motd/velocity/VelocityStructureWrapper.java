package not.savage.motd.velocity;

import com.velocitypowered.api.proxy.server.ServerPing;
import com.velocitypowered.api.util.Favicon;
import net.kyori.adventure.text.minimessage.MiniMessage;
import not.savage.motd.core.WrappedInfoStructure;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VelocityStructureWrapper extends WrappedInfoStructure<ServerPing.Builder> {

    private final ServerPing.Builder packet;

    public VelocityStructureWrapper(ServerPing.Builder packet) {
        this.packet = packet;
    }

    @Override
    public ServerPing.Builder getInfoStructure() {
        return packet;
    }

    @Override
    public void setPlayersOnline(int playersOnline) {
        packet.onlinePlayers(playersOnline);
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        packet.maximumPlayers(maxPlayers);
    }

    @Override
    public void setMotd(String motd) {
        packet.description(MiniMessage.miniMessage().deserialize(motd));
    }

    @Override
    public void setHoverText(List<String> hoverText) {
        if (hoverText.isEmpty()) return;
        List<ServerPing.SamplePlayer> players = new ArrayList<>();
        for (String line : hoverText) {
            players.add(new ServerPing.SamplePlayer(GSON_SERIALIZER.serialize(MiniMessage.miniMessage().deserialize(line)), SPOOFED_UUID));
        }
        getInfoStructure().samplePlayers(players.toArray(new ServerPing.SamplePlayer[0]));
    }

    @Override
    public void setVersion(String version) {
        int protocolVersion = getInfoStructure().getVersion().getProtocol();
        getInfoStructure().version(new ServerPing.Version(protocolVersion, version));
    }

    @Override
    public void setFavicon(Path favicon) {
        Favicon fav = null;
        try {
            fav = Favicon.create(favicon);
        } catch (Exception ignored) { }
        assert fav != null;
        getInfoStructure().favicon(fav);
    }

    @Override
    public void setProtocol(int protocol) {
        String version = getInfoStructure().getVersion().getName();
        getInfoStructure().version(new ServerPing.Version(protocol, version));
    }
}
