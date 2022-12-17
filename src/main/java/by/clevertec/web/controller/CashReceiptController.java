package by.clevertec.web.controller;

import by.clevertec.service.CashReceiptService;
import by.clevertec.service.dto.CashReceiptDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clevertecTestTask/v1/cashReceipt")
@RequiredArgsConstructor
public class CashReceiptController {

    private final CashReceiptService<CashReceiptDto> cashReceiptService;
    @GetMapping
    public ResponseEntity<Object> giftCertificatesByParameter(@RequestParam MultiValueMap<String,
            String> allRequestParams){
         CashReceiptDto cashReceipt = cashReceiptService.getCashReceipt(allRequestParams);
        return new ResponseEntity<>(cashReceipt, HttpStatus.CREATED);
    }
}
