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
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTable.WA_TASK_TYPES_ST_CIC_CRIMINALINJURIESCOMPENSATION;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.COMPLETE_HEARING_OUTCOME_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.FOLLOW_UP_NONCOMPLIANCE_OF_DIR_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.ISSUE_DECISION_NOTICE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_CASE_WITHDRAWAL_DIR_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_CORRECTIONS_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_DIR_RELISTED_CASE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_DIR_RELISTED_CASE_WITHIN_5DAYS_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_DIR_RETURNED_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_FURTHER_EVIDENCE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_LISTING_DIR_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_OTHER_DIR_RETURNED_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_POSTPONEMENT_DIR_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_REINSTATEMENT_DECISION_NOTICE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_RULE27_DECISION_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_SET_ASIDE_DIR_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_STAY_DIR_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_STRIKE_OUT_DIR_RETURNED_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_TIME_EXT_DIR_RETURNED_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PROCESS_WRITTEN_REASONS_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REFER_CASE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REGISTER_NEW_CASE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_CORRECTIONS_REQ_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_LISTING_DIR_JUDGE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_LISTING_DIR_LO_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_LIST_CASE_JUDGE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_LIST_CASE_LO_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_LIST_CASE_WITHIN_5DAYS_JUDGE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_LIST_CASE_WITHIN_5DAYS_LO_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_NEW_CASE_PROVIDE_DIR_JUDGE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_NEW_CASE_PROVIDE_DIR_LO_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_OTHER_REQ_JUDGE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_OTHER_REQ_LO_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_POSTPONEMENT_REQ_JUDGE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_POSTPONEMENT_REQ_LO_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_REINSTATEMENT_REQ_JUDGE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_REINSTATEMENT_REQ_LO_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_RULE27_REQ_JUDGE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_RULE27_REQ_LO_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_SET_ASIDE_REQ_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_STAY_REQ_JUDGE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_STAY_REQ_LO_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_STRIKE_OUT_REQ_JUDGE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_STRIKE_OUT_REQ_LO_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_TIME_EXT_REQ_JUDGE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_TIME_EXT_REQ_LO_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_WITHDRAWAL_REQ_JUDGE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_WITHDRAWAL_REQ_LO_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REVIEW_WRITTEN_REASONS_REQ_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.STITCH_COLLATE_HEARING_BUNDLE_TASK;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.VET_NEW_CASE_DOCUMENTS_TASK;

class CamundaTaskTypesTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_TYPES_ST_CIC_CRIMINALINJURIESCOMPENSATION;
    }

    static Stream<Arguments> scenarioProvider() {
        List<Map<String, String>> taskTypes = List.of(
            Map.of(
                "taskTypeId", PROCESS_CASE_WITHDRAWAL_DIR_TASK,
                "taskTypeName", "Process Case Withdrawal Directions"
            ),
            Map.of(
                "taskTypeId", PROCESS_RULE27_DECISION_TASK,
                "taskTypeName", "Process Rule 27 decision"
            ),
            Map.of(
                "taskTypeId", PROCESS_LISTING_DIR_TASK,
                "taskTypeName", "Process listing directions"
            ),
            Map.of(
                "taskTypeId", PROCESS_DIR_RELISTED_CASE_TASK,
                "taskTypeName", "Process directions re. listed case"
            ),
            Map.of(
                "taskTypeId", PROCESS_DIR_RELISTED_CASE_WITHIN_5DAYS_TASK,
                "taskTypeName", "Process directions re. listed case (within 5 days)"
            ),
            Map.of(
                "taskTypeId", PROCESS_SET_ASIDE_DIR_TASK,
                "taskTypeName", "Process Set Aside directions"
            ),
            Map.of(
                "taskTypeId", PROCESS_CORRECTIONS_TASK,
                "taskTypeName", "Process corrections"
            ),
            Map.of(
                "taskTypeId", PROCESS_DIR_RETURNED_TASK,
                "taskTypeName", "Process directions returned"
            ),
            Map.of(
                "taskTypeId", PROCESS_POSTPONEMENT_DIR_TASK,
                "taskTypeName", "Process postponement directions"
            ),
            Map.of(
                "taskTypeId", PROCESS_TIME_EXT_DIR_RETURNED_TASK,
                "taskTypeName", "Process time extension directions returned"
            ),
            Map.of(
                "taskTypeId", PROCESS_REINSTATEMENT_DECISION_NOTICE_TASK,
                "taskTypeName", "Process Reinstatement decision notice"
            ),
            Map.of(
                "taskTypeId", PROCESS_OTHER_DIR_RETURNED_TASK,
                "taskTypeName", "Process other directions reurned"
            ),
            Map.of(
                "taskTypeId", PROCESS_WRITTEN_REASONS_TASK,
                "taskTypeName", "Process Written Reasons"
            ),
            Map.of(
                "taskTypeId", PROCESS_STRIKE_OUT_DIR_RETURNED_TASK,
                "taskTypeName", "Process strike out directions returned"
            ),
            Map.of(
                "taskTypeId", PROCESS_STAY_DIR_TASK,
                "taskTypeName", "Process stay directions"
            ),
            Map.of(
                "taskTypeId", ISSUE_DECISION_NOTICE_TASK,
                "taskTypeName", "Issue Decision Notice"
            ),
            Map.of(
                "taskTypeId", COMPLETE_HEARING_OUTCOME_TASK,
                "taskTypeName", "Complete Hearing Outcome"
            ),
            Map.of(
                "taskTypeId", REFER_CASE_TASK,
                "taskTypeName", "Refer Case"
            ),
            Map.of(
                "taskTypeId", VET_NEW_CASE_DOCUMENTS_TASK,
                "taskTypeName", "Vet New Case Documents"
            ),
            Map.of(
                "taskTypeId", REVIEW_NEW_CASE_PROVIDE_DIR_LO_TASK,
                "taskTypeName", "Review new case and provide directions - Legal Officer"
            ),
            Map.of(
                "taskTypeId", REVIEW_TIME_EXT_REQ_LO_TASK,
                "taskTypeName", "Review Time extension request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", REVIEW_STRIKE_OUT_REQ_LO_TASK,
                "taskTypeName", "Review Strike out request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", REVIEW_STAY_REQ_LO_TASK,
                "taskTypeName", "Review stay request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", REVIEW_LISTING_DIR_LO_TASK,
                "taskTypeName", "Review listing directions - Legal Officer"
            ),
            Map.of(
                "taskTypeId", REVIEW_WITHDRAWAL_REQ_LO_TASK,
                "taskTypeName", "Review withdrawal request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", REVIEW_RULE27_REQ_LO_TASK,
                "taskTypeName", "Review Rule 27 request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", REVIEW_LIST_CASE_LO_TASK,
                "taskTypeName", "Review List Case - Legal Officer"
            ),
            Map.of(
                "taskTypeId", REVIEW_OTHER_REQ_LO_TASK,
                "taskTypeName", "Review Reinstatement request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", REVIEW_LIST_CASE_WITHIN_5DAYS_LO_TASK,
                "taskTypeName", "Review list case (within 5 days) - Legal Officer"
            ),
            Map.of(
                "taskTypeId", REVIEW_POSTPONEMENT_REQ_LO_TASK,
                "taskTypeName", "Review Postponement request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", REVIEW_REINSTATEMENT_REQ_LO_TASK,
                "taskTypeName", "Review Reinstatement request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", REVIEW_LIST_CASE_WITHIN_5DAYS_JUDGE_TASK,
                "taskTypeName", "Review list case (within 5 days) - Judge"
            ),
            Map.of(
                "taskTypeId", REVIEW_POSTPONEMENT_REQ_JUDGE_TASK,
                "taskTypeName", "Review Postponement request - Judge"
            ),
            Map.of(
                "taskTypeId", REVIEW_CORRECTIONS_REQ_TASK,
                "taskTypeName", "Review Corrections request"
            ),
            Map.of(
                "taskTypeId", REVIEW_WRITTEN_REASONS_REQ_TASK,
                "taskTypeName", "Review Written Reasons request"
            ),
            Map.of(
                "taskTypeId", REVIEW_REINSTATEMENT_REQ_JUDGE_TASK,
                "taskTypeName", "Review Reinstatement request - Judge"
            ),
            Map.of(
                "taskTypeId", REVIEW_SET_ASIDE_REQ_TASK,
                "taskTypeName", "Review Set Aside request"
            ),
            Map.of(
                "taskTypeId", REVIEW_STAY_REQ_JUDGE_TASK,
                "taskTypeName", "Review stay request - Judge"
            ),
            Map.of(
                "taskTypeId", REVIEW_NEW_CASE_PROVIDE_DIR_JUDGE_TASK,
                "taskTypeName", "Review new case and provide directions - Judge"
            ),
            Map.of(
                "taskTypeId", REVIEW_OTHER_REQ_JUDGE_TASK,
                "taskTypeName", "Review other request - Judge"
            ),
            Map.of(
                "taskTypeId", REVIEW_WITHDRAWAL_REQ_JUDGE_TASK,
                "taskTypeName", "Review withdrawal request - Judge"
            ),
            Map.of(
                "taskTypeId", REVIEW_RULE27_REQ_JUDGE_TASK,
                "taskTypeName", "Review Rule 27 request - Judge"
            ),
            Map.of(
                "taskTypeId", REVIEW_LISTING_DIR_JUDGE_TASK,
                "taskTypeName", "Review listing directions - Judge"
            ),
            Map.of(
                "taskTypeId", REVIEW_LIST_CASE_JUDGE_TASK,
                "taskTypeName", "Review List Case - Judge"
            ),
            Map.of(
                "taskTypeId", REVIEW_STRIKE_OUT_REQ_JUDGE_TASK,
                "taskTypeName", "Review Strike out request - Judge"
            ),
            Map.of(
                "taskTypeId", REVIEW_TIME_EXT_REQ_JUDGE_TASK,
                "taskTypeName", "Review Time extention request - Judge"
            ),
            Map.of(
                "taskTypeId", FOLLOW_UP_NONCOMPLIANCE_OF_DIR_TASK,
                "taskTypeName", "Follow up noncompliance of directions"
            ),
            Map.of(
                "taskTypeId", REGISTER_NEW_CASE_TASK,
                "taskTypeName", "Register New Case"
            ),
            Map.of(
                "taskTypeId", PROCESS_FURTHER_EVIDENCE_TASK,
                "taskTypeName", "Process Further Evidence"
            ),
            Map.of(
                "taskTypeId", STITCH_COLLATE_HEARING_BUNDLE_TASK,
                "taskTypeName", "Stitch/collate hearing bundle"
            )
        );
        return Stream.of(
            Arguments.of(
                taskTypes
            )
        );
    }

    @Test
    void check_dmn_changed() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(1));
        assertThat(logic.getOutputs().size(), is(2));
        assertThat(logic.getRules().size(), is(50));
    }

    @ParameterizedTest(name = "retrieve all task type data")
    @MethodSource("scenarioProvider")
    void should_evaluate_dmn_return_all_task_type_fields(List<Map<String, Object>> expectedTaskTypes) {

        VariableMap inputVariables = new VariableMapImpl();
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectedTaskTypes));
    }

}
