package yejiangxia.lingting.BrokenLine;

/**
 * Created by asus-pc on 2017/7/12.
 */

public class BrokenLineCusVisit {

    //拜访日期
    private long time;
    private String date;
    //拜访数量
    private int data;

    public BrokenLineCusVisit() {

    }

    public BrokenLineCusVisit(BrokenLineCusVisit b){
        this.date = b.date;
        this.data = b.data;
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
