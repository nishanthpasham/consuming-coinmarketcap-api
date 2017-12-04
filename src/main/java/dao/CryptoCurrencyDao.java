package dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import consume.CryptoCurrencyInfo;

@Repository("CryptoCurrencyDao")
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "net.nishanth.coinmarketcap")
@PropertySource(value = { "classpath:application.properties" })
public class CryptoCurrencyDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	@Autowired
    private Environment env;
	
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
        return dataSource;
    }
 
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        return jdbcTemplate;
    }

	public void insertCryptoCurrencyInfo(CryptoCurrencyInfo cryptoCurrencyInfo) {
		String sql = "INSERT INTO crypto.crypto_currency_info("
				+ "id, name, symbol, rank, price_usd, price_btc, 24h_volume_usd, market_cap_usd, available_supply, total_supply, percentage_change_one_hour, "
				+ "percentage_change_twenty_four_hour, percentage_change_seven_day, last_updated)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		String sql1 = "INSERT INTO crypto.crypto_currency_info(" +
	"id, name, symbol, rank, price_usd, price_btc, \"24h_volume_usd\", market_cap_usd, available_supply, "
			+ "total_supply, percentage_change_one_hour, percentage_change_twenty_four_hour, percentage_change_seven_day, last_updated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql1,
				new Object[] { cryptoCurrencyInfo.id, cryptoCurrencyInfo.name, cryptoCurrencyInfo.symbol,
						cryptoCurrencyInfo.rank, cryptoCurrencyInfo.priceUsd, cryptoCurrencyInfo.priceBtc,
						cryptoCurrencyInfo.hVolumeUsd, cryptoCurrencyInfo.marketCapUsd,
						cryptoCurrencyInfo.availableSupply, cryptoCurrencyInfo.totalSupply,
						cryptoCurrencyInfo.percentChangeOneHour, cryptoCurrencyInfo.percentChangeTwentyFourHour,
						cryptoCurrencyInfo.percentChangeSevenDay, cryptoCurrencyInfo.lastUpdated });
	}
	
	public void staticInsertCryptoCurrencyInfo() {
		String sql = "INSERT INTO crypto.\"crypto_currency_info\"(" +
	"id, name, symbol, rank, price_usd, price_btc, \"24h_volume_usd\", market_cap_usd, available_supply, total_supply, percentage_change_one_hour, percentage_change_twenty_four_hour, percentage_change_seven_day, last_updated)"+
	"VALUES ('bitcoin', 'BitCoin', 'BTC', 1, 100, 1, 100, 10000, 200, 1000, 1, 24, 7, 100)";
		getJdbcTemplate().update(sql);
	}

}
