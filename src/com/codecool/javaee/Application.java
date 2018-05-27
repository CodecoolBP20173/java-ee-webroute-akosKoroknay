package com.codecool.javaee;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class Application {

    public static void main(String[] args) {
        Application application = new Application();
        try {
            application.startServer();
        } catch (Exception e) {
            System.out.println("An error occured while starting the web server");
        }
    }

    static void executeRoute(HttpExchange httpExchange) throws IOException {
        Method[] methods = RequestHandler.class.getMethods();
        RequestHandler requestHandler = new RequestHandler();
        for (Method m : methods) {
            if (m.isAnnotationPresent(WebRoute.class)) {
                WebRoute webRoute = m.getAnnotation(WebRoute.class);

                if (httpExchange.getRequestURI().toString().equals(webRoute.route())) {
                    try {
                        System.out.println("The requested URL was: " + httpExchange.getRequestURI().toString() + "\nThe invoked method was: " + m.getName() + "\n");
                        m.invoke(requestHandler, httpExchange);
                    } catch (Exception e) {
                        System.out.println("Error when trying to invoke method: " + m.getName());
                    }
                    break;
                }
            }
        }
        System.out.println("The requested URL " + httpExchange.getRequestURI().toString() + "does not exist" + "\nThe invoked method was: onHome\n");
        RequestHandler.onHome(httpExchange);

    }

    void startServer() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                executeRoute(httpExchange);
            }
        });
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
