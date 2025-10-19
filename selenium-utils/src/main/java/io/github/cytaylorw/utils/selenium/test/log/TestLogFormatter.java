package io.github.cytaylorw.utils.selenium.test.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class TestLogFormatter extends Formatter {
    private static final int CLASS_NAME_LIMIT = 30;
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String format(LogRecord record) {
    	String level = String.format("%-9s", String.format("[%s]",record.getLevel()));
    	
    	String className = record.getSourceClassName();
    	if (className == null) className = record.getLoggerName();

    	// Truncate class name in the middle
    	if (className.length() > CLASS_NAME_LIMIT) {
    	    int keepEnd = CLASS_NAME_LIMIT / 3 * 2;
    	    int keepStart = CLASS_NAME_LIMIT - keepEnd - 3;
    	    className = className.substring(0, keepStart) + "..." + className.substring(className.length() - keepEnd);
    	}

    	// Pad method name to at least 15 characters
    	String methodName = record.getSourceMethodName();
    	if (methodName == null) methodName = "unknown";
    	methodName = String.format("%-15s", methodName); // left-align, pad with spaces

    	String timestamp = dateTimeFormat.format(new Date(record.getMillis()));

    	return String.format("%s %s %s %s - %s%n",
    			level,
    	        timestamp,
    	        className,
    	        methodName,
    	        formatMessage(record));
    }

}
