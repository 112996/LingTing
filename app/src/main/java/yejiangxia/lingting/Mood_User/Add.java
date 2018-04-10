package yejiangxia.lingting.Mood_User;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by asus-pc on 2017/8/30.
 */

public class Add extends BmobObject {

    private String Text;
    private Integer Num;
    private BmobFile Picture;
    private User auther;

    public String getText() {return Text;}

    public void setText(String text) {this.Text = text;}

    public Integer getNum() {return Num;}

    public void setNum(Integer num) {this.Num = num;}

    public BmobFile getPicture() {return Picture;}

    public void setPicture(BmobFile picture) {this.Picture = picture;}

    public User getAuther() {return auther;}

    public void setAuther(User auther) {this.auther = auther;}


}
