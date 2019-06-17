package ds.githubclient;

import ds.githubclient.data.network.GithubService;
import ds.githubclient.data.network.model.SearchResponse;
import ds.githubclient.data.network.model.User;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

import java.util.List;


public class test {

    public static void main(String[] args) {
        // GithubService.getEndpoint().searchUser(query, lastUserId, perPage)
        //            .flatMap { searchResponse -> Single.zip(
        //                Single.just(searchResponse),
        //                Observable.fromIterable(searchResponse.items)
        //                    .flatMap { user -> GithubService.getEndpoint().retrieveUser(user.login.orEmpty()).onErrorReturn { user } }.toList(),
        //                BiFunction { t1, t2 -> createSearchResponse(t1, t2) as SearchResponse<List<User>> }) }
        //            .observeOn(AndroidSchedulers.mainThread())
        //            .subscribeOn(Schedulers.io())
        //            .onErrorReturn { null }
        //            .subscribe { result, throwable -> onComplete(result, throwable) }
//
//        GithubService.Companion.getEndpoint().searchUser("", 0, 10, "", "")
//                .flatMap(new Function<SearchResponse<List<User>>, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<SearchResponse> apply(SearchResponse<List<User>> listSearchResponse) throws Exception {
//                        return Single.zip(
//                                Single.just(listSearchResponse),
//                                Observable.fromIterable(listSearchResponse.getItems()).flatMap(new Function<User, ObservableSource<User>>() {
//                                    @Override
//                                    public ObservableSource<User> apply(final User user) throws Exception {
//                                        return GithubService.Companion.getEndpoint().retrieveUser(user.getLogin(), "", "");
//                                    }
//                                }).toList(),
//                                new BiFunction<SearchResponse<List<User>>, List<User>, Object>() {
//                                    @Override
//                                    public Object apply(SearchResponse<List<User>> listSearchResponse, List<User> users) throws Exception {
//                                        return new SearchResponse<List<User>>(listSearchResponse.getTotalCount(), listSearchResponse.getIncompleteResults(), users);
//                                    }
//                                }
//                        );
//                    }
//                })
    }
}
