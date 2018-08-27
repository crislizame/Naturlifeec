package net.lizame.naturlife.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.lizame.naturlife.R;
import net.lizame.naturlife.buscar.VProductos;
import net.lizame.naturlife.buscar.VProductosAdapter;
import net.lizame.naturlife.buscar.VerPedidos;
import net.lizame.naturlife.buscar.VerPedidosAdapter;
import net.lizame.naturlife.core.Session;
import net.lizame.naturlife.core.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VerPedidosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VerPedidosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerPedidosFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private VerPedidosAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ArrayList<VerPedidos> data;
    private ProgressBar pb;
    private ViewPager vp;
    String tipo;
    Session session;
    private SearchView searchView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public VerPedidosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerPedidosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerPedidosFragment newInstance(String param1, String param2) {
        VerPedidosFragment fragment = new VerPedidosFragment();
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
        View view = inflater.inflate(R.layout.fragment_ver_pedidos, container, false);
        pb = view.findViewById(R.id.pb_nuevopro);
        vp = view.findViewById(R.id.vp_pb);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        data = new ArrayList<>();
        session = new Session(getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new VerPedidosAdapter(getContext(),data);
        recyclerView.setAdapter(adapter);

        pb.setVisibility(View.VISIBLE);
        vp.setVisibility(View.VISIBLE);
        final ArrayList<VerPedidos> items = new ArrayList<>();

        String urling = core.BASE_URL + "pedidos/verpedidos_ws.php?lol=listo";
        RequestQueue queue4 = Volley.newRequestQueue(getContext());
        urling = urling.replaceAll(" ", "%20");
        StringRequest stringRequest4 = new StringRequest(Request.Method.POST,urling,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Success","Todo Belen "+response);
                if(response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        DecimalFormat df = new DecimalFormat("0.00");

                        for (int i = 0;i<ja.length();i++){
                            JSONObject json_data = ja.getJSONObject(i);
                            String nombre = json_data.getString("nombre");
                            String estado = json_data.getString("estado");
                            String line = json_data.getString("pednumped");
                            String stock = json_data.getString("fecha");
                            float preciox = Float.parseFloat(json_data.getString("pedvaltot"));
                            String precio = "$"+df.format(preciox);

                            items.add(new VerPedidos(nombre, line,precio,stock,estado));

                        }
                        data.clear();
                        data.addAll(items);
                        adapter.notifyDataSetChanged();

                        pb.setVisibility(View.INVISIBLE);
                        vp.setVisibility(View.INVISIBLE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(),"¡No hay Pedidos!",Toast.LENGTH_LONG).show();
                        pb.setVisibility(View.INVISIBLE);
                        vp.setVisibility(View.INVISIBLE);
                    }


                }else{
                    Log.i("sizejson","");
                }
                if(response.equals("null"))
                {
                    Toast.makeText(getContext(),"¡No hay Pedidos!",Toast.LENGTH_LONG).show();
                    pb.setVisibility(View.INVISIBLE);
                    vp.setVisibility(View.INVISIBLE);

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
                final String bc_string = "";
                Map<String, String>  params = new HashMap<String, String>();
                params.put("usrcodigo",session.getusrcodigo());



                return params;
            }

            /*stringRequest4.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
        };
        queue4.add(stringRequest4);
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
