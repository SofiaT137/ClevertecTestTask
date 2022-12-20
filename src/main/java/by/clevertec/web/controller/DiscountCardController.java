package by.clevertec.web.controller;

import by.clevertec.service.DiscountCardService;
import by.clevertec.service.dto.DiscountCardDto;
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
 * Class "DiscountCardController" presents REST controller for the "DiscountCard" entity
 */
@RestController
@RequestMapping("clevertecTestTask/v1/discountCard")
@RequiredArgsConstructor
public class DiscountCardController {

    private final DiscountCardService<DiscountCardDto> discountCardService;

    /**
     * Method "getAllDiscountCards" returns all the "DiscountCardDto" entities
     * @param pageSize Number of "DiscountCardDto" entities per page (default value is 5)
     * @param pageNumber Number of the page with "DiscountCardDto" entities (default value is 0)
     * @return Response entity with page of DiscountCardDto entity and HttpStatus "OK"
     */
    @GetMapping
    public ResponseEntity<Object> getAllDiscountCards(@RequestParam(defaultValue = "0", required = false)
                                                 int pageNumber,
                                                 @RequestParam(defaultValue = "5", required = false)
                                                 int pageSize) {
        Page<DiscountCardDto> discountCardDtoPage = discountCardService.getAll(pageNumber, pageSize);
        return new ResponseEntity<>(discountCardDtoPage, HttpStatus.OK);
    }
    /**
     * Method "getDiscountCardById" returns ResponseEntity with "DiscountCardDto" entity and HttpStatus "OK"
     * @param id "DiscountCard" id(Long value)
     * @return "DiscountCardDto" entity and HttpStatus "OK"
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDiscountCardById(@PathVariable Long id) {
        DiscountCardDto discountCardDto = discountCardService.getById(id);
        return new ResponseEntity<>(discountCardDto, HttpStatus.OK);
    }

    /**
     * Method "insertDiscountCard" inserts the "DiscountCardDto" entity
     * @param discountCardDto "DiscountCardDto" entity
     * @return Response entity with HttpStatus "CREATED"
     */
    @PostMapping
    public ResponseEntity<Object> insertDiscountCard(@RequestBody DiscountCardDto discountCardDto) {
        discountCardService.insert(discountCardDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Method "updateDiscountCard" updates the "DiscountCardDto" entity
     * @param id "DiscountCard" id(Long value)
     * @param discountCardDto "DiscountCardDto" entity
     * @return Response entity with HttpStatus "OK"
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDiscountCard(@PathVariable Long id,
                                                @RequestBody DiscountCardDto discountCardDto) {
        discountCardService.update(id, discountCardDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method "deleteDiscountCard" deletes the "DiscountCardDto" entity
     * @param id "DiscountCard" id(Long value)
     * @return Response entity with HttpStatus "OK"
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDiscountCard(@PathVariable Long id) {
        discountCardService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
