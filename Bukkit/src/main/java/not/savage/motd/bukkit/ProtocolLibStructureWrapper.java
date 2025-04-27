package not.savage.motd.bukkit;

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import not.savage.motd.core.WrappedInfoStructure;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProtocolLibStructureWrapper extends WrappedInfoStructure<WrappedServerPing> {

    private final WrappedServerPing packet;

    public ProtocolLibStructureWrapper(WrappedServerPing packet) {
        this.packet = packet;
    }

    @Override
    public WrappedServerPing getInfoStructure() {
        return packet;
    }

    @Override
    public void setPlayersOnline(int playersOnline) {
        getInfoStructure().setPlayersOnline(playersOnline);
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        getInfoStructure().setPlayersMaximum(maxPlayers);
    }

    @Override
    public void setMotd(String message) {
        getInfoStructure().setMotD(WrappedChatComponent.fromJson(GSON_SERIALIZER.serialize(MiniMessage.miniMessage().deserialize(message))));
    }

    @Override
    public void setHoverText(List<String> hoverText) {
        List<WrappedGameProfile> lines = new ArrayList<>();
        for (String message : hoverText) {
            WrappedGameProfile profile = new WrappedGameProfile(SPOOFED_UUID, LegacyComponentSerializer.legacySection().serialize(LegacyComponentSerializer.legacyAmpersand().deserialize(message)));
            lines.add(profile);
        }
        getInfoStructure().setPlayers(lines);
    }

    @Override
    public void setVersion(String version) {
        getInfoStructure().setVersionName(LegacyComponentSerializer.legacySection().serialize(LegacyComponentSerializer.legacyAmpersand().deserialize(version)));
    }

    @Override
    public void setFavicon(Path favicon) {
        try {
            getInfoStructure().setFavicon(WrappedServerPing.CompressedImage.fromPng(Files.newInputStream(favicon)));
        } catch (Exception ignored) {}
    }

    @Override
    public void setProtocol(int protocol) {
        getInfoStructure().setVersionProtocol(protocol);
    }
}
