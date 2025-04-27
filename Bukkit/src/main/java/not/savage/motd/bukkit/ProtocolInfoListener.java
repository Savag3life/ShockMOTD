package not.savage.motd.bukkit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import lombok.Setter;
import not.savage.motd.core.MessageOfTheDayConfiguration;
import not.savage.motd.core.PlayerCountProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ProtocolInfoListener extends PacketAdapter {

    private MessageOfTheDayConfiguration messageConfig;
    @Setter private PlayerCountProvider playerCountProvider;

    public ProtocolInfoListener(
            final JavaPlugin plugin,
            final MessageOfTheDayConfiguration messageConfig,
            final PlayerCountProvider playerCountProvider
    ) {
        super(
                PacketAdapter.params().plugin(plugin).types(PacketType.Status.Server.SERVER_INFO)
        );
        this.messageConfig = messageConfig;
        this.playerCountProvider = playerCountProvider;
    }

    @Override
    public void onPacketSending(final PacketEvent event) {
        WrappedServerPing ping = event.getPacket().getServerPings().read(0);
        ProtocolLibStructureWrapper wrapper = new ProtocolLibStructureWrapper(ping);
        messageConfig.applyTo(wrapper, playerCountProvider);
        event.getPacket().getServerPings().write(0, wrapper.getInfoStructure());
    }

    public void setConfig(MessageOfTheDayConfiguration messageConfig) {
        this.messageConfig = messageConfig;
    }
}
