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
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTable.WA_TASK_TYPES_ST_CIC_CRIMINALJURIESCOMPENSATION;

class CamundaTaskTypesTest extends DmnDecisionTableBaseUnitTest {
    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_TYPES_ST_CIC_CRIMINALJURIESCOMPENSATION;
    }

    static Stream<Arguments> scenarioProvider() {
        List<Map<String, String>> taskTypes = List.of(
            Map.of(
                "taskTypeId", "processCaseWithdrawalDirections",
                "taskTypeName", "Process Case Withdrawal Directions"
            ),
            Map.of(
                "taskTypeId", "processRule27Decision",
                "taskTypeName", "Process Rule 27 decision"
            ),
            Map.of(
                "taskTypeId", "processListingDirections",
                "taskTypeName", "Process listing directions"
            ),
            Map.of(
                "taskTypeId", "processDirectionsReListedCase",
                "taskTypeName", "Process directions re. listed case"
            ),
            Map.of(
                "taskTypeId", "processDirectionsReListedCaseWithin5Days",
                "taskTypeName", "Process directions re. listed case (within 5 days)"
            ),
            Map.of(
                "taskTypeId", "processSetAsideDirections",
                "taskTypeName", "Process Set Aside directions"
            ),
            Map.of(
                "taskTypeId", "processCorrections",
                "taskTypeName", "Process corrections"
            ),
            Map.of(
                "taskTypeId", "processDirectionsReturned",
                "taskTypeName", "Process directions returned"
            ),
            Map.of(
                "taskTypeId", "processPostponementDirections",
                "taskTypeName", "Process postponement directions"
            ),
            Map.of(
                "taskTypeId", "processTimeExtensionDirectionsReturned",
                "taskTypeName", "Process time extension directions returned"
            ),
            Map.of(
                "taskTypeId", "processReinstatementDecisionNotice",
                "taskTypeName", "Process Reinstatement decision notice"
            ),
            Map.of(
                "taskTypeId", "processOtherDirectionsReturned",
                "taskTypeName", "Process other directions reurned"
            ),
            Map.of(
                "taskTypeId", "processWrittenReasons",
                "taskTypeName", "Process Written Reasons"
            ),
            Map.of(
                "taskTypeId", "processStrikeOutDirectionsReturned",
                "taskTypeName", "Process strike out directions returned"
            ),
            Map.of(
                "taskTypeId", "processStayDirections",
                "taskTypeName", "Process stay directions"
            ),
            Map.of(
                "taskTypeId", "issueDecisionNotice",
                "taskTypeName", "Issue Decision Notice"
            ),
            Map.of(
                "taskTypeId", "completeHearingOutcome",
                "taskTypeName", "Complete Hearing Outcome"
            ),
            Map.of(
                "taskTypeId", "referCase",
                "taskTypeName", "Refer Case"
            ),
            Map.of(
                "taskTypeId", "vetNewCaseDocuments",
                "taskTypeName", "Vet New Case Documents"
            ),
            Map.of(
                "taskTypeId", "reviewNewCaseAndProvideDirectionsLO",
                "taskTypeName", "Review new case and provide directions - Legal Officer"
            ),
            Map.of(
                "taskTypeId", "reviewTimeExtensionRequestLO",
                "taskTypeName", "Review Time extension request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", "reviewStrikeOutRequestLO",
                "taskTypeName", "Review Strike out request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", "reviewStayRequestLO",
                "taskTypeName", "Review stay request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", "reviewListingDirectionsLO",
                "taskTypeName", "Review listing directions - Legal Officer"
            ),
            Map.of(
                "taskTypeId", "reviewWithdrawalRequestLO",
                "taskTypeName", "Review withdrawal request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", "reviewRule27RequestLO",
                "taskTypeName", "Review Rule 27 request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", "reviewListCaseLO",
                "taskTypeName", "Review List Case - Legal Officer"
            ),
            Map.of(
                "taskTypeId", "reviewOtherRequestLO",
                "taskTypeName", "Review Reinstatement request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", "reviewListCaseWithin5DaysLO",
                "taskTypeName", "Review list case (within 5 days) - Legal Officer"
            ),
            Map.of(
                "taskTypeId", "reviewPostponementRequestLO",
                "taskTypeName", "Review Postponement request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", "reviewReinstatementRequestLO",
                "taskTypeName", "Review Reinstatement request - Legal Officer"
            ),
            Map.of(
                "taskTypeId", "reviewListCaseWithin5DaysJudge",
                "taskTypeName", "Review list case (within 5 days) - Judge"
            ),
            Map.of(
                "taskTypeId", "reviewPostponementRequestJudge",
                "taskTypeName", "Review Postponement request - Judge"
            ),
            Map.of(
                "taskTypeId", "reviewCorrectionsRequest",
                "taskTypeName", "Review Corrections request"
            ),
            Map.of(
                "taskTypeId", "reviewWrittenReasonsRequest",
                "taskTypeName", "Review Written Reasons request"
            ),
            Map.of(
                "taskTypeId", "reviewReinstatementRequestJudge",
                "taskTypeName", "Review Reinstatement request - Judge"
            ),
            Map.of(
                "taskTypeId", "reviewSetAsideRequest",
                "taskTypeName", "Review Set Aside request"
            ),
            Map.of(
                "taskTypeId", "reviewStayRequestJudge",
                "taskTypeName", "Review stay request - Judge"
            ),
            Map.of(
                "taskTypeId", "reviewNewCaseAndProvideDirectionsJudge",
                "taskTypeName", "Review new case and provide directions - Judge"
            ),
            Map.of(
                "taskTypeId", "reviewOtherRequestJudge",
                "taskTypeName", "Review other request - Judge"
            ),
            Map.of(
                "taskTypeId", "reviewWithdrawalRequestJudge",
                "taskTypeName", "Review withdrawal request - Judge"
            ),
            Map.of(
                "taskTypeId", "reviewRule27RequestJudge",
                "taskTypeName", "Review Rule 27 request - Judge"
            ),
            Map.of(
                "taskTypeId", "reviewListingDirectionsJudge",
                "taskTypeName", "Review listing directions - Judge"
            ),
            Map.of(
                "taskTypeId", "reviewListCaseJudge",
                "taskTypeName", "Review List Case - Judge"
            ),
            Map.of(
                "taskTypeId", "reviewStrikeOutRequestJudge",
                "taskTypeName", "Review Strike out request - Judge"
            ),
            Map.of(
                "taskTypeId", "reviewTimeExtensionRequestJudge",
                "taskTypeName", "Review Time extention request - Judge"
            )
        );
        return Stream.of(
            Arguments.of(
                taskTypes
            )
        );
    }

    @ParameterizedTest(name = "retrieve all task type data")
    @MethodSource("scenarioProvider")
    void should_evaluate_dmn_return_all_task_type_fields(List<Map<String, Object>> expectedTaskTypes) {

        VariableMap inputVariables = new VariableMapImpl();
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectedTaskTypes));
    }

    @Test
    void check_dmn_changed() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(1));
        assertThat(logic.getOutputs().size(), is(2));
        assertThat(logic.getRules().size(), is(46));
    }
}
