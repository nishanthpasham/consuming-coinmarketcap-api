package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.springframework.web.client.RestTemplate;

public class CryptoApp {

	public static void main(String args[]) {
		
		RestTemplate restTemplate = new RestTemplate();
		String numOfCryptoCurrencies = "50";
		
		Logger logger = Logger.getLogger("MyLog");  
	    FileHandler fh;  
	    try {  
	        fh = new FileHandler("/Users/nishanth/work/eclipse-workspaces/crackingTheCodingInterview/consuming-coinmarketcap-api/src/main/java/controller/crypto.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
	    } catch (Exception e) {
	    	System.out.println("Exception with logger: "+e);
		}
	    
	    String query = "INSERT INTO crypto.\"crypto_currency_info\"("
				+ "id, name, symbol, rank, price_usd, price_btc, \"24h_volume_usd\", market_cap_usd, available_supply, total_supply, max_supply, percentage_change_one_hour, "
				+ "percentage_change_twenty_four_hour, percentage_change_seven_day, last_updated)"
				+ " Select ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, to_timestamp(?) "
				+ " WHERE NOT EXISTS (SELECT 1 FROM crypto.\"crypto_currency_info\" WHERE symbol=? AND last_updated=to_timestamp(?))";
	    
	    String restEndPoint = "https://api.coinmarketcap.com/v1/ticker/?limit="+numOfCryptoCurrencies;
	    
	    logger.info("-------- PostgreSQL " + "JDBC Connection Testing ------------");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			logger.info("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			e.printStackTrace();
			return;
		}
		logger.info("PostgreSQL JDBC Driver Registered!");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
					"admin");
		} catch (SQLException e) {
			logger.info("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		
		Thread th = new Thread(new SaveCrytoDatatoDB(logger, restTemplate, query, connection, restEndPoint));
		th.start();
		try {
			th.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}