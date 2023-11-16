package uk.gov.hmcts.reform.wataskconfigurationtemplate.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigurationExpectationBuilder {
    private static List<String> EXPECTED_PROPERTIES = Arrays.asList(
        "caseName","majorPriority","minorPriority","workType", "roleCategory","dueDateIntervalDays","description"
    );
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private Map<String,Map<String,Object>> expectations = new HashMap<>();

    public static ConfigurationExpectationBuilder defaultExpectations() {
        ConfigurationExpectationBuilder builder = new ConfigurationExpectationBuilder();
        builder.expectedValue("caseName", "Joe Blogs", false);
        builder.expectedValue("workType", "routine_work", true);
        builder.expectedValue("roleCategory", "ADMIN", true);
        builder.expectedValue("minorPriority", "500", true);
        builder.expectedValue("majorPriority", "5000", true);
        builder.expectedValue("region", "London", false);
        builder.expectedValue("location", "East", false);
        builder.expectedValue("description", "[Orders: Send order]", true);
        return builder;
    }

    public List<Map<String,Object>> build() {
        return EXPECTED_PROPERTIES.stream()
            .filter(p -> expectations.containsKey(p))
            .map(p -> expectations.get(p))
            .collect(Collectors.toList());
    }

    public ConfigurationExpectationBuilder expectedValue(String name, Object value, boolean canReconfigure) {
        expectations.put(name, Map.of(
            "name", name,
            "value", value,
            "canReconfigure", canReconfigure
        ));
        return this;
    }

    public static String now() {
        return LocalDateTime.now().format(dateTimeFormat);
    }
}
