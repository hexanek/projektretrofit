package pl.lewandowski.projektretrofit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GitHubService {

    private GitHubApi gitHubApi;

    GitHubService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").addConverterFactory(GsonConverterFactory.create()).build();

        gitHubApi = retrofit.create(GitHubApi.class);
    }

    List<String> getTopContributors(String userName) throws IOException {
        List<Repository> repos = gitHubApi.listRepos(userName).execute().body();

        repos = repos != null ? repos : Collections.emptyList();

        return repos.stream().flatMap(repo -> getContributors(userName, repo)).sorted((a, b) -> b.getContributions() - a.getContributions()).map(Contributor::getName).distinct().sorted().collect(Collectors.toList());
    }

    private Stream<Contributor> getContributors(String userName, Repository repo) {
        List<Contributor> contributors = null;
        try {
            contributors = gitHubApi.listRepoContributors(userName, repo.getName()).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        contributors = contributors != null ? contributors : Collections.emptyList();

        return contributors.stream().filter(c -> c.getContributions() > 100);
    }

}
