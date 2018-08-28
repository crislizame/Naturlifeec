package net.lizame.naturlife.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import net.lizame.naturlife.R;
import net.lizame.naturlife.core.Session;
import net.lizame.naturlife.core.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductosFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    EditText barcode,regsan,stock;
    ImageButton btn_scan;
    Spinner spinner;
    Handler mHandler;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    Spinner spinner5;
    Button btn_ingproducto;
    ImageView addProducto;
    Bitmap photobmp;
    Session session;
    EditText costo,pvp1,pvp2,descrip;
     ProgressBar pb;
     ViewPager vp;
    String encodedImage,foto,funcion;
    private final int PICKER =1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProductosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductosFragment newInstance(String param1, String param2) {
        ProductosFragment fragment = new ProductosFragment();
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
        View view =  inflater.inflate(R.layout.fragment_productos, container, false);

        vp = view.findViewById(R.id.vp_pb);
        pb = view.findViewById(R.id.pb_nuevopro);
        descrip = view.findViewById(R.id.despro_edtxt);
        costo = view.findViewById(R.id.costact_edtxt);
        btn_scan = view.findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(this);
        pvp1 = view.findViewById(R.id.pvp1_edtxt);
        pvp2 = view.findViewById(R.id.pvp1_edtxt2);
        barcode = view.findViewById(R.id.edt_codbarras);
        regsan = view.findViewById(R.id.edt_regsan);
        stock = view.findViewById(R.id.edt_stock);
        spinner = view.findViewById(R.id.codlin_select);
        spinner2 = view.findViewById(R.id.prepro_select);
        spinner3 = view.findViewById(R.id.pais_select);
        spinner4 = view.findViewById(R.id.medpro_select);
        spinner5 = view.findViewById(R.id.marpro_select);
        addProducto = view.findViewById(R.id.imgProducto);
        addProducto.setOnClickListener(this);
        btn_ingproducto = view.findViewById(R.id.btn_ingproducto);
        btn_ingproducto.setOnClickListener(this);
        btn_ingproducto.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(getContext());

                String arrayn1 = core.BASE_URL + "productos/array_ws.php?array=1";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, arrayn1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //response = response.replace("][",",");
                        try {
                            JSONArray ja = new JSONArray(response);
                            JSONObject json_data = ja.getJSONObject(0);
                            ArrayList<String> spinerdesc = new ArrayList<String>();
                            String[] strFrutas = new String[0];
                            Array lol;

                            for (int l = 0; l < ja.length(); l++) {

                                // strFrutas = new String[]{ja.getJSONObject(l).getString("lindescrip")};
                                // Collections.addAll(spinerdesc, strFrutas);

                                spinerdesc.add(ja.getJSONObject(l).getString("lindescrip"));
                            }

                            ArrayAdapter<String> comboAdapter;
// Specify the layout to use when the list of choices appears
                            comboAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinerdesc);
                            //Cargo el spinner con los datos
                            spinner.setAdapter(comboAdapter);


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

                RequestQueue queue2 = Volley.newRequestQueue(getContext());
                String arrayn2 = core.BASE_URL + "productos/array_ws.php?array=2";

                StringRequest stringRequest1 = new StringRequest(Request.Method.GET, arrayn2, new Response.Listener<String>() {
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

                                listaFrutas.add(ja.getJSONObject(l).getString("predescri"));
                            }

                            ArrayAdapter<String> comboAdapter;
// Specify the layout to use when the list of choices appears
                            comboAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listaFrutas);
                            //Cargo el spinner con los datos
                            spinner2.setAdapter(comboAdapter);


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
                RequestQueue queue3 = Volley.newRequestQueue(getContext());
                String arrayn3 = core.BASE_URL + "productos/array_ws.php?array=3";

                StringRequest stringRequest3 = new StringRequest(Request.Method.GET, arrayn3, new Response.Listener<String>() {
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

                                listaFrutas.add(ja.getJSONObject(l).getString("meddescri"));
                            }

                            ArrayAdapter<String> comboAdapter;
// Specify the layout to use when the list of choices appears
                            comboAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listaFrutas);
                            //Cargo el spinner con los datos
                            spinner5.setAdapter(comboAdapter);


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
                RequestQueue queue4 = Volley.newRequestQueue(getContext());
                String arrayn4 = core.BASE_URL + "productos/array_ws.php?array=4";

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

                                listaFrutas.add(ja.getJSONObject(l).getString("mardescri"));
                            }

                            ArrayAdapter<String> comboAdapter;
// Specify the layout to use when the list of choices appears
                            comboAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listaFrutas);
                            //Cargo el spinner con los datos
                            spinner4.setAdapter(comboAdapter);


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

                queue.add(stringRequest);
                queue2.add(stringRequest1);
                queue3.add(stringRequest3);
                queue4.add(stringRequest4);

                //novo
            }
        }).start();

        return view;
    }

    public void onClick(final View v) { //check for what button is pressed
        switch (v.getId()) {
            case R.id.imgProducto:
                //p
                PickFile();
                break;
            case R.id.btn_ingproducto:
                Log.i("aasa","Presionaste Ingresar");
                btn_ingproducto.setVisibility(View.INVISIBLE);
                IngresarProducto();
                break;
            case R.id.btn_scan:

                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
// milliseconds to display result on screen after scan
                scanIntegrator.initiateScan();

                break;
            default:
                break;
        }
    }

    public void IngresarProducto(){

                //obtenemos los spinner que hemos seleccionado
                pb.setVisibility(View.VISIBLE);
                vp.setVisibility(View.VISIBLE);
        mHandler = new Handler();

                session = new Session(getActivity());
                try {


                    final String regsann = (regsan.getText().toString().isEmpty()) ? "" : regsan.getText().toString();
                    final String stockk = stock.getText().toString();
                    final String barr = (barcode.getText().toString().isEmpty()) ? "" : barcode.getText().toString();
                    final String lindescrip = spinner.getSelectedItem().toString();
                    final String predescrip = spinner2.getSelectedItem().toString();
                    final String pais = spinner3.getSelectedItem().toString();
                    final String meddescrip = spinner5.getSelectedItem().toString();
                    final String mardescrip = spinner4.getSelectedItem().toString();
                    final String usrcodigo = session.getusrcodigo();
                    final String costox = costo.getText().toString();
                    final String pvp1x = pvp1.getText().toString();
                    final String pvp2x = pvp2.getText().toString();
                    final String descripx = descrip.getText().toString();

                    if (costox.isEmpty() || pvp1x.isEmpty() || pvp2x.isEmpty() || descripx.isEmpty() || stockk.isEmpty()) {
                        Toast toast = Toast.makeText(getActivity(), "Valores Importantes Vacíos.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, -100);
                        toast.show();

                        pb.setVisibility(View.GONE);
                        vp.setVisibility(View.INVISIBLE);

                    } else if (encodedImage == null) {
                        Toast toast = Toast.makeText(getActivity(), "Debe Subir Imagen producto, por favor vuelba a subir", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, -100);
                        toast.show();
                        pb.setVisibility(View.GONE);
                        vp.setVisibility(View.INVISIBLE);


                    } else {
                        new Thread(new Runnable() {
                            public void run() {
                        //Log.i("encodeimage",""+ encodedImage);
                        RequestQueue queue4 = Volley.newRequestQueue(getContext());
                        String urling = core.BASE_URL + "productos/ing_ws.php?lincodigo=" + lindescrip + "";
                        urling = urling.replaceAll(" ", "%20");

                        StringRequest stringRequest4 = new StringRequest(Request.Method.POST, urling, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                // mTextView.setText("Response is: "+ response.substring(0,500));
                                Log.i("Success", "Todo Belen " + response);

                                costo.setText("");
                                regsan.setText("");
                                barcode.setText("");
                                stock.setText("");
                                pvp1.setText("");
                                pvp2.setText("");
                                descrip.setText("");
                                addProducto.setImageResource(R.drawable.ic_input_add);
                                encodedImage = null;
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run () {
                                        pb.setVisibility(View.GONE);
                                        vp.setVisibility(View.INVISIBLE);
                                    }
                                });

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //mTextView.setText("That didn't work!");
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run () {
                                        pb.setVisibility(View.GONE);
                                        vp.setVisibility(View.INVISIBLE);
                                    }
                                });
                                Toast toast = Toast.makeText(getActivity(), "Se ha producido un error favor de volber a intentar o comunicarse con el Administrador.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, -100);
                                toast.show();

                                error.printStackTrace();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("imgproducto", encodedImage);
                                params.put("costo", costox);
                                params.put("pvp1", pvp1x);
                                params.put("pvp2", pvp2x);
                                params.put("descrip", descripx);
                                params.put("barcode", barr);
                                params.put("regsan", regsann);
                                params.put("stock", stockk);
                                params.put("medcodigo", meddescrip);
                                params.put("precodigo", predescrip);
                                params.put("pais", pais);
                                params.put("marcodigo", mardescrip);
                                params.put("usrcodigo", usrcodigo);
                                params.put("lincodigo", lindescrip);

                                return params;
                            }

            /*stringRequest4.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
                        };
                        queue4.add(stringRequest4);
                            }
                        }).start();
                    }
                } catch (Exception e) {
                    pb.setVisibility(View.GONE);
                    vp.setVisibility(View.INVISIBLE);

                    Toast toast = Toast.makeText(getActivity(), "Se ha producido un error favor de volber a intentar o comunicarse con el Administrador.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, -100);
                    toast.show();
                }

    }
    public void PickFile (){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try{
            startActivityForResult(
                    Intent.createChooser(intent, "Instale un Administrador de Archivos."),
                    PICKER);
        }catch (android.content.ActivityNotFoundException ex){

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        Log.i("aasa",""+requestCode);


        switch (requestCode){

            case PICKER:
                if (resultCode == RESULT_OK) {
                    mHandler = new Handler();

                    foto = "foto";
                    InputStream is = null;
                    BufferedInputStream bis = null;
                    final Uri selectedImageUri = data.getData();
                    String dataFU = getRealPathFromURI(selectedImageUri);
                    Log.i("aasa",""+selectedImageUri.toString());


                                try {
                                photobmp = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver() , Uri.parse(selectedImageUri.toString()));
                                addProducto.setImageBitmap(photobmp);
                                } catch (IOException e) {

                                    e.printStackTrace();
                                }




                    new Thread(new Runnable() {
                        public void run() {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run () {
                            pb.setVisibility(View.VISIBLE);
                            vp.setVisibility(View.VISIBLE);
                        }
                    });


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photobmp.compress(Bitmap.CompressFormat.JPEG, 35, baos);
            byte[] imageBytes = baos.toByteArray();
                            baos = null;
                            photobmp = null;
                            try{

                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                mHandler.post(new Runnable() {
                    @Override
                    public void run () {
                        pb.setVisibility(View.GONE);
                        vp.setVisibility(View.INVISIBLE);
                    }
                });
                onStop();
            }catch (Exception e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run () {
                        pb.setVisibility(View.GONE);
                        vp.setVisibility(View.INVISIBLE);
                    }
                });
                e.printStackTrace();
                Toast toast = Toast.makeText(getActivity(),"La imagen del Producto se ha subido incorrectamente favor de volber a intentar.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, -100);
                toast.show();

            }

            //]  M photobmp = BitmapFactory.decodeStream(bis);


        }
    }).start();


                }
                break;
                default:

                    IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                    if (scanningResult != null) {

                        barcode.setText(scanningResult.getContents());
                        Log.i("aasa",""+requestCode);

                    }
                    break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getRealPathFromURI(Uri contentUri){
    Cursor cursor = null;
    try{
    String[] proj = {MediaStore.Images.Media.DATA};
    cursor = getActivity().getApplicationContext().getContentResolver().query(contentUri,proj,
            null,null,null);
    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);
    }finally {
        if(cursor != null){
            cursor.close();
        }
    }
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
