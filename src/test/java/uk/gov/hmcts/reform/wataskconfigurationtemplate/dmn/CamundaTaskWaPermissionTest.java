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
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "processRule27Decision",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "processListingDirections",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "processDirectionsReListedCase",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "processDirectionsReListedCaseWithin5Days",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "processSetAsideDirections",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "processCorrections",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "processDirectionsReturned",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "processPostponementDirections",
                "someCaseData",
                defaultAdminWithCompletePermissions()
            ),
            Arguments.of(
                "processFurtherEvidence",
                "someCaseData",
                defaultAdminWithCompletePermissions()
            ),
            Arguments.of(
                "processTimeExtensionDirectionsReturned",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "processReinstatementDecisionNotice",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "processOtherDirectionsReturned",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "processWrittenReasons",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "processStrikeOutDirectionsReturned",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "processStayDirections",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "issueDecisionNotice",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "completeHearingOutcome",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "referCase",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "vetNewCaseDocuments",
                "someCaseData",
                defaultRegionalAdminPermissions()
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
                defaultJudicialPermissions()
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
                defaultSpecificAccessRequestJudicialPermissions()
            ),
            Arguments.of(
                "reviewSpecificAccessRequestLegalOps",
                "someCaseData",
                defaultSpecificAccessRequestLegalOpsPermissions()
            ),
            Arguments.of(
                "reviewSpecificAccessRequestAdmin",
                "someCaseData",
                defaultSpecificAccessRequestAdminPermissions()
            ),
            Arguments.of(
                "reviewSpecificAccessRequestCTSC",
                "someCaseData",
                defaultSpecificAccessRequestCTSCPermissions()
            ),
            Arguments.of(
                "followUpNoncomplianceOfDirections",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "registerNewCase",
                "someCaseData",
                defaultRegionalAdminPermissions()
            ),
            Arguments.of(
                "stitchCollateHearingBundle",
                "someCaseData",
                defaultRegionalAdminPermissions()
            )
        );
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

    private void assertThatInputContainInOrder(List<String> inputColumnIds, List<DmnDecisionTableInputImpl> inputs) {
        IntStream.range(0, inputs.size())
            .forEach(i -> assertThat(inputs.get(i).getInputVariable(), is(inputColumnIds.get(i))));
    }

    private void assertThatOutputContainInOrder(List<String> outputColumnIds, List<DmnDecisionTableOutputImpl> output) {
        IntStream.range(0, output.size())
            .forEach(i -> assertThat(output.get(i).getOutputName(), is(outputColumnIds.get(i))));
    }

    private static List<Map<String, Object>> defaultRegionalAdminPermissions() {
        return List.of(
            taskSupervisorPermissions(),
            regionalCentreAdminPermissions(),
            regionalCentreAdminLeaderPermissions()
        );
    }

    private static List<Map<String, Object>> defaultLegalOperationsPermissions() {
        return List.of(
            taskSupervisorPermissions(),
            seniorTribunalCaseworkerPermissions(),
            tribunalCaseworkerPermissions()
        );
    }

    private static List<Map<String, Object>> defaultJudicialPermissions() {
        return List.of(
            taskSupervisorPermissions(),
            seniorJudgePermissions(),
            judgePermissions()
        );
    }

    private static List<Map<String, Object>> defaultSpecificAccessRequestJudicialPermissions() {
        return List.of(
            taskSupervisorPermissions(),
            leadershipJudgePermissions()
        );
    }

    private static List<Map<String, Object>> defaultSpecificAccessRequestLegalOpsPermissions() {
        return List.of(
            taskSupervisorPermissions(),
            seniorTribunalCaseworkerPermissions()
        );
    }

    private static List<Map<String, Object>> defaultSpecificAccessRequestAdminPermissions() {
        return List.of(
            taskSupervisorPermissions(),
            hearingCentreTeamLeaderPermissions()
        );
    }

    private static List<Map<String, Object>> defaultSpecificAccessRequestCTSCPermissions() {
        return List.of(
            taskSupervisorPermissions(),
            ctscTeamLeaderPermissions()
        );
    }

    private static List<Map<String, Object>> defaultAdminWithCompletePermissions() {
        return List.of(
            taskSupervisorPermissions(),
            regionalCentreAdminWithCompletePermissions(),
            regionalCentreAdminLeaderWithCompletePermissions()
        );
    }

    private static Map<String, Object> taskSupervisorPermissions() {
        return Map.of(
            "name", "task-supervisor",
            "value", "Read,Own,Claim,Unclaim,Manage,Assign,Unassign,Complete,Cancel",
            "autoAssignable", false
        );
    }

    private static Map<String, Object> regionalCentreAdminPermissions() {
        return Map.of(
            "name", "regional-centre-admin",
            "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
            "roleCategory", "ADMIN",
            "assignmentPriority", 1,
            "autoAssignable", false
        );
    }

    private static Map<String, Object> regionalCentreAdminLeaderPermissions() {
        return Map.of(
            "name", "regional-centre-team-leader",
            "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel",
            "roleCategory", "ADMIN",
            "assignmentPriority", 2,
            "autoAssignable", false
        );
    }

    private static Map<String, Object> seniorTribunalCaseworkerPermissions() {
        return Map.of(
            "name", "senior-tribunal-caseworker",
            "value", "Read,Own,Claim,Manage,Assign,Unassign,Complete,Cancel",
            "roleCategory", "LEGAL_OPERATIONS",
            "assignmentPriority", 1,
            "autoAssignable", false
        );
    }

    private static Map<String, Object> tribunalCaseworkerPermissions() {
        return Map.of(
            "name", "tribunal-caseworker",
            "value", "Read,Own,Claim,Assign,Unassign,Complete,Cancel",
            "roleCategory", "LEGAL_OPERATIONS",
            "assignmentPriority", 2,
            "autoAssignable", false
        );
    }

    private static Map<String, Object> seniorJudgePermissions() {
        return Map.of(
            "name", "senior-judge",
            "value", "Read,Execute,Claim,Manage,Assign,Unassign,Complete,Cancel",
            "roleCategory", "JUDICIAL",
            "authorisations", "328",
            "assignmentPriority", 1,
            "autoAssignable", false
        );
    }

    private static Map<String, Object> judgePermissions() {
        return Map.of(
            "name", "judge",
            "value", "Read,Own,Claim,Assign,Unassign,Complete,Cancel",
            "roleCategory", "JUDICIAL",
            "authorisations", "328",
            "assignmentPriority", 2,
            "autoAssignable", false
        );
    }

    private static Map<String, Object> leadershipJudgePermissions() {
        return Map.of(
            "name", "leadership-judge",
            "value", "Read,Own,Claim,Manage,Assign,Unassign,Complete,Cancel",
            "roleCategory", "JUDICIAL",
            "authorisations", "328",
            "assignmentPriority", 1,
            "autoAssignable", false
        );
    }

    private static Map<String, Object> hearingCentreTeamLeaderPermissions() {
        return Map.of(
            "name", "hearing-centre-team-leader",
            "value", "Read,Own,Claim,Manage,Assign,Unassign,Complete,Cancel",
            "roleCategory", "ADMIN",
            "autoAssignable", false
        );
    }

    private static Map<String, Object> ctscTeamLeaderPermissions() {
        return Map.of(
            "name", "ctsc-team-leader",
            "value", "Read,Own,Claim,Manage,Assign,Unassign,Complete,Cancel",
            "roleCategory", "CTSC",
            "autoAssignable", false
        );
    }

    private static Map<String, Object> regionalCentreAdminWithCompletePermissions() {
        return Map.of(
            "name", "regional-centre-admin",
            "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Complete",
            "roleCategory", "ADMIN",
            "assignmentPriority", 1,
            "autoAssignable", false
        );
    }

    private static Map<String, Object> regionalCentreAdminLeaderWithCompletePermissions() {
        return Map.of(
            "name", "regional-centre-team-leader",
            "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel,Complete",
            "roleCategory", "ADMIN",
            "assignmentPriority", 2,
            "autoAssignable", false
        );
    }
}
