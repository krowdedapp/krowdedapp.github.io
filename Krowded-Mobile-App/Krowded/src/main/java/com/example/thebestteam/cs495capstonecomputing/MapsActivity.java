package com.example.thebestteam.cs495capstonecomputing;

import android.R.layout;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.google.android.gms.location.LocationServices.getGeofencingClient;


public class MapsActivity extends FragmentActivity
        implements
        android.location.LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {

    static User user;


    public static ArrayList<Geofence> geofencesTriggered =  new ArrayList<>();
    public static int transitionType = -1;

    public static String placeName = "Rounders"; // Holds name of last/current business

    public static boolean notificationDisplayed = false;
    private static boolean wereMarkersPlaced = false;

    //holds the lat and long in locations 0 and 1 respectively
    ArrayList<Double> currentLocation = new ArrayList<>(Arrays.asList(0.0, 0.0));

    private static final long GEO_DURATION = 60 * 60 * 1000;//1 day
    private static final float GEOFENCE_RADIUS = 100.0f; // in meters

    private static final String TAG = "MapsActivity";

    private GoogleMap mGoogleMap;
    Spinner mSprPlaceType;

    String[] mPlaceType=null;
    String[] mPlaceTypeName=null;

    double mLatitude=0;
    double mLongitude=0;
    Button btnFind;
    ImageButton listViewIB;

    HashMap<String, String> mMarkerPlaceLink = new HashMap<String, String>();

    private static DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        user = LoginActivity.user;
        if(user != null) {
            Log.e("!!", "onCreate: " + user.getEmail() );
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Array of place types
        mPlaceType = getResources().getStringArray(R.array.place_type);

        // Array of place type names
        mPlaceTypeName = getResources().getStringArray(R.array.place_type_name);

        // Creating an array adapter with an array of Place types
        // to populate the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, layout.simple_spinner_dropdown_item, mPlaceTypeName);

        // Getting reference to the Spinner
        mSprPlaceType = (Spinner) findViewById(R.id.spr_place_type);

        // Setting adapter on Spinner to set place types
        mSprPlaceType.setAdapter(adapter);

        //Getting reference to ListViewIB
        listViewIB = findViewById(R.id.ListViewIB);

        // Getting reference to Find Button
        btnFind = (Button) findViewById(R.id.btn_find);

        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        } else { // Google Play Services are available

            // Getting reference to the SupportMapFragment
            SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            fragment.getMapAsync(this);
        }

        View legend=findViewById(R.id.legend);
        legend.bringToFront();

        //Create on click listener to switch to list view
        listViewIB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PlaceJSONParser.allPlaces.size() == 0)
                   Toast.makeText(getBaseContext(),"You have not found any places yet",Toast.LENGTH_SHORT).show();
                else
                    startActivity(new Intent(MapsActivity.this, LViewActivity.class));
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        //reloading the map if it already existed
        MapStateManager mgr = new MapStateManager(this);
        CameraPosition position = mgr.getSavedCameraPosition();

        if(position != null){
            CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
            mGoogleMap.moveCamera(update);
            mGoogleMap.setMapType(mgr.getSavedMapType());
        }


        //checking permissions
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(MapsActivity.this, "You have to accept to 'enjoy' all of Krowded's 'services'!", Toast.LENGTH_LONG).show();
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mGoogleMap.setMyLocationEnabled(true);
            }
        }

        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location From GPS
        Location location = locationManager.getLastKnownLocation(provider);

        if(location!=null){
            onLocationChanged(location);//maybe crashes here?
        }
        locationManager.requestLocationUpdates(provider, 2000, 0, this);


        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                Intent intent = new Intent(MapsActivity.this, PlaceDetailsActivity.class);
                String reference = mMarkerPlaceLink.get(arg0.getId());
                intent.putExtra("reference", reference);
                startActivity(intent);
            }
        });


        if(notificationDisplayed && wereMarkersPlaced || getIntent().hasExtra("back")) {
            placeMarkers();
            notificationDisplayed = false;
        }

        if(getIntent().hasExtra("triggered"))
        //    if(getIntent().hasExtra("triggered") || getIntent().hasExtra("triggered_fences"))
        {
            Intent i = new Intent(this,DisplayNotificationActivity.class);
            int temp = getIntent().getIntExtra("transition",-1);
            i.putExtra("transition",temp);

            startActivity(i);
        }

        // Setting click event lister for the find button
        btnFind.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                wereMarkersPlaced = true;
                placeMarkers();
            }
        });


        Button loginButton = findViewById(R.id.btnLogin);
        if (user == null) loginButton.setText("Login");
        else loginButton.setText("Profile");

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    private void placeMarkers()
    {

        int selectedPosition = mSprPlaceType.getSelectedItemPosition();
        String type = mPlaceType[selectedPosition];

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location="+mLatitude+","+mLongitude);
        sb.append("&radius=5000");
        sb.append("&types="+type);
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyBb4_AGSb9PWWsv3AfQQpvJMZpGV9oajiQ");

        // Creating a new non-ui thread task to download Google place json data
        PlacesTask placesTask = new PlacesTask();

        // Invokes the "doInBackground()" method of the class PlaceTask
        placesTask.execute(sb.toString());
    }

    /** A method to download json data from url */
    @SuppressLint("LongLogTag")
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

            //No one will ever see this comment.
        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    /* to download Google Places */
    private class PlacesTask extends AsyncTask<String, Integer, String> {

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){
            ParserTask parserTask = new ParserTask();

            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }
    }

    /* parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String,String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                /* Getting the parsed data as a List construct */
                places = placeJsonParser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }

            startGeofences();
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        //called before the JsonParser is called
        @Override
        protected void onPostExecute(List<HashMap<String,String>> list){

            // Clears all the existing markers
            mGoogleMap.clear();

            for(int i=0;i<list.size();i++){



                // Getting a place from the places list
                final HashMap<String, String> hmPlace = list.get(i);

                // Getting latitude of the place
                final double lat = Double.parseDouble(hmPlace.get("lat"));

                // Getting longitude of the place
                final double lng = Double.parseDouble(hmPlace.get("lng"));

                // Getting name
                final String name = hmPlace.get("place_name");

                // Getting vicinity
                String vicinity = hmPlace.get("vicinity");

                final LatLng latLng = new LatLng(lat, lng);
                final String placeID = hmPlace.get("place_name");

                final HashMap<String,String> foo = hmPlace;

                // Database calls for each location/marker
                mRoot.child("location").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("hmPlace ID:",placeID);


                        // if location has no entry in Firebase, create one
                        if (!dataSnapshot.hasChild(placeID)) {

                            Log.d("T A G","placeID obj is null in Firebase");
                            mRoot.child("location").child(placeID).child("Details").setValue(foo);
                            mRoot.child("location").child(placeID).child("Population").setValue(0);
                        }


                        // Retrieve population and Krowdedness from FB and color marker accordingly
                        double krowdedScore = 0;

                        if (dataSnapshot.child(placeID).hasChild("krowdedness")) {
                            Double.parseDouble(dataSnapshot.child(placeID).child("krowdedness").getValue(String.class));
                       }

                        if (dataSnapshot.child(placeID).hasChild("Population")) {
                            krowdedScore = krowdedScore + dataSnapshot.child(placeID).child("Population").getValue(Long.class);
                        }

                        Log.d("KROWDED SCORE:",String.valueOf(krowdedScore));


                        // Creating a marker
                        MarkerOptions markerOptions = new MarkerOptions();

                        // Setting the position for the marker
                        markerOptions.position(latLng);

                        // Setting the title for the marker.
                        //This will be displayed on taping the marker
                        markerOptions.title(name);


                        //set marker color based on krowdedness
                        if (krowdedScore > 10)
                        {
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        }
                        else if (krowdedScore > 5) { markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)); }
                        else
                        {
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                        }

                        // Placing a marker on the touched position
                        Marker m = mGoogleMap.addMarker(markerOptions);

                        // Linking Marker id and place reference
                        mMarkerPlaceLink.put(m.getId(), hmPlace.get("reference"));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        LatLng latLng = new LatLng(mLatitude, mLongitude);

        currentLocation.set(0,location.getLatitude());
        currentLocation.set(1,location.getLongitude());

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    public void loginButton(View view) {
        if (user != null) {
            startActivity(new Intent(MapsActivity.this, ProfileActivity.class));
        } else {
            startActivity(new Intent(MapsActivity.this, LoginActivity.class));
        }
    }


    /**
     * p
     *  a
     *   n
     *    c
     *     a
     *      k
     *       e
     *        s
     *         !
     **/

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "Google Api Client Connected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Google Connection Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Connection Failed:" + connectionResult.getErrorMessage());
    }

    //
    // Create a Geofence
    private Geofence createGeofence( ArrayList<Double> latLong, float radius, String geofenceName ) {
        Log.d(TAG, "createGeofence");
        return new Geofence.Builder()
                .setRequestId(geofenceName)
                .setCircularRegion( latLong.get(0), latLong.get(1), radius)
                .setExpirationDuration( GEO_DURATION )//change to full day probably
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |  Geofence.GEOFENCE_TRANSITION_EXIT )
                .build();
    }


    // Create a Geofence Request
    private GeofencingRequest createGeofenceRequest(Geofence geofence ) {
        Log.d(TAG, "createGeofenceRequest");
        return new GeofencingRequest.Builder()
                .setInitialTrigger( GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence( geofence )
                .build();
    }



    private PendingIntent geoFencePendingIntent;
    private final int GEOFENCE_REQ_CODE = 0;
    private PendingIntent createGeofencePendingIntent() {
        Log.d(TAG, "createGeofencePendingIntent");
        if ( geoFencePendingIntent != null )
            return geoFencePendingIntent;

        Intent intent = new Intent( this, GeofenceTransitionService.class);

        return PendingIntent.getService(
                this, GEOFENCE_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT );
    }


    // Add the created GeofenceRequest to the device's monitoring list
    private void addGeofence(GeofencingRequest request) {
        Log.d(TAG, "addGeofence");
        GeofencingClient fence = getGeofencingClient(this);
        //check permissions
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
            //add fence to list of fences
            fence.addGeofences(request,createGeofencePendingIntent());
    }


    // create a geofence for each location
    public void startGeofences() {
        FencesCreated.removeOldFences();
        String name = "NAME NOT FOUND";
        double lat = 0.0;
        double lng = 0.0;
        ArrayList<Double> latlng = new ArrayList<>(2);
        latlng.add(0.0);
        latlng.add(0.0);
        Log.i(TAG, "startGeofence()");

          for(JSONObject place : PlaceJSONParser.allPlaces) {
            try {
                name = place.getString("name");
                String templat = place.getJSONObject("geometry").getJSONObject("location").getString("lat");
                String templng = place.getJSONObject("geometry").getJSONObject("location").getString("lng");
                lat = Double.parseDouble(templat);
                lng = Double.parseDouble(templng);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(!FencesCreated.isIn(name)) {
                latlng.set(0, lat);
                latlng.set(1, lng);
                Geofence geofence = createGeofence(latlng, GEOFENCE_RADIUS, name);
                FencesCreated.storeFence(geofence,latlng);
                GeofencingRequest geofenceRequest = createGeofenceRequest(geofence);
                addGeofence(geofenceRequest);
            }
        }
    }

    public static class MapStateManager {

        private static final String LONGITUDE = "longitude";
        private static final String LATITUDE = "latitude";
        private static final String ZOOM = "zoom";
        private static final String BEARING = "bearing";
        private static final String TILT = "tilt";
        private static final String MAPTYPE = "MAPTYPE";

        private static final String PREFS_NAME ="mapCameraState";

        private SharedPreferences mapStatePrefs;

        public MapStateManager(Context context) {
            mapStatePrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }


        public CameraPosition getSavedCameraPosition() {
            double latitude = mapStatePrefs.getFloat(LATITUDE, 0);
            if (latitude == 0) {
                return null;
            }
            double longitude = mapStatePrefs.getFloat(LONGITUDE, 0);
            LatLng target = new LatLng(latitude, longitude);

            float zoom = mapStatePrefs.getFloat(ZOOM, 0);
            float bearing = mapStatePrefs.getFloat(BEARING, 0);
            float tilt = mapStatePrefs.getFloat(TILT, 0);

            CameraPosition position = new CameraPosition(target, zoom, tilt, bearing);
            return position;
        }

        public int getSavedMapType() {
            return mapStatePrefs.getInt(MAPTYPE, GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    public static class FencesCreated {

        private static ArrayList<Geofence> allFences = new ArrayList<>();
        private static ArrayList<ArrayList<Double>> geofenceLocations = new ArrayList<>();
        private static Geofence triggeredFence = null;

        public static Geofence getTriggeredFence()
        { return triggeredFence; }

        public static void setTriggeredFence(Geofence g)
        { triggeredFence = g; }


        public static void storeFence(Geofence f, ArrayList<Double> lat_lng)
        {
            allFences.add(f);
            geofenceLocations.add(lat_lng);
        }

        public static ArrayList<Geofence> getStoredFences()
        { return allFences; }

        public static ArrayList<ArrayList<Double>> getFenceLocations()
        { return geofenceLocations; }



        public static Boolean isIn(String name)
        {
            for(Geofence fence : allFences){
                if(fence.getRequestId() == name)
                    return true;
            }
            return false;
        }

        //need to remove the corresponding ,locations aswell
        //since they are symetric arrays
        public static void removeOldFences()
        {
            //removes all fences that are not near you
            for(Geofence fence : allFences) {
                if (!isInPlaces(fence.getRequestId())) {
                    geofenceLocations.remove(allFences.indexOf(fence));
                    allFences.remove(fence);
                }
            }
        }

        private static Boolean isInPlaces(String name)
        {
            for(JSONObject place : PlaceJSONParser.allPlaces){
                try {
                    if(!isIn(place.getString("name"))) {
                        return true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }
}
