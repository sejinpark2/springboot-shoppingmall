package com.example.demo.option;

import com.example.demo.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@NoArgsConstructor
public class OptionResponse {

    @NoArgsConstructor
    @Setter
    @Getter
    public static class FindByProductIdDTO{
        private Long id;

        private Long productId;

        private String optionName;

        private Long price;

        private Long quantity;

        public FindByProductIdDTO(Option option) {
            this.id = option.getId();
            this.productId = option.getProduct().getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
            this.quantity = option.getQuantity();
        }
    }

    @NoArgsConstructor
    @Setter
    @Getter
    public static class FindAllDTO{
        private Long id;

        private Long productId;

        private String optionName;

        private Long price;

        private Long quantity;

        public FindAllDTO(Option option) {
            this.id = option.getId();
            this.productId = option.getProduct().getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
            this.quantity = option.getQuantity();
        }
    }
}
