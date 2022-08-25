package com.horizon.intl.customer.rewards.repos;

import com.horizon.intl.customer.rewards.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, String> {
}
