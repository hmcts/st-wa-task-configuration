package uk.gov.hmcts.reform.wataskconfigurationtemplate.dmn;

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
import uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CaseDataBuilder;
import uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.ConfigurationExpectationBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTable.WA_TASK_CONFIGURATION_ST_CIC_CRIMINALJURIESCOMPENSATION;

class CamundaTaskWaConfigurationTest extends DmnDecisionTableBaseUnitTest {


    public static final String MINOR_PRIORITY = "minorPriority";
    public static final String MAJOR_PRIORITY = "majorPriority";
    public static final String DUE_DATE_INTERVAL_DAYS = "dueDateIntervalDays";


    static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            Arguments.of(
                "processCaseWithdrawalDirections",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "routine_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .expectedValue("description", "[Orders: Send order](/cases/case-details"
                        + "/${[CASE_REFERENCE]}/trigger/caseworker-send-order/"
                        + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",true)
                    .build()
            ),
            Arguments.of(
                "processRule27Decision",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "routine_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue("description",
                                   "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-send-order/"
                                       + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                    true)
                    .build()
            ),
            Arguments.of(
                "processListingDirections",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "routine_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                    "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                        + "/caseworker-send-order/"
                                        + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                    true)
                    .build()
            ),
            Arguments.of(
                "stitchCollateHearingBundle",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "hearing_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .build()
            )
        );
    }

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CONFIGURATION_ST_CIC_CRIMINALJURIESCOMPENSATION;
    }

    @ParameterizedTest(name = "task type: {0} case data: {1}")
    @MethodSource("scenarioProvider")
    void should_return_correct_configuration_values_for_scenario(
        String taskType, Map<String, Object> caseData,
        List<Map<String, Object>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskType", taskType);
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        resultsMatch(dmnDecisionTableResult.getResultList(), expectation);
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(9));
    }

    private void resultsMatch(List<Map<String, Object>> results, List<Map<String, Object>> expectation) {
        assertThat(results.size(), is(expectation.size()));
        for (int index = 0; index < expectation.size(); index++) {
            if ("dueDateOrigin".equals(expectation.get(index).get("name"))) {
                assertEquals(
                    results.get(index).get("canReconfigure"),
                    expectation.get(index).get("canReconfigure")
                );
                assertTrue(validNow(
                    LocalDateTime.parse(results.get(index).get("value").toString()),
                    LocalDateTime.parse(expectation.get(index).get("value").toString())
                ));
            } else {
                assertThat(results.get(index), is(expectation.get(index)));
            }
        }
    }

    private boolean validNow(LocalDateTime actual, LocalDateTime expected) {
        LocalDateTime now = LocalDateTime.now();
        return actual != null
            && (expected.isEqual(actual) || expected.isBefore(actual))
            && (now.isEqual(actual) || now.isAfter(actual));
    }
}

