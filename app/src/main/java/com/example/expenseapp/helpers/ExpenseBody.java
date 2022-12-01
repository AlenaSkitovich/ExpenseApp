package com.example.expenseapp.helpers;

public class ExpenseBody {

    private String sum;
    private String name;
    private String time;
    private String day;
    private String month;
    private String year;
    private String login;

    public ExpenseBody(String sum, String name, String time, String day, String month, String year,String login) {
        this.sum = sum;
        this.name = name;
        this.time = time;
        this.day = day;
        this.month = month;
        this.year = year;
        this.login = login;
    }

    public ExpenseBody(String sum, String name, String time) {
        this.sum = sum;
        this.name = name;
        this.time = time;
    }

    public String getSum() {
        return sum;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return "ExpenseBody{" +
                "sum='" + sum + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", day='" + day + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
