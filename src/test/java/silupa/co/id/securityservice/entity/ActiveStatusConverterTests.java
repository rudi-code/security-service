package silupa.co.id.securityservice.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ActiveStatusConverterTests {

	private final ActiveStatusConverter converter = new ActiveStatusConverter();

	@Test
	void convertsBooleanStatusToLegacyDatabaseValues() {
		assertThat(converter.convertToDatabaseColumn(true)).isEqualTo("1");
		assertThat(converter.convertToDatabaseColumn(false)).isEqualTo("0");
	}

	@Test
	void readsLegacyActiveAndInactiveValues() {
		assertThat(converter.convertToEntityAttribute("1")).isTrue();
		assertThat(converter.convertToEntityAttribute("Y")).isTrue();
		assertThat(converter.convertToEntityAttribute("0")).isFalse();
		assertThat(converter.convertToEntityAttribute(null)).isFalse();
	}
}
