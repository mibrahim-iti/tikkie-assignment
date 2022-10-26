import {Stack, StackProps} from 'aws-cdk-lib';
import {Construct} from 'constructs';
import {AttributeType} from 'aws-cdk-lib/aws-dynamodb';
import {DynamoTable} from "./dynamo-table";
import {LambdaFunction} from "./lambda-function";
import {IConfig} from "../configuration/config.type";
import {LambdaFunctionRestApi} from "./lambda-function-rest-api";

export class InfraTsStack extends Stack {
    constructor(scope: Construct, id: string, config: IConfig, props?: StackProps) {
        super(scope, id, props);

        const dynamoTable = new DynamoTable(this, 'TikkieDynamoTable', {
            tableName: config.tableName,
            environment: config.environment,
            partitionKey: {
                name: 'id',
                type: AttributeType.STRING
            }, sortKey: {
                name: 'fullName',
                type: AttributeType.STRING
            },
            globalSecondaryIndex: {
                indexName: 'full-name-gsi',
                partitionKey: {
                    name: 'fullName',
                    type: AttributeType.STRING
                }
            }
        });

        const createNewPersonFunction = new LambdaFunction(this, 'createNewPersonFunction', {
            handler: 'com.tikkie.functions.CreatePersonFunction::handleRequest',
            dynamoTableName: dynamoTable.tableName,
            region: config.awsRegion
        });

        dynamoTable.grantFullAccess(createNewPersonFunction);

        const findPersonByIdFunction = new LambdaFunction(this, 'findPersonByIdFunction', {
            handler: 'com.tikkie.functions.FindPersonByIdFunction::handleRequest',
            dynamoTableName: dynamoTable.tableName,
            region: config.awsRegion
        });

        dynamoTable.grantReadData(findPersonByIdFunction);

        const deletePersonByIdFunction = new LambdaFunction(this, 'deletePersonByIdFunction', {
            handler: 'com.tikkie.functions.DeletePersonByIdFunction::handleRequest',
            dynamoTableName: dynamoTable.tableName,
            region: config.awsRegion
        });

        dynamoTable.grantReadWriteData(deletePersonByIdFunction);

        const listPersonFunction = new LambdaFunction(this, 'listPersonFunction', {
            handler: 'com.tikkie.functions.ListPersonFunction::handleRequest',
            dynamoTableName: dynamoTable.tableName,
            region: config.awsRegion
        });

        dynamoTable.grantReadData(listPersonFunction);

        new LambdaFunctionRestApi(this, 'createNewPersonFunctionRestApi', config, {
            handler: createNewPersonFunction,
            httpMethod: 'POST',
            resourcesForPath: ['person'],
            outputId: 'PersonCreateURL'
        });

        new LambdaFunctionRestApi(this, 'findPersonByIdFunctionRestApi', config, {
            handler: findPersonByIdFunction,
            httpMethod: 'GET',
            resourcesForPath: ['person', '{id}'],
            outputId: 'FindPersonByIdURL'
        });

        new LambdaFunctionRestApi(this, 'deletePersonByIdFunctionRestApi', config, {
            handler: deletePersonByIdFunction,
            httpMethod: 'DELETE',
            resourcesForPath: ['person', '{id}'],
            outputId: 'DeletePersonByIdURL'
        });

        new LambdaFunctionRestApi(this, 'listPersonFunctionRestApi', config, {
            handler: listPersonFunction,
            httpMethod: 'GET',
            resourcesForPath: ['person'],
            outputId: 'ListPersonURL'
        });

    }
}
