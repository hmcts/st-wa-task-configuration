ARG APP_INSIGHTS_AGENT_VERSION=2.5.1

# Application image

FROM hmctspublic.azurecr.io/base/java:21-distroless

COPY lib/AI-Agent.xml /opt/app/
COPY build/libs/st-wa-task-configuration.jar /opt/app/

EXPOSE 4551
CMD [ "st-wa-task-configuration.jar" ]
