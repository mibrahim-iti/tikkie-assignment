import {aws_dynamodb as dynamodb, RemovalPolicy, Stack} from "aws-cdk-lib";
import {DynamoTableProperties} from "./dynamo-table-properties.type";

export class DynamoTable extends dynamodb.Table {
    constructor(stack: Stack, id: string, dynamoTableProperties: DynamoTableProperties) {
        super(stack, id, {
            tableName: dynamoTableProperties.environment + '-' + dynamoTableProperties.tableName,
            partitionKey: dynamoTableProperties.partitionKey,
            sortKey: dynamoTableProperties.sortKey,
            billingMode: dynamodb.BillingMode.PAY_PER_REQUEST,
            removalPolicy: RemovalPolicy.DESTROY
        });

        if (dynamoTableProperties.globalSecondaryIndex !== null) {
            this.addGlobalSecondaryIndex(dynamoTableProperties.globalSecondaryIndex);
        }
    }
}