package com.shopping.service;

import com.shopping.dto.CartProductDto;
import com.shopping.entity.Cart;
import com.shopping.entity.CartProduct;
import com.shopping.entity.Member;
import com.shopping.entity.Product;
import com.shopping.repository.CartProductRepository;
import com.shopping.repository.CartRepository;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor //  외부에서 객체를 구해서 final 변수에 주입해줌
public class CartService {
    private final ProductRepository productRepository ;
    private final MemberRepository memberRepository ;
    private final CartRepository cartRepository ;
    private final CartProductRepository cartProductRepository ;

    // CartService01
    // 상품 id, 수량, 이메일 정보를 이용하여 카트 상품(CartProduct) Entity를 생성해 줍니다.
    // 신규 '카트 상품'(CartProduct) 이면, 내 카트에 추가하고, 아니면 기존 수량에 값을 누적시켜 줍니다.
    public Long addCart(CartProductDto cartProductDto, String email){
        Long productId = cartProductDto.getProductId() ;
        Product product = this.productRepository.findById(productId).orElseThrow(EntityNotFoundException::new) ;

        Member member = this.memberRepository.findByEmail(email) ;

        Long memberId = member.getId();
        Cart cart = this.cartRepository.findByMemberId(memberId) ;

        if (cart == null){
            cart = Cart.createCart(member) ;
            this.cartRepository.save(cart) ;
        }

        Long cartId = cart.getId() ;
        CartProduct savedCartProduct = this.cartProductRepository.findByCartIdAndProductId(cartId, productId);

        int count = cartProductDto.getCount() ;
        if(savedCartProduct != null){ // 해당 장바구니에 이미 상품이 들어 있는 경우
            savedCartProduct.addCount(count);
            this.cartProductRepository.save(savedCartProduct) ; //누적 안되는 문제 해결
            return savedCartProduct.getId() ;

        }else{ // 신규 상품을 장바구니에 담는 경우
            CartProduct cartProduct = CartProduct.createCartProduct(cart, product, count) ;
            System.out.println("cartProduct.getId()= "+cartProduct.getId());
            this.cartProductRepository.save(cartProduct);
            return cartProduct.getId() ;
        }
    }
}
