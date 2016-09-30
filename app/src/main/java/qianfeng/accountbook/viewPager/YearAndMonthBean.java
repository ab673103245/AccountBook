package qianfeng.accountbook.viewPager;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class YearAndMonthBean {
    private String yearAndMonth;
    private List<DayBean> dayBeanList;


    public YearAndMonthBean(String yearAndMonth, List<DayBean> dayBeanList) {
        this.yearAndMonth = yearAndMonth;
        this.dayBeanList = dayBeanList;
    }

    public YearAndMonthBean() {
    }

    public String getYearAndMonth() {
        return yearAndMonth;
    }

    public void setYearAndMonth(String yearAndMonth) {
        this.yearAndMonth = yearAndMonth;
    }

    public List<DayBean> getDayBeanList() {
        return dayBeanList;
    }

    public void setDayBeanList(List<DayBean> dayBeanList) {
        this.dayBeanList = dayBeanList;
    }
}
