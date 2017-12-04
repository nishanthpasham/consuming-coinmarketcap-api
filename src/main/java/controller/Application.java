package controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import consume.CryptoCurrencyInfo;

@SpringBootApplication
@PropertySource(value = { "classpath:application.properties" })
public class Application implements CommandLineRunner {

	public static void main(String args[]) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... arg0) throws Exception {
		RestTemplate restTemplate = new RestTemplate();

		CryptoCurrencyInfo[] cryptoCurrencyInfoArray = restTemplate
				.getForObject("https://api.coinmarketcap.com/v1/ticker/?limit=1", CryptoCurrencyInfo[].class);

		System.out.println(Arrays.toString(cryptoCurrencyInfoArray));
		for(CryptoCurrencyInfo cryptoCurrencyInfo : cryptoCurrencyInfoArray) {

			String sql = "INSERT INTO crypto.crypto_currency_info("
					+ "id, name, symbol, rank, price_usd, price_btc, 24h_volume_usd, market_cap_usd, available_supply, total_supply, percentage_change_one_hour, "
					+ "percentage_change_twenty_four_hour, percentage_change_seven_day, last_updated)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			jdbcTemplate.update(sql,
					new Object[] { cryptoCurrencyInfo.id, cryptoCurrencyInfo.name, cryptoCurrencyInfo.symbol,
							cryptoCurrencyInfo.rank, cryptoCurrencyInfo.priceUsd, cryptoCurrencyInfo.priceBtc,
							cryptoCurrencyInfo.hVolumeUsd, cryptoCurrencyInfo.marketCapUsd,
							cryptoCurrencyInfo.availableSupply, cryptoCurrencyInfo.totalSupply,
							cryptoCurrencyInfo.percentChangeOneHour, cryptoCurrencyInfo.percentChangeTwentyFourHour,
							cryptoCurrencyInfo.percentChangeSevenDay, cryptoCurrencyInfo.lastUpdated });
		
		}

	}

}
