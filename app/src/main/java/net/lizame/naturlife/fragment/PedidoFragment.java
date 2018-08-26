package net.lizame.naturlife.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.lizame.naturlife.ItemAdapter;
import net.lizame.naturlife.PedidoproActivity;
import net.lizame.naturlife.R;
import net.lizame.naturlife.buscar.Item;
import net.lizame.naturlife.core.Session;
import net.lizame.naturlife.core.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PedidoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PedidoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PedidoFragment extends Fragment implements ItemAdapter.ItemListener, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Button btnView,btnnext;
    private ImageButton btnDialog;
    BottomSheetBehavior behavior;
    private BottomSheetDialog mBottomSheetDialog;
    RelativeLayout rl_verificar;
    RelativeLayout rl_next;
    private ItemAdapter mAdapter;
    EditText Buscar_Cliente;
    private String mParam1;
    private String mParam2;
    private ProgressBar pb;
    private ViewPager vp;
    Session session;
    TextView cp,ip,cup,pg,cumple;
    String codigoguardado;
    private OnFragmentInteractionListener mListener;

    public PedidoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PedidoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PedidoFragment newInstance(String param1, String param2) {
        PedidoFragment fragment = new PedidoFragment();
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
        final View view = inflater.inflate(R.layout.fragment_pedido, container, false);

        pb = view.findViewById(R.id.pb_nuevopro);
        vp = view.findViewById(R.id.vp_pb);

        Buscar_Cliente = view.findViewById(R.id.edtx_buscar);
        ip = view.findViewById(R.id.txt_impagas);
        btnnext = view.findViewById(R.id.btn_next);
        cumple = view.findViewById(R.id.tv_cumple);
        cp = view.findViewById(R.id.txt_primcomp);
        cup = view.findViewById(R.id.txt_cupo);
        pg = view.findViewById(R.id.txt_factpag);
        rl_verificar = view.findViewById(R.id.rl_verificar);
        rl_next = view.findViewById(R.id.rl_next);
        btnDialog = view.findViewById(R.id.btn_buscar);
        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                vp.setVisibility(View.VISIBLE);

                showBottomSheetDialog();
            }
        });

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



        return view;
    }
    private void showBottomSheetDialog() {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        session = new Session(getActivity());

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
                codigoguardado = item.getCodigo();
                String urling2 = core.BASE_URL + "productos/pedidos2_ws.php?lol=listo";
                RequestQueue queue2 = Volley.newRequestQueue(getContext());
                urling2 = urling2.replaceAll(" ", "%20");
                StringRequest stringRequest2 = new StringRequest(Request.Method.POST,urling2,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        // mTextView.setText("Response is: "+ response.substring(0,500));
                        Log.i("Success","Todo Belen "+response+"   "+codigoguardado);
                        if(response.length()>0){
                            try {
                                JSONArray ja = new JSONArray(response);
                                JSONObject json_data = ja.getJSONObject(0);
                                String compras = json_data.getString("compras");
                                String pagadas = json_data.getString("pagadas");
                                String impagas = json_data.getString("impagas");
                                Integer cupo = json_data.getInt("cupo");
                                Integer validador = 1;
                                    cp.setText(compras);

                                if(pagadas.equals("SI")){
                                    pg.setText(pagadas);
                                }else{
                                    pg.setText(pagadas);
                                    pg.setTextColor(Color.parseColor("#cc0000"));
                                    validador = 0;
                                }
                                if(impagas.equals("0")){
                                    ip.setText(impagas);
                                }else{
                                    ip.setText(impagas);
                                    ip.setTextColor(Color.parseColor("#cc0000"));
                                    validador = 0;
                                }
                                if(cupo > 0){
                                    cup.setText(cupo.toString());

                                }else{
                                    cup.setText(cupo.toString());
                                    cup.setTextColor(Color.parseColor("#cc0000"));
                                    validador = 0;
                                }

                                if(validador == 1){
                                    rl_next.setVisibility(View.VISIBLE);
                                    cumple.setVisibility(View.INVISIBLE);
                                    session.setclicodigo(codigoguardado);
                                    btnnext.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(getContext(), PedidoproActivity.class);
                                            startActivity(i);
                                            getActivity().finish();
                                        }
                                    });

                                }else{
                                    btnnext.setVisibility(View.INVISIBLE);

                                    rl_next.setVisibility(View.VISIBLE);
                                    cumple.setText("El Cliente "+item.getTitle()+" no cumple los requisitos.");
                                    cumple.setVisibility(View.VISIBLE);
                                }
                                rl_verificar.setVisibility(View.VISIBLE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }else{
                            Log.i("sizejson","");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //mTextView.setText("That didn't work!");

                        Log.i("Error","Todo Malon "+error.toString());

                        error.printStackTrace();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {

                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("clicodigo", codigoguardado);



                        return params;
                    }

            /*stringRequest4.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
                };
                queue2.add(stringRequest2);
            }
        }));
        mBottomSheetDialog.setContentView(view);


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

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //mAdapter.setListener(null);

    }

    public List<Item> createItems() {
        String urling = core.BASE_URL + "productos/pedidos_ws.php?lol=listo";
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
                final String bc_string = Buscar_Cliente.getText().toString();
                Map<String, String>  params = new HashMap<String, String>();
                params.put("cliruc", bc_string);



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
