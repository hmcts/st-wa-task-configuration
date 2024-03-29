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
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTable.WA_TASK_INITIATION_ST_CIC_CRIMINALINJURIESCOMPENSATION;

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
                        "taskId", "processCaseWithdrawalDirections",
                        "name", "Process Case Withdrawal Directions",
                        "workingDaysAllowed", 10,
                        "processCategories", "processCaseWithdrawalDirections",
                        "workType", "routine_work",
                        "roleCategory", "ADMIN"
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
                        "roleCategory", "ADMIN"
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
                        "roleCategory", "ADMIN"
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
                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case (within 5 days)")),
                List.of(
                    Map.of(
                        "taskId", "processDirectionsReListedCaseWithin5Days",
                        "name", "Process directions re. listed case (within 5 days)",
                        "workingDaysAllowed", 1,
                        "processCategories", "processDirectionsReListedCaseWithin5Days",
                        "workType", "priority",

                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Set aside request")),
                List.of(
                    Map.of(
                        "taskId", "processSetAsideDirections",
                        "name", "Process Set Aside directions",
                        "workingDaysAllowed", 1,
                        "processCategories", "processSetAsideDirections",
                        "workType", "decision_making_work",
                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Corrections")),
                List.of(
                    Map.of(
                        "taskId", "processCorrections",
                        "name", "Process postponement directions",
                        "workingDaysAllowed", 3,
                        "processCategories", "processCorrections",
                        "workType", "decision_making_work",
                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "New case")),
                List.of(
                    Map.of(
                        "taskId", "processDirectionsReturned",
                        "name", "Process directions returned",
                        "workingDaysAllowed", 10,
                        "processCategories", "processDirectionsReturned",
                        "workType", "decision_making_work",
                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Postponement request")),
                List.of(
                    Map.of(
                        "taskId", "processPostponementDirections",
                        "name", "Process postponement directions",
                        "workingDaysAllowed", 1,
                        "processCategories", "processPostponementDirections",
                        "workType", "decision_making_work",
                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Time extension request")),
                List.of(
                    Map.of(
                        "taskId", "processTimeExtensionDirectionsReturned",
                        "name", "Process time extension directions returned",
                        "workingDaysAllowed", 1,
                        "processCategories", "processTimeExtensionDirectionsReturned",
                        "workType", "decision_making_work",
                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Reinstatement request")),
                List.of(
                    Map.of(
                        "taskId", "processReinstatementDecisionNotice",
                        "name", "Process Reinstatement decision notice",
                        "workingDaysAllowed", 5,
                        "processCategories", "processReinstatementDecisionNotice",
                        "workType", "decision_making_work",
                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "*",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Other")),
                List.of(
                    Map.of(
                        "taskId", "processOtherDirectionsReturned",
                        "name", "Process other directions returned",
                        "workingDaysAllowed", 10,
                        "processCategories", "processOtherDirectionsReturned",
                        "workType", "decision_making_work",
                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Written reasons request")),
                List.of(
                    Map.of(
                        "taskId", "processWrittenReasons",
                        "name", "Process Written Reasons",
                        "workingDaysAllowed", 3,
                        "processCategories", "processWrittenReasons",
                        "workType", "decision_making_work",
                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Strike out request")),
                List.of(
                    Map.of(
                        "taskId", "processStrikeOutDirectionsReturned",
                        "name", "Process strike out directions returned",
                        "workingDaysAllowed", 10,
                        "processCategories", "processStrikeOutDirectionsReturned",
                        "workType", "routine_work",
                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "create-draft-order",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Stay request")),
                List.of(
                    Map.of(
                        "taskId", "processStayDirections",
                        "name", "Process stay directions",
                        "workingDaysAllowed", 10,
                        "processCategories", "processStayDirections",
                        "workType", "routine_work",
                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "create-hearing-summary",
                "AwaitingHearing",
                null,
                List.of(
                    Map.of(
                        "taskId", "issueDecisionNotice",
                        "name", "Issue Decision Notice",
                        "workingDaysAllowed", 1,
                        "processCategories", "issueDecisionNotice",
                        "workType", "hearing_work",
                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "caseworker-record-listing",
                "AwaitingHearing",
                null,
                List.of(
                    Map.of(
                        "taskId", "completeHearingOutcome",
                        "name", "Complete Hearing Outcome",
                        "workingDaysAllowed", 5,
                        "processCategories", "completeHearingOutcome",
                        "workType", "hearing_work",
                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "caseworker-case-built",
                "CaseManagement",
                null,
                List.of(
                    Map.of(
                        "taskId", "referCase",
                        "name", "Refer Case",
                        "workingDaysAllowed", 2,
                        "processCategories", "referCase",
                        "workType", "routine_work",
                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "citizen-cic-submit-dss-application",
                "DssSubmitted",
                null,
                List.of(
                    Map.of(
                        "taskId", "vetNewCaseDocuments",
                        "name", "Vet New Case Documents",
                        "workingDaysAllowed", 5,
                        "processCategories", "vetNewCaseDocuments",
                        "workType", "applications",
                        "roleCategory", "ADMIN"
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "New case")),
                List.of(
                    Map.of(
                        "taskId", "reviewNewCaseAndProvideDirectionsLO",
                        "name", "Review new case and provide directions - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", "reviewNewCaseAndProvideDirectionsLO",
                        "workType", "decision_making_work",
                        "roleCategory", "LEGAL_OPERATIONS"
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Time extension request")),
                List.of(
                    Map.of(
                        "taskId", "reviewTimeExtensionRequestLO",
                        "name", "Review Time extension request - Legal Officer",
                        "workingDaysAllowed", 1,
                        "processCategories", "reviewTimeExtensionRequestLO",
                        "workType", "decision_making_work",
                        "roleCategory", "LEGAL_OPERATIONS"
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Strike out request")),
                List.of(
                    Map.of(
                        "taskId", "reviewStrikeOutRequestLO",
                        "name", "Review Strike out request - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", "reviewStrikeOutRequestLO",
                        "workType", "decision_making_work",
                        "roleCategory", "LEGAL_OPERATIONS"
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Stay request")),
                List.of(
                    Map.of(
                        "taskId", "reviewStayRequestLO",
                        "name", "Review stay request - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", "reviewStayRequestLO",
                        "workType", "decision_making_work",
                        "roleCategory", "LEGAL_OPERATIONS"
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listing directions")),
                List.of(
                    Map.of(
                        "taskId", "reviewListingDirectionsLO",
                        "name", "Review listing directions - Legal Officer",
                        "workingDaysAllowed", 1,
                        "processCategories", "reviewListingDirectionsLO",
                        "workType", "decision_making_work",
                        "roleCategory", "LEGAL_OPERATIONS"
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Withdrawal request")),
                List.of(
                    Map.of(
                        "taskId", "reviewWithdrawalRequestLO",
                        "name", "Review withdrawal request - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", "reviewWithdrawalRequestLO",
                        "workType", "decision_making_work",
                        "roleCategory", "LEGAL_OPERATIONS"
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Rule 27 request")),
                List.of(
                    Map.of(
                        "taskId", "reviewRule27RequestLO",
                        "name", "Review Rule 27 request - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", "reviewRule27RequestLO",
                        "workType", "decision_making_work",
                        "roleCategory", "LEGAL_OPERATIONS"
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case")),
                List.of(
                    Map.of(
                        "taskId", "reviewListCaseLO",
                        "name", "Review List Case - Legal Officer",
                        "workingDaysAllowed", 1,
                        "processCategories", "reviewListCaseLO",
                        "workType", "decision_making_work",
                        "roleCategory", "LEGAL_OPERATIONS"
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case (within 5 days)")),
                List.of(
                    Map.of(
                        "taskId", "reviewListCaseWithin5DaysLO",
                        "name", "Review list case (within 5 days) - Legal Officer",
                        "workingDaysAllowed", 1,
                        "processCategories", "reviewListCaseWithin5DaysLO",
                        "workType", "decision_making_work",
                        "roleCategory", "LEGAL_OPERATIONS"
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Postponement request")),
                List.of(
                    Map.of(
                        "taskId", "reviewPostponementRequestLO",
                        "name", "Review Postponement request - Legal Officer",
                        "workingDaysAllowed", 1,
                        "processCategories", "reviewPostponementRequestLO",
                        "workType", "decision_making_work",
                        "roleCategory", "LEGAL_OPERATIONS"
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Reinstatement")),
                List.of(
                    Map.of(
                        "name", "Review Reinstatement request - Legal Officer",
                        "workType", "decision_making_work",
                        "taskId", "reviewReinstatementRequestLO",
                        "workingDaysAllowed", 5,
                        "processCategories", "reviewReinstatementRequestLO",
                        "roleCategory", "LEGAL_OPERATIONS"
                    )
                )
            ),
            Arguments.of(
                "refer-to-legal-officer",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Other")),
                List.of(
                    Map.of(
                        "taskId", "reviewOtherRequestLO",
                        "name", "Review Reinstatement request - Legal Officer",
                        "workingDaysAllowed", 5,
                        "processCategories", "reviewOtherRequestLO",
                        "workType", "decision_making_work",
                        "roleCategory", "LEGAL_OPERATIONS"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case (within 5 days)")),
                List.of(
                    Map.of(
                        "taskId", "reviewListCaseWithin5DaysJudge",
                        "name", "Review list case (within 5 days) - Judge",
                        "workingDaysAllowed", 1,
                        "processCategories", "reviewListCaseWithin5DaysJudge",
                        "workType", "decision_making_work",
                        "roleCategory", "JUDICIAL"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Postponement request")),
                List.of(
                    Map.of(
                        "taskId", "reviewPostponementRequestJudge",
                        "name", "Review Postponement request - Judge",
                        "workingDaysAllowed", 1,
                        "processCategories", "reviewPostponementRequestJudge",
                        "workType", "decision_making_work",
                        "roleCategory", "JUDICIAL"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Corrections")),
                List.of(
                    Map.of(
                        "taskId", "reviewCorrectionsRequest",
                        "name", "Review Corrections request",
                        "workingDaysAllowed", 1,
                        "processCategories", "reviewCorrectionsRequest",
                        "workType", "decision_making_work",
                        "roleCategory", "JUDICIAL"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Written reasons request")),
                List.of(
                    Map.of(
                        "taskId", "reviewWrittenReasonsRequest",
                        "name", "Review Written Reasons request",
                        "workingDaysAllowed", 28,
                        "processCategories", "reviewWrittenReasonsRequest",
                        "workType", "decision_making_work",
                        "roleCategory", "JUDICIAL"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Reinstatement")),
                List.of(
                    Map.of(
                        "taskId", "reviewReinstatementRequestJudge",
                        "name", "Review Reinstatement request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", "reviewReinstatementRequestJudge",
                        "workType", "decision_making_work",
                        "roleCategory", "JUDICIAL"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseClosed",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Set aside request")),
                List.of(
                    Map.of(
                        "taskId", "reviewSetAsideRequest",
                        "name", "Review Set Aside request",
                        "workingDaysAllowed", 2,
                        "processCategories", "reviewSetAsideRequest",
                        "workType", "decision_making_work",
                        "roleCategory", "JUDICIAL"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Stay request")),
                List.of(
                    Map.of(
                        "taskId", "reviewStayRequestJudge",
                        "name", "Review stay request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", "reviewStayRequestJudge",
                        "workType", "decision_making_work",
                        "roleCategory", "JUDICIAL"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "New case")),
                List.of(
                    Map.of(
                        "taskId", "reviewNewCaseAndProvideDirectionsJudge",
                        "name", "Review new case and provide directions - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", "reviewNewCaseAndProvideDirectionsJudge",
                        "workType", "decision_making_work",
                        "roleCategory", "JUDICIAL"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                null,
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Other")),
                List.of(
                    Map.of(
                        "taskId", "reviewOtherRequestJudge",
                        "name", "Review other request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", "reviewOtherRequestJudge",
                        "workType", "decision_making_work",
                        "roleCategory", "JUDICIAL"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Withdrawal request")),
                List.of(
                    Map.of(
                        "taskId", "reviewWithdrawalRequestJudge",
                        "name", "Review withdrawal request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", "reviewWithdrawalRequestJudge",
                        "workType", "decision_making_work",
                        "roleCategory", "JUDICIAL"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Rule 27 request")),
                List.of(
                    Map.of(
                        "taskId", "reviewRule27RequestJudge",
                        "name", "Review Rule 27 request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", "reviewRule27RequestJudge",
                        "workType", "decision_making_work",
                        "roleCategory", "JUDICIAL"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listing directions")),
                List.of(
                    Map.of(
                        "taskId", "reviewListingDirectionsJudge",
                        "name", "Review listing directions - Judge",
                        "workingDaysAllowed", 1,
                        "processCategories", "reviewListingDirectionsJudge",
                        "workType", "decision_making_work",
                        "roleCategory", "JUDICIAL"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "AwaitingHearing",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Listed case")),
                List.of(
                    Map.of(
                        "taskId", "reviewListCaseJudge",
                        "name", "Review List Case - Judge",
                        "workingDaysAllowed", 1,
                        "processCategories", "reviewListCaseJudge",
                        "workType", "decision_making_work",
                        "roleCategory", "JUDICIAL"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Strike out request")),
                List.of(
                    Map.of(
                        "taskId", "reviewStrikeOutRequestJudge",
                        "name", "Review Strike out request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", "reviewStrikeOutRequestJudge",
                        "workType", "decision_making_work",
                        "roleCategory", "JUDICIAL"
                    )
                )
            ),
            Arguments.of(
                "refer-to-judge",
                "CaseManagement",
                Map.of("Data", Map.of("cicCaseReferralTypeForWA", "Time Extension request")),
                List.of(
                    Map.of(
                        "taskId", "reviewTimeExtensionRequestJudge",
                        "name", "Review Time extention request - Judge",
                        "workingDaysAllowed", 2,
                        "processCategories", "reviewTimeExtensionRequestJudge",
                        "workType", "decision_making_work",
                        "roleCategory", "JUDICIAL"
                    )
                )
            ),
            Arguments.of(
                "caseworker-send-order",
                "CaseManagement",
                null,
                List.of(
                    Map.of(
                        "taskId", "followUpNoncomplianceOfDirections",
                        "name", "Follow up noncompliance of directions",
                        "workingDaysAllowed", 1,
                        "processCategories", "followUpNoncomplianceOfDirections",
                        "workType", "routine_work",
                        "roleCategory", "ADMIN"
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
        assertThat(logic.getRules().size(), is(47));

    }
}
