import {App} from 'aws-cdk-lib';
import {Template} from 'aws-cdk-lib/assertions';
import {InfraTsStack} from '../lib/infra-ts-stack';
import {IConfig} from "../configuration/config.type";

describe("InfraTsStackTest", () => {
    const config: IConfig = {environment: 'dev', tableName: 'test', awsAccount: 'test', awsRegion: 'test'};
    test('Dynamo Queue Created', () => {
        const app = new App();
        // WHEN
        const stack = new InfraTsStack(app, 'MyTestStack', config);
        // THEN
        const template = Template.fromStack(stack);
        template.resourceCountIs("AWS::DynamoDB::Table", 1);
    });

    test('Lambda Function Created', () => {
        const app = new App();
        // WHEN
        const stack = new InfraTsStack(app, 'MyTestStack', config);
        // THEN
        const template = Template.fromStack(stack);
        template.hasResourceProperties("AWS::Lambda::Function", {
            Handler: "com.tikkie.functions.CreatePersonFunction::handleRequest",
            Runtime: "java11",
        });
        template.hasResourceProperties("AWS::Lambda::Function", {
            Handler: "com.tikkie.functions.FindPersonByIdFunction::handleRequest",
            Runtime: "java11",
        });
        template.hasResourceProperties("AWS::Lambda::Function", {
            Handler: "com.tikkie.functions.DeletePersonByIdFunction::handleRequest",
            Runtime: "java11",
        });
        template.hasResourceProperties("AWS::Lambda::Function", {
            Handler: "com.tikkie.functions.ListPersonFunction::handleRequest",
            Runtime: "java11",
        });
    });

    test('ApiGateway RestApi Created', () => {
        const app = new App();
        // WHEN
        const stack = new InfraTsStack(app, 'MyTestStack', config);
        // THEN
        const template = Template.fromStack(stack);
        template.resourceCountIs("AWS::ApiGateway::RestApi", 4);
    });
});
