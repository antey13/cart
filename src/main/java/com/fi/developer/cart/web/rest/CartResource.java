package com.fi.developer.cart.web.rest;

import com.fi.developer.cart.domain.Cart;
import com.fi.developer.cart.domain.Product;
import com.fi.developer.cart.security.SecurityUtils;
import com.fi.developer.cart.service.CartService;
import com.fi.developer.cart.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fi.developer.cart.domain.Cart}.
 */
@RestController
@RequestMapping("/api")
public class CartResource {

    private final Logger log = LoggerFactory.getLogger(CartResource.class);

    private static final String ENTITY_NAME = "cartCart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private CartService cartService;


    @PostMapping("/addProduct")
    public ResponseEntity<Product> addToCart(@RequestBody Product product) throws URISyntaxException {
        String userName = SecurityUtils.getCurrentUserLogin().get();
        log.debug("REST request to save Product : {}", product);

        Product saved = cartService.saveProduct(userName, product);
        return ResponseEntity.created(new URI("/api/products/" + saved.getId())).body(saved);
    }

    /**
     * {@code POST  /carts} : Create a new cart.
     *
     * @param cart the cart to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cart, or with status {@code 400 (Bad Request)} if the cart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carts")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) throws URISyntaxException {
        log.debug("REST request to add Product to Cart : {}", cart);
        Cart result = cartService.save(cart);
        return ResponseEntity.created(new URI("/api/carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /carts} : Updates an existing cart.
     *
     * @param cart the cart to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cart,
     * or with status {@code 400 (Bad Request)} if the cart is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cart couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carts")
    public ResponseEntity<Cart> updateCart(@RequestBody Cart cart) throws URISyntaxException {
        log.debug("REST request to update Cart : {}", cart);
        if (cart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cart result = cartService.save(cart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cart.getId()))
            .body(result);
    }


    @GetMapping("/carts")
    public ResponseEntity<List<Cart>> getAllCarts(Pageable pageable) {
        log.debug("REST request to get a page of Carts");
        Page<Cart> page = cartService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/carts")
    public ResponseEntity<List<Cart>> getAllCartsForUser(Pageable pageable) {
        log.debug("REST request to get a page of Carts");
        Page<Cart> page = cartService.findAllForUser(SecurityUtils.getCurrentUserLogin().get(),pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carts/:id} : get the "id" cart.
     *
     * @param id the id of the cart to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cart, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carts/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable String id) {
        log.debug("REST request to get Cart : {}", id);
        Optional<Cart> cart = cartService.findById(id);
        return ResponseUtil.wrapOrNotFound(cart);
    }

    /**
     * {@code DELETE  /carts/:id} : delete the "id" cart.
     *
     * @param id the id of the cart to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable String id) {
        log.debug("REST request to delete Cart : {}", id);

        cartService.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
