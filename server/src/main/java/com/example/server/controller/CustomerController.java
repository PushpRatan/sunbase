package com.example.server.controller;

import com.example.server.dto.requestDtos.CustomerRequestDto;
import com.example.server.dto.responseDtos.CustomerResponseDto;
import com.example.server.exceptions.CustomerAlreadyExists;
import com.example.server.exceptions.CustomerNotFound;
import com.example.server.service.CustomerService;
import com.example.server.service.ExternalApiCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    ExternalApiCall externalApiCall = new ExternalApiCall();

    //Controller for creating the new customer
    @PostMapping("/create")
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto customerRequestDto,
            @RequestParam boolean syncDb) {

        try {
            CustomerResponseDto customerResponseDto = customerService.createCustomer(customerRequestDto, syncDb);
            return new ResponseEntity<>(customerResponseDto, HttpStatus.CREATED);
        } catch (CustomerAlreadyExists e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    // Controller for editing the data
    @PutMapping("/update/{email}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable String email,
            @RequestBody CustomerRequestDto customerRequestDto) {

        try {
            CustomerResponseDto customerResponseDto = customerService.updateCustomer(email, customerRequestDto);
            return new ResponseEntity<>(customerResponseDto, HttpStatus.ACCEPTED);
        } catch (CustomerNotFound e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    //Controller for fetching the customer data from db
    @GetMapping("/getCustomers")
    public ResponseEntity<Page<CustomerResponseDto>> getAllCustomers(@RequestParam int pageNo,
            @RequestParam int rowsCount, @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String searchBy) {

        Page<CustomerResponseDto> customerList = customerService.getAllCustomers(pageNo, rowsCount, sortBy, searchBy);
        return new ResponseEntity<>(customerList, HttpStatus.FOUND);

    }

    //Controller for getting a particular data according to the requirement
    @GetMapping("/searchBy")
    public ResponseEntity<List<CustomerResponseDto>> searchBySpecificType(@RequestParam String searchBy,
            @RequestParam String searchQuery) {
        List<CustomerResponseDto> searchedResult = customerService.searchBySpecificType(searchBy, searchQuery);
        return new ResponseEntity<>(searchedResult, HttpStatus.FOUND);
    }

    //Controller for delete a particular data
    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestParam String email) {
        try {
            String result = customerService.deleteCustomer(email);
            return new ResponseEntity(result, HttpStatus.ACCEPTED);
        } catch (CustomerNotFound e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    //Phase 2- Syncing data from different library
    @GetMapping("/sync")
    public ResponseEntity<Object[]> getTokenFromApi() {
        Object[] customerObject = externalApiCall.getTokenFromApi();
        return new ResponseEntity<>(customerObject, HttpStatus.ACCEPTED);
    }
}