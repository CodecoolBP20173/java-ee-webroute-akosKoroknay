package com.codecool.javaee;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import static com.codecool.javaee.Application.executeRoute;

public class RequestHandler implements HttpHandler {

    @WebRoute(route = "/test")
    public static void onTest(HttpExchange requestData) throws IOException {
//         Here goes the code to handle all requests going to myserver.com/test
//         and to return something
        String response = "You are on the test page";
        requestData.sendResponseHeaders(200, response.length());
        OutputStream os = requestData.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    @WebRoute()
    public static void onHome(HttpExchange requestData) throws IOException {
//         Here goes the code to handle all requests going to myserver.com/test
//         and to return something
        String response = "You are on the Home page";
        requestData.sendResponseHeaders(200, response.length());
        OutputStream os = requestData.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        executeRoute(httpExchange);
    }
}
//
//    static class MyHandler implements HttpHandler {
//        @Override
//        public void handle(HttpExchange t) throws IOException {
//            String response = "This is the response";
//            t.sendResponseHeaders(200, response.length());
//            OutputStream os = t.getResponseBody();
//            os.write(response.getBytes());
//            os.close();
//        }
//    }


