package com.shopping.repository;

import com.shopping.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> { // CartProductRepository01
    // CartProductRepository01
    CartProduct findByCartIdAndProductId(Long cartId, Long productId) ;
}
