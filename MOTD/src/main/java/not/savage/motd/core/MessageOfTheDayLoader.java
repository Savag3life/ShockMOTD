package not.savage.motd.core;

import not.savage.motd.core.utility.ConfigBuilder;

import java.io.File;
import java.nio.file.Path;

public interface MessageOfTheDayLoader {

    default MessageOfTheDayConfiguration loadConfiguration(Path rootDirectory) {
        return new ConfigBuilder<>(MessageOfTheDayConfiguration.class)
                .withPath(new File(rootDirectory.toFile(), "message-of-the-day.yml").toPath())
                .build();
    }

}
