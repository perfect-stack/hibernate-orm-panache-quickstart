FROM  public.ecr.aws/lambda/java:11

ARG APP_VERSION=$APP_VERSION

ADD target/hibernate-orm-panache-quickstart-${APP_VERSION}-runner.jar /var/task/lib/hibernate-orm-panache-quickstart.jar
ADD target/lib/  /var/task/lib/

CMD ["io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest"]