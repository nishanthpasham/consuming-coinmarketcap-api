package consume;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CryptoCurrencyInfoSerializer extends StdSerializer<CryptoCurrencyInfo> {

	protected CryptoCurrencyInfoSerializer(Class<?> t, boolean dummy) {
		super(t, dummy);
	}

	@Override
	public void serialize(CryptoCurrencyInfo cryptoCurrency, JsonGenerator jgen, SerializerProvider arg2)
			throws IOException, JsonGenerationException {
		jgen.writeStartObject();
        jgen.writeStringField("id", cryptoCurrency.id);
        jgen.writeStringField("name", cryptoCurrency.name);
        jgen.writeStringField("symbol", cryptoCurrency.symbol);
        jgen.writeNumberField("rank", cryptoCurrency.rank);
        jgen.writeNumberField("price_usd", cryptoCurrency.priceUsd);
        jgen.writeNumberField("24h_volume_usd", cryptoCurrency.hVolumeUsd);
        jgen.writeNumberField("market_cap_usd", cryptoCurrency.marketCapUsd);
        jgen.writeNumberField("available_supply", cryptoCurrency.availableSupply);
        jgen.writeNumberField("total_supply", cryptoCurrency.totalSupply);
        jgen.writeNumberField("percent_change_1h", cryptoCurrency.percentChangeOneHour);
        jgen.writeNumberField("percent_change_24h", cryptoCurrency.percentChangeTwentyFourHour);
        jgen.writeNumberField("percent_change_7d", cryptoCurrency.percentChangeSevenDay);
        jgen.writeNumberField("last_updated", cryptoCurrency.lastUpdated);
        jgen.writeEndObject();
	}

}
