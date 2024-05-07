package com.example.bachhoaonline.retrofit;

import com.example.bachhoaonline.model.sanphamModel;
import com.example.bachhoaonline.model.taikhoanModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIbachhoa {
@GET("getallsanpham.php")
Observable<sanphamModel> getsanpham();


@POST("dangki.php")
@FormUrlEncoded
Observable<taikhoanModel> dangki(
        @Field("tentaikhoan") String tentaikhoan,
        @Field("sodienthoai") String sodienthoai,
        @Field("matkhau") String matkhau

);

}
