package com.github.east196.rap.dict;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

import com.github.east196.core.api.Result;

public interface DictSelectApi {

    @GET("/sys/dict/getDictItems/{dictCode}")
    Call<Result<List<SysDictItem>>> ajaxGetDictItems(@Path("dictCode") String dictCode);
    @GET("/sys/dict/getDictText/{dictCode}/{key}")
    Call<Result<String>> ajaxFilterDictText(@Path("dictCode") String dictCode, @Path("key") String key);
}
