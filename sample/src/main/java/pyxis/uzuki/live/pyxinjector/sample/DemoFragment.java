package pyxis.uzuki.live.pyxinjector.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import pyxis.uzuki.live.pyxinjector.PyxInjector;
import pyxis.uzuki.live.pyxinjector.annotation.Argument;
import pyxis.uzuki.live.pyxinjector.annotation.BindView;
import pyxis.uzuki.live.pyxinjector.annotation.OnClick;
import pyxis.uzuki.live.pyxinjector.annotation.OnLongClick;
import pyxis.uzuki.live.pyxinjector.base.InjectFragment;
import pyxis.uzuki.live.pyxinjector.utils.RecentlyClicked;

/**
 * PyxInjector
 * Class: MainActivity
 * Created by Pyxis on 2017-10-23.
 */
public class DemoFragment extends InjectFragment {
    private View rootView;
    private @Argument String name = "";
    private @Argument("age") String age = "";

    private @BindView RecyclerView recyclerView;
    private ArrayList<String> itemSet = new ArrayList<>();

    private ListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_recycler, container, false);
        return rootView;
    }

    @NotNull
    @Override
    public View getCreatedView() {
        return rootView;
    }

    @Override
    public void onViewCreated(@org.jetbrains.annotations.Nullable View view, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        for (int i = 0; i < 17; i++) {
            itemSet.add(String.valueOf(i));
        }

        adapter.notifyDataSetChanged();
    }

    public class ListAdapter extends RecyclerView.Adapter<ListHolder> {

        @Override
        public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.activity_recycler_item, parent, false);
            return new ListHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ListHolder holder, int position) {
            holder.txtNum.setText(String.format("#%d", position + 1));

        }

        @Override
        public int getItemCount() {
            return itemSet.size();
        }
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        private @BindView TextView txtNum;

        public ListHolder(View itemView) {
            super(itemView);
            PyxInjector.getInstance().execute(getActivity(), this, itemView);
        }
    }
}
