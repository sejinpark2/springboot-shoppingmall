package com.example.demo.option;

import com.example.demo.core.error.exception.Exception404;
import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Option save(Long productId, OptionResponse.FindByProductIdDTO optionDto) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            Option option = Option.builder()
                    .optionName(optionDto.getOptionName())
                    .price(optionDto.getPrice())
                    .quantity(optionDto.getQuantity())
                    .product(product)
                    .build();
            optionRepository.save(option);
            return option;
        } else {
            return null;
        }
    }

    // ** 옵션 개별상품 검색
    public List<OptionResponse.FindByProductIdDTO> findByProductId(Long id) {
        List<Option> optionList = optionRepository.findByProductId(id);
        List<OptionResponse.FindByProductIdDTO> optionResponses =
                optionList.stream().map(OptionResponse.FindByProductIdDTO::new)
                        .collect(Collectors.toList());
        return optionResponses;
    }

    // ** 옵션 전체상품 검색
    public List<OptionResponse.FindAllDTO> findAll() {
        List<Option> optionList = optionRepository.findAll();
        List<OptionResponse.FindAllDTO> findAllDTOS =
                optionList.stream().map(OptionResponse.FindAllDTO::new)
                        .collect(Collectors.toList());
        return findAllDTOS;
    }

    @Transactional
    public void update(Long id, OptionResponse.FindAllDTO optionDTO) {
        Option option = optionRepository.findById(id).orElseThrow(
                () -> new Exception404("해당 옵션을 찾을 수 없습니다. : " + optionDTO.getId()));

        option.updateFromDTO(optionDTO);

    }

    @Transactional
    public void delete(Long id) {
        optionRepository.deleteById(id);
    }
}
