package bds.resource;

import bds.MyWishSrvApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class MyResource {
    Logger logger = LoggerFactory.getLogger(MyResource.class);
    @RequestMapping("/resource")
    public Map<String,Object> home(HttpEntity<byte[]> requestEntity) {
        Map<String,Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World2");
        HttpHeaders headers = requestEntity.getHeaders();

        logger.info("hithere");
        logger.info(headers.toString());
        return model;
    }
}
