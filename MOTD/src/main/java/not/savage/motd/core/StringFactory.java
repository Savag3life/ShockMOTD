package not.savage.motd.core;

import net.kyori.adventure.text.minimessage.MiniMessage;
import not.savage.motd.core.utility.ComponentWalker;

import java.util.List;

public interface StringFactory {
    default int getTrueStringLength(String message) {
        List<ComponentWalker.TextElement> components = ComponentWalker.collectTextElements(MiniMessage.miniMessage().deserialize(message));
        int length = 0;
        for (ComponentWalker.TextElement component : components) {
            if (component.isBold()) {
                length += DefaultFontInfo.getLengthOf(component.text());
                String str = component.text();
                str = str.replace(" ", "");
                length += str.length();
            } else {
                length += DefaultFontInfo.getLengthOf(component.text());
            }
        }
        return length;
    }
}
