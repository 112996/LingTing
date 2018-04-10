package yejiangxia.lingting.BrokenLine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/12.
 */

public class BrokenLineCusVisitView extends View {

    private  int width;
    private int height;

    public int Xpoint=50;
    public int Ypoint=height-50;
    public int Xscale;
    public int Yscale;
    public int Xlength;
    public int Ylength;

    //浅紫色背景的画笔
    private Paint mPaint_bg;

    //文本数据的画笔
    private Paint mPaint_xdata;
    private Paint mPaint_ydata;
    private Paint mPaint_linezhou;
    private Paint mPaint_scanle;
    private Paint mPaint_text;
    private Paint mPaint_end;
    //折线圆点的紫色背景
    private Paint mPaint_point_bg;

    private int circle=5;

    //折虚线的画笔
    private Paint mPaint_brokenline;

    //路径
    private Path mpath=new Path();

    //客户拜访的折线（BrokenLineCusVisit）数据
    private List<BrokenLineCusVisit> mdata;

    public BrokenLineCusVisitView(Context context) {
        super(context);
    }

    public BrokenLineCusVisitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        mPaint_bg=new Paint(Paint.ANTI_ALIAS_FLAG);
        //无效的颜色设置
        mPaint_bg.setColor(Color.argb(0x00,0x00,0x00,0x00));

        PathEffect pathEffect = new DashPathEffect(new float[] { 20,10}, 1);
        mPaint_brokenline=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_brokenline.reset();
        mPaint_brokenline.setStyle(Paint.Style.STROKE);
        //两点间相连虚线的颜色
        mPaint_brokenline.setColor(Color.argb(0xff,0x93,0x9d,0xa9));
        mPaint_brokenline.setTextSize(20);
        mPaint_brokenline.setAntiAlias(true);
        mPaint_brokenline.setPathEffect(pathEffect);

        mPaint_point_bg=new Paint(Paint.ANTI_ALIAS_FLAG);
        //圆点颜色
        mPaint_point_bg.setARGB(0xff,0xeb,0x61,0x00);
        mPaint_point_bg.setTextSize(4);


        mPaint_xdata=new Paint(Paint.ANTI_ALIAS_FLAG);
        //X轴日期的颜色
        mPaint_xdata.setColor(Color.argb(0xff,0x6f,0x72,0x76));
        mPaint_xdata.setTextSize(25);
        mPaint_xdata.setTextAlign(Paint.Align.CENTER);

        mPaint_text=new Paint(Paint.ANTI_ALIAS_FLAG);
        //具体次数颜色
        mPaint_text.setColor(Color.argb(0xff,0xa7,0xc9,0xf3));
        mPaint_text.setTextSize(35);

        mPaint_ydata=new Paint(Paint.ANTI_ALIAS_FLAG);

        //Y轴20 40 60 80 的颜色
        mPaint_ydata.setColor(Color.argb(0xff,0x93,0x9d,0xa9));
        mPaint_ydata.setTextAlign(Paint.Align.CENTER);
        mPaint_ydata.setTextSize(30);

        mPaint_linezhou=new Paint(Paint.ANTI_ALIAS_FLAG);
        //坐标轴的颜色
        mPaint_linezhou.setColor(Color.argb(0xff,0x6f,0x72,0x76));
        mPaint_linezhou.setTextSize(10);


        mPaint_end=new Paint(Paint.ANTI_ALIAS_FLAG);
        //Count的显示颜色
        mPaint_end.setColor(Color.argb(0xff,0x8d,0xb1,0xdd));
        mPaint_end.setTextSize(40);
        mPaint_end.setTextAlign(Paint.Align.CENTER);

        mPaint_scanle=new Paint(Paint.ANTI_ALIAS_FLAG);
        //Y轴上刻度的颜色
        mPaint_scanle.setColor(Color.argb(0xff,0xa7,0xc9,0xf3));
        mPaint_scanle.setTextSize(4);
        invalidate();
    }

    public List<BrokenLineCusVisit> getMdata() {
        return mdata;
    }

    public void setMdata(List<BrokenLineCusVisit> mdata) {
        this.mdata = mdata;
        requestLayout();//相当于调用onMeasure方法
        invalidate();//相当于调用onDraw方法
    }

    @Override
    public void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        //绘制折线图的白色背景
        canvas.drawRect(1,0,width,height,mPaint_bg);
        //设置Y轴
        canvas.drawLine(Xpoint+10,Ypoint,Xpoint+10,Yscale,mPaint_linezhou);//Y轴线
        for(int i=1;i<6;i++){
            canvas.drawLine(Xpoint+10,Ypoint-i*Yscale,Xpoint+20,Ypoint-i*Yscale,mPaint_scanle);//刻度
            try {
                String[] num={"","20","40","60","80",""};
                canvas.drawText(num[i],Xpoint-22,Ypoint-i*Yscale+5,mPaint_ydata);//文字
            }catch (Exception e){
            }
        }

        canvas.drawLine(Xpoint+10,Yscale-5,Xpoint+7,Yscale+9,mPaint_linezhou);//箭头
        canvas.drawLine(Xpoint+10,Yscale-5,Xpoint+13,Yscale+9,mPaint_linezhou);
        canvas.drawText("Count",Xpoint+15,Yscale-17,mPaint_end);
        //设置X轴
        canvas.drawLine(Xpoint+10,Ypoint,Xpoint+Xlength,Ypoint,mPaint_linezhou);
        int i;
        for(i=0;i<mdata.size();i++){
            canvas.drawLine(Xpoint+(i+1)*Xscale,Ypoint,Xpoint+(i+1)*Xscale,Ypoint+10,mPaint_linezhou);
            try {
                canvas.drawText(mdata.get(i).getDate(),Xpoint+(i+1)*Xscale,Ypoint+22,mPaint_xdata);
            }catch (Exception e){

            }
        }
        canvas.drawLine(Xpoint+Xlength+Xscale,Ypoint+3,Xpoint+Xlength-6+Xscale,Ypoint-3,mPaint_linezhou);
        canvas.drawLine(Xpoint+Xlength+Xscale,Ypoint+3,Xpoint+Xlength-6+Xscale,Ypoint+3,mPaint_linezhou);
        for (i= 0; i < mdata.size();i++){
            if (i!=mdata.size()-1){
                mpath.moveTo((i+1)*Xscale+ Xpoint,2*Yscale+(float)(100-mdata.get(i).getData())/20*Yscale);
                mpath.lineTo(Xscale * (i + 2) + Xpoint,2*Yscale+(float)(100-mdata.get(i+1).getData())/20*Yscale);
                canvas.drawPath(mpath,mPaint_brokenline);

            }
            for(int j=0;j<mdata.size();j++){
                canvas.drawCircle((j+1)*Xscale+ Xpoint,2*Yscale+(float)(100-mdata.get(j).getData())/20*Yscale,circle, mPaint_point_bg);
                String data=mdata.get(j).getData()+"";
                canvas.drawText(data,(j+1)*Xscale+ Xpoint,2*Yscale+(float)(100-mdata.get(j).getData())/20*Yscale-mPaint_brokenline.measureText(data),mPaint_text);
            }

        }

    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
//        width=getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
//        height=getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec);
        if(mdata.size()<6){
            width=Xscale*8;
            //通过调用onMeasure源码中的方法进行获得宽度，
//            width=getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        }
        else{
            width=Xscale*(mdata.size()+2);
        }
        height=getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        Xlength=width-Xpoint-Xscale;
        Ylength=Ypoint-Yscale;
        Xpoint=50;
        Ypoint=height-70;
        Xscale=150;
        Yscale=Ypoint/7;
        setMeasuredDimension(width,height);
    }

}
