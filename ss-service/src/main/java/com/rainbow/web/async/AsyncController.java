package com.rainbow.web.async;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: AsyncController
 * @date 2018/9/17 11:38
 */
@RestController
public class AsyncController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/order")
    public Callable<String> order() throws Exception {
        logger.info("主线程开始!");
        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("副线程开始!");
                Thread.sleep(1000);
                logger.info("副线程结束!");
                return "success";
            }
        };
        logger.info("主线程返回!");
        return result;
    }

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;


    @RequestMapping("/order1")
    public DeferredResult<String> order1() throws Exception {

        logger.info("主线程开始!");

        String orderNumber = RandomStringUtils.randomNumeric(8);
        try {
            mockQueue.setPlaceOrder(orderNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber, result);

        logger.info("主线程返回!");
        return result;
    }

}
