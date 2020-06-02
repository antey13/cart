package com.fi.developer.cart.service;

import com.fi.developer.cart.domain.Cart;
import com.fi.developer.cart.domain.Product;
import com.fi.developer.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;


    public Product saveProduct(String userName, Product product){
        Cart cart = new Cart();
        cart.setAmount(product.getAmount());
        cart.setProductTitle(product.getTitle());
        cart.setUserName(userName);
        cartRepository.save(cart);
        return product;
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Page<Cart> findAll(Pageable pageable) {
        return cartRepository.findAll(pageable);
    }

    public Optional<Cart> findById(String id) {
        return cartRepository.findById(id);
    }

    public void deleteById(String id) {
        cartRepository.deleteById(id);
    }

    public Page<Cart> findAllForUser(String userName, Pageable pageable) {
        return cartRepository.findAllByUserName(userName,pageable);
    }
}
