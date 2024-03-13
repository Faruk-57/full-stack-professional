package com.amigoscode.customer;

import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.RequestValidationException;
import com.amigoscode.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {

        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {

        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Integer customerId) {
        return customerDao.selectCustomerById(customerId)
                .orElseThrow(()-> new ResourceNotFoundException("customer with id [%s] not found".formatted(customerId)
                ));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        // check if email exists
        String email = customerRegistrationRequest.email();
       if (customerDao.existsPersonWithEmail(email)) {
           throw new DuplicateResourceException("email already taken");
       }
       //add
        Customer customer =  new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age());
        customerDao.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer customerId){
        if(!customerDao.existsPersonWithId(customerId)){
            throw new ResourceNotFoundException("customer with id [%s] not found".formatted(customerId));
        }
        customerDao.deleteCustomerById(customerId);
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest updateRequest) {
        Customer customer = getCustomer(customerId);

        boolean changes = false;

        if(updateRequest.name() != null && !customer.getName().equals(updateRequest.name())) {
            customer.setName(updateRequest.name());
            changes = true;
        }
        if(updateRequest.email() != null && !customer.getEmail().equals(updateRequest.email())) {
            if(customerDao.existsPersonWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException("email already taken");
            }
            customer.setEmail((updateRequest.email()));
            changes = true;
        }
        if(updateRequest.age() != null && !customer.getAge().equals(updateRequest.age())) {
            customer.setAge(updateRequest.age());
            changes = true;
        }
        if (!changes){
            throw new RequestValidationException("no data changes found");
        }
        customerDao.updateCustomer(customer);


    }
}
