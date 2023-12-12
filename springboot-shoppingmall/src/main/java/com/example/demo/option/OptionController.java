package com.example.demo.option;

import com.example.demo.core.utils.ApiUtils;
import com.example.demo.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OptionController {
    private final OptionService optionService;

    /**
     * @param id
     * ProductId
     * @return
     * OptionResponse.FindByProductIdDTO 리스트로 반환.
     * */
    @GetMapping("/products/{id}/options")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        List<OptionResponse.FindByProductIdDTO> optionResponses = optionService.findByProductId(id);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionResponses);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/options")
    public ResponseEntity<?> findAll() {
        List<OptionResponse.FindAllDTO> optionResponses = optionService.findAll();

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionResponses);
        return ResponseEntity.ok(apiResult);
    }

    @PostMapping("/products/{id}/options/save")
    public ResponseEntity<?> save(@PathVariable Long id, @RequestBody @Valid OptionResponse.FindByProductIdDTO optionDTO){
        optionService.save(id, optionDTO);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    @PostMapping("/options/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid OptionResponse.FindAllDTO optionDTO) {
        optionService.update(id, optionDTO);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }



    @PostMapping("/options/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        optionService.delete(id);

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }
}
