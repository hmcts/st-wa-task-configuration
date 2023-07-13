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

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Withdrawal request")),
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
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Rule 27 request")),
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
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listing directions")),
                List.of(
                    Map.of(
                        "taskId", "processListingDirections",
                        "name", "Process listing directions",
                        "workingDaysAllowed", 1,
                        "processCategories", "processListingDirections",
                        "workType", "routine_work",
                        "roleCategory","ADMIN"
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case")),
                List.of(
                    Map.of(
                        "taskId", "processDirectionsReListedCase",
                        "name", "Process directions re. listed case",
                        "workingDaysAllowed", 1,
                        "processCategories", "processDirectionsReListedCase",
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
        assertThat(logic.getRules().size(), is(4));

    }

}
