package net.grietainc.naturlife.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.grietainc.naturlife.R;
import net.grietainc.naturlife.core.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddClienteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddClienteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddClienteFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText et_razon, et_rucc, et_tel1,et_tel2,et_tel4,et_tel5,et_fechnac;

    Spinner sp_tipo,sp_ciudad, sp_zona, sp_sexo,sp_cate;
    Button btn_nextt;
    private OnFragmentInteractionListener mListener;

    public AddClienteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddClienteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddClienteFragment newInstance(String param1, String param2) {
        AddClienteFragment fragment = new AddClienteFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_cliente, container, false);
                et_razon = view.findViewById(R.id.et_razon);
                et_rucc = view.findViewById(R.id.et_rucc);
                et_tel1 = view.findViewById(R.id.et_tel1);
                et_tel2 = view.findViewById(R.id.et_tel2);
                et_tel4 = view.findViewById(R.id.et_tel4);
                et_tel5 = view.findViewById(R.id.et_tel5);
                sp_tipo = view.findViewById(R.id.sp_tipoo);
                sp_ciudad = view.findViewById(R.id.sp_ciudado);
                sp_zona = view.findViewById(R.id.et_zono);
                sp_sexo = view.findViewById(R.id.sp_sexoo);
                sp_cate = view.findViewById(R.id.sp_cateo);
        et_fechnac = view.findViewById(R.id.et_fechnac);
        et_fechnac.setOnClickListener(this);
        et_fechnac.setTag(et_fechnac.getKeyListener());
        et_fechnac.setKeyListener(null);
                btn_nextt = view.findViewById(R.id.btn_nextt);
                btn_nextt.setOnClickListener(this);
        new Thread(new Runnable() {
            public void run() {
                cargarspinner();
            }
        }).start();

        return view;
    }
    private void cargarspinner() {
        RequestQueue queue4 = Volley.newRequestQueue(getContext());
        String arrayn4 = core.BASE_URL + "clientes/obspinner_ws.php?lol=1&nn=3";

        StringRequest stringRequest4 = new StringRequest(Request.Method.GET, arrayn4, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //response = response.replace("][",",");
                try {
                    JSONArray ja = new JSONArray(response);
                    JSONObject json_data = ja.getJSONObject(0);
                    ArrayList<String> listaFrutas = new ArrayList<String>();
                    String[] strFrutas = new String[0];
                    Array lol;

                    for (int l = 0; l < ja.length(); l++) {

                        // strFrutas = new String[]{ja.getJSONObject(l).getString("lindescrip")};
                        // Collections.addAll(listaFrutas, strFrutas);

                        listaFrutas.add(ja.getJSONObject(l).getString("tipdescrip"));
                    }

                    ArrayAdapter<String> comboAdapter;
// Specify the layout to use when the list of choices appears
                    comboAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listaFrutas);
                    //Cargo el spinner con los datos
                    sp_tipo.setAdapter(comboAdapter);


                    Log.i("sizejson", "" + ja.length());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", "" + error.getMessage());
            }
        });

        queue4.add(stringRequest4);
        RequestQueue queue1 = Volley.newRequestQueue(getContext());
        String arrayn1 = core.BASE_URL + "clientes/obspinner_ws.php?lol=2&nn=3";

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, arrayn1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //response = response.replace("][",",");
                try {
                    JSONArray ja = new JSONArray(response);
                    JSONObject json_data = ja.getJSONObject(0);
                    ArrayList<String> listaFrutas = new ArrayList<String>();
                    String[] strFrutas = new String[0];
                    Array lol;

                    for (int l = 0; l < ja.length(); l++) {

                        // strFrutas = new String[]{ja.getJSONObject(l).getString("lindescrip")};
                        // Collections.addAll(listaFrutas, strFrutas);

                        listaFrutas.add(ja.getJSONObject(l).getString("zondescrip"));
                    }

                    ArrayAdapter<String> comboAdapter;
// Specify the layout to use when the list of choices appears
                    comboAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listaFrutas);
                    //Cargo el spinner con los datos
                    sp_zona.setAdapter(comboAdapter);


                    Log.i("sizejson", "" + ja.length());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", "" + error.getMessage());
            }
        });

        queue1.add(stringRequest1);
        RequestQueue queue2 = Volley.newRequestQueue(getContext());
        String arrayn2 = core.BASE_URL + "clientes/obspinner_ws.php?lol=3&nn=3";

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, arrayn2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //response = response.replace("][",",");
                try {
                    JSONArray ja = new JSONArray(response);
                    JSONObject json_data = ja.getJSONObject(0);
                    ArrayList<String> listaFrutas = new ArrayList<String>();
                    String[] strFrutas = new String[0];
                    Array lol;

                    for (int l = 0; l < ja.length(); l++) {

                        // strFrutas = new String[]{ja.getJSONObject(l).getString("lindescrip")};
                        // Collections.addAll(listaFrutas, strFrutas);

                        listaFrutas.add(ja.getJSONObject(l).getString("ciudescrip"));
                    }

                    ArrayAdapter<String> comboAdapter;
// Specify the layout to use when the list of choices appears
                    comboAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listaFrutas);
                    //Cargo el spinner con los datos
                    sp_ciudad.setAdapter(comboAdapter);


                    Log.i("sizejson", "" + ja.length());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", "" + error.getMessage());
            }
        });

        queue2.add(stringRequest2);


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
        switch (view.getId()) {
            case R.id.et_fechnac:
                showDatePickerDialog();
                break;
            case R.id.btn_nextt:

                break;
        }
    }
    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = twoDigits(day) + " / " + twoDigits(month+1) + " / " + year;
                et_fechnac.setText(selectedDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
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
