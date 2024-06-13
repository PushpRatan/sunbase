package com.example.server.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ExternalApiCall {
    private final String tokenUrl = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
    private final String customerListApi = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";

    // fetching token and data from sunbase url
    public Object[] getTokenFromApi() {

        // Setting the login and password
        String requestBody = "{ \"login_id\": \"test@sunbasedata.com\", \"password\": \"Test@123\" }";

        // Calling the function to recieve token
        String token = Apicall(tokenUrl, requestBody);
        System.out.println(token);
        // storing the token
        String acessToken = token.substring(19, token.length() - 3);

        // fetching data from sunbase db by getCustomer function
        List<Object> customers = getCustomers(acessToken, customerListApi);

        //Return the received data as an array
        Object[] customerReceived = customers.toArray();
        return customerReceived;
    }

    public String Apicall(String apiUrl, String requestBody) {

        //RestTemplate and httpHeader helps to consume the external api
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        //setting content type of header
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class);

        String responseBody = responseEntity.getBody();
        return responseBody;
    }

    public List<Object> getCustomers(String token, String apiurl) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Object[]> responseEntity = restTemplate.exchange(
                apiurl,
                HttpMethod.GET,
                requestEntity,
                Object[].class);

        Object[] responseBody = responseEntity.getBody();
        return List.of(responseBody);
    }
}
