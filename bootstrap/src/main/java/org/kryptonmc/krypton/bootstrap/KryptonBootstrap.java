package org.kryptonmc.krypton.bootstrap;

import io.github.slimjar.app.Application;
import io.github.slimjar.app.ApplicationConfiguration;
import io.github.slimjar.app.ApplicationFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public final class KryptonBootstrap {

    public static void main(final String[] args) throws IOException, ReflectiveOperationException {
        final ApplicationConfiguration config = ApplicationConfiguration.createDefault("krypton");
        final ApplicationFactory appFactory = new ApplicationFactory(config);
        final Collection<String> modules = Collections.singleton("krypton-server");
        final Application app =
                appFactory.createIsolatedApplication(
                        modules,
                        "org.kryptonmc.krypton.bootstrap.KryptonApplication",
                        args
                );
        app.start();
    }

}
