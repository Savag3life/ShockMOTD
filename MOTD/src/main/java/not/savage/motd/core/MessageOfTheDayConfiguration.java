package not.savage.motd.core;

import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import not.savage.motd.core.utility.ComponentWalker;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.io.File;
import java.util.List;

@ConfigSerializable
@Getter
public class MessageOfTheDayConfiguration implements VersionLineFactory, CenteredLineFactory {

    private List<String> mainMessageLines = List.of(
            "c:<gradient:#58E6FF:#FFFFFF:#58E6FF>ShockSeries Test Realm <bold><#AFBED1>»</bold> <gray>[<#58E6FF>1.20.6 - 1.21.1+<gray>]",
            "c:<bold><#FF5555>ꜰᴀᴄᴛɪᴏɴꜱ<white>, <#FFAA00>ꜱᴋʏʙʟᴏᴄᴋ<white>, <#FFFF55>ꜱᴍᴘ<white>, ᴀɴᴅ <#FF5555>ᴍ<#FFAA00>ᴏ<#FFFF55>ʀ<#55FF55>ᴇ"
    );

    private final List<String> hoverMessage = List.of(
            "&b&lSupreme&f&lVentures &f» &bShockSeries Test Server",
            " ",
            "Currently Hosting:",
            "&b&l* &fAll-In-One Chat Core",
            "&b&l* &fScriptable Player Levels",
            "&b&l* &fIndepth Player Stats",
            "&b&l* &fSMP Focused Guilds",
            "&b&l* &fCompetitive Factions Fork",
            "&b&l* &fPremium Currency Shop",
            "&b&l* &fDiscord Linking",
            "&b&l* &fand many &am&eo&br&ce... ",
            " ",
            "&7&nhttps://supremeventures.ca/discord"
    );

    // Left Side + Padding + Right Side
    // Left Side Empty + Right Side = Override Player Count with Right Side
    // Left Side + Right Side Empty = Spoof Player Count + Padding + Left Side
    // Left Side Empty + Right Side Empty = Don't Override Anything - Normal Player Count
    private String leftSideLine = "&cᴊᴜꜱᴛ ᴜᴘᴅᴀᴛᴇᴅ"; // Empty Means Just Override Player Count
    private String rightSideLine = "&aJ&co&ei&bn &aN&co&ew"; // Empty Means Don't Override Anything

    // -1 Means Current Online Player + 1
    private int maxPlayers = -1;

    private String favicon = "server-icon.png";

    public void applyTo(WrappedInfoStructure<?> packet, PlayerCountProvider playerCountProvider) {

        if (!mainMessageLines.isEmpty()) {
            String firstMotdLine = mainMessageLines.get(0);
            String secondMotdLine = "";
            if (mainMessageLines.size() > 1)
                secondMotdLine = mainMessageLines.get(1);

            if (firstMotdLine.startsWith("c:")) {
                firstMotdLine = firstMotdLine.substring(2);
                firstMotdLine = createCenteredLine(firstMotdLine);
            }

            if (secondMotdLine.startsWith("c:")) {
                secondMotdLine = secondMotdLine.substring(2);
                secondMotdLine = createCenteredLine(secondMotdLine);
            }

            packet.setMotd("%s\n%s".formatted(firstMotdLine, secondMotdLine));
        }

        packet.setHoverText(hoverMessage);

        int playerCount = playerCountProvider.getOnlinePlayerCount();
        int maxPlayerCount = maxPlayers == -1 ? playerCount + 1 : maxPlayers;
        packet.setMaxPlayers(maxPlayerCount);
        packet.setPlayersOnline(playerCount);

        if (!favicon.isEmpty())
            packet.setFavicon(new File(favicon).toPath());

        if (leftSideLine == null || leftSideLine.isEmpty()) {
            if (rightSideLine == null || rightSideLine.isEmpty()) {
                // Nothing - this is vanilla behavior
                return;
            } else {
                createCustomVersionWithoutMarginText(packet, playerCount, maxPlayerCount, rightSideLine);
            }
        } else {
            if (rightSideLine == null || rightSideLine.isEmpty()) {
                createVanillaVersionWithMarginText(packet, playerCount, maxPlayerCount, leftSideLine);
            } else {
                if (rightSideLine.equals("empty")) {
                    createEmptyVersionWithMarginText(packet, leftSideLine);
                    return;
                }
                createCustomVersionWithMarginText(packet, playerCount, maxPlayerCount,leftSideLine, rightSideLine);
            }
        }
    }
}
