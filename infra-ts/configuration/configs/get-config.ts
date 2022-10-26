import {Environment, IConfig, IProcessVariables} from "../config.type";
import {getDevConfig} from "./get-dev-config";
import {getProdConfig} from "./get-prod-config";

export function getConfig(processVariables: IProcessVariables): IConfig {
    const environment: Environment = processVariables.ENV || "dev";
    switch (environment) {
        case "dev":
            return getDevConfig();
        case "prod":
            return getProdConfig();
    }
}