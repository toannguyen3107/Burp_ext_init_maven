package com.decodeSBI;

import burp.api.montoya.proxy.http.InterceptedRequest;
import burp.api.montoya.proxy.http.ProxyRequestHandler;
import burp.api.montoya.proxy.http.ProxyRequestReceivedAction;
import burp.api.montoya.proxy.http.ProxyRequestToBeSentAction;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.core.Annotations;
import org.json.JSONObject;

import static burp.api.montoya.core.HighlightColor.RED;

public class ProcessProxyHttpRequest implements ProxyRequestHandler {
    @Override
    public ProxyRequestReceivedAction handleRequestReceived(InterceptedRequest interceptedRequest) {
        if (interceptedRequest.url().contains("mbuat.sbilhbank.com")) {
            Annotations annotations = interceptedRequest.annotations();
            annotations.setHighlightColor(RED);
            HttpRequest modifiedRequest;

            String body = interceptedRequest.bodyToString();
            JSONObject jsonObject = new JSONObject(body);
            //appzillonHeader  
            String appzillonHeader = jsonObject.optString("appzillonHeader");
            JSONObject jsonResult = new JSONObject().put("appzillonHeader", Crypto.decode1(appzillonHeader));
            // appzillonBody
            String appzillonBody = jsonObject.optString("appzillonBody");
            jsonResult.put("appzillonBody", Crypto.decode1(appzillonBody));
            String appzillonSafe = jsonObject.optString("appzillonSafe");
            jsonResult.put("appzillonSafe", appzillonSafe);
            modifiedRequest = interceptedRequest.withBody(jsonResult.toString());

            return ProxyRequestReceivedAction.continueWith(modifiedRequest, annotations);
        }
        return ProxyRequestReceivedAction.doNotIntercept(interceptedRequest);
    }

    @Override
    public ProxyRequestToBeSentAction handleRequestToBeSent(InterceptedRequest interceptedRequest) {
        return ProxyRequestToBeSentAction.continueWith(interceptedRequest);
    }

}
