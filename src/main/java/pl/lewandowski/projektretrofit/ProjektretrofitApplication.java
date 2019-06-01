package pl.lewandowski.projektretrofit;

import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

@SpringBootApplication
public class ProjektretrofitApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ProjektretrofitApplication.class, args);

        String userName = "eugenp";
        List<String> topContributors = new GitHubService().getTopContributors(userName);
        topContributors.forEach(System.out::println);


    }

}
