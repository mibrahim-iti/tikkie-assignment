import {LambdaRestApi} from "aws-cdk-lib/aws-apigateway";
import {RestApiProperties} from "./rest-api-properties";
import {IConfig} from "../configuration/config.type";
import {CfnOutput, Stack} from "aws-cdk-lib";

export class LambdaFunctionRestApi extends LambdaRestApi {
    constructor(stack: Stack, id: string, config: IConfig, props: RestApiProperties) {
        super(stack, id, {
            handler: props.handler,
            proxy: false,
            deployOptions: {
                stageName: config.environment,
                description: `${config.environment.toUpperCase()} Stage`,
                variables: {ALIAS: config.environment.toUpperCase()}
            }
        })

        let root = this.root;
        props.resourcesForPath.forEach(resourcePath => {
            root = root.resourceForPath(resourcePath);
        });
        root.addMethod(props.httpMethod);

        new CfnOutput(stack, `${props.outputId}`, {
            value: `${props.httpMethod} ${this.url}${props.resourcesForPath.join('/')}`
        });
    }

}

