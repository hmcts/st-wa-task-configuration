package uk.gov.hmcts.reform.wataskconfigurationtemplate.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigurationExpectationBuilder {

    private static final String DEFAULT_CALENDAR = "https://www.gov.uk/bank-holidays/england-and-wales.json";
    private static final String EXTRA_TEST_CALENDAR = "https://raw.githubusercontent.com/hmcts/"
        + "civil-wa-task-configuration/master/src/main/resources/privilege-calendar.json";

    private static List<String> EXPECTED_PROPERTIES = Arrays.asList(
        "dueDateOrigin","dueDateNonWorkingCalendar","majorPriority","minorPriority","workType", "roleCategory",
        "dueDateIntervalDays", "description"
    );
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private Map<String,Map<String,Object>> expectations = new HashMap<>();

    public static ConfigurationExpectationBuilder defaultExpectations() {
        ConfigurationExpectationBuilder builder = new ConfigurationExpectationBuilder();
        builder.expectedValue("dueDateOrigin", now(), true);
        builder.expectedValue("dueDateNonWorkingCalendar", DEFAULT_CALENDAR, true);
        builder.expectedValue("workType", "routine_work", true);
        builder.expectedValue("roleCategory", "ADMIN", true);
        builder.expectedValue("minorPriority", "500", true);
        builder.expectedValue("majorPriority", "5000", true);
        builder.expectedValue("dueDateIntervalDays", "5", true);
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
