package yejiangxia.lingting.BrokenLine;

/**
 * Created by asus-pc on 2017/7/12.
 */

public class BrokenLineZanshi {
    //拜访日期
    private String date;
    //拜访数量
    private int data;

    public BrokenLineZanshi() {

    }

    public BrokenLineZanshi(BrokenLineZanshi d) {
        this.data=d.data;
        this.date=d.date;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

}
