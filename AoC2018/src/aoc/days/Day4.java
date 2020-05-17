package aoc.days;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aoc.Day;

public class Day4 extends Day<Integer, Integer> {
	public static void main(String[] args) {
		(new Day4()).run(4);
	}
	
	private String findRegex(String regex, String value) {
		Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(value);
	    if(!matcher.find()) return null;
	    return matcher.group();
	}
	
	private class Activity implements Comparable<Activity>{
		String activity;
		Date date;
		int guardNo;
		SimpleDateFormat df;
		public Activity(String date, int guardNo, String activity) throws ParseException {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			this.date = df.parse(date);
			this.guardNo = guardNo;
			this.activity = activity;
		}
		
		public String toString() {
			return "Timestamp: "+df.format(date)+" Guard: #"+guardNo+" activity: "+activity;
		}
		
		@Override
		public int compareTo(Activity o) {
			return this.date.compareTo(o.date);
		}
	}
	
	private class GuardStats{
		Map<Integer, Integer> sleepTime = new HashMap<>();
		Map<Integer, int[]> minsOccurence = new HashMap<>();
	}
	
	private List<Activity> parseActivities(String input) throws ParseException{
		String[] lines = input.split("\n");
		List<Activity> activities = new ArrayList<>();
		for(String row : lines) {
			String timestamp = findRegex("[0-9]+-.+:[0-9]+", row);
			int guardNo = findRegex("#\\d+", row) == null ? 0 : Integer.parseInt(findRegex("#\\d+", row).substring(1));
			String activity = findRegex("[a-z]{2,}\\s[a-z]{2,}", row);
			activities.add(new Activity(timestamp, guardNo, activity));
		}
		Collections.sort(activities); // sort by dates
		return activities;
	}
	
	private GuardStats getGuardStats(String input) throws ParseException {
		List<Activity> activities = parseActivities(input);
		GuardStats gs = new GuardStats();
		int currGuardNo = 0; 
		
		for(int i=0; i<activities.size(); i++) {
			Activity a = activities.get(i);
			if(a.guardNo != 0) currGuardNo = a.guardNo;
			else a.guardNo = currGuardNo;
			
			if(a.activity.equals("falls asleep")) {
				@SuppressWarnings("deprecation")
				int fallAsleep = a.date.getMinutes();
				@SuppressWarnings("deprecation")
				int wokeUp = activities.get(i+1).date.getMinutes();
				
				int[] mins = gs.minsOccurence.get(a.guardNo);
				if(mins == null) mins = new int[59];
				
				for(int j=fallAsleep; j<wokeUp; j++) mins[j]++;
				gs.minsOccurence.put(a.guardNo, mins);
				
				gs.sleepTime.put(
						a.guardNo, gs.sleepTime.get(a.guardNo)==null ?
						wokeUp - fallAsleep :
							gs.sleepTime.get(a.guardNo)+(wokeUp - fallAsleep));
			}
		}
		return gs;
	}
	
	@Override
	protected Integer A(String input) throws ParseException {
		
		GuardStats gs = getGuardStats(input);
		Map.Entry<Integer, Integer> guardMaxSleepTime = null;
		for(Map.Entry<Integer, Integer> guard : gs.sleepTime.entrySet())
			if(guardMaxSleepTime == null || guard.getValue().compareTo(guardMaxSleepTime.getValue()) > 0)
				guardMaxSleepTime = guard;
		
		int[] guardMaxMinsOccurence = gs.minsOccurence.get(guardMaxSleepTime.getKey());
		int mostSleptMinsOccur = 0, mostSleptMins = 0; 
		for(int i=0; i<guardMaxMinsOccurence.length; i++)
			if(mostSleptMins<guardMaxMinsOccurence[i])
			{
				mostSleptMins = guardMaxMinsOccurence[i];
				mostSleptMinsOccur = i;
			}
		
		return guardMaxSleepTime.getKey() * mostSleptMinsOccur;
	}

	@Override
	protected Integer B(String input) throws ParseException {
		GuardStats gs = getGuardStats(input);
	
		int highestMins =0;
		int highestInt = 0;
		int highestGuard = 0;
		for(Map.Entry<Integer, int[]> guard : gs.minsOccurence.entrySet()) {
			for(int i=0; i<guard.getValue().length; i++)
				if(guard.getValue()[i]>highestMins) {
					highestMins = guard.getValue()[i];
					highestInt = i;
					highestGuard = guard.getKey();
				}
		}
		
		return highestGuard*highestInt;
	}

	@Override
	protected void test() {
		// TODO Auto-generated method stub
		
	}
	
}
