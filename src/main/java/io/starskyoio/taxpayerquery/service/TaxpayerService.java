package io.starskyoio.taxpayerquery.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import io.starskyoio.taxpayerquery.config.AppConfig;
import io.starskyoio.taxpayerquery.dto.QueryDTO;
import io.starskyoio.taxpayerquery.dto.QueryResultDTO;
import io.starskyoio.taxpayerquery.dto.TaxpayerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.*;

@Service
public class TaxpayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaxpayerService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppConfig appConfig;


    private final ExecutorService executor = new ThreadPoolExecutor(
            32,
            32,
            0,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10000),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public QueryResultDTO query(QueryDTO queryDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        QueryResultDTO result = restTemplate.postForEntity(appConfig.getQueryUrl(), new HttpEntity<>(queryDTO, headers), QueryResultDTO.class).getBody();
        return result;
    }

    public void download() {
        QueryDTO queryDTO = new QueryDTO();
        queryDTO.setPageIndex(1);
        queryDTO.setPageSize(appConfig.getPageSize());
        queryDTO.setPjnd("2020");
        QueryResultDTO result = query(queryDTO);
        if (!result.getSuccess()) {
            LOGGER.warn("查询失败");
            return;
        }
        CopyOnWriteArrayList<TaxpayerInfo> list = new CopyOnWriteArrayList<>();
        list.addAll(result.getValue().getResult());
        int pages = (int) Math.ceil(result.getValue().getTotal() / (double) appConfig.getPageSize());
        CountDownLatch countDownLatch = new CountDownLatch(pages - 1);
        for (int i = 2; i <= pages; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    QueryDTO query = new QueryDTO();
                    query.setPageIndex(index);
                    query.setPageSize(appConfig.getPageSize());
                    query.setPjnd("2020");
                    QueryResultDTO ret = query(query);
                    LOGGER.info("查询pageIndex:{}, success:{}", index, ret.getSuccess());
                    list.addAll(ret.getValue().getResult());
                } finally {
                    countDownLatch.countDown();
                }

            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exportExcel(list);
    }

    public void exportExcel(List<TaxpayerInfo> data) {
        ExcelWriter excelWriter = null;
        String fileName = "D:\\test.xlsx";
        try {
            excelWriter = EasyExcel.write(fileName, TaxpayerInfo.class).build();
            int sheetSize = (int) Math.ceil(data.size() / 10000d);
            int startIndex;
            int endIndex;
            for (int i = 0; i < sheetSize; i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "标签" + (i + 1)).build();
                startIndex = i * 10000;
                if (i == sheetSize - 1) {
                    endIndex = data.size() - 1;
                } else {
                    endIndex = (i + 1) * 10000 - 1;
                }
                excelWriter.write(data.subList(startIndex, endIndex), writeSheet);
            }
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }
}
