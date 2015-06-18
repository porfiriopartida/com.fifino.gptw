/*
package com.fifino.framework.network;

import java.util.HashMap;

//import org.json.JSONException;
//import org.json.JSONObject;

import com.loopj.twicecircled.android.http.AsyncHttpClient;
import com.loopj.twicecircled.android.http.AsyncHttpResponseHandler;
import com.loopj.twicecircled.android.http.RequestParams;

//
//import fifis.network.client.ClientReporter;
//import fifis.network.client.FifisClient;
//
////import java.io.BufferedWriter;
////import java.io.IOException;
////import java.io.OutputStreamWriter;
////import java.io.PrintWriter;
////import java.net.InetAddress;
////import java.net.Socket;
////import java.net.UnknownHostException;
//
////public class ClientHandler implements Runnable {
//public class ClientHandler extends FifisClient{
//	public static final int SERVERPORT = 5000;
//	public static final String SERVER_IP = "192.168.1.67";
////	public static final String SERVER_IP = "189.170.114.232"; 
//	public ClientHandler(){
//		this(new FlappyDroidClientReporter(), ClientHandler.SERVER_IP, ClientHandler.SERVERPORT);
//	}
//	public ClientHandler(ClientReporter reporter, String server, int port) {
//		super(reporter, server, port);
////		send("1");
//	}
//}
public class ClientHandler implements FifinoHttpClient {
    String jsonResponse = "_";

    @Override
    public String post(String host, String url) {
        return this.post(host, url, new String[0]);
    }

    @Override
    public String get(final String host, final String url) {
        return this.get(host, url, new String[0]);
    }

    @Override
    public String post(final String host, final String url, final String[] args) {
        this.jsonResponse = "_";
        new Thread() {
            public void run() {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < args.length; i++) {
                    map.put(args[i], args[++i]);
                }
                RequestParams params = new RequestParams(map);
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(host + url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String res) {
                        jsonResponse = res;
                    }
                });
            }
        }.start();
        while ("_".equals(jsonResponse)) {
            // Just stick here until the score is set.
        }
        return jsonResponse;
    }

    @Override
    public String get(final String host, final String url, final String[] args) {
        this.jsonResponse = "_";
        new Thread() {
            public void run() {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < args.length; i++) {
                    map.put(args[i], args[++i]);
                }
                RequestParams params = new RequestParams(map);
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(host + url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String res) {
                        jsonResponse = res;
                    }
                });
            }
        }.start();
        while ("_".equals(jsonResponse)) {
            // Just stick here until the score is set.
        }
        return jsonResponse;
    }
}*/
