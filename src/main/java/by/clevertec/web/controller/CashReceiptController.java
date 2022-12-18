package by.clevertec.web.controller;

import by.clevertec.service.CashReceiptService;
import by.clevertec.service.dto.CashReceiptDto;
import by.clevertec.web.payload.request.FileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("clevertecTestTask/v1/cashReceipt")
@RequiredArgsConstructor
public class CashReceiptController {

    private final CashReceiptService<CashReceiptDto> cashReceiptService;
    @GetMapping
    public ResponseEntity<Object> getCashReceiptByTransferredParameters(@RequestParam MultiValueMap<String,
            String> allRequestParams){
         CashReceiptDto cashReceipt = cashReceiptService.getCashReceiptByTransferredParameters(allRequestParams);
        return new ResponseEntity<>(cashReceipt, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Object> getCashReceiptByFileData(@RequestBody FileInfo info)
            throws FileNotFoundException {
        System.out.println(info);
        CashReceiptDto cashReceipt = cashReceiptService.getCashReceiptByFileData(info.getPath());
        return new ResponseEntity<>(cashReceipt, HttpStatus.CREATED);
    }
}
