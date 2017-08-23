Welcome to the AWS CodeStar sample web application
==================================================

This sample code helps get you started with a simple Java web application
deployed by AWS Elastic Beanstalk.

What's Here
-----------

This sample includes:

* README.md - this file
* .ebextensions/ - this directory contains the Java configuration file that
  allows AWS Elastic Beanstalk to deploy your Java application
* buildspec.yml - this file is used by AWS CodeBuild to build the web
  application
* pom.xml - this file is the Maven Project Object Model for the web application
* src/ - this directory contains your Java application source files


Getting Started
---------------

These directions assume you want to develop on your local computer, and not
from the Amazon EC2 instance itself. If you're on the Amazon EC2 instance, the
virtual environment is already set up for you, and you can start working on the
code.

To work on the sample code, you'll need to clone your project's AWS CodeCommit
repository to your local computer. If you haven't, do that first. You can find
instructions in the AWS CodeStar user guide.

* use homebrew to install maven: brew update, brew install maven, brew install tomcat7

1. Build and run the application.

        $ mvn compile
        $ mvn clean package tomcat7:run

2. Open http://localhost:8080/ in a web browser to view your application.

What Do I Do Next?
------------------

Once you have a virtual environment running, you can start making changes to
the sample Java web application. We suggest making a small change to
/src/main/webapp/WEB-INF/views/index.jsp first, so you can see how changes
pushed to your project's repository in AWS CodeCommit are automatically picked
up and deployed to the Amazon EC2 instance by AWS Elastic Beanstalk. (You can
watch the progress on your project dashboard.) Once you've seen how that works,
start developing your own code, and have fun!

Learn more about AWS CodeStar by reading the user guide.  Ask questions or make
suggestions on our forum.

User Guide: http://docs.aws.amazon.com/codestar/latest/userguide/welcome.html

Forum: https://forums.aws.amazon.com/forum.jspa?forumID=248
