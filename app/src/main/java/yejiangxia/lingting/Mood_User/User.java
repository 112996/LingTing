package yejiangxia.lingting.Mood_User;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by asus-pc on 2017/8/27.
 */

public class User extends BmobObject {

    private String UserName;
    private String UserTel;
    private String BownDate;
    private BmobFile Icon;

    public BmobFile getIcon() {return Icon;}

    public void setIcon(BmobFile icon) {this.Icon = icon;}


    public String getBownDate() {return BownDate;}

    public void setBownDate(String bownDate) {
        this.BownDate = bownDate;
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getUserTel() {
        return UserTel;
    }

    public void setUserTel(String userTel) {
        this.UserTel = userTel;
    }


}
