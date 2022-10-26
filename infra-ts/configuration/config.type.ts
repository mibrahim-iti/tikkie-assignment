
export type Environment =
    | "prod"
    | "dev";

export interface IConfig {
    environment: Environment;
    tableName: string;
    awsAccount: string;
    awsRegion: string;
}

export interface IProcessVariables {
    ENV?: Environment;
    TABLE_NAME: string;
}