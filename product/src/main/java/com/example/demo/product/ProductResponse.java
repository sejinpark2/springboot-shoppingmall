package com.example.demo.product;


import com.example.demo.option.Option;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {

    @Setter
    @Getter
    public static class FindAllDTO{

        private Long id;

        private String productName;

        private String description;

        private String image;

        private Long price;

        public FindAllDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
        }
    }

    @Setter
    @Getter
    public static class FindByIdDTO{

        private Long id;

        private String productName;

        private String description;

        private String image;

        private Long price;

        private List<OptionDTO> optionList;

        public FindByIdDTO(Product product, List<Option> optionList) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.description = product.getDescription();
            this.image = product.getImage();
            this.price = product.getPrice();
            this.optionList = optionList.stream().map(OptionDTO::new)
                    .collect(Collectors.toList());
            //optionList의 각 요소를 OptionDTO로 변한하고 optionList에 할당
        }
    }

    @Setter
    @Getter
    public static class OptionDTO {
        private Long id;
        private String optionName;
        private Long price;
        private Long quantity;


        public OptionDTO(Option option) {
            this.id = option.getId();
            this.optionName = option.getOptionName();
            this.price = option.getPrice();
            this.quantity = option.getQuantity();
        }
    }
}

