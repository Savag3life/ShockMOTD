package not.savage.motd.core;

import not.savage.motd.core.utility.ComponentWalker;

import java.util.List;

public interface CenteredLineFactory extends StringFactory {

    int MESSAGE_MAX_WIDTH = 225;
    int CORRECTED_MAX_WIDTH = MESSAGE_MAX_WIDTH - 25; // This removes the space under the ping.

    default String createCenteredLine(String message) {
        int messageLength = getTrueStringLength(message);
        if (messageLength >= MESSAGE_MAX_WIDTH) {
            return message;
        }
        int spaces = ((CORRECTED_MAX_WIDTH - messageLength) / 2) / DefaultFontInfo.SPACE.getLength();
        StringBuilder centeredLine = new StringBuilder();
        centeredLine.append("<white> ".repeat(Math.max(0, spaces)));
        centeredLine.append(message);
        return centeredLine.toString();
    }
}
