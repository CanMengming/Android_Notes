package Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.androidsoft.mynotes_2017144235.R;

/**
 * 自定义View具备可配置的功能
 *
 *1.input_icon:输入框前面的图标
 *2.input_hint:输入框提示内容
 *3.is_password:输入的内容是否需要以密文的形式展示(即密码显示点)
 */

public class InputView extends FrameLayout {

    //创建变量获取自定义的属性
    private int inputIcon;
    private String inputHint;
    private boolean isPassword;

    private View mView;
    private ImageView mIvIcon;
    private EditText mEtInput;


    public InputView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                     int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * 初始化方法
     */
    private void init(Context context, AttributeSet attrs){
        if (attrs== null)
            return;
        //获取自定义属性
        TypedArray typedArray= context.obtainStyledAttributes(attrs, R.styleable.inputView);

        //获取attrs.xml中自定义的属性--inputView_input_icon自定义图片属性，并传入默认值logo.png照片
        //分别获取输入控件中的inputIcon,inputHint,isPassword自定义属性
        inputIcon= typedArray.getResourceId(R.styleable.inputView_input_icon, R.mipmap.logo);
        inputHint= typedArray.getString(R.styleable.inputView_input_hint);
        isPassword= typedArray.getBoolean(R.styleable.inputView_is_password, false);

        typedArray.recycle();      //释放typedArray资源

        //绑定layput布局
        mView= LayoutInflater.from(context).inflate(R.layout.input_view, this,
                                                                               false);
        mIvIcon= mView.findViewById(R.id.iv_icon);    //input_view.xml中定义ImageView与EditText
        mEtInput= mView.findViewById(R.id.et_input);

        //布局关联属性
        mIvIcon.setImageResource(inputIcon);
        mEtInput.setHint(inputHint);
        mEtInput.setInputType(isPassword ? InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_VARIATION_PASSWORD
                                : InputType.TYPE_CLASS_PHONE);

        addView(mView);

    }

    /**
     * 返回输入的内容
     * @return
     */
    public String getInputStr(){
        //trim()清理空格
        return mEtInput.getText().toString().trim();
    }
}
