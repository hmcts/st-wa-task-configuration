# st-wa-task-configuration

[![Build Status](https://travis-ci.org/hmcts/wa-task-configuration-template.svg?branch=master)](https://travis-ci.org/hmcts/wa-task-configuration-template)

## Notes

Since Spring Boot 2.1 bean overriding is disabled. If you want to enable it you will need to set `spring.main.allow-bean-definition-overriding` to `true`.

JUnit 5 is now enabled by default in the project. Please refrain from using JUnit4 and use the next generation

## Importing DMNS and BPMNs to Camunda
You must have the camunda-bpm started locally.
To import the existing DMNS and BPMNs files which are located in /src/main/resources
run the following command:

```bash
  ./scripts/camunda-deployment.sh
```

Important: The DMNs file name must match the pattern of wa-task-{initiation/cancellation/configuration}-{juridisctionId}-{caseType}
Also note that the tasks can only be started when both the initiation and cancellation DMNs are imported.

When they are successfully imported you should be able to view them in the Camunda Cockpit
via http://localhost:8080/app/cockpit/default/#/dashboard

## Building and deploying the application


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

