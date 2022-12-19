package by.clevertec.web.controller;

import by.clevertec.service.ProductService;
import by.clevertec.service.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clevertecTestTask/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService<ProductDto> productService;
    @GetMapping
    public ResponseEntity<Object> getAllProducts(@RequestParam(defaultValue = "0",required = false)
                                                  int pageNumber,
                                                  @RequestParam (defaultValue = "5", required = false)
                                                  int pageSize){
        Page<ProductDto> productDtoPage = productService.getAll(pageNumber,pageSize);
        return new ResponseEntity<>(productDtoPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id){
        ProductDto productDto = productService.getById(id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> insertProduct(@RequestBody ProductDto productDto){
        productService.insert(productDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id,
                                                @RequestBody  ProductDto productDto){
        productService.update(id,productDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
