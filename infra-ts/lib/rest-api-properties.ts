import {LambdaFunction} from "./lambda-function";

export interface RestApiProperties {
    handler: LambdaFunction,
    httpMethod: string,
    resourcesForPath: string[],
    outputId: string
}