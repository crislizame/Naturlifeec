package net.grietainc.naturlife.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.grietainc.naturlife.ItemAdapter;
import net.grietainc.naturlife.R;
import net.grietainc.naturlife.buscar.Item;
import net.grietainc.naturlife.core.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BuscarClienteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuscarClienteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuscarClienteFragment extends Fragment implements ItemAdapter.ItemListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    BottomSheetBehavior behavior;
    EditText bname,bruc;
    Spinner tipocodigo,zoncodigo,ciucodigo;
    private BottomSheetDialog mBottomSheetDialog;
    private ProgressBar pb;
    private ViewPager vp;
    Button btn_buscador;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BuscarClienteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuscarClienteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuscarClienteFragment newInstance(String param1, String param2) {
        BuscarClienteFragment fragment = new BuscarClienteFragment();
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
        View view = inflater.inflate(R.layout.fragment_buscar_cliente, container, false);
        // Inflate the layout for this fragment
        pb = view.findViewById(R.id.pb_nuevopro);
        vp = view.findViewById(R.id.vp_pb);
        bname = view.findViewById(R.id.et_namerazon);
        bruc = view.findViewById(R.id.et_rucci);
        tipocodigo = view.findViewById(R.id.sp_tipoo);
        zoncodigo = view.findViewById(R.id.sp_zona);
        ciucodigo = view.findViewById(R.id.sp_ciudado);
        btn_buscador = view.findViewById(R.id.btn_buscador);
        btn_buscador.setOnClickListener(this);
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });
        new Thread(new Runnable() {
            public void run() {
            cargarspinner();
            }
        }).start();
        return view;
    }

    private void cargarspinner() {
        RequestQueue queue4 = Volley.newRequestQueue(getContext());
        String arrayn4 = core.BASE_URL + "clientes/obspinner_ws.php?lol=1&nn=2";

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
                    tipocodigo.setAdapter(comboAdapter);


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
        String arrayn1 = core.BASE_URL + "clientes/obspinner_ws.php?lol=2&nn=2";

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
                    zoncodigo.setAdapter(comboAdapter);


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
        String arrayn2 = core.BASE_URL + "clientes/obspinner_ws.php?lol=3&nn=2";

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
                    ciucodigo.setAdapter(comboAdapter);


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
    private void showBottomSheetDialog() {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
      //  session = new Session(getActivity());

        mBottomSheetDialog = new BottomSheetDialog(getContext());
        View view = getLayoutInflater().inflate(R.layout.sheet, null);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ItemAdapter(createItems(), new ItemAdapter.ItemListener() {
            @Override
            public void onItemClick(final Item item) {
                if (mBottomSheetDialog != null) {
                    mBottomSheetDialog.dismiss();
                }
               // codigoguardado = item.getCodigo();

            }
        }));
        mBottomSheetDialog.setContentView(view);


    }
    public List<Item> createItems() {
        String urling = core.BASE_URL + "clientes/bcliente_ws.php?lol=listo";
        RequestQueue queue4 = Volley.newRequestQueue(getContext());
        urling = urling.replaceAll(" ", "%20");
        final ArrayList<Item> items = new ArrayList<>();
        StringRequest stringRequest4 = new StringRequest(Request.Method.POST,urling,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                // mTextView.setText("Response is: "+ response.substring(0,500));
                Log.i("Success","Todo Belen "+response);
                if(response.length()>0 || response != null){
                    try {
                        JSONArray ja = new JSONArray(response);
                       /* Log.i("sizejson","a"+ja.length());
                        Log.i("sizejson2",""+json_data.getString("clinombre"));
                        Log.i("sizejson3",""+json_data2.getString("clinombre"));*/


                        for (int i = 0;i<ja.length();i++){
                            JSONObject json_data = ja.getJSONObject(i);
                            String nombricito = json_data.getString("clinombre");
                            String ruccito = json_data.getString("cliruc");
                            String codigito = json_data.getString("clicodigo");
                            items.add(new Item(R.mipmap.ic_logo_ppp, nombricito,ruccito,codigito));

                        }
                        mBottomSheetDialog.show();
                        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                mBottomSheetDialog = null;
                            }
                        });

                        pb.setVisibility(View.INVISIBLE);
                        vp.setVisibility(View.INVISIBLE);




                    } catch (JSONException e) {
                        pb.setVisibility(View.INVISIBLE);
                        vp.setVisibility(View.INVISIBLE);
                        Toast toast = Toast.makeText(getActivity(),"No se encontro Cliente.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, -100);
                        toast.show();
                        Log.i("sizejson","");
                        e.printStackTrace();
                    }


                }else{

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
               pb.setVisibility(View.INVISIBLE);
               vp.setVisibility(View.INVISIBLE);
                Log.i("Error","Todo Malon "+error.toString());

                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
               final String bc_string = (bname.getText().toString().isEmpty()) ? "" : bname.getText().toString();
               final String br_string = (bruc.getText().toString().isEmpty()) ? "" : bruc.getText().toString();
               final String li_string = (tipocodigo.getSelectedItem().toString().isEmpty()) ? "" : tipocodigo.getSelectedItem().toString();
               final String zo_string = (zoncodigo.getSelectedItem().toString().isEmpty()) ? "" : zoncodigo.getSelectedItem().toString();
               final String ci_string = (ciucodigo.getSelectedItem().toString().isEmpty()) ? "" : ciucodigo.getSelectedItem().toString();

                Map<String, String>  params = new HashMap<String, String>();
                params.put("bname", bc_string);
                params.put("bruc", br_string);
                params.put("lcli", li_string);
                params.put("zcli", zo_string);
                params.put("ccli", ci_string);



                return params;
            }

            /*stringRequest4.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
        };
        queue4.add(stringRequest4);



        return items;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_buscador:
                pb.setVisibility(View.VISIBLE);
                vp.setVisibility(View.VISIBLE);

                showBottomSheetDialog();
                break;
        }
    }

    @Override
    public void onItemClick(Item item) {
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

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
