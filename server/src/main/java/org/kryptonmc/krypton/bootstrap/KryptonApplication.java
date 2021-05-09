package org.kryptonmc.krypton.bootstrap;

import io.github.slimjar.app.Application;

public class KryptonApplication extends Application {
    private final String[] args;


    public KryptonApplication(String[] args) {
        this.args = args;
    }


    @Override
    public boolean start() {
        System.out.println("Krypton Started");
        for (String arg: args) {
            System.out.println(arg);
        }
        return super.start();
    }
}
