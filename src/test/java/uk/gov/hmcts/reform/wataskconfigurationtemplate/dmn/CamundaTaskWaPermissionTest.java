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
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTable.WA_TASK_PERMISSIONS_ST_CIC_CRIMINALJURIESCOMPENSATION;

class CamundaTaskWaPermissionTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_PERMISSIONS_ST_CIC_CRIMINALJURIESCOMPENSATION;
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
            )
        );
    }

    private static List<Map<String, Object>> defaultAdminPermissions() {
        return List.of(
            Map.of(
                "name", "regional-centre-admin",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                "roleCategory", "ADMIN",
                "autoAssignable", false
            ),
            Map.of(
                "name", "regional-centre-team-leader",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel",
                "roleCategory", "ADMIN",
                "autoAssignable", false
            )
        );
    }
    private static List<Map<String, Object>> defaultLegalOperationsPermissions() {
        return List.of(
            Map.of(
                "name", "senior-legal-caseworker",
                "value", "Read,Own,Claim,Manage,Assign,Unassign,Complete,Cancel",
                "roleCategory", "LEGAL_OPERATIONS",
                "autoAssignable", false
            ),
            Map.of(
                "name", "tribunal-caseworker",
                "value", "Read,Own,Claim,Manage,Assign,Unassign,Complete,Cancel",
                "roleCategory", "LEGAL_OPERATIONS",
                "autoAssignable", false
            )
        );
    }

    @ParameterizedTest (name = "task type: {0} case data: {1}")
    @MethodSource ("scenarioProvider")
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
        assertThat(logic.getRules().size(), is(4));

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
