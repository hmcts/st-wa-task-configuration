package uk.gov.hmcts.st.taskconfiguration.dmn;

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
import uk.gov.hmcts.st.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.st.taskconfiguration.DmnDecisionTable.WA_TASK_COMPLETION_ST_CIC_CRIMINALINJURIESCOMPENSATION;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.AUTO_COMPLETE_MODE;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.COMPLETE_HEARING_OUTCOME_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.DEFAULT_NONE_COMPLETE_MODE;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.FOLLOW_UP_NONCOMPLIANCE_OF_DIR_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.ISSUE_CASE_TO_RESPONDENT_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.ISSUE_DECISION_NOTICE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_CASE_WITHDRAWAL_DIR_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_CORRECTIONS_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_DIR_RELISTED_CASE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_DIR_RELISTED_CASE_WITHIN_5DAYS_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_DIR_RETURNED_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_FURTHER_EVIDENCE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_LISTING_DIR_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_OTHER_DIR_RETURNED_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_POSTPONEMENT_DIR_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_REINSTATEMENT_DECISION_NOTICE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_RULE27_DECISION_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_SET_ASIDE_DIR_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_STAY_DIR_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_STRIKE_OUT_DIR_RETURNED_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_TIME_EXT_DIR_RETURNED_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PROCESS_WRITTEN_REASONS_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REGISTER_NEW_CASE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_CORRECTIONS_REQ_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_LISTING_DIR_JUDGE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_LISTING_DIR_LO_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_LIST_CASE_JUDGE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_LIST_CASE_LO_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_LIST_CASE_WITHIN_5DAYS_JUDGE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_LIST_CASE_WITHIN_5DAYS_LO_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_NEW_CASE_PROVIDE_DIR_JUDGE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_NEW_CASE_PROVIDE_DIR_LO_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_OTHER_REQ_JUDGE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_OTHER_REQ_LO_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_POSTPONEMENT_REQ_JUDGE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_POSTPONEMENT_REQ_LO_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_REINSTATEMENT_REQ_JUDGE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_REINSTATEMENT_REQ_LO_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_RULE27_REQ_JUDGE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_RULE27_REQ_LO_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_SET_ASIDE_REQ_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_STAY_REQ_JUDGE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_STAY_REQ_LO_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_STRIKE_OUT_REQ_JUDGE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_STRIKE_OUT_REQ_LO_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_TIME_EXT_REQ_JUDGE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_TIME_EXT_REQ_LO_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_WITHDRAWAL_REQ_JUDGE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_WITHDRAWAL_REQ_LO_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.REVIEW_WRITTEN_REASONS_REQ_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.STITCH_COLLATE_HEARING_BUNDLE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.VET_NEW_CASE_DOCUMENTS_TASK;

class CamundaTaskCompletionTest extends DmnDecisionTableBaseUnitTest {
    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_COMPLETION_ST_CIC_CRIMINALINJURIESCOMPENSATION;
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "caseworker-send-order",
                List.of(
                    Map.of(
                        "taskType", PROCESS_CASE_WITHDRAWAL_DIR_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_RULE27_DECISION_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_LISTING_DIR_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_DIR_RELISTED_CASE_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_DIR_RELISTED_CASE_WITHIN_5DAYS_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_SET_ASIDE_DIR_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_CORRECTIONS_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_DIR_RETURNED_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_POSTPONEMENT_DIR_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_TIME_EXT_DIR_RETURNED_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_REINSTATEMENT_DECISION_NOTICE_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_OTHER_DIR_RETURNED_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_WRITTEN_REASONS_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_STRIKE_OUT_DIR_RETURNED_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_STAY_DIR_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    )
                )
            ),
            Arguments.of(
                "caseworker-issue-final-decision",
                List.of(
                    Map.of(
                        "taskType", ISSUE_DECISION_NOTICE_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    )
                )
            ),
            Arguments.of(
                "caseworker-issue-decision",
                List.of(
                    Map.of(
                        "taskType", ISSUE_DECISION_NOTICE_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    )
                )
            ),
            Arguments.of(
                "create-hearing-summary",
                List.of(
                    Map.of(
                        "taskType", COMPLETE_HEARING_OUTCOME_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                List.of(
                    Map.of(
                        "taskType", FOLLOW_UP_NONCOMPLIANCE_OF_DIR_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_FURTHER_EVIDENCE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                List.of(
                    Map.of(
                        "taskType", FOLLOW_UP_NONCOMPLIANCE_OF_DIR_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_FURTHER_EVIDENCE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    )
                )
            ),
            Arguments.of(
                "caseworker-issue-case",
                List.of(
                    Map.of(
                        "taskType", ISSUE_CASE_TO_RESPONDENT_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    )
                )
            ),
            Arguments.of(
                "caseworker-edit-cica-case-details",
                List.of(
                    Map.of(
                        "taskType", VET_NEW_CASE_DOCUMENTS_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    )
                )
            ),
            Arguments.of(
                "caseworker-case-built",
                List.of(
                    Map.of(
                        "taskType", VET_NEW_CASE_DOCUMENTS_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                List.of(
                    Map.of(
                        "taskType", REVIEW_NEW_CASE_PROVIDE_DIR_LO_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_TIME_EXT_REQ_LO_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_STRIKE_OUT_REQ_LO_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_STAY_REQ_LO_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_LISTING_DIR_LO_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_WITHDRAWAL_REQ_LO_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_RULE27_REQ_LO_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_LIST_CASE_LO_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_OTHER_REQ_LO_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_LIST_CASE_WITHIN_5DAYS_LO_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_POSTPONEMENT_REQ_LO_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_REINSTATEMENT_REQ_LO_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_LIST_CASE_WITHIN_5DAYS_JUDGE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_POSTPONEMENT_REQ_JUDGE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_CORRECTIONS_REQ_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_WRITTEN_REASONS_REQ_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_REINSTATEMENT_REQ_JUDGE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_SET_ASIDE_REQ_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_STAY_REQ_JUDGE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_NEW_CASE_PROVIDE_DIR_JUDGE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_OTHER_REQ_JUDGE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_WITHDRAWAL_REQ_JUDGE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_RULE27_REQ_JUDGE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_LISTING_DIR_JUDGE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_LIST_CASE_JUDGE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_STRIKE_OUT_REQ_JUDGE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", REVIEW_TIME_EXT_REQ_JUDGE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    )
                )
            ),
            Arguments.of(
                "edit-case",
                List.of(
                    Map.of(
                        "taskType", REGISTER_NEW_CASE_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_FURTHER_EVIDENCE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    )
                )
            ),
            Arguments.of(
                "caseworker-amend-document",
                List.of(
                    Map.of(
                        "taskType", PROCESS_FURTHER_EVIDENCE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    )
                )
            ),
            Arguments.of(
                "contact-parties",
                List.of(
                    Map.of(
                        "taskType", FOLLOW_UP_NONCOMPLIANCE_OF_DIR_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    ),
                    Map.of(
                        "taskType", PROCESS_FURTHER_EVIDENCE_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    )
                )
            ),
            Arguments.of(
                "createBundle",
                List.of(
                    Map.of(
                        "taskType", STITCH_COLLATE_HEARING_BUNDLE_TASK,
                        "completionMode", AUTO_COMPLETE_MODE
                    )
                )
            ),
            Arguments.of(
                "caseworker-document-management",
                List.of(
                    Map.of(
                        "taskType", FOLLOW_UP_NONCOMPLIANCE_OF_DIR_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    )
                )
            ),
            Arguments.of(
                "caseworker-amend-due-date",
                List.of(
                    Map.of(
                        "taskType", FOLLOW_UP_NONCOMPLIANCE_OF_DIR_TASK,
                        "completionMode", DEFAULT_NONE_COMPLETE_MODE
                    )
                )
            )
        );
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(1));
        assertThat(logic.getOutputs().size(), is(2));
        assertThat(logic.getRules().size(), is(50));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource("scenarioProvider")
    void given_event_ids_should_evaluate_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

}
