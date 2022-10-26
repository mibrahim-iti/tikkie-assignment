import { IProcessVariables } from "./config.type";
import { getConfig } from "./configs/get-config";

export const config = getConfig(process.env as unknown as IProcessVariables);