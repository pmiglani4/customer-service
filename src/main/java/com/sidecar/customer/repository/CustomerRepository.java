package com.sidecar.customer.repository;

import com.sidecar.customer.domain.Customer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Cacheable(value = "customers")
    List<Customer> findAll();

    @Cacheable(value = "customer", key = "#id")
    Optional<Customer> findById(Long id);

    @Caching(evict = @CacheEvict(value = "customers",allEntries = true),
            put = @CachePut(value="customer", key = "#result.id", condition = "#result != null"))
    Customer save(Customer customer);

    @Caching(evict = { @CacheEvict(value = "customers",allEntries = true),
                 @CacheEvict(value = "customer",key = "#customer.id")})
    void delete(Customer customer);

}
