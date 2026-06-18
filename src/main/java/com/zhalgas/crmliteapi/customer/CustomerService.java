package com.zhalgas.crmliteapi.customer;


import com.zhalgas.crmliteapi.customer.dto.CustomerRequest;
import com.zhalgas.crmliteapi.customer.dto.CustomerResponse;
import com.zhalgas.crmliteapi.user.User;
import com.zhalgas.crmliteapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    public CustomerResponse create(CustomerRequest request) {
        User owner = userRepository.findById(request.ownerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Owner id not found: " + request.ownerId()
                ));

        Customer customer = customerMapper.toEntity(request, owner);
        Customer savedCustomer =  customerRepository.save(customer);

        return customerMapper.toResponse(savedCustomer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll() {

        return customerRepository.findAll()
                .stream()
                .map(customer -> customerMapper.toResponse(customer))
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Customer id not found: " + id
                ));
        return customerMapper.toResponse(customer);
    }

    @Transactional
    public CustomerResponse update(Long id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Customer id not found: " + id
                ));

        User owner = userRepository.findById(request.ownerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Owner id not found: " + request.ownerId()
                ));

        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());
        customer.setCompany(request.company());
        customer.setStatus(request.status());
        customer.setOwner(owner);

        Customer updatedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(updatedCustomer);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Customer id not found: " + id
            );
        }
          customerRepository.deleteById(id);
    }
}
