package com.example.demo.cart;

import com.example.demo.option.Option;
import com.example.demo.product.Product;
import com.example.demo.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.stream.Collectors;

public class CartResponse {

    @Setter
    @Getter
    public static class UpdateDTO{
        List<CartDTO> dtoList;

        private Long totalPrice;

        public UpdateDTO(List<Cart> dtoList) {
            this.dtoList = dtoList.stream().map(CartDTO::new).collect(Collectors.toList());
            this.totalPrice = totalPrice;
        }

        @Setter
        @Getter
        public class CartDTO{
            private Long cartId;

            private Long optionId;

            public String optionName;

            private Long quantity;

            private Long price;

            public CartDTO(Cart cart) {
                this.cartId = cart.getId();
                this.optionId = cart.getOption().getId();
                this.optionName = cart.getOption().getOptionName();
                this.quantity = cart.getQuantity();
                this.price = cart.getPrice();
            }
        }

    }



    @Setter
    @Getter
    public static class FindAllDTO{
        List<ProductDTO> products;

        private Long totalPrice;

        public FindAllDTO(List<Cart> cartList) {
            this.products = cartList.stream()
                    .map(cart -> cart.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(cartList, product)).collect(Collectors.toList());

            this.totalPrice = cartList.stream()
                    .mapToLong(cart -> cart.getOption().getPrice() * cart.getQuantity())
                    .sum();
        }

        @Setter
        @Getter
        public class ProductDTO{
            private Long id;

            private String productName;

            List<CartDTO> cartDTOS;

            public ProductDTO(List<Cart> cartList, Product product) {
                this.id = product.getId();
                this.productName = product.getProductName();
                this.cartDTOS = cartList.stream()
                        .filter(cart -> cart.getOption().getProduct().getId() == product.getId())
                        .map(CartDTO::new).collect(Collectors.toList());
            }

            @Setter
            @Getter
            public class CartDTO {
                private Long id;

                private OptionDTO optionDTO;

                private Long quantity;

                private Long price;

                public CartDTO(Cart cart) {
                    this.id = cart.getId();
                    this.quantity = cart.getQuantity();
                    this.optionDTO = new OptionDTO(cart.getOption());
                    this.price = cart.getPrice();
                }

                @Setter
                @Getter
                public class OptionDTO {

                    private Long id;
                    private String optionName;
                    private Long price;

                    public OptionDTO(Option option) {
                        this.id = option.getId();
                        this.optionName = option.getOptionName();
                        this.price = option.getPrice();
                    }
                }
            }
        }
    }
}
