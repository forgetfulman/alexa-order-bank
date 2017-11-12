# Alexa and the Order Bank

## Contents
This projects provides the Java AWS Lambda function to support the Order Bank Information Service Alexa Skill.

The following components are included:

- **connect package**: contains a HTTP client used for connecting to the Order Bank Elastic Search instance.
- **intent package**: contains the intent module used to bind intents to their associated action. Each action asks a question 
of the Order Bank and formats the response for returning to an Alexa enabled device.
- **orderbank package**: used to format the queries sent to the ElasticSearch knowledge base and parse the response.
- **orderbankquestions package**: the questions that the intent actions can ask of the Order Bank.
- **speechassests package**: contains the intent schema and example utterances for accessing the Alexa skill.

## Usage
The build process "buildZip" produces a package to be deployed as an Amazon Lambda service.


## Resources
Links to the Amazon Alexa documentation:

- [Using the Alexa Skills Kit Samples](https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/using-the-alexa-skills-kit-samples)
- [Getting Started](https://developer.amazon.com/appsandservices/solutions/alexa/alexa-skills-kit/getting-started-guide)
- [Invocation Name Guidelines](https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/choosing-the-invocation-name-for-an-alexa-skill)
- [Developing an Alexa Skill as an AWS Lambda Function](https://developer.amazon.com/appsandservices/solutions/alexa/alexa-skills-kit/docs/developing-an-alexa-skill-as-a-lambda-function)

The following link is to a nice base AWS Lambda project, that uses injection with Google Guise for Alexa intent handling: 

- [Template AWS Lamabda Project for Alexa](https://github.com/ardetrick/amazon-alexa-skill-starter-java)
