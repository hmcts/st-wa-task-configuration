package uk.gov.hmcts.st.taskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.st.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.st.taskconfiguration.DmnDecisionTableBaseUnitTest;
import uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CamundaTaskWaInitiationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_INITIATION_ST_CIC_CRIMINALINJURIESCOMPENSATION;
    }

    static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Withdrawal request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_CASE_WITHDRAWAL_DIR_TASK,
                        "name", "Process Case Withdrawal Directions",
                        "workingDaysAllowed", 10,
                        "processCategories", CamundaTaskConstants.PROCESS_CASE_WITHDRAWAL_DIR_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Rule 27 request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_RULE27_DECISION_TASK,
                        "name", "Process Rule 27 decision",
                        "workingDaysAllowed", 5,
                        "processCategories", CamundaTaskConstants.PROCESS_RULE27_DECISION_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listing directions")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_LISTING_DIR_TASK,
                        "name", "Process listing directions",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.PROCESS_LISTING_DIR_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_DIR_RELISTED_CASE_TASK,
                        "name", "Process directions re. listed case",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.PROCESS_DIR_RELISTED_CASE_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case (within 5 days)")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_DIR_RELISTED_CASE_WITHIN_5DAYS_TASK,
                        "name", "Process directions re. listed case (within 5 days)",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.PROCESS_DIR_RELISTED_CASE_WITHIN_5DAYS_TASK,
                        "workType", CamundaTaskConstants.PRIORITY_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Set aside request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_SET_ASIDE_DIR_TASK,
                        "name", "Process Set Aside directions",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.PROCESS_SET_ASIDE_DIR_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Corrections")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_CORRECTIONS_TASK,
                        "name", "Process corrections",
                        "workingDaysAllowed", 3,
                        "processCategories", CamundaTaskConstants.PROCESS_CORRECTIONS_TASK,
                        "workType", CamundaTaskConstants.HEARING_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "New case")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_DIR_RETURNED_TASK,
                        "name", "Process directions returned",
                        "workingDaysAllowed", 10,
                        "processCategories", CamundaTaskConstants.PROCESS_DIR_RETURNED_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Postponement request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_POSTPONEMENT_DIR_TASK,
                        "name", "Process postponement directions",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.PROCESS_POSTPONEMENT_DIR_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Time extension request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_TIME_EXT_DIR_RETURNED_TASK,
                        "name", "Process time extension directions returned",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.PROCESS_TIME_EXT_DIR_RETURNED_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Reinstatement request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_REINSTATEMENT_DECISION_NOTICE_TASK,
                        "name", "Process Reinstatement decision notice",
                        "workingDaysAllowed", 5,
                        "processCategories", CamundaTaskConstants.PROCESS_REINSTATEMENT_DECISION_NOTICE_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "*",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Other")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_OTHER_DIR_RETURNED_TASK,
                        "name", "Process other directions returned",
                        "workingDaysAllowed", 10,
                        "processCategories", CamundaTaskConstants.PROCESS_OTHER_DIR_RETURNED_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Written reasons request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_WRITTEN_REASONS_TASK,
                        "name", "Process Written Reasons",
                        "workingDaysAllowed", 3,
                        "processCategories", CamundaTaskConstants.PROCESS_WRITTEN_REASONS_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Strike out request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_STRIKE_OUT_DIR_RETURNED_TASK,
                        "name", "Process strike out directions returned",
                        "workingDaysAllowed", 10,
                        "processCategories", CamundaTaskConstants.PROCESS_STRIKE_OUT_DIR_RETURNED_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Stay request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_STAY_DIR_TASK,
                        "name", "Process stay directions",
                        "workingDaysAllowed", 10,
                        "processCategories", CamundaTaskConstants.PROCESS_STAY_DIR_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-hearing-summary",
                "AwaitingHearing",
                null,
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.ISSUE_DECISION_NOTICE_TASK,
                        "name", "Issue Decision Notice",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.ISSUE_DECISION_NOTICE_TASK,
                        "workType", CamundaTaskConstants.HEARING_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "caseworker-record-listing",
                "AwaitingHearing",
                null,
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.COMPLETE_HEARING_OUTCOME_TASK,
                        "name", "Complete Hearing Outcome",
                        "workingDaysAllowed", 5,
                        "processCategories", CamundaTaskConstants.COMPLETE_HEARING_OUTCOME_TASK,
                        "workType", CamundaTaskConstants.HEARING_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "caseworker-case-built",
                "CaseManagement",
                null,
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REFER_CASE_TASK,
                        "name", "Refer Case",
                        "workingDaysAllowed", 2,
                        "processCategories", CamundaTaskConstants.REFER_CASE_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "citizen-cic-submit-dss-application",
                "Submitted",
                null,
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.VET_NEW_CASE_DOCUMENTS_TASK,
                        "name", "Vet New Case Documents",
                        "workingDaysAllowed", 5,
                        "processCategories", CamundaTaskConstants.VET_NEW_CASE_DOCUMENTS_TASK,
                        "workType", CamundaTaskConstants.APPLICATION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "citizen-cic-submit-dss-application",
                "Draft",
                null,
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REGISTER_NEW_CASE_TASK,
                        "name", "Register New Case",
                        "workingDaysAllowed", 5,
                        "processCategories", CamundaTaskConstants.REGISTER_NEW_CASE_TASK,
                        "workType", CamundaTaskConstants.APPLICATION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "New case")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_NEW_CASE_PROVIDE_DIR_LO_TASK,
                        "name", "Review new case and provide directions - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", CamundaTaskConstants.REVIEW_NEW_CASE_PROVIDE_DIR_LO_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Time extension request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_TIME_EXT_REQ_LO_TASK,
                        "name", "Review Time extension request - Legal Officer",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.REVIEW_TIME_EXT_REQ_LO_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Strike out request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_STRIKE_OUT_REQ_LO_TASK,
                        "name", "Review Strike out request - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", CamundaTaskConstants.REVIEW_STRIKE_OUT_REQ_LO_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Stay request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_STAY_REQ_LO_TASK,
                        "name", "Review stay request - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", CamundaTaskConstants.REVIEW_STAY_REQ_LO_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listing directions")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_LISTING_DIR_LO_TASK,
                        "name", "Review listing directions - Legal Officer",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.REVIEW_LISTING_DIR_LO_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Withdrawal request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_WITHDRAWAL_REQ_LO_TASK,
                        "name", "Review withdrawal request - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", CamundaTaskConstants.REVIEW_WITHDRAWAL_REQ_LO_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Rule 27 request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_RULE27_REQ_LO_TASK,
                        "name", "Review Rule 27 request - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", CamundaTaskConstants.REVIEW_RULE27_REQ_LO_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_LIST_CASE_LO_TASK,
                        "name", "Review List Case - Legal Officer",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.REVIEW_LIST_CASE_LO_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case (within 5 days)")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_LIST_CASE_WITHIN_5DAYS_LO_TASK,
                        "name", "Review list case (within 5 days) - Legal Officer",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.REVIEW_LIST_CASE_WITHIN_5DAYS_LO_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Postponement request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_POSTPONEMENT_REQ_LO_TASK,
                        "name", "Review Postponement request - Legal Officer",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.REVIEW_POSTPONEMENT_REQ_LO_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Reinstatement request")),
                List.of(
                    Map.of(
                        "name", "Review Reinstatement request - Legal Officer",
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "taskId", CamundaTaskConstants.REVIEW_REINSTATEMENT_REQ_LO_TASK,
                        "workingDaysAllowed", 5,
                        "processCategories", CamundaTaskConstants.REVIEW_REINSTATEMENT_REQ_LO_TASK,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "*",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Other")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_OTHER_REQ_LO_TASK,
                        "name", "Review Reinstatement request - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", CamundaTaskConstants.REVIEW_OTHER_REQ_LO_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case (within 5 days)")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_LIST_CASE_WITHIN_5DAYS_JUDGE_TASK,
                        "name", "Review list case (within 5 days) - Judge",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.REVIEW_LIST_CASE_WITHIN_5DAYS_JUDGE_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Postponement request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_POSTPONEMENT_REQ_JUDGE_TASK,
                        "name", "Review Postponement request - Judge",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.REVIEW_POSTPONEMENT_REQ_JUDGE_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Corrections")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_CORRECTIONS_REQ_TASK,
                        "name", "Review Corrections request",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.REVIEW_CORRECTIONS_REQ_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Written reasons request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_WRITTEN_REASONS_REQ_TASK,
                        "name", "Review Written Reasons request",
                        "workingDaysAllowed", 28,
                        "processCategories", CamundaTaskConstants.REVIEW_WRITTEN_REASONS_REQ_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Reinstatement request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_REINSTATEMENT_REQ_JUDGE_TASK,
                        "name", "Review Reinstatement request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", CamundaTaskConstants.REVIEW_REINSTATEMENT_REQ_JUDGE_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Set aside request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_SET_ASIDE_REQ_TASK,
                        "name", "Review Set Aside request",
                        "workingDaysAllowed", 2,
                        "processCategories", CamundaTaskConstants.REVIEW_SET_ASIDE_REQ_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Stay request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_STAY_REQ_JUDGE_TASK,
                        "name", "Review stay request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", CamundaTaskConstants.REVIEW_STAY_REQ_JUDGE_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "New case")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_NEW_CASE_PROVIDE_DIR_JUDGE_TASK,
                        "name", "Review new case and provide directions - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", CamundaTaskConstants.REVIEW_NEW_CASE_PROVIDE_DIR_JUDGE_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "*",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Other")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_OTHER_REQ_JUDGE_TASK,
                        "name", "Review other request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", CamundaTaskConstants.REVIEW_OTHER_REQ_JUDGE_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Withdrawal request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_WITHDRAWAL_REQ_JUDGE_TASK,
                        "name", "Review withdrawal request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", CamundaTaskConstants.REVIEW_WITHDRAWAL_REQ_JUDGE_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Rule 27 request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_RULE27_REQ_JUDGE_TASK,
                        "name", "Review Rule 27 request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", CamundaTaskConstants.REVIEW_RULE27_REQ_JUDGE_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listing directions")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_LISTING_DIR_JUDGE_TASK,
                        "name", "Review listing directions - Judge",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.REVIEW_LISTING_DIR_JUDGE_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_LIST_CASE_JUDGE_TASK,
                        "name", "Review List Case - Judge",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.REVIEW_LIST_CASE_JUDGE_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Strike out request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_STRIKE_OUT_REQ_JUDGE_TASK,
                        "name", "Review Strike out request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", CamundaTaskConstants.REVIEW_STRIKE_OUT_REQ_JUDGE_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Time Extension request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.REVIEW_TIME_EXT_REQ_JUDGE_TASK,
                        "name", "Review Time extention request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", CamundaTaskConstants.REVIEW_TIME_EXT_REQ_JUDGE_TASK,
                        "workType", CamundaTaskConstants.DECISION_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "caseworker-send-order",
                "CaseManagement",
                null,
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.FOLLOW_UP_NONCOMPLIANCE_OF_DIR_TASK,
                        "name", "Follow up noncompliance of directions",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.FOLLOW_UP_NONCOMPLIANCE_OF_DIR_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "citizen-cic-update-dss-application",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Evidence request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_FURTHER_EVIDENCE_TASK,
                        "name", "Process further evidence",
                        "workingDaysAllowed", 10,
                        "processCategories", CamundaTaskConstants.PROCESS_FURTHER_EVIDENCE_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "caseworker-document-management",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Evidence request")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.PROCESS_FURTHER_EVIDENCE_TASK,
                        "name", "Process further evidence",
                        "workingDaysAllowed", 10,
                        "processCategories", CamundaTaskConstants.PROCESS_FURTHER_EVIDENCE_TASK,
                        "workType", CamundaTaskConstants.ROUTINE_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "createBundle",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Hearing Bundle")),
                List.of(
                    Map.of(
                        "taskId", CamundaTaskConstants.STITCH_COLLATE_HEARING_BUNDLE_TASK,
                        "name", "Stitch/collate hearing bundle",
                        "workingDaysAllowed", 1,
                        "processCategories", CamundaTaskConstants.STITCH_COLLATE_HEARING_BUNDLE_TASK,
                        "workType", CamundaTaskConstants.HEARING_WORK_TYPE,
                        "roleCategory", CamundaTaskConstants.ROLE_CATEGORY_ADMIN
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
        assertThat(logic.getOutputs().size(), is(7));
        assertThat(logic.getRules().size(), is(50));
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

}
