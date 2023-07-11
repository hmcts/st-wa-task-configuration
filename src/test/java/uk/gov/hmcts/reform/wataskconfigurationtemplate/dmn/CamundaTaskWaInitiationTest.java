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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTable.WA_TASK_INITIATION_ST_CIC_CRIMINALJURIESCOMPENSATION;

class CamundaTaskWaInitiationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_INITIATION_ST_CIC_CRIMINALJURIESCOMPENSATION;
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "create-draft-order",
                "caseManagement",
                Map.of("Data", Map.of("referToJudgeReferralReason", "Withdrawal Request")),
                List.of(
                    Map.of(
                        "taskId", "processCaseWithdrawalDirections",
                        "name", "Process Case Withdrawal Directions",
                        "workingDaysAllowed", 10,
                        "processCategories", "processCaseWithdrawalDirections",
                        "workType", "routine_work",
                        "roleCategory","ADMIN"
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "caseManagement",
                Map.of("Data", Map.of("referToJudgeReferralReason", "Rule 27 Request")),
                List.of(
                    Map.of(
                        "taskId", "processRule27Decision",
                        "name", "Process Rule 27 decision",
                        "workingDaysAllowed", 5,
                        "processCategories", "processRule27Decision",
                        "workType", "routine_work",
                        "roleCategory","ADMIN"
                    )
                )
            )
        );
    }

    @ParameterizedTest(name = "event id: {0} post event state: {1} appeal type: {2}")
    @MethodSource("scenarioProvider")
    void given_multiple_event_ids_should_evaluate_dmn(String eventId,
                                                      String postEventState,
                                                      Map<String, Object> map,
                                                      List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", map);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(2));

    }

}
