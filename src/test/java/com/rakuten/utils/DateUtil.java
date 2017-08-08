package com.rakuten.utils;

public class DateUtil {

	public static String getMonthName(int month) {
		String monthName = "";
		switch (month) {
		case 1:
			monthName = "Januar";
			break;
		case 2:
			monthName = "Februar";
			break;
		case 3:
			monthName = "März";
			break;
		case 4:
			monthName = "April";
			break;
		case 5:
			monthName = "Mai";
			break;
		case 6:
			monthName = "Juni";
			break;
		case 7:
			monthName = "Juli";
			break;
		case 8:
			monthName = "August";
			break;
		case 9:
			monthName = "September";
			break;
		case 10:
			monthName = "Oktober";
			break;
		case 11:
			monthName = "November";
			break;
		case 12:
			monthName = "Dezember";
			break;
		default:
			monthName = "Januar";
			break;
		}
		return monthName;
	}

	public static int getDayFromDate(String date) {
		String currentDay = date.split("/")[1];
		if (currentDay.startsWith("0")) {
			currentDay = currentDay.replace("0", "");
		}
		int day = Integer.parseInt(currentDay);
		return day;
	}

	public static int getMonthFromDate(String date) {
		String currentMonth = date.split("/")[0];
		if (currentMonth.startsWith("0")) {
			currentMonth = currentMonth.replace("0", "");
		}
		int month = ((Integer.parseInt(currentMonth)));
		return month;
	}

	public static int getYearFromDate(String date) {
		String currentYear = date.split("/")[2];
		return Integer.parseInt(currentYear);
	}

}
