package com.truedreamz.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.truedreamz.playwithrxandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //ConcurrencySchedulerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConcurrencySchedulerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConcurrencySchedulerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.progress_operation_running)
    ProgressBar progressBar;

    @BindView(R.id.list_threading_log)
    ListView logsListView;

    private List<String> logs_list;
    private CompositeDisposable disposables = new CompositeDisposable();
    private Unbinder unbinder;

    private LogAdapter adapterLog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public ConcurrencySchedulerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConcurrencySchedulerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConcurrencySchedulerFragment newInstance(String param1, String param2) {
        ConcurrencySchedulerFragment fragment = new ConcurrencySchedulerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_concurrency_scheduler, container, false);
        unbinder = ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        /*new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                startConcurrency();
            }
        }, 0, 2000);//put here time 1000 milliseconds=1 second*/
        startConcurrency();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
       /* if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/

    private void startConcurrency(){
        progressBar.setVisibility(View.INVISIBLE);
        //addingStatus("Start process");

        DisposableObserver<Boolean> d = getDisposableObserver();

        getObservable()
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                        return objectObservable.delay(5, TimeUnit.SECONDS);
                    }
                })
                //.replay(10,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d);


        disposables.add(d);

       /* Observable.interval(5, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull Long aLong) throws Exception {
                        return getDataObservable(); //Where you pull your data
                    }
                });*/
    }


    private Observable<Boolean> getObservable() {
        return Observable.just(true)
                .map(
                        aBoolean -> {
                            addingStatus("Doing background operation!!!");
                            doingBackgroundOperation();
                            return aBoolean;
                        });
    }

    private void doingBackgroundOperation() {
        addingStatus("Calling method............");

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),"Calling method for every 5 seconds",Toast.LENGTH_SHORT).show();
            }
        });

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            //Timber.d("Operation was interrupted");
        }
    }

    private DisposableObserver<Boolean> getDisposableObserver() {
        return new DisposableObserver<Boolean>() {

            @Override
            public void onComplete() {
                addingStatus("Disposable On complete");
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Throwable e) {
                addingStatus(String.format("Disposable Error!  %s", e.getMessage()));
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNext(Boolean bool) {
                addingStatus(String.format("getDisposableObserver() onNext - \"%b\"", bool));
            }
        };
    }

    private void addingStatus(String logMsg) {

        if (_isCurrentlyOnMainThread()) {
            logs_list.add(0, logMsg + " (main thread) ");
            adapterLog.clear();
            adapterLog.addAll(logs_list);
        } else {
            logs_list.add(0, logMsg + " (NOT main thread) ");
            // You can only do below stuff on main thread.
            new Handler(Looper.getMainLooper())
                    .post(
                            () -> {
                                adapterLog.clear();
                                adapterLog.addAll(logs_list);
                            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        disposables.clear();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupLogger();
    }


    private void setupLogger() {
        logs_list = new ArrayList<String>();
        adapterLog = new LogAdapter(getActivity(), new ArrayList<String>());
        logsListView.setAdapter(adapterLog);
    }

    private boolean _isCurrentlyOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private class LogAdapter extends ArrayAdapter<String> {

        public LogAdapter(Context context, ArrayList<String> logs) {
            super(context, R.layout.item_log, R.id.item_log, logs);
        }
    }
}