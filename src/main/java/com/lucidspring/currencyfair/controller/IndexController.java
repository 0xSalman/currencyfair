package com.lucidspring.currencyfair.controller;

import com.lucidspring.currencyfair.config.AppConfig;
import com.lucidspring.currencyfair.service.ProcessorService;
import com.lucidspring.currencyfair.util.LogLevel;
import com.lucidspring.currencyfair.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Main controllers to display home page and load data
 */

@Controller
public class IndexController {

	@Autowired
	private ApplicationContext applicationContxt;
	@Autowired
	private AppConfig appConfig;

    private boolean initialLoad;

	@RequestMapping(value="/")
	public String getIndex() {
        LoggerUtil.logDebug(IndexController.class, "Requested home page");
		return "index";
	}

	@RequestMapping(value = "/loadData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String loadData() {

        LoggerUtil.logEnter(IndexController.class, LogLevel.INFO, "loadData");

        processData();
        this.initialLoad = true;

        LoggerUtil.logExit(IndexController.class, LogLevel.INFO, "loadData");

        return "Success";
    }

	/**
	 * Re-process data every 5 seconds.
	 * In other words, update data and notify front end of changes via web socket
	 * or whatever concrete implementation of processor is doing
     * Do not process data unless initial load data has been completed
	 */
	@Scheduled(fixedDelay=5000)
	public void sendNotifications() {

        LoggerUtil.logEnter(IndexController.class, LogLevel.DEBUG, "sendNotifications");

        if (initialLoad)
			processData();

        LoggerUtil.logExit(IndexController.class, LogLevel.DEBUG, "sendNotifications");
	}

	private void processData() {

        LoggerUtil.logEnter(IndexController.class, LogLevel.DEBUG, "processData");

		try {
			ProcessorService processorService = (ProcessorService)applicationContxt.getBean(appConfig.getTradeProcessor());
			processorService.process();
		} catch (Exception e) {
			LoggerUtil.logError(IndexController.class, "Error occurred", e);
		}

        LoggerUtil.logExit(IndexController.class, LogLevel.DEBUG, "processData");
	}
}
