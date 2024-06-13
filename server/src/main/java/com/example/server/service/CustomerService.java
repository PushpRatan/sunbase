package com.example.server.service;

import com.example.server.dto.requestDtos.CustomerRequestDto;
import com.example.server.dto.responseDtos.CustomerResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto, boolean syncDb);

    CustomerResponseDto updateCustomer(String email, CustomerRequestDto customerRequestDto);

    Page<CustomerResponseDto> getAllCustomers(int pageNo, int rowsCount, String sortBy, String searchBy);

    List<CustomerResponseDto> searchBySpecificType(String searchBy, String searchQuery);

    String deleteCustomer(String email);
}
