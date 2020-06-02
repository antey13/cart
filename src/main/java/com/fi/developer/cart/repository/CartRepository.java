package com.fi.developer.cart.repository;

import com.fi.developer.cart.domain.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Cart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    Optional<Cart> findByUserName(String userName);

    Page<Cart> findAllByUserName(String userName, Pageable pageable);
}
