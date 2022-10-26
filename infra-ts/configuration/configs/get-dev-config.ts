import {IConfig} from "../config.type";

export function getDevConfig(): IConfig {
    return {
        environment: "dev",
        tableName: "Person",
        awsAccount: "CHANGE_ME",
        awsRegion: "CHANGE_ME"
    };
}