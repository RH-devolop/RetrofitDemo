package com.example.retrofit.photo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.retrofit.adapter.PhotoAdapter;
import com.example.retrofit.model.PhotoArticleBean;
import com.example.retrofit.base.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


/**
 * @author RH
 * @date 2018/3/5
 */
public class PhotoFragment extends BaseListFragment<PhotoPresenter> implements IPhotoArticle.View {
    private static final String TAG = "PhotoFragment";
    public static PhotoFragment photoFragment;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static PhotoFragment getInstance() {
        if (photoFragment == null) {
            photoFragment = new PhotoFragment();
        }
        return photoFragment;
    }

    private PhotoAdapter photoAdapter;
    private List<PhotoArticleBean.Data> dataList = new ArrayList<>();


    @Override
    protected void setPresenter() {
        presenter = new PhotoPresenter(compositeDisposable);
        presenter.attachView(this);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        photoAdapter = new PhotoAdapter(dataList, this);
        recyclerView.setAdapter(photoAdapter);
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initData() {
        presenter.loadData();
    }

    @Override
    public void onUpdateUI(List<PhotoArticleBean.Data> list) {
        //dataList.clear();
        //dataList.addAll(list);
        dataList.addAll(0, list);
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadingFinish() {
        refreshLayout.setRefreshing(false);
    }


    @Override
    public void responsInfo(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRefresh() {
        presenter.loadData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //CompositeDisposable的clear()方法和dispose()方法类似，clear()可以多次被调用来丢弃容器中所有的Disposable，但dispose()被调用一次后就会失效。
        compositeDisposable.clear();
    }

}
