package not.savage.motd.core.utility;

import com.google.gson.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.util.ArrayList;
import java.util.List;

public class ComponentWalker {

    // Record to store each piece of text along with its bold state
    public record TextElement(String text, boolean isBold) {}

    public static List<TextElement> collectTextElements(Component component) {
        String rawMessage = GsonComponentSerializer.gson().serialize(component);
        if (rawMessage.equals(" ") || rawMessage.equals("") || rawMessage.equals("\"\"") || rawMessage.equals("\" \"")) return new ArrayList<>();
        JsonObject object;
        try {
            object = JsonParser.parseString(rawMessage).getAsJsonObject();
        } catch (IllegalStateException | JsonSyntaxException er) {
            try {
                if (rawMessage.startsWith("\"")) {
                    rawMessage = rawMessage.substring(1, rawMessage.length() - 1);
                }
                object = JsonParser.parseString("{\"text\":\"" + rawMessage + "\"}").getAsJsonObject();
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
        return collectTextElements(object, false);
    }

    // Method to walk the JSON structure and collect text elements
    public static List<TextElement> collectTextElements(JsonObject jsonObject, boolean inheritedBold) {
        List<TextElement> elements = new ArrayList<>();

        // Determine if this object has "bold" set
        boolean isBold = jsonObject.has("bold") ? jsonObject.get("bold").getAsBoolean() : inheritedBold;

        // If there's direct text, collect it
        if (jsonObject.has("text")) {
            String text = jsonObject.has("text") ? jsonObject.get("text").getAsString() : "";
            if (!text.isEmpty()) {
                elements.add(new TextElement(text, isBold));
            }
        }

        // Recursively process any "extra" array
        if (jsonObject.has("extra")) {
            JsonArray extras = jsonObject.getAsJsonArray("extra");
            for (JsonElement extraElement : extras) {
                if (extraElement.isJsonObject()) {
                    elements.addAll(collectTextElements(extraElement.getAsJsonObject(), isBold));
                } else if (extraElement.isJsonPrimitive() && extraElement.getAsJsonPrimitive().isString()) {
                    elements.add(new TextElement(extraElement.getAsString(), isBold));
                }
            }
        }

        return elements;
    }
}
