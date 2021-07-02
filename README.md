
# Xero Java Custom Connections Starter
This Java project contains the code necessary to use the Xero-Java SDK and complete Custom Connection flow

Note: this project was built & tested using [Visual  Studio Code](https://code.visualstudio.com/) and [Apache Tomcat 9.x](http://tomcat.apache.org/) server.

## Create a Xero App
To obtain your API keys, follow these steps and create a Xero app

* Create a [free Xero user account](https://www.xero.com/us/signup/api/) (if you don't have one)
* Login to [Xero developer center](https://developer.xero.com/myapps)
* Click "New App" link
* Select Custom connection
* Enter your App name, company url
* Agree to terms and condition and click "Create App".
* On the "Authorisation details" page, select the scopes for your app and designate the authorised user.
* Click "Save and connect" button.
* Authorise the connection from the email sent to the designated user
* Back in "My Apps" > "App details", click "Generate a secret" button.
* Copy your client id and client secret and save for use later.

## Add your API keys to this app
You'll need to set the *clientId, clientSecret* in the following files

* Authorization.java
* TokenRefresh.java

## Build and deploy
Compile your app and deploy to a server (tomcat, etc)
```sh
mvn clean install
```

Deploy on Tomcat or other Java server.

## License

This software is published under the [MIT License](http://en.wikipedia.org/wiki/MIT_License).

	Copyright (c) 2019 Xero Limited

	Permission is hereby granted, free of charge, to any person
	obtaining a copy of this software and associated documentation
	files (the "Software"), to deal in the Software without
	restriction, including without limitation the rights to use,
	copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the
	Software is furnished to do so, subject to the following
	conditions:

	The above copyright notice and this permission notice shall be
	included in all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
	EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
	OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
	NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
	HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
	WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
	OTHER DEALINGS IN THE SOFTWARE.