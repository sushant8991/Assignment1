import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Calendar {
	
	private static Calendar self;
	private static java.util.Calendar cal;
	
	
	private Calendar() {
		cal = java.util.Calendar.getInstance();
	}
	
	public static Calendar getInstance() {
		if (self == null) {
			self = new Calendar();
		}
		return self;
	}
	
	public void incrementDate(int days) {
		cal.add(java.util.Calendar.DATE, days);		
	}
	
	public synchronized void setDate(Date date) {
		try {
			cal.setTime(date);
	        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);  
	        cal.set(java.util.Calendar.MINUTE, 0);  
	        cal.set(java.util.Calendar.SECOND, 0);  
	        cal.set(java.util.Calendar.MILLISECOND, 0);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}
	public synchronized Date Date() {
		try {
	        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);  
	        cal.set(java.util.Calendar.MINUTE, 0);  
	        cal.set(java.util.Calendar.SECOND, 0);  
	        cal.set(java.util.Calendar.MILLISECOND, 0);
			return cal.getTime();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}

	public synchronized Date getDueDate(int loanPeriod) {
		Date now = Date();
		cal.add(java.util.Calendar.DATE, loanPeriod);
		Date dueDate = cal.getTime();
		cal.setTime(now);
		return dueDate;
	}
	
	public synchronized long getDaysDifference(Date targetDate) {
		long diffMillis = Date().getTime() - targetDate.getTime();
	    long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);
	    return diffDays;
	}

}
