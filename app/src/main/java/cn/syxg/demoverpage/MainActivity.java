package cn.syxg.demoverpage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MZBannerView mzBannerView;
    private ArcHeardView mArcHeardView;
    private int mStartColor [];
    private int mEndColor [];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置状态栏颜色
        StatusBarUtils.setColor(this,getResources().getColor(R.color.start_color),0);

        mzBannerView = (MZBannerView) findViewById(R.id.arc_view_pager);
        mArcHeardView = (ArcHeardView) findViewById(R.id.mArcHeardView);

        mStartColor = new int[]{
                getResources().getColor(R.color.start_color),
                getResources().getColor(R.color.page1_start_color),
                getResources().getColor(R.color.page2_start_color),
                getResources().getColor(R.color.page3_start_color),
        };
        mEndColor = new int[]{
                getResources().getColor(R.color.end_color),
                getResources().getColor(R.color.page1_end_color),
                getResources().getColor(R.color.page2_end_color),
                getResources().getColor(R.color.page3_end_color)
        };

        //设置ArcHeardView的初始颜色
        mArcHeardView.setColor(getResources().getColor(R.color.start_color),getResources().getColor(R.color.end_color));

        //设置新的zBannerView的监听事件
        mzBannerView.addPageChangeLisnter(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                StatusBarUtils.setColor(MainActivity.this,mStartColor[position],0);
                mArcHeardView.setColor(mStartColor[position],mEndColor[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //添加mzBannerView的数据源
        mzBannerView.setPages(mockData(), new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        mzBannerView.start();

    }

    public static class BannerViewHolder implements MZViewHolder<MyData> {
        private TextView text1,text2;
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.arc_view_pager_item,null);
            text1 = (TextView) view.findViewById(R.id.arc_title);
            text2 = (TextView) view.findViewById(R.id.arc_content);
            return view;
        }

        @Override
        public void onBind(Context context, int position, MyData data) {
            // 数据绑定
            // mImageView.setImageResource(data);
            text1.setText(data.title);
            text2.setText(data.content);
        }
    }


    public List<MyData> mockData(){
        List<MyData> datas = new ArrayList<>();
        for(int i=0;i<4;i++){
            MyData myData = new MyData();
            myData.title = "大盗Jeremy";
            myData.content = "Find your\n"+"Plan"+i;
            datas.add(myData);
        }
        return datas;
    }

    public static class MyData{
        public  String title;
        public String content;
    }
}
