package com.example.server.transformer;


import com.example.server.dto.requestDtos.CustomerRequestDto;
import com.example.server.dto.responseDtos.CustomerResponseDto;
import com.example.server.model.Customer;

public class CustomerTransformer {

    public static CustomerResponseDto customerToCustomerResponseDto(Customer customer){
        return CustomerResponseDto.builder()
                .Uid(customer.getUid())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .state(customer.getState())
                .street(customer.getStreet())
                .address(customer.getAddress())
                .joinedOn(customer.getJoinedOn())
                .phone(customer.getPhone())
                .city(customer.getCity())
                .build();
    }
    public static Customer customerRequestDtoToCustomer(CustomerRequestDto customerRequestDto){
        return Customer.builder()
                .firstName(customerRequestDto.getFirstName())
                .lastName(customerRequestDto.getLastName())
                .email(customerRequestDto.getEmail())
                .phone(customerRequestDto.getPhone())
                .city(customerRequestDto.getCity())
                .address(customerRequestDto.getAddress())
                .state(customerRequestDto.getState())
                .street(customerRequestDto.getStreet())
                .build();

    }
}