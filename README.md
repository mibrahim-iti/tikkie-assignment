# Tikkie Assignment
This is a lambda function maven project providing create,read and delete operations into DynamoDB table, 
and it has IaC module create using AWS CDK typescript to allow easy deployment into AWS services

### 1- How to BUILD & DEPLOY the AWS stack
#### _Prerequisites:_
* Maven and JDK 11, NodeJs, Node Package Manager (npm), TypeScript compiler (tsc) and AWS CDK installed and properly configured.
  build-and-deploy.sh

> __I assume you have linux or mac machine like I do.__ 
* Clone the project into your machine.
* Open terminal.
* Change directory (`cd`) to the project folder.
* Make sure to edit configurations in that path infra-ts/configuration/configs/[**get-dev-config.ts OR get-prod-config.ts**] for **dev or prod** and add your aws _account id_ and _region_.
* Make sure you are under infra-ts directory or change directory to infra-ts, and run (`npm install && tsc`)
* Change directory back to main project folder **tikkie-assignment** (`cd ..`)
* Open _build-and-deploy.sh_ and _destroy.sh_ then change the **ENV** if you want (by default it's configured to _dev_) and the profile name by replacing **CHANGE_ME** word by your configured profile in ~/.aws/config and make sure to add the correct credentials to ~/.aws/credentials

Example:

**~/.aws/config**
```  
[profile aws-test]
region = us-east-1
output = json 
```
**~/.aws/credentials**
```  
[aws-test]
aws_access_key_id = CHANGE_ME
aws_secret_access_key = CHANGE_ME
```

* Give execute permission to `chmod 777 build-and-deploy.sh`
(this is bash script file which build the project and deploy it to your aws configured account).
* Run the next command in your terminal `./build-and-deploy.sh`

### 2- _How to configure APIs in Postman to play with it:_
After success deploy you will see links in the terminal for each lambda function (PersonCreateURL, FindPersonByIdURL, ListPersonURL and DeletePersonByIdURL)

Copy the links and put it into **tikkie-assignment/postman/Tikkie.postman_environment.json** mapping each link provided
until _/person_ in the path, so copy the _base https link_ and _environment_ only (for example https://xxxxxxxxxx.execute-api.us-east-1.amazonaws.com/dev) and replace it with CHANGE_ME(With multi E letter)
inside environment json file.

**Note:** There will be 4 different links, one for each API (create, read and delete) operations, so make sure to copy the correct one for each (create, read and delete) operation and map it correctly to postman environment variable_

And now you can import **Tikkie.postman_environment.json** and **Tikkie.postman_collection.json** into your Postman and play with services.

### 3- How to DESTROY the AWS stack
* Make sure you are under **infra-ts** folder or change directory to **infra-ts**
* Give execute permission to `**`chmod 777 destroy.sh`**`
* Run the next command in your terminal `./destroy.sh`

