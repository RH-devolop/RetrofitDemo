package com.example.retrofit.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.RxFragment;

/**
 * @author RH
 * @date 2018/3/5
 */
public abstract class BaseFragment<T extends BasePresenter> extends RxFragment {
    /**
     * 注意：父类是抽象类，其中有抽象方法，那么子类继承父类，并把父类中的所有抽象方法都实现（覆盖）了，
     * 子类才有创建对象的实例的能力，否则子类也必须是抽象类。抽象类中可以有构造方法，是子类在构造子类对象时需要调用的父类（抽象类）的构造方法。
     */
    protected T presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        /*if (presenter != null) {
            presenter.attachView(this);
        }*/
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(attachLayoutId(), container, false);
        initView(view);
        initData();
        return view;
    }


    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */

    protected abstract void setPresenter();

    protected abstract int attachLayoutId();

    protected abstract void initView(View view);

    protected abstract void initData();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
