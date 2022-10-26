import {AttributeType} from "aws-cdk-lib/aws-dynamodb/lib/table";

export type DynamoTableProperties = {
    tableName: string,
    environment: string,
    partitionKey: {
        name: string
        type: AttributeType
    },
    sortKey: {
        name: string
        type: AttributeType
    },
    globalSecondaryIndex: {
        indexName: string,
        partitionKey: {
            name: string,
            type: AttributeType
        }
    }
}