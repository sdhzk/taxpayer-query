package io.starskyoio.taxpayerquery.controller;

import io.starskyoio.taxpayerquery.dto.QueryDTO;
import io.starskyoio.taxpayerquery.dto.QueryResultDTO;
import io.starskyoio.taxpayerquery.service.TaxpayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taxpayer")
public class TaxpayerQueryController {

    @Autowired
    private TaxpayerService taxpayerService;


    @PostMapping("/query")
    public QueryResultDTO query(@RequestBody QueryDTO queryDTO) {
        return taxpayerService.query(queryDTO);
    }

    @GetMapping("/download")
    public String download() {
        taxpayerService.download();
        return "success";
    }
}
