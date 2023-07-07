package uk.gov.hmcts.reform.wataskconfigurationtemplate.dmn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTableBaseUnitTest;
import uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.DelayUntilRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTable.WA_TASK_INITIATION_ST_CIC_CRIMINALJURIESCOMPENSATION;

@Slf4j
class CamundaTaskWaInitiationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_INITIATION_ST_CIC_CRIMINALJURIESCOMPENSATION;
    }

    @ParameterizedTest
    @MethodSource("scenarioProvider")
    void given_input_should_return_outcome_dmn(String eventId,
                                               String postEventState,
                                               String appealType,
                                               List<Map<String, Object>> expectedDmnOutcome) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        if (StringUtils.isNotBlank(appealType)) {
            inputVariables.putValue("additionalData", Map.of(
                "Data", Map.of("appealType", appealType)
            ));
        }

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectedDmnOutcome));
    }

    @Test
    void should_return_outcome_for_delay_until_in_dmn() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "delayUntilDate");
        inputVariables.putValue("postEventState", "");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        String delayUntilDate = LocalDateTime.now().withHour(18).withMinute(0).withSecond(0)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        DelayUntilRequest delayUntil = DelayUntilRequest.builder()
            .delayUntil(delayUntilDate)
            .delayUntilTime("16:00:00")
            .build();

        DelayUntilRequest delayUntil1 = getMapper().convertValue(
            dmnDecisionTableResult.getResultList().get(0).get("delayUntil"),
            DelayUntilRequest.class
        );
        assertThat(delayUntil1, is(delayUntil));
    }

    @Test
    void should_return_outcome_for_delay_until_time_in_dmn() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "delayUntilTime");
        inputVariables.putValue("postEventState", "");


        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        DelayUntilRequest delayUntilRequest = DelayUntilRequest.builder().delayUntilTime("16:00:00").build();

        Object delayUntil = dmnDecisionTableResult.getResultList().get(0).get("delayUntil");
        assertThat(getMapper().convertValue(delayUntil, DelayUntilRequest.class), equalTo(delayUntilRequest));
    }

    @Test
    void should_return_outcome_for_delay_until_interval_in_dmn() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "delayUntilInterval");
        inputVariables.putValue("postEventState", "");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);


        DelayUntilRequest delayUntil
            = DelayUntilRequest.builder()
            .delayUntilIntervalDays(4)
            .delayUntilNonWorkingCalendar("https://www.gov.uk/bank-holidays/england-and-wales.json")
            .delayUntilSkipNonWorkingDays(true)
            .delayUntilOrigin("2022-12-23T18:00")
            .delayUntilNonWorkingDaysOfWeek("SATURDAY,SUNDAY")
            .delayUntilMustBeWorkingDay("No")
            .build();

        assertThat(getMapper().convertValue(
            dmnDecisionTableResult.getResultList().get(0).get("delayUntil"),
            DelayUntilRequest.class
        ), equalTo(delayUntil));
    }

    private static LinkedHashMap<String, Object> sortMap(Map<String, Object> delayUntilIntervalDays) {
        return delayUntilIntervalDays.entrySet().stream()
            .sorted(Comparator.comparing(Map.Entry::getKey))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public static Stream<Arguments> scenarioProvider() {

        String delayUntil = LocalDateTime.now().plusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            + "T18:00";
        return Stream.of(
            Arguments.of(
                "create-draft-order", "", "protection",
                List.of(
                    Map.of(
                        "taskId", "followUpNonStandardDirection",
                        "name", "Follow-up non-standard direction",
                        "workingDaysAllowed", 2,
                        "delayDuration", 0,
                        "processCategories", "caseProgression",
                        "taskType", "followUpNonStandardDirection"
                    )
                )
            )
        );
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(22));

    }

    private ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

}
