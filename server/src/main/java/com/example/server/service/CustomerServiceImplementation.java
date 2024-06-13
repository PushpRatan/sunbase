package com.example.server.service;

import com.example.server.dto.requestDtos.CustomerRequestDto;
import com.example.server.dto.responseDtos.CustomerResponseDto;
import com.example.server.exceptions.CustomerAlreadyExists;
import com.example.server.exceptions.CustomerNotFound;
import com.example.server.model.Customer;
import com.example.server.repository.CustomerRepository;
import com.example.server.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImplementation implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    //create the customer
    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto, boolean SyncDb) {

        Customer customer = customerRepository.findByEmail(customerRequestDto.getEmail());

        CustomerResponseDto customerResponseDto = new CustomerResponseDto();

        //updating same customer while performing sync
        if (SyncDb && customer != null) {
            customerResponseDto = updateCustomer(customerRequestDto.getEmail(), customerRequestDto);
        } else if (customer != null) {
            throw new CustomerAlreadyExists("Customer Already exits");
        } else {
            //making customer object from customerRequestDto
            customer = CustomerTransformer.customerRequestDtoToCustomer(customerRequestDto);

            customer.setUid(String.valueOf(UUID.randomUUID()));

            Customer savedCustomer = customerRepository.save(customer);
            // making customerResponse from customer
            customerResponseDto = CustomerTransformer.customerToCustomerResponseDto(savedCustomer);
            customerResponseDto.setMessage("user has been added");
        }
        return customerResponseDto;
    }


    //Edit the customer
    @Override
    public CustomerResponseDto updateCustomer(String email, CustomerRequestDto customerRequestDto) {

        //Check if customer is present
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            //if not present... throw error!
            throw new CustomerNotFound("Customer not found");
        }

        //Checking the requestDto and assign the non empty values
        if (customerRequestDto.getFirstName() != null) {
            customer.setFirstName(customerRequestDto.getFirstName());
        }
        if (customerRequestDto.getLastName() != null) {
            customer.setLastName(customerRequestDto.getLastName());
        }
        if (customerRequestDto.getStreet() != null) {
            customer.setStreet(customerRequestDto.getStreet());
        }
        if (customerRequestDto.getAddress() != null) {
            customer.setAddress(customerRequestDto.getAddress());
        }
        if (customerRequestDto.getCity() != null) {
            customer.setCity(customerRequestDto.getCity());
        }
        if (customerRequestDto.getState() != null) {
            customer.setState(customerRequestDto.getState());
        }
        if (customerRequestDto.getPhone() != null) {
            customer.setPhone(customerRequestDto.getPhone());
        }
        if (customerRequestDto.getEmail() != null) {
            customer.setEmail(customerRequestDto.getEmail());
        }

        Customer savedCustomer = customerRepository.save(customer);

        //making responseDto
        CustomerResponseDto customerResponseDto = CustomerTransformer.customerToCustomerResponseDto(savedCustomer);
        customerResponseDto.setMessage("user has been successfully updated");

        return customerResponseDto;

    }

    //Fetching all the customers
    @Override
    public Page<CustomerResponseDto> getAllCustomers(int pageNo, int rowsCount, String sortBy, String searchBy) {

        //This function returns the list of cust with required number of rows
        Pageable currentPageWithRequiredRows;

        if (sortBy != null) {
            currentPageWithRequiredRows = PageRequest.of(pageNo - 1, rowsCount, Sort.by(sortBy));
        } else {
            currentPageWithRequiredRows = PageRequest.of(pageNo - 1, rowsCount);
        }

        Page<Customer> customersPage = customerRepository.findAll(currentPageWithRequiredRows);
        return customersPage.map(this::convertToDto);
    }

    public CustomerResponseDto convertToDto(Customer customer) {
        return CustomerTransformer.customerToCustomerResponseDto(customer);
    }

    //Search on the basis of specific criteria
    @Override
    public List<CustomerResponseDto> searchBySpecificType(String searchBy, String searchQuery) {
        List<Customer> searchRes = new ArrayList<>();

        //checking the criteria and finding it from repository
        if (searchBy.equals("firstName")) {
            searchRes = customerRepository.findByFirstNameLike(searchQuery);
        } else if (searchBy.equals("city")) {
            searchRes = customerRepository.findByCityLike(searchQuery);
        } else if (searchBy.equals("phone")) {
            searchRes = customerRepository.findByPhoneLike(searchQuery);
        } else if (searchBy.equals("email")) {
            searchRes = customerRepository.findByEmailLike(searchQuery);
        }
        List<CustomerResponseDto> searchList = new ArrayList<>();

        for (Customer cust : searchRes) {
            searchList.add(CustomerTransformer.customerToCustomerResponseDto(cust));
        }
        return searchList;
    }

    //For deleting
    @Override
    @Transactional
    public String deleteCustomer(String email) {

        Customer customer = customerRepository.findByEmail(email);

        if (customer == null) {
            throw new CustomerNotFound("Customer not found with this emailId");
        }

        customerRepository.deleteByEmail(email);

        return "Customer deleted Successfully";

    }

}
