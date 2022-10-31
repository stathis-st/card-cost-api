package com.stathis.cardcostapi.error;

import com.stathis.cardcostapi.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR ;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        HttpStatus httpStatus = response.getStatusCode();

        if (httpStatus.is4xxClientError()) {
            if (httpStatus.value() == 404) {
                throw new ResourceNotFoundException("bin not found");
            } else {
                throw new RuntimeException("Client error: " + httpStatus.value() + ", " + httpStatus.getReasonPhrase());
            }
        } else if (httpStatus.is5xxServerError()) {
            throw new RuntimeException("Server error: " + httpStatus.value() + ", " + httpStatus.getReasonPhrase());
        }
    }
}
