import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy

setupAppenders()
setupLoggers()

def setupAppenders() {

	def patternFormat = "[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%thread] %-5level %logger{10} - %msg%n%rEx";

	appender("STDOUT", ConsoleAppender) {
		encoder(PatternLayoutEncoder) {
			pattern = patternFormat;
		}
	}

	def logDir = System.getProperty("logDir")
	def maxNumFile = numberOfFilesToKeep()
	def maxFileSize = rollingFileSize()

	appender("SPRINGBOOT", RollingFileAppender) {
		file = "${logDir}/app.log"
		rollingPolicy(FixedWindowRollingPolicy) {
			fileNamePattern = "app.%i.log"
			minIndex = 1
			maxIndex = maxNumFile
		}
		triggeringPolicy(SizeBasedTriggeringPolicy)  {
			maxFileSize = maxFileSize
		}
		encoder(PatternLayoutEncoder) {
			pattern = patternFormat;
		}
	}
}

def setupLoggers() {
	logger("com.lucidspring.currencyfair", getLogLevel(), ["SPRINGBOOT"])
//    logger("org.springframework.web.servlet", DEBUG, ["SPRINGBOOT"])
//    logger("org.springframework.data.mongodb.core", DEBUG, ["SPRINGBOOT"])
//	logger("springboot.test", getLogLevel(), ["STDOUT"])
//	root(INFO, ["SPRINGBOOT"])
}

def numberOfFilesToKeep() {
	return isDevelopmentEnv() ? 5 : 50
}

def rollingFileSize() {
	return isDevelopmentEnv() ? "2MB" : "50MB"
}

// logging level in development environments must be debug
def getLogLevel() {
	return isDevelopmentEnv() ? DEBUG : INFO
}

// DEV & TST are development environments
def isDevelopmentEnv() {
	def env =  System.properties["app.env"] ?: "DEV"
	return env == "DEV" || env == "TST"
}