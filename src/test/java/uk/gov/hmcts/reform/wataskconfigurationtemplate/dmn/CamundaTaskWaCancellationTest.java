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
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTable.WA_TASK_CANCELLATION_WA_WACASETYPE;

class CamundaTaskWaCancellationTest extends DmnDecisionTableBaseUnitTest {
    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CANCELLATION_WA_WACASETYPE;
    }

    @ParameterizedTest
    @MethodSource("scenarioProvider")
    void given_multiple_event_ids_should_evaluate_dmn(String fromState,
                                                      String eventId,
                                                      String state,
                                                      List<Map<String, Object>> expectedDmnOutcome) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("fromState", fromState);
        inputVariables.putValue("event", eventId);
        inputVariables.putValue("state", state);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectedDmnOutcome));
    }

    public static Stream<Arguments> scenarioProvider() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "Warn",
                "warningCode", "TA01",
                "warningText", "There is an application task which might impact other active tasks"
            ),
            Map.of(
                "action", "Warn",
                "warningCode", "TA02",
                "warningText", "There is another task on this case that needs your attention"
            )
        );
        return Stream.of(
            Arguments.of(
                "any from state", "_DUMMY_makeAnApplication", "any state",
                outcome
            ),
            Arguments.of(
                "", "_DUMMY_makeAnApplication", "",
                outcome
            ),
            Arguments.of(
                null, "_DUMMY_makeAnApplication", null,
                outcome
            ),
            Arguments.of(
                null, "_DUMMY_makeAnApplication101", null,
                List.of(
                    Map.of(
                        "action", "Warn",
                        "warningCode", "Code101",
                        "warningText", "Warning Text 101",
                        "processCategories", "timeExtension"
                    ),
                    Map.of(
                        "action", "Warn",
                        "warningCode", "Code102",
                        "warningText", "Warning Text 102",
                        "processCategories", "followUpOverdue"
                    )
                )
            ),
            Arguments.of(
                null, "_DUMMY_makeAnApplication103", null,
                List.of(
                    Map.of(
                        "action", "Warn",
                        "warningCode", "Code103",
                        "warningText", "Warning Text 103"
                    ), Map.of(
                        "action", "Warn",
                        "warningCode", "Code103",
                        "warningText", "Warning Text 103"
                    )
                )
            ),
            Arguments.of(
                null, "_DUMMY_makeAnApplication104", null,
                List.of(
                    Map.of(
                        "action", "Warn",
                        "warningCode", "Code104",
                        "warningText", "Warning Text 104",
                        "processCategories", "timeExtension"
                    ), Map.of(
                        "action", "Warn",
                        "warningCode", "Code104",
                        "warningText", "Warning Text 104",
                        "processCategories", "followUpOverdue"
                    )
                )
            ),
            Arguments.of(
                null, "_DUMMY_makeAnApplication105", null,
                List.of(
                    Map.of(
                        "action", "Warn",
                        "warningCode", "Code105",
                        "warningText", "Warning Text 105",
                        "processCategories", "timeExtension"
                    )
                )
            ),
            Arguments.of(
                null, "_DUMMY_makeAnApplication106", null,
                List.of(
                    Map.of(
                        "action", "Warn",
                        "warningCode", "Code105",
                        "warningText", "Warning Text 105",
                        "processCategories", "timeExtension"
                    )
                )
            ),
            Arguments.of(
                null, "_DUMMY_makeAnApplication107", null,
                List.of(
                    Map.of(
                        "action", "Warn",
                        "processCategories", "followUpOverdue"
                    )
                )
            ),
            Arguments.of(
                null, "_DUMMY_makeAnApplication102", null,
                List.of(
                    Map.of(
                        "action", "Warn"
                    )
                )
            ),
            Arguments.of(
                null, "UPDATE", null,
                List.of(
                    Map.of(
                        "action", "Reconfigure"
                    )
                )
            ),
            Arguments.of(
                null, "makeAnApplication", null,
                List.of(
                    Map.of(
                        "action", "Warn",
                        "warningCode", "TA01",
                        "warningText", "There is an application task which might impact other active tasks"
                    )
                )
            ),
            Arguments.of(
                null, "withdrawAppeal", null,
                List.of(
                    Map.of(
                        "action", "Cancel",
                        "processCategories", "caseProgression"
                    )
                )
            )
        );
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(3));
        assertThat(logic.getOutputs().size(), is(4));
        assertThat(logic.getRules().size(), is(19));
    }
}
