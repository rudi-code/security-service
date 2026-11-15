package silupa.co.id.securityservice.entity;

import java.util.Locale;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ActiveStatusConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean active) {
		return Boolean.TRUE.equals(active) ? "1" : "0";
	}

	@Override
	public Boolean convertToEntityAttribute(String value) {
		if (value == null) {
			return false;
		}
		return switch (value.strip().toUpperCase(Locale.ROOT)) {
			case "1", "Y", "YES", "TRUE" -> true;
			default -> false;
		};
	}
}
