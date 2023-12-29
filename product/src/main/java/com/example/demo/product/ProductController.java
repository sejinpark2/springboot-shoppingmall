package com.example.demo.product;

import com.example.demo.core.security.CustomUserDetails;
import com.example.demo.core.utils.ApiUtils;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products/save")
    public ResponseEntity<?> save(@RequestBody @Valid ProductRequest.SaveDTO productDTO) {
            productService.save(productDTO);

            return ResponseEntity.ok().build();

    }


    // 전체 상품 확인
    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page) {
        List<ProductResponse.FindAllDTO> productResonses = productService.findAll(page);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(productResonses);
        return ResponseEntity.ok(apiResult);
    }

    // 개별 상품 확인
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        ProductResponse.FindByIdDTO product = productService.findById(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(product);
        return ResponseEntity.ok(apiResult);
    }

    @PostMapping("/products/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ProductRequest.SaveDTO productDTO) {
        productService.update(id, productDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/products/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);

        return ResponseEntity.ok().build();
    }


}