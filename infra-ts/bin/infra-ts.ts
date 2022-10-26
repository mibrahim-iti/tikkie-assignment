#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import {InfraTsStack} from '../lib/infra-ts-stack';
import {IProcessVariables} from "../configuration/config.type";
import {getConfig} from "../configuration/configs/get-config";

const app = new cdk.App();
const config = getConfig(process.env as unknown as IProcessVariables);

new InfraTsStack(app, 'TikkieAssignmentInfraTsStack', config, {

    env: {account: config.awsAccount, region: config.awsRegion}
});