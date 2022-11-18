package com.ct.ctexchange.currency;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BitcoinPricingAlgo {

	@Getter
	private double btcPrice = 100.0;
	
	private double factor = 10.0;

	@Scheduled(fixedDelay = 5000)
	private void updatePrice() {
		btcPrice += factor;

		if (btcPrice >= 460.0) {
			factor = -10.0;
		}

		if (btcPrice <= 100.0) {
			factor = 10.0;
		}
		
		log.info("price {}",btcPrice);
	}

}
