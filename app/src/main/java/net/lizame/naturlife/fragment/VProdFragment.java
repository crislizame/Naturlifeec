package net.lizame.naturlife.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.lizame.naturlife.R;
import net.lizame.naturlife.activity.AddCliente2Activity;
import net.lizame.naturlife.activity.VProductosCActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VProdFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VProdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VProdFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LinearLayout stocka;
    LinearLayout stockd;
    LinearLayout all;
    LinearLayout line;
    LinearLayout bs;
    private OnFragmentInteractionListener mListener;

    public VProdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VProdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VProdFragment newInstance(String param1, String param2) {
        VProdFragment fragment = new VProdFragment();
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
        View view = inflater.inflate(R.layout.fragment_vprod, container, false);
        stocka = view.findViewById(R.id.btn_stocka);
        stocka.setOnClickListener(this);
        stockd = view.findViewById(R.id.btn_stockd);
        stockd.setOnClickListener(this);
        all = view.findViewById(R.id.btn_all);
        all.setOnClickListener(this);
        line = view.findViewById(R.id.btn_line);
        line.setOnClickListener(this);
        bs = view.findViewById(R.id.btn_bestSeller);
        bs.setOnClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), VProductosCActivity.class);

        switch (view.getId())
        {
            case R.id.btn_stocka:
                intent.putExtra("tipo", "stocka");
                startActivity(intent);
            break;
            case R.id.btn_stockd:
                intent.putExtra("tipo", "stockd");
                startActivity(intent);
                break;
            case R.id.btn_all:
                intent.putExtra("tipo", "all");
                startActivity(intent);
                break;
            case R.id.btn_bestSeller:
                intent.putExtra("tipo", "bs");
                startActivity(intent);
                break;
            case R.id.btn_line:
                intent.putExtra("tipo", "line");
                startActivity(intent);
                break;
        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
