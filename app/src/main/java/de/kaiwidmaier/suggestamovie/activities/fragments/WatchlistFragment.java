package de.kaiwidmaier.suggestamovie.activities.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import de.kaiwidmaier.suggestamovie.R;
import de.kaiwidmaier.suggestamovie.activities.FilterActivity;
import de.kaiwidmaier.suggestamovie.activities.MainActivity;
import de.kaiwidmaier.suggestamovie.activities.SearchActivity;
import de.kaiwidmaier.suggestamovie.adapters.RecyclerMovieAdapter;
import de.kaiwidmaier.suggestamovie.adapters.utils.SimpleItemTouchHelperCallback;
import de.kaiwidmaier.suggestamovie.data.DataHelper;
import de.kaiwidmaier.suggestamovie.data.Movie;

public class WatchlistFragment extends Fragment {

  public static final String BASE_URL = "http://api.themoviedb.org/3/";
  private RecyclerView recyclerWatchlist;
  private RecyclerMovieAdapter movieAdapter;
  private ArrayList<Movie> watchlist;
  private LinearLayout layoutWatchlistEmpty;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    View result=inflater.inflate(R.layout.fragment_watchlist, container, false);


    layoutWatchlistEmpty = result.findViewById(R.id.layout_watchlist_empty);
    recyclerWatchlist = result.findViewById(R.id.recycler_watchlist);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerWatchlist.getContext(), DividerItemDecoration.VERTICAL);
    recyclerWatchlist.addItemDecoration(dividerItemDecoration);

    watchlist = ((DataHelper) getActivity().getApplication()).getWatchlist();

    movieAdapter = new RecyclerMovieAdapter(getActivity(), watchlist, false);

    recyclerWatchlist.setAdapter(movieAdapter);
    checkEmpty();

    //Swipe to remove and long press to change position
    ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(movieAdapter);
    ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
    touchHelper.attachToRecyclerView(recyclerWatchlist);
    return result;
  }

  private void checkEmpty(){
    if(watchlist.isEmpty()){
      layoutWatchlistEmpty.setVisibility(View.VISIBLE);
    }
    else{
      layoutWatchlistEmpty.setVisibility(View.GONE);
    }
  }

  @Override
  public void onResume() {
    if (movieAdapter != null) {
      movieAdapter.notifyDataSetChanged();
    }
    super.onResume();
    checkEmpty();
  }

}
