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
        "caseName","caseManagementCategory","region","location","locationName","priorityDate","majorPriority",
        "minorPriority","calculatedDates","dueDateOrigin","dueDateTime","dueDateNonWorkingCalendar",
        "dueDateNonWorkingDaysOfWeek","dueDateSkipNonWorkingDays","dueDateMustBeWorkingDay","workType",
        "roleCategory", "dueDateIntervalDays", "description"
    );
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private Map<String,Map<String,Object>> expectations = new HashMap<>();

    public static ConfigurationExpectationBuilder defaultExpectations() {
        ConfigurationExpectationBuilder builder = new ConfigurationExpectationBuilder();
        builder.expectedValue("caseName", "Joe Blogs", true);
        builder.expectedValue("caseManagementCategory", "Criminal Injuries Compensation", true);
        builder.expectedValue("region", "1", true);
        builder.expectedValue("location", "336559", true);
        builder.expectedValue("locationName", "Glasgow Tribunals Centre", true);
        builder.expectedValue("calculatedDates", "nextHearingDate,dueDate,priorityDate", true);
        builder.expectedValue("dueDateTime", "16:00", true);
        builder.expectedValue("dueDateNonWorkingCalendar", "https://www.gov.uk/bank-holidays/" +
            "england-and-wales.json", true);
        builder.expectedValue("dueDateNonWorkingDaysOfWeek", "SATURDAY,SUNDAY", false);
        builder.expectedValue("dueDateSkipNonWorkingDays", "true", false);
        builder.expectedValue("dueDateMustBeWorkingDay", "Next", false);
        builder.expectedValue("workType", "routine_work", true);
        builder.expectedValue("roleCategory", "ADMIN", true);
        builder.expectedValue("minorPriority", "500", true);
        builder.expectedValue("majorPriority", "5000", true);
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
