package uk.gov.hmcts.reform.wataskconfigurationtemplate.dmn;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.hamcrest.MatcherAssert;
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
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTable. WA_TASK_COMPLETION_ST_CIC_CRIMINALJURIESCOMPENSATION;
class CamundaTaskCompletionTest extends DmnDecisionTableBaseUnitTest {
    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE =  WA_TASK_COMPLETION_ST_CIC_CRIMINALJURIESCOMPENSATION;
    }
    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "caseworker-send-order",
                List.of(
                    Map.of(
                        "taskType", "processCaseWithdrawalDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "processRule27Decision",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "processListingDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "processDirectionsReListedCase",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "processDirectionsReListedCaseWithin5Days",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "processSetAsideDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "processCorrections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "processDirectionsReturned",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "processPostponementDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "processTimeExtensionDirectionsReturned",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "processReinstatementDecisionNotice",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "processOtherDirectionsReturned",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "processWrittenReasons",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "processStrikeOutDirectionsReturned",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "processStayDirections",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "caseworker-issue-final-decision",
                List.of(
                    Map.of(
                         "taskType", "issueDecisionNotice",
                         "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "caseworker-issue-decision",
                List.of(
                    Map.of(
                        "taskType", "issueDecisionNotice",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "create-hearing-summary",
                List.of(
                    Map.of(
                        "taskType", "completeHearingOutcome",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                List.of(
                    Map.of(
                        "taskType", "referCase",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                List.of(
                    Map.of(
                        "taskType", "referCase",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "caseworker-edit-cica-case-details",
                List.of(
                    Map.of(
                        "taskType", "vetNewCaseDocuments",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "caseworker-case-built",
                List.of(
                    Map.of(
                        "taskType", "vetNewCaseDocuments",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                List.of(
                    Map.of(
                        "taskType", "reviewNewCaseAndProvideDirectionsLO",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewTimeExtensionRequestLO",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewStrikeOutRequestLO",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewStayRequestLO",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewListingDirectionsLO",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewWithdrawalRequestLO",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewRule27RequestLO",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewListCaseLO",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewOtherRequestLO",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewListCaseWithin5DaysLO",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewPostponementRequestLO",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewReinstatementRequestLO",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewListCaseWithin5DaysJudge",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewPostponementRequestJudge",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewCorrectionsRequest",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewWrittenReasonsRequest",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewReinstatementRequestJudge",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewSetAsideRequest",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewStayRequestJudge",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewNewCaseAndProvideDirectionsJudge",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewOtherRequestJudge",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }
    @ParameterizedTest(name = "event id: {0}")
    @MethodSource("scenarioProvider")
    void given_event_ids_should_evaluate_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }
    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(40));
    }
}
