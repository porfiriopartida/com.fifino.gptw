package com.fifino.framework.network;

/**
 * A HTTP get-post interface
 * 
 * @author porfiriopartida
 *
 */
public interface FifinoHttpClient {
    public String post(String host, String url);

    public String get(String host, String url);

    public String post(String host, String url, String[] args);

    public String get(String host, String url, String[] args);
}