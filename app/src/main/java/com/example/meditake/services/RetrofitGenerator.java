
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class gRetrofitGenerator {

    private static Retrofit instance;

    private RetrofitGenerator(){

    }

    synchronized
    public static Retrofit getRetrofit(){
        if (instance==null){
            instance  = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.8:8080/api/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}


