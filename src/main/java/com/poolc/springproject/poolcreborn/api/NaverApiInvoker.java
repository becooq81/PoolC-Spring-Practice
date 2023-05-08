package com.poolc.springproject.poolcreborn.api;

import com.poolc.springproject.poolcreborn.exception.InvalidRequestException;
import com.poolc.springproject.poolcreborn.util.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


public class NaverApiInvoker {

    @Value("${poolcreborn.app.clientId}")
    private String clientId;
    @Value("${poolcreborn.app.clientSecret}")
    private String clientSecret;
    private static final String url = "https://openapi.naver.com/";

    private String path;
    private HttpMethod method;
    private String query;

    public NaverApiInvoker(ApiSearchRequest request) {
        path = request.getCommand().getUrl();
        method = request.getCommand().getHttpMethod();
        query = request.getQuery();
    }

    public ResponseEntity<String> naverBookSearchApi() throws InvalidRequestException {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path(path)
                .queryParam("query", query)
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = buildRequest(method, uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();

        return restTemplate.exchange(req, String.class);
    }

    private RequestEntity.HeadersBuilder<?> buildRequest(HttpMethod method, URI uri) throws InvalidRequestException {
        if (method.equals(HttpMethod.GET)) {
            return RequestEntity.get(uri);
        } else if (method.equals(HttpMethod.POST)) {
            return RequestEntity.post(uri);
        } else {
            throw new InvalidRequestException(Message.REQUEST_DOES_NOT_EXIST);
        }
    }

}