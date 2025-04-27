package not.savage.motd.core;

import org.spongepowered.configurate.util.Strings;

public interface VersionLineFactory extends StringFactory {

    int LEFT_MARGIN_LINE_START = 245;
    String VANILLA_PLAYER_COUNT_FORMAT = "§7%d§8/§7%d";

    default void createEmptyVersionWithMarginText(WrappedInfoStructure<?> structure, String leftMarginContent) {
        structure.setProtocol(-1);
        structure.setVersion(leftMarginContent + Strings.repeat(" ", (int) (LEFT_MARGIN_LINE_START / 3.2)));
    }

    /**
     * Creates a version bar with "left-side-text" + favicon + "playerCount/maxPlayers"
     * @param structure
     * @param playerCount
     * @param maxPlayers
     * @param leftMarginContent
     */
    default void createVanillaVersionWithMarginText(WrappedInfoStructure<?> structure, int playerCount, int maxPlayers, String leftMarginContent) {
        int versionSize = getPlayerCountSize(playerCount, maxPlayers);
        structure.setProtocol(-1);
        structure.setVersion(leftMarginContent + Strings.repeat(" ", (LEFT_MARGIN_LINE_START - versionSize) / DefaultFontInfo.SPACE.getLength()) + String.format(VANILLA_PLAYER_COUNT_FORMAT, playerCount, maxPlayers));
    }

    /**
     * Creates a version bar with "right-side-text" + "playerCount/maxPlayers"
     * @param structure
     * @param playerCount
     * @param maxPlayers
     * @param version
     */
    default void createCustomVersionWithoutMarginText(WrappedInfoStructure<?> structure, int playerCount, int maxPlayers, String version) {
        structure.setProtocol(-1);
        structure.setVersion(version + " " + String.format(VANILLA_PLAYER_COUNT_FORMAT, playerCount, maxPlayers));
    }

    default void createCustomVersionWithMarginText(WrappedInfoStructure<?> structure, int playerCount, int maxPlayers, String leftMarginContent, String rightMarginContent) {
        int versionSize = getTrueStringLength(rightMarginContent);
        versionSize += getPlayerCountSize(playerCount, maxPlayers);
        structure.setProtocol(-1);
        if (versionSize >= LEFT_MARGIN_LINE_START) {
            structure.setVersion(leftMarginContent + rightMarginContent + " " + String.format(VANILLA_PLAYER_COUNT_FORMAT, playerCount, maxPlayers));
            return;
        }
        structure.setVersion(leftMarginContent + Strings.repeat(" ",(int) ((LEFT_MARGIN_LINE_START - versionSize) / 3)) + rightMarginContent + " " + String.format(VANILLA_PLAYER_COUNT_FORMAT, playerCount, maxPlayers));
    }

    default int getPlayerCountSize(int x, int z) {
        return DefaultFontInfo.SLASH.getLength() + DefaultFontInfo.getLengthOf(String.valueOf(x))
                + DefaultFontInfo.getLengthOf(String.valueOf(z)) + DefaultFontInfo.SPACE.getLength();
    }
}
