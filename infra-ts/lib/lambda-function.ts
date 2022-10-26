import {aws_lambda as lambda, Duration, Stack} from "aws-cdk-lib";
import {RetentionDays} from "aws-cdk-lib/aws-logs";
import * as path from "path";
import {Construct} from "constructs";

export class LambdaFunction extends lambda.Function {
    constructor(scope: Construct, id: string, props: { handler: string, dynamoTableName: string, region: string }) {
        super(scope, id, {
            runtime: lambda.Runtime.JAVA_11,
            handler: props.handler,
            code: lambda.Code.fromAsset(path.join(__dirname, '..', '..', 'tikkie-functions', 'target', 'tikkie-functions.jar')),
            memorySize: 1024,
            timeout: Duration.seconds(10),
            logRetention: RetentionDays.ONE_WEEK,
            environment: {
                REGION: Stack.of(scope).region,
                AVAILABILITY_ZONES: JSON.stringify(
                    Stack.of(scope).availabilityZones,
                ),
                PERSON_DYNAMODB_TABLE_NAME: props.dynamoTableName,
                CONFIG_REGION: props.region
            }
        });

    }
}
