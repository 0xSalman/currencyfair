A small trade application

####Application Workflow:

	1. Attempt to receive large number of trade messages in JSON format using Akka/MQ
	2. Persist data to mongodb
	3. Read the data back and process it to produce simple aggregates:
		- Top 5 traded currency pairs
		- Number of trades per country and top 5 traded pairs for each country)
	4. Push data to websocket subscribed clients for real time updates on front end
	5. Render a pie chart and global map on front end for provided data

####Technologies/Frameworks used (latest versions):

	Java 7
	Spring: Spring Boot, Spring MVC, Spring Data, Spring Security, Spring Messaging
	JMS/activeMQ
	Akka
	MongoDB
	Websocket
	HTML5
	Twitter Bootstrap
	jQuery
	D3.js

####Notes/Thoughts:

	- For consumer and processor, strategy pattern is employed to use bean name configured in application.properties at run time.
		This adds the flexibility to implement more implementations and switch between them at run time.
		The following keys need to be configured in application.properties (the value should match some bean name that implements the Consumer/Processor interface):
		- app.tradeConsumer
		- app.tradeProcessor


	- Number of akka receiver actors are configured via **app.akkaRoutees** key in application.properties

	- It would be nice to add some animation/transition when rendering global map

	- Integration test is missing

	- It would be interesting to see how much performance would improve if RESTful services were built using
		Akka/Spray and perhaps do away with Spring MVC by having AngularJS based app directly interact with RESTful services

	- Akka actors seems to perform better than MQ/activeMQ. Maybe because activeMQ is embedded

	- In memory database or saving records in batch would improve performance

	- Spring security uses in memory authentication
