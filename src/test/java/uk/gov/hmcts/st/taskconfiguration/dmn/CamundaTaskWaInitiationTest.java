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
import uk.gov.hmcts.st.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.st.taskconfiguration.DmnDecisionTable.WA_TASK_INITIATION_ST_CIC_CRIMINALINJURIESCOMPENSATION;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.APPLICATION_WORK_TYPE;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.COMPLETE_HEARING_OUTCOME_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.DECISION_WORK_TYPE;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.FOLLOW_UP_NONCOMPLIANCE_OF_DIR_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.HEARING_WORK_TYPE;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.ISSUE_CASE_TO_RESPONDENT_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.ISSUE_DECISION_NOTICE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.PRIORITY_WORK_TYPE;
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
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.ROLE_CATEGORY_ADMIN;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.ROLE_CATEGORY_LO;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.ROUTINE_WORK_TYPE;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.STITCH_COLLATE_HEARING_BUNDLE_TASK;
import static uk.gov.hmcts.st.taskconfiguration.utils.CamundaTaskConstants.VET_NEW_CASE_DOCUMENTS_TASK;

class CamundaTaskWaInitiationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_INITIATION_ST_CIC_CRIMINALINJURIESCOMPENSATION;
    }

    static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Withdrawal request")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_CASE_WITHDRAWAL_DIR_TASK,
                        "name", "Process Case Withdrawal Directions",
                        "workingDaysAllowed", 10,
                        "processCategories", PROCESS_CASE_WITHDRAWAL_DIR_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Rule 27 request")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_RULE27_DECISION_TASK,
                        "name", "Process Rule 27 decision",
                        "workingDaysAllowed", 5,
                        "processCategories", PROCESS_RULE27_DECISION_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listing directions")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_LISTING_DIR_TASK,
                        "name", "Process listing directions",
                        "workingDaysAllowed", 1,
                        "processCategories", PROCESS_LISTING_DIR_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_DIR_RELISTED_CASE_TASK,
                        "name", "Process directions re. listed case",
                        "workingDaysAllowed", 1,
                        "processCategories", PROCESS_DIR_RELISTED_CASE_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case (within 5 days)")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_DIR_RELISTED_CASE_WITHIN_5DAYS_TASK,
                        "name", "Process directions re. listed case (within 5 days)",
                        "workingDaysAllowed", 1,
                        "processCategories", PROCESS_DIR_RELISTED_CASE_WITHIN_5DAYS_TASK,
                        "workType", PRIORITY_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Set aside request")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_SET_ASIDE_DIR_TASK,
                        "name", "Process Set Aside directions",
                        "workingDaysAllowed", 1,
                        "processCategories", PROCESS_SET_ASIDE_DIR_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Corrections")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_CORRECTIONS_TASK,
                        "name", "Process corrections",
                        "workingDaysAllowed", 3,
                        "processCategories", PROCESS_CORRECTIONS_TASK,
                        "workType", HEARING_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "New case")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_DIR_RETURNED_TASK,
                        "name", "Process directions returned",
                        "workingDaysAllowed", 10,
                        "processCategories", PROCESS_DIR_RETURNED_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Postponement request")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_POSTPONEMENT_DIR_TASK,
                        "name", "Process postponement directions",
                        "workingDaysAllowed", 1,
                        "processCategories", PROCESS_POSTPONEMENT_DIR_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Time extension request")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_TIME_EXT_DIR_RETURNED_TASK,
                        "name", "Process time extension directions returned",
                        "workingDaysAllowed", 1,
                        "processCategories", PROCESS_TIME_EXT_DIR_RETURNED_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Reinstatement request")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_REINSTATEMENT_DECISION_NOTICE_TASK,
                        "name", "Process Reinstatement decision notice",
                        "workingDaysAllowed", 5,
                        "processCategories", PROCESS_REINSTATEMENT_DECISION_NOTICE_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "*",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Other")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_OTHER_DIR_RETURNED_TASK,
                        "name", "Process other directions returned",
                        "workingDaysAllowed", 10,
                        "processCategories", PROCESS_OTHER_DIR_RETURNED_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Written reasons request")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_WRITTEN_REASONS_TASK,
                        "name", "Process Written Reasons",
                        "workingDaysAllowed", 3,
                        "processCategories", PROCESS_WRITTEN_REASONS_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Strike out request")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_STRIKE_OUT_DIR_RETURNED_TASK,
                        "name", "Process strike out directions returned",
                        "workingDaysAllowed", 10,
                        "processCategories", PROCESS_STRIKE_OUT_DIR_RETURNED_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Stay request")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_STAY_DIR_TASK,
                        "name", "Process stay directions",
                        "workingDaysAllowed", 10,
                        "processCategories", PROCESS_STAY_DIR_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "create-hearing-summary",
                "AwaitingHearing",
                null,
                List.of(
                    Map.of(
                        "taskId", ISSUE_DECISION_NOTICE_TASK,
                        "name", "Issue Decision Notice",
                        "workingDaysAllowed", 1,
                        "processCategories", ISSUE_DECISION_NOTICE_TASK,
                        "workType", HEARING_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "caseworker-record-listing",
                "AwaitingHearing",
                null,
                List.of(
                    Map.of(
                        "taskId", COMPLETE_HEARING_OUTCOME_TASK,
                        "name", "Complete Hearing Outcome",
                        "workingDaysAllowed", 5,
                        "processCategories", COMPLETE_HEARING_OUTCOME_TASK,
                        "workType", HEARING_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "caseworker-case-built",
                "CaseManagement",
                null,
                List.of(
                    Map.of(
                        "taskId", ISSUE_CASE_TO_RESPONDENT_TASK,
                        "name", "Issue Case To Respondent",
                        "workingDaysAllowed", 2,
                        "processCategories", ISSUE_CASE_TO_RESPONDENT_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "citizen-cic-submit-dss-application",
                "Submitted",
                null,
                List.of(
                    Map.of(
                        "taskId", VET_NEW_CASE_DOCUMENTS_TASK,
                        "name", "Vet New Case Documents",
                        "workingDaysAllowed", 5,
                        "processCategories", VET_NEW_CASE_DOCUMENTS_TASK,
                        "workType", APPLICATION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "citizen-cic-submit-dss-application",
                "Draft",
                null,
                List.of(
                    Map.of(
                        "taskId", REGISTER_NEW_CASE_TASK,
                        "name", "Register New Case",
                        "workingDaysAllowed", 5,
                        "processCategories", REGISTER_NEW_CASE_TASK,
                        "workType", APPLICATION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "New case")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_NEW_CASE_PROVIDE_DIR_LO_TASK,
                        "name", "Review new case and provide directions - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", REVIEW_NEW_CASE_PROVIDE_DIR_LO_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Time extension request")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_TIME_EXT_REQ_LO_TASK,
                        "name", "Review Time extension request - Legal Officer",
                        "workingDaysAllowed", 1,
                        "processCategories", REVIEW_TIME_EXT_REQ_LO_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Strike out request")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_STRIKE_OUT_REQ_LO_TASK,
                        "name", "Review Strike out request - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", REVIEW_STRIKE_OUT_REQ_LO_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Stay request")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_STAY_REQ_LO_TASK,
                        "name", "Review stay request - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", REVIEW_STAY_REQ_LO_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listing directions")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_LISTING_DIR_LO_TASK,
                        "name", "Review listing directions - Legal Officer",
                        "workingDaysAllowed", 1,
                        "processCategories", REVIEW_LISTING_DIR_LO_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Withdrawal request")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_WITHDRAWAL_REQ_LO_TASK,
                        "name", "Review withdrawal request - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", REVIEW_WITHDRAWAL_REQ_LO_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Rule 27 request")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_RULE27_REQ_LO_TASK,
                        "name", "Review Rule 27 request - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", REVIEW_RULE27_REQ_LO_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_LIST_CASE_LO_TASK,
                        "name", "Review List Case - Legal Officer",
                        "workingDaysAllowed", 1,
                        "processCategories", REVIEW_LIST_CASE_LO_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case (within 5 days)")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_LIST_CASE_WITHIN_5DAYS_LO_TASK,
                        "name", "Review list case (within 5 days) - Legal Officer",
                        "workingDaysAllowed", 1,
                        "processCategories", REVIEW_LIST_CASE_WITHIN_5DAYS_LO_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Postponement request")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_POSTPONEMENT_REQ_LO_TASK,
                        "name", "Review Postponement request - Legal Officer",
                        "workingDaysAllowed", 1,
                        "processCategories", REVIEW_POSTPONEMENT_REQ_LO_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_LO
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
                        "workType", DECISION_WORK_TYPE,
                        "taskId", REVIEW_REINSTATEMENT_REQ_LO_TASK,
                        "workingDaysAllowed", 5,
                        "processCategories", REVIEW_REINSTATEMENT_REQ_LO_TASK,
                        "roleCategory", ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "*",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Other")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_OTHER_REQ_LO_TASK,
                        "name", "Review Reinstatement request - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", REVIEW_OTHER_REQ_LO_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_LO
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case (within 5 days)")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_LIST_CASE_WITHIN_5DAYS_JUDGE_TASK,
                        "name", "Review list case (within 5 days) - Judge",
                        "workingDaysAllowed", 1,
                        "processCategories", REVIEW_LIST_CASE_WITHIN_5DAYS_JUDGE_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Postponement request")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_POSTPONEMENT_REQ_JUDGE_TASK,
                        "name", "Review Postponement request - Judge",
                        "workingDaysAllowed", 1,
                        "processCategories", REVIEW_POSTPONEMENT_REQ_JUDGE_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Corrections")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_CORRECTIONS_REQ_TASK,
                        "name", "Review Corrections request",
                        "workingDaysAllowed", 1,
                        "processCategories", REVIEW_CORRECTIONS_REQ_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Written reasons request")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_WRITTEN_REASONS_REQ_TASK,
                        "name", "Review Written Reasons request",
                        "workingDaysAllowed", 28,
                        "processCategories", REVIEW_WRITTEN_REASONS_REQ_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Reinstatement request")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_REINSTATEMENT_REQ_JUDGE_TASK,
                        "name", "Review Reinstatement request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", REVIEW_REINSTATEMENT_REQ_JUDGE_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Set aside request")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_SET_ASIDE_REQ_TASK,
                        "name", "Review Set Aside request",
                        "workingDaysAllowed", 2,
                        "processCategories", REVIEW_SET_ASIDE_REQ_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Stay request")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_STAY_REQ_JUDGE_TASK,
                        "name", "Review stay request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", REVIEW_STAY_REQ_JUDGE_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "New case")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_NEW_CASE_PROVIDE_DIR_JUDGE_TASK,
                        "name", "Review new case and provide directions - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", REVIEW_NEW_CASE_PROVIDE_DIR_JUDGE_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "*",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Other")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_OTHER_REQ_JUDGE_TASK,
                        "name", "Review other request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", REVIEW_OTHER_REQ_JUDGE_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Withdrawal request")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_WITHDRAWAL_REQ_JUDGE_TASK,
                        "name", "Review withdrawal request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", REVIEW_WITHDRAWAL_REQ_JUDGE_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Rule 27 request")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_RULE27_REQ_JUDGE_TASK,
                        "name", "Review Rule 27 request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", REVIEW_RULE27_REQ_JUDGE_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listing directions")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_LISTING_DIR_JUDGE_TASK,
                        "name", "Review listing directions - Judge",
                        "workingDaysAllowed", 1,
                        "processCategories", REVIEW_LISTING_DIR_JUDGE_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_LIST_CASE_JUDGE_TASK,
                        "name", "Review List Case - Judge",
                        "workingDaysAllowed", 1,
                        "processCategories", REVIEW_LIST_CASE_JUDGE_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Strike out request")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_STRIKE_OUT_REQ_JUDGE_TASK,
                        "name", "Review Strike out request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", REVIEW_STRIKE_OUT_REQ_JUDGE_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Time Extension request")),
                List.of(
                    Map.of(
                        "taskId", REVIEW_TIME_EXT_REQ_JUDGE_TASK,
                        "name", "Review Time extention request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", REVIEW_TIME_EXT_REQ_JUDGE_TASK,
                        "workType", DECISION_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_JUDICIAL
                    )
                )
            ),
            Arguments.of(
                "caseworker-send-order",
                "CaseManagement",
                null,
                List.of(
                    Map.of(
                        "taskId", FOLLOW_UP_NONCOMPLIANCE_OF_DIR_TASK,
                        "name", "Follow up noncompliance of directions",
                        "workingDaysAllowed", 1,
                        "processCategories", FOLLOW_UP_NONCOMPLIANCE_OF_DIR_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "citizen-cic-update-dss-application",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Evidence request")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_FURTHER_EVIDENCE_TASK,
                        "name", "Process further evidence",
                        "workingDaysAllowed", 10,
                        "processCategories", PROCESS_FURTHER_EVIDENCE_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "caseworker-document-management",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Evidence request")),
                List.of(
                    Map.of(
                        "taskId", PROCESS_FURTHER_EVIDENCE_TASK,
                        "name", "Process further evidence",
                        "workingDaysAllowed", 10,
                        "processCategories", PROCESS_FURTHER_EVIDENCE_TASK,
                        "workType", ROUTINE_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    )
                )
            ),
            Arguments.of(
                "caseworker-record-listing",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Hearing Bundle")),
                List.of(
                    Map.of(
                        "taskId", COMPLETE_HEARING_OUTCOME_TASK,
                        "name", "Complete Hearing Outcome",
                        "workingDaysAllowed", 5,
                        "processCategories", COMPLETE_HEARING_OUTCOME_TASK,
                        "workType", HEARING_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
                    ),
                    Map.of(
                        "taskId", STITCH_COLLATE_HEARING_BUNDLE_TASK,
                        "name", "Stitch/collate hearing bundle",
                        "workingDaysAllowed", 1,
                        "processCategories", STITCH_COLLATE_HEARING_BUNDLE_TASK,
                        "workType", HEARING_WORK_TYPE,
                        "roleCategory", ROLE_CATEGORY_ADMIN
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
