package not.savage.motd.core;

import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public abstract class WrappedInfoStructure<K> {

    protected final GsonComponentSerializer GSON_SERIALIZER = GsonComponentSerializer.gson();
    protected final UUID SPOOFED_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public abstract K getInfoStructure();

    public abstract void setPlayersOnline(int playersOnline);

    public abstract void setMaxPlayers(int maxPlayers);

    public abstract void setMotd(String motd);

    public abstract void setHoverText(List<String> hoverText);

    public abstract void setVersion(String version);

    public abstract void setFavicon(Path favicon);

    public abstract void setProtocol(int protocol);

}
