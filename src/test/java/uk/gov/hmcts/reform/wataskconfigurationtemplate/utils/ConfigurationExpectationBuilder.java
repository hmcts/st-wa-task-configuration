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
        "caseName","region","location", "locationName","caseManagementCategory","priorityDate","dueDateOrigin",
        "dueDateTime","dueDateNonWorkingCalendar","dueDateNonWorkingDaysOfWeek",
        "dueDateSkipNonWorkingDays","dueDateMustBeWorkingDay","calculatedDates","majorPriority","minorPriority",
        "workType", "roleCategory","dueDateIntervalDays","description"
    );
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private Map<String,Map<String,Object>> expectations = new HashMap<>();

    public static ConfigurationExpectationBuilder defaultExpectations() {
        ConfigurationExpectationBuilder builder = new ConfigurationExpectationBuilder();
        builder.expectedValue("caseName", "Joe Blogs", false);
        builder.expectedValue("region", "SOUTH", false);
        builder.expectedValue("location", "London", true);
        builder.expectedValue("locationName", "London", true);
        builder.expectedValue("caseManagementCategory", "CIC", true);
        builder.expectedValue("priorityDate", "dueDate", false);
        builder.expectedValue("dueDateOrigin", "500", true);
        builder.expectedValue("dueDateTime", "500", true);
        builder.expectedValue("dueDateNonWorkingCalendar", "SATURDAY,SUNDAY", false);
        builder.expectedValue("dueDateNonWorkingDaysOfWeek", "true", false);
        builder.expectedValue("dueDateSkipNonWorkingDays", "No", false);
        builder.expectedValue("dueDateMustBeWorkingDay", "500", false);
        builder.expectedValue("calculatedDates", "nextHearingDate,dueDate,priorityDate", false);
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
