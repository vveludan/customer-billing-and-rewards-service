package com.horizon.intl.customer.rewards.controller;

import com.horizon.intl.customer.rewards.domain.Customer;
import com.horizon.intl.customer.rewards.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rewards/api/v1")
@ApiOperation("Customer API")
public class CustomerController {

    private final CustomerService customerService;
    @GetMapping("/customers/{id}")
    @ApiOperation(value = "Get Customer By Customer Id", notes = "Returns Customer for given Customer Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Retrieved Customer"),
            @ApiResponse(code = 404, message = "Customer not found for customerId:"),
            @ApiResponse(code = 500, message = "Internal Error Occurred While Retrieving Customer")
    })
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") @ApiParam(name = "id", example = "111-11-1111 (SSN FORMAT)") String id) {
        Customer customer = customerService.getCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
    @GetMapping("/customers")
    @ApiOperation(value = " Get All Customers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Retrieved All Customers"),
            @ApiResponse(code = 204, message = "No Customers Found"),
            @ApiResponse(code = 500, message = "Internal Error Occurred While Retrieving Customers")
    })
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if(customers == null || customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    @PostMapping("/customers")
    @ApiOperation(value = "Create Customer", notes = "Returns Created Customer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully Created Customer"),
            @ApiResponse(code = 409, message = "Cannot Create Customer as Customer Already Exists"),
            @ApiResponse(code = 500, message = "Internal Error Occurred While Creating Customer")
    })
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    @PutMapping("/customers")
    @ApiOperation(value = "Update Customer", notes = "Returns Updated Customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Updated Customer"),
            @ApiResponse(code = 404, message = "Cannot Update Customer as Customer not found for customerId:"),
            @ApiResponse(code = 500, message = "Internal Error Occurred while Updating Customer")
    })
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.updateCustomer(customer), HttpStatus.OK);
    }

    @DeleteMapping("/customers/{id}")
    @ApiOperation(value = "Delete Customer", notes = "Returns 204 status on Success")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully Deleted Customer"),
            @ApiResponse(code = 404, message = "Cannot Delete Customer as Customer not found for customerId: "),
            @ApiResponse(code = 500, message = "Internal Error Occurred While Deleting Customer")
    })
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") @ApiParam(name = "id", example = "111-11-1111 (SSN FORMAT)") String id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
