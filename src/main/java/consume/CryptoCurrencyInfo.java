package consume;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonSerialize(using = CryptoCurrencyInfoSerializer.class)
public class CryptoCurrencyInfo {
	
	public String id;
	public String name;
	public String symbol;
	public int rank;
	@JsonProperty("price_usd")
	public double priceUsd;
	@JsonProperty("price_btc")
	public double priceBtc;
	@JsonProperty("24h_volume_usd")
	public double hVolumeUsd;
	@JsonProperty("market_cap_usd")
	public double marketCapUsd;
	@JsonProperty("available_supply")
	public double availableSupply;
	@JsonProperty("total_supply")
	public double totalSupply;
	@JsonProperty("max_supply")
	public double maxSupply;
	@JsonProperty("percent_change_1h")
	public double percentChangeOneHour;
	@JsonProperty("percent_change_24h")
	public double percentChangeTwentyFourHour;
	@JsonProperty("percent_change_7d")
	public double percentChangeSevenDay;
	@JsonProperty("last_updated")
	public double lastUpdated;
	
	@Override
	public String toString() {
		return "CryptoCurrencyInfo [id=" + id + ", name=" + name + ", symbol=" + symbol + ", rank=" + rank
				+ ", priceUsd=" + priceUsd + ", priceBtc=" + priceBtc + ", hVolumeUsd=" + hVolumeUsd + ", marketCapUsd="
				+ marketCapUsd + ", availableSupply=" + availableSupply + ", totalSupply=" + totalSupply
				+ ", maxSupply=" + maxSupply + ", percentChangeOneHour=" + percentChangeOneHour
				+ ", percentChangeTwentyFourHour=" + percentChangeTwentyFourHour + ", percentChangeSevenDay="
				+ percentChangeSevenDay + ", lastUpdated=" + lastUpdated + "]";
	}
	
	
	
	
}
