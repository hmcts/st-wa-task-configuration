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
import uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTable;
import uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTableBaseUnitTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CamundaTaskTypesTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_TYPES_WA_WACASETYPE;
    }

    static Stream<Arguments> scenarioProvider() {
        List<Map<String, String>> taskTypes = List.of(
            Map.of(
                "taskTypeId", "processApplication",
                "taskTypeName", "Process Application"
            ),
            Map.of(
                "taskTypeId", "reviewAppealSkeletonArgument",
                "taskTypeName", "Review Appeal Skeleton Argument"
            ),
            Map.of(
                "taskTypeId", "decideOnTimeExtension",
                "taskTypeName", "Decide On Time Extension"
            ),
            Map.of(
                "taskTypeId", "followUpOverdueCaseBuilding",
                "taskTypeName", "Follow-up overdue case building"
            ),
            Map.of(
                "taskTypeId", "attendCma",
                "taskTypeName", "Attend Cma"
            ),
            Map.of(
                "taskTypeId", "reviewRespondentResponse",
                "taskTypeName", "Review Respondent Response"
            ),
            Map.of(
                "taskTypeId", "followUpOverdueRespondentEvidence",
                "taskTypeName", "Follow-up overdue respondent evidence"
            ),
            Map.of(
                "taskTypeId", "FOLLOWUPOVERDUERESPONDENTEVIDENCE",
                "taskTypeName", "Follow-up overdue respondent evidence"
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
        assertThat(logic.getRules().size(), is(8));

    }
}
