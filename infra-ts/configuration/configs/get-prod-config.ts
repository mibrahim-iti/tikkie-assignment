import {IConfig} from "../config.type";

export function getProdConfig(): IConfig {
    return {
        environment: "prod",
        tableName: "Person",
        awsAccount: "CHANGE_ME",
        awsRegion: "CHANGE_ME"
    };
}