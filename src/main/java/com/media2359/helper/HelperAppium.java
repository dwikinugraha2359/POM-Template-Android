package com.media2359.helper;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

/**
 * HelperAppium
 */
public class HelperAppium {

    private AppiumDriverLocalService service;

    public void startServer(int port) {
        System.out.println("Start Appium Server on Port – " + port);
        // Build the Appium service
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.usingPort(port);
        // builder.withCapabilities(cap);
        // builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        // builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");

        // Start the server with the builder
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
    }

    public void stopServer() {
        System.out.println("Stop Appium Server");
        service.stop();
    }

    public boolean checkIfServerIsRunnning(int port) {
        // Assume no connection is possible.
        boolean result = false;

        try {
            (new Socket("0.0.0.0", port)).close();
            result = true;
        } catch (IOException e) {
            // Could not connect.
            System.out.println(e.getMessage());
        }

        return result;
    }
}