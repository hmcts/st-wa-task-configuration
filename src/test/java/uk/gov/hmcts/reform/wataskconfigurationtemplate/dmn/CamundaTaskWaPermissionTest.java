package uk.gov.hmcts.reform.wataskconfigurationtemplate.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableInputImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableOutputImpl;
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
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTable.WA_TASK_PERMISSIONS_ST_CIC_CRIMINALINJURIESCOMPENSATION;

class CamundaTaskWaPermissionTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_PERMISSIONS_ST_CIC_CRIMINALINJURIESCOMPENSATION;
    }

    static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            Arguments.of(
                "processCaseWithdrawalDirections",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "processRule27Decision",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "processListingDirections",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "processDirectionsReListedCase",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "processDirectionsReListedCaseWithin5Days",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "processSetAsideDirections",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "processCorrections",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "processDirectionsReturned",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "processPostponementDirections",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "processTimeExtensionDirectionsReturned",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "processReinstatementDecisionNotice",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "processOtherDirectionsReturned",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "processWrittenReasons",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "processStrikeOutDirectionsReturned",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "processStayDirections",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "issueDecisionNotice",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "completeHearingOutcome",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "referCase",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "vetNewCaseDocuments",
                "someCaseData",
                defaultAdminPermissions()
            ),
            Arguments.of(
                "reviewNewCaseAndProvideDirectionsLO",
                "someCaseData",
                defaultLegalOperationsPermissions()
            ),
            Arguments.of(
                "reviewTimeExtensionRequestLO",
                "someCaseData",
                defaultLegalOperationsPermissions()
            ),
            Arguments.of(
                "reviewStrikeOutRequestLO",
                "someCaseData",
                defaultLegalOperationsPermissions()
            ),
            Arguments.of(
                "reviewStayRequestLO",
                "someCaseData",
                defaultLegalOperationsPermissions()
            ),
            Arguments.of(
                "reviewListingDirectionsLO",
                "someCaseData",
                defaultLegalOperationsPermissions()
            ),
            Arguments.of(
                "reviewWithdrawalRequestLO",
                "someCaseData",
                defaultLegalOperationsPermissions()
            ),
            Arguments.of(
                "reviewRule27RequestLO",
                "someCaseData",
                defaultLegalOperationsPermissions()
            ),
            Arguments.of(
                "reviewListCaseLO",
                "someCaseData",
                defaultLegalOperationsPermissions()
            ),
            Arguments.of(
                "reviewOtherRequestLO",
                "someCaseData",
                defaultLegalOperationsPermissions()
            ),
            Arguments.of(
                "reviewListCaseWithin5DaysLO",
                "someCaseData",
                defaultLegalOperationsPermissions()
            ),
            Arguments.of(
                "reviewPostponementRequestLO",
                "someCaseData",
                defaultLegalOperationsPermissions()
            ),
            Arguments.of(
                "reviewReinstatementRequestLO",
                "someCaseData",
                defaultLegalOperationsPermissions()
            ),
            Arguments.of(
                "reviewListCaseWithin5DaysJudge",
                "someCaseData",
                defaultJudicialPermissions()
            ),
            Arguments.of(
                "reviewPostponementRequestJudge",
                "someCaseData",
                defaultJudicialWithOutFeePaidJudgePermissions()
            ),
            Arguments.of(
                "reviewCorrectionsRequest",
                "someCaseData",
                defaultJudicialPermissions()
            ),
            Arguments.of(
                "reviewWrittenReasonsRequest",
                "someCaseData",
                defaultJudicialPermissions()
            ),
            Arguments.of(
                "reviewReinstatementRequestJudge",
                "someCaseData",
                defaultJudicialPermissions()
            ),
            Arguments.of(
                "reviewSetAsideRequest",
                "someCaseData",
                defaultJudicialPermissions()
            ),
            Arguments.of(
                "reviewStayRequestJudge",
                "someCaseData",
                defaultJudicialPermissions()
            ),
            Arguments.of(
                "reviewNewCaseAndProvideDirectionsJudge",
                "someCaseData",
                defaultJudicialPermissions()
            ),
            Arguments.of(
                "reviewOtherRequestJudge",
                "someCaseData",
                defaultJudicialPermissions()
            ),
            Arguments.of(
                "reviewWithdrawalRequestJudge",
                "someCaseData",
                defaultJudicialPermissions()
            ),
            Arguments.of(
                "reviewRule27RequestJudge",
                "someCaseData",
                defaultJudicialPermissions()
            ),
            Arguments.of(
                "reviewListingDirectionsJudge",
                "someCaseData",
                defaultJudicialPermissions()
            ),
            Arguments.of(
                "reviewListCaseJudge",
                "someCaseData",
                defaultJudicialPermissions()
            ),
            Arguments.of(
                "reviewStrikeOutRequestJudge",
                "someCaseData",
                defaultJudicialPermissions()
            ),
            Arguments.of(
                "reviewTimeExtensionRequestJudge",
                "someCaseData",
                defaultJudicialPermissions()
            ),
            Arguments.of(
                "reviewSpecificAccessRequestJudiciary",
                "someCaseData",
                defaultReviewSpecificAccessRequestJudiciaryPermissions()
            ),
            Arguments.of(
                "reviewSpecificAccessRequestLegalOps",
                "someCaseData",
                defaultReviewSpecificAccessRequestLegalOpsPermissions()
            ),
            Arguments.of(
                "reviewSpecificAccessRequestAdmin",
                "someCaseData",
                defaultReviewSpecificAccessRequestAdminPermissions()
            ),
            Arguments.of(
                "reviewSpecificAccessRequestCTSC",
                "someCaseData",
                defaultReviewSpecificAccessRequestCTSCPermissions()
            ),
            Arguments.of(
                "followUpNoncomplianceOfDirections",
                "someCaseData",
                defaultAdminPermissions()
            )
        );
    }

    private static List<Map<String, Object>> defaultAdminPermissions() {
        return List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Own,Claim,Unclaim,Manage,Assign,Unassign,Complete,Cancel",
                "autoAssignable", false
            ),
            Map.of(
                "name", "regional-centre-admin",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                "roleCategory", "ADMIN",
                "assignmentPriority", 1,
                "autoAssignable", false
            ),
            Map.of(
                "name", "regional-centre-team-leader",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel",
                "roleCategory", "ADMIN",
                "assignmentPriority", 2,
                "autoAssignable", false
            )
        );
    }

    private static List<Map<String, Object>> defaultLegalOperationsPermissions() {
        return List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Own,Claim,Unclaim,Manage,Assign,Unassign,Complete,Cancel",
                "autoAssignable", false
            ),
            Map.of(
                "name", "senior-legal-caseworker",
                "value", "Read,Own,Claim,Manage,Assign,Unassign,Complete,Cancel",
                "roleCategory", "LEGAL_OPERATIONS",
                "assignmentPriority", 1,
                "autoAssignable", false
            ),
            Map.of(
                "name", "tribunal-caseworker",
                "value", "Read,Own,Claim,Assign,Unassign,Complete,Cancel",
                "roleCategory", "LEGAL_OPERATIONS",
                "assignmentPriority", 2,
                "autoAssignable", false
            )
        );
    }

    private static List<Map<String, Object>> defaultJudicialPermissions() {
        return List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Own,Claim,Unclaim,Manage,Assign,Unassign,Complete,Cancel",
                "autoAssignable", false
            ),
            Map.of(
                "name", "senior-judge",
                "value", "Read,Execute,Own,Claim,Manage,Assign,Unassign,Complete,Cancel",
                "roleCategory", "JUDICIAL",
                "assignmentPriority", 1,
                "autoAssignable", false
            ),
            Map.of(
                "name", "judge",
                "value", "Read,Execute,Own,Claim,Assign,Unassign,Complete,Cancel",
                "roleCategory", "JUDICIAL",
                "assignmentPriority", 2,
                "autoAssignable", false
            ),
            Map.of(
                "name", "fee-paid-judge",
                "value", "Read,Execute,Own,Claim,Manage,Complete",
                "roleCategory", "JUDICIAL",
                "assignmentPriority", 3,
                "autoAssignable", false
            )
        );
    }

    private static List<Map<String, Object>> defaultJudicialWithOutFeePaidJudgePermissions() {
        return List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Own,Claim,Unclaim,Manage,Assign,Unassign,Complete,Cancel",
                "autoAssignable", false
            ),
            Map.of(
                "name", "senior-judge",
                "value", "Read,Execute,Own,Claim,Manage,Assign,Unassign,Complete,Cancel",
                "roleCategory", "JUDICIAL",
                "assignmentPriority", 1,
                "autoAssignable", false
            ),
            Map.of(
                "name", "judge",
                "value", "Read,Execute,Own,Claim,Assign,Unassign,Complete,Cancel",
                "roleCategory", "JUDICIAL",
                "assignmentPriority", 2,
                "autoAssignable", false
            )
        );
    }

    private static List<Map<String, Object>> defaultReviewSpecificAccessRequestJudiciaryPermissions() {
        return List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Own,Claim,Unclaim,Manage,Assign,Unassign,Complete,Cancel",
                "autoAssignable", false
            ),
            Map.of(
                "name", "leadership-judge",
                "value", "Read,Own,Claim,Unclaim,Assign",
                "roleCategory", "JUDICIAL",
                "autoAssignable", false
            )
        );
    }

    private static List<Map<String, Object>> defaultReviewSpecificAccessRequestLegalOpsPermissions() {
        return List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Own,Claim,Unclaim,Manage,Assign,Unassign,Complete,Cancel",
                "autoAssignable", false
            ),
            Map.of(
                "name", "senior-legal-caseworker",
                "value", "Read,Own,Claim,Unclaim,Assign",
                "roleCategory", "LEGAL_OPERATIONS",
                "autoAssignable", false
            )
        );
    }

    private static List<Map<String, Object>> defaultReviewSpecificAccessRequestAdminPermissions() {
        return List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Own,Claim,Unclaim,Manage,Assign,Unassign,Complete,Cancel",
                "autoAssignable", false
            ),
            Map.of(
                "name", "hearing-centre-team-leader",
                "value", "Read,Own,Claim,Unclaim,Assign",
                "roleCategory", "ADMIN",
                "autoAssignable", false
            )
        );
    }

    private static List<Map<String, Object>> defaultReviewSpecificAccessRequestCTSCPermissions() {
        return List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Own,Claim,Unclaim,Manage,Assign,Unassign,Complete,Cancel",
                "autoAssignable", false
            ),
            Map.of(
                "name", "ctsc-team-leader",
                "value", "Read,Own,Claim,Unclaim,Assign",
                "roleCategory", "CTSC",
                "autoAssignable", false
            )
        );
    }

    @ParameterizedTest(name = "task type: {0} case data: {1}")
    @MethodSource("scenarioProvider")
    void given_null_or_empty_inputs_when_evaluate_dmn_it_returns_expected_rules(String taskType,
                                                                                String caseData,
                                                                                List<Map<String, String>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));
        inputVariables.putValue("case", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();

        List<String> inputColumnIds = asList("taskType", "case");
        //Inputs
        assertThat(logic.getInputs().size(), is(2));
        assertThatInputContainInOrder(inputColumnIds, logic.getInputs());
        //Outputs
        List<String> outputColumnIds = asList(
            "caseAccessCategory",
            "name",
            "value",
            "roleCategory",
            "authorisations",
            "assignmentPriority",
            "autoAssignable"
        );
        assertThat(logic.getOutputs().size(), is(7));
        assertThatOutputContainInOrder(outputColumnIds, logic.getOutputs());
        //Rules
        assertThat(logic.getRules().size(), is(12));

    }

    private void assertThatInputContainInOrder(List<String> inputColumnIds, List<DmnDecisionTableInputImpl> inputs) {
        IntStream.range(0, inputs.size())
            .forEach(i -> assertThat(inputs.get(i).getInputVariable(), is(inputColumnIds.get(i))));
    }

    private void assertThatOutputContainInOrder(List<String> outputColumnIds, List<DmnDecisionTableOutputImpl> output) {
        IntStream.range(0, output.size())
            .forEach(i -> assertThat(output.get(i).getOutputName(), is(outputColumnIds.get(i))));
    }
}
