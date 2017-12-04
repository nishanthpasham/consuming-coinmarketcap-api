package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.springframework.web.client.RestTemplate;

import consume.CryptoCurrencyInfo;

public class SaveCrytoDatatoDB implements Runnable {
	
	private final Logger logger;
	private final RestTemplate restTemplate;
	private final String query;
	private final String restEndPoint;
	private final Connection connection;
	private final long sleepInterval = 150000; //hit REST service every 2.5 mins
	
	public SaveCrytoDatatoDB(final Logger logger, final RestTemplate restTemplate, String query, final Connection connection, String restEndPoint) {
		this.logger = logger;
		this.restTemplate = restTemplate;
		this.query = query;
		this.connection = connection;
		this.restEndPoint = restEndPoint;
	}

	@Override
	public void run() {
		
		while (true) {
			CryptoCurrencyInfo[] cryptoCurrencyInfoArray = restTemplate
					.getForObject(restEndPoint, CryptoCurrencyInfo[].class);

//			logger.info(Arrays.toString(cryptoCurrencyInfoArray));

			PreparedStatement stmt = null;
			
			try {
				for (CryptoCurrencyInfo cryptoCurrencyInfo : cryptoCurrencyInfoArray) {
					stmt = connection.prepareStatement(query);
					int i = 0;
					stmt.setString(++i, cryptoCurrencyInfo.id);
					stmt.setString(++i, cryptoCurrencyInfo.name);
					stmt.setString(++i, cryptoCurrencyInfo.symbol);
					stmt.setInt(++i, cryptoCurrencyInfo.rank);
					stmt.setDouble(++i, cryptoCurrencyInfo.priceUsd);
					stmt.setDouble(++i, cryptoCurrencyInfo.priceBtc);
					stmt.setDouble(++i, cryptoCurrencyInfo.hVolumeUsd);
					stmt.setDouble(++i, cryptoCurrencyInfo.marketCapUsd);
					stmt.setDouble(++i, cryptoCurrencyInfo.availableSupply);
					stmt.setDouble(++i, cryptoCurrencyInfo.totalSupply);
					stmt.setDouble(++i, cryptoCurrencyInfo.maxSupply);
					stmt.setDouble(++i, cryptoCurrencyInfo.percentChangeOneHour);
					stmt.setDouble(++i, cryptoCurrencyInfo.percentChangeTwentyFourHour);
					stmt.setDouble(++i, cryptoCurrencyInfo.percentChangeSevenDay);
					stmt.setDouble(++i, cryptoCurrencyInfo.lastUpdated);
					
					stmt.setString(++i, cryptoCurrencyInfo.symbol);
					stmt.setDouble(++i, cryptoCurrencyInfo.lastUpdated);

					stmt.execute();
				}
			} catch (SQLException e) {
				logger.info("Connection Failed! Check output console");
				e.printStackTrace();
				return;
			}
			try {
				Thread.sleep(sleepInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
