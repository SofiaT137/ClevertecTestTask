package by.clevertec.web.controller;

import by.clevertec.service.ProductService;
import by.clevertec.service.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class "ProductController" presents REST controller for the "Product" entity
 */
@RestController
@RequestMapping("clevertecTestTask/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService<ProductDto> productService;

    /**
     * Method "getAllProducts" returns all the "ProductDto" entities
     * @param pageSize Number of "ProductDto" entities per page (default value is 5)
     * @param pageNumber Number of the page with "ProductDto" entities (default value is 0)
     * @return Response entity with page of ProductDto entity and HttpStatus "OK"
     */
    @GetMapping
    public ResponseEntity<Object> getAllProducts(@RequestParam(defaultValue = "0",required = false)
                                                  int pageNumber,
                                                  @RequestParam (defaultValue = "5", required = false)
                                                  int pageSize){
        Page<ProductDto> productDtoPage = productService.getAll(pageNumber,pageSize);
        return new ResponseEntity<>(productDtoPage, HttpStatus.OK);
    }

    /**
     * Method "getProductById" returns ResponseEntity with "ProductDto" entity and HttpStatus "OK"
     * @param id "Product" id(Long value)
     * @return "ProductDto" entity and HttpStatus "OK"
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id){
        ProductDto productDto = productService.getById(id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    /**
     * Method "insertProduct" inserts the "ProductDto" entity
     * @param productDto "ProductDto" entity
     * @return Response entity with HttpStatus "CREATED"
     */
    @PostMapping
    public ResponseEntity<Object> insertProduct(@RequestBody ProductDto productDto){
        productService.insert(productDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Method "updateProduct" updates the "ProductDto" entity
     * @param id "Product" id(Long value)
     * @param productDto "ProductDto" entity
     * @return Response entity with HttpStatus "OK"
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id,
                                                @RequestBody  ProductDto productDto){
        productService.update(id,productDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method "deleteProduct" deletes the "ProductDto" entity
     * @param id "Product" id(Long value)
     * @return Response entity with HttpStatus "OK"
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
