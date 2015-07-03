package gaurav.locationpractice;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {


    final static String tag = "MainActivityFragment";
    GoogleApiClient mGoogleapiclient;
    LocationRequest mLocationRequest;
    TextView location_text;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        location_text = (TextView) v.findViewById(R.id.location);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGoogleapiclient = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API).
                addConnectionCallbacks(MainActivityFragment.this).addOnConnectionFailedListener(MainActivityFragment.this).build();
    }

    @Override
    public void onStart() {
        Log.i(tag, "Inside On Start");
        mGoogleapiclient.connect();
        super.onStart();

    }

    @Override
    public void onStop() {
        Log.i(tag, "Inside On Stop");
        super.onStop();
        mGoogleapiclient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(tag, "Google Api Connected");
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(2 * 1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleapiclient, mLocationRequest, MainActivityFragment.this);
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i(tag, "OnConnectionSuspended");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(tag, "Inside Location Changed");
        location_text.setText("Latitude = " + location.getLatitude() + " , Longitude = " + location.getLongitude() + " , Accuracy = " + location.getAccuracy() + " , Altitude = " + location.getAltitude());


    }


}
