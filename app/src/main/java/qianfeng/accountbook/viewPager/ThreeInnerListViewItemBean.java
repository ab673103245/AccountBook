package qianfeng.accountbook.viewPager;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class ThreeInnerListViewItemBean {

    private String date;
    private String id;

    public ThreeInnerListViewItemBean(String date, String id) {
        this.date = date;
        this.id = id;
    }

    public ThreeInnerListViewItemBean() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ThreeInnerListViewItemBean{" +
                "date='" + date + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
