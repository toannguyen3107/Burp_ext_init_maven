package com.decodeSBI;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
public class decodeSBI implements BurpExtension {
    @Override
    public void initialize(MontoyaApi api) {
        // setname extension
        api.extension().setName("decodeSBI");
        // write a message to our output stream
        api.proxy().registerRequestHandler(new ProcessProxyHttpRequest());

    }
}
