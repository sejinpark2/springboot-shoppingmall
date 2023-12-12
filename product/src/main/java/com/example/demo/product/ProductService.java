package com.example.demo.product;

import com.example.demo.core.error.exception.Exception404;
import com.example.demo.option.Option;
import com.example.demo.option.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public List<ProductResponse.FindAllDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 3);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponse.FindAllDTO> productResonses = productPage.getContent().stream().map(ProductResponse.FindAllDTO::new)
                .collect(Collectors.toList());

        return productResonses;
    }

    public ProductResponse.FindByIdDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new Exception404("해당 상품을 찾을 수 없습니다. : " + id));

        // product.getId() 로 option 상품 검색
        List<Option> options = optionRepository.findByProductId(product.getId());

        // 검색이 완료된 제품 반환
        return new ProductResponse.FindByIdDTO(product, options);

    }

    @Transactional
    public void save(ProductRequest.SaveDTO productDTO) {
        productRepository.save(productDTO.toEntity());
    }


    @Transactional
    public void update(Long id, ProductRequest.SaveDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.updateFromDTO(productDTO);
        }
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
