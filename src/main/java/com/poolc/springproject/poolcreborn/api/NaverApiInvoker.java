package com.poolc.springproject.poolcreborn.api;

import com.poolc.springproject.poolcreborn.exception.InvalidRequestException;
import com.poolc.springproject.poolcreborn.util.Message;
import org.springframework.beans.factory.annotation.Value;
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
    private static String url;

    private String path;
    private String method;
    private String query;

    public NaverApiInvoker(NaverApiInvokerCommand command) throws InvalidRequestException {
        url = command.getAddress();
        path = choosePath(command.getType());
        method = command.getMethod();
        query = command.getQuery();
    }

    private String choosePath(ApiType type) throws InvalidRequestException {
        String path = "v1/search/";
        // 지금은 두 개뿐이지만 확장 가능성을 고려해 switch 문 사용
        switch (type) {
            case BOOK:
                path += "book";
                break;
            case PRODUCT:
                path += "shop";
                break;
            default:
                throw new InvalidRequestException(Message.UNAVAILABLE_API);
        }
        path += ".json";
        return path;
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

    private RequestEntity.HeadersBuilder<?> buildRequest(String method, URI uri) throws InvalidRequestException {
        if (method.equals("get")) {
            return RequestEntity.get(uri);
        } else if (method.equals("post")) {
            return RequestEntity.post(uri);
        } else {
            throw new InvalidRequestException(Message.REQUEST_DOES_NOT_EXIST);
        }
    }

}
