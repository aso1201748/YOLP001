package jp.ac.st.asojuku.yolp001;

import jp.co.yahoo.android.maps.GeoPoint;
import jp.co.yahoo.android.maps.MapActivity;
import jp.co.yahoo.android.maps.MapController;
import jp.co.yahoo.android.maps.MapView;
import jp.co.yahoo.android.maps.PinOverlay;
import jp.co.yahoo.android.maps.navi.NaviController;
import jp.co.yahoo.android.maps.navi.NaviController.NaviControllerListener;
import jp.co.yahoo.android.maps.routing.RouteOverlay;
import jp.co.yahoo.android.maps.routing.RouteOverlay.RouteOverlayListener;
import jp.co.yahoo.android.maps.weather.WeatherOverlay;
import jp.co.yahoo.android.maps.weather.WeatherOverlay.WeatherOverlayListener;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends MapActivity implements LocationListener, WeatherOverlayListener, RouteOverlayListener, NaviControllerListener,
MapView.MapTouchListener{

	LocationManager mLocationManager = null;
	MapView mMapView = null;
	int lastLatitude = 0;
	int lastLongitude = 0;
	NaviController naviController = null;
	RouteOverlay routeOverlay = null;

	WeatherOverlay mWeatherOverlay = null;

	PinOverlay mPinOverlay = null;
	GeoPoint mGoalPos;
	GeoPoint mStartPos;
	static final int MENUITEM_CLEAR = 1;

	@Override
	public void onLocationChanged(Location location){

		double lat = location.getLatitude();
		int latitude = (int)(lat * 1000000);

		double lon = location.getLongitude();
		int longitude = (int)(lon * 1000000);

		if(latitude/1000 != this.lastLatitude/1000 || longitude/1000 != this.lastLongitude/1000){

			GeoPoint gp = new GeoPoint(latitude, longitude);

			MapController c = mMapView.getMapController();

			c.setCenter(gp);

			this.lastLatitude = latitude;
			this.lastLongitude = longitude;

			mPinOverlay = new PinOverlay(PinOverlay.PIN_VIOLET);
			mMapView.getOverlays().add(mPinOverlay);
			mPinOverlay.addPoint(gp,null);

			mStartPos = gp;


		}

	}



	@Override
	public boolean onLongPress(MapView arg0, Object arg1, PinOverlay arg2, GeoPoint arg3) {
		// TODO 自動生成されたメソッド・スタブ
		
		if(routeOverlay!=null){
			routeOverlay.cancel();
		}
		
		mGoalPos = arg3;
		
		mMapView.getOverlays().remove(arg2);
		
		mMapView.getOverlays().remove(routeOverlay);
		
		routeOverlay = new RouteOverlay(this, "dj0zaiZpPTdhZ1hERlB4QU01ViZzPWNvbnN1bWVyc2VjcmV0Jng9Mjg-");
		
		//出発地ピンの吹き出し設定
		routeOverlay.setStartTitle("出発地");

		//目的地ピンの吹き出し設定
		routeOverlay.setGoalTitle("目的地");

		//出発地、目的地、移動手段を設定
		routeOverlay.setRoutePos(mStartPos, mGoalPos, RouteOverlay.TRAFFIC_WALK);

		//RouteOverlayListenerの設定
		routeOverlay.setRouteOverlayListener(this);

		//検索を開始
		routeOverlay.search();

		//MapViewにRouteOverlayを追加
		mMapView.getOverlays().add(routeOverlay);
		
		return false;
	}



	@Override
	public boolean onPinchIn(MapView arg0) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}



	@Override
	public boolean onPinchOut(MapView arg0) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}



	@Override
	public boolean onGoal(NaviController arg0) {
		// TODO 自動生成されたメソッド・スタブ
        //案内処理を継続しない場合は停止させる
        naviController.stop();
		return false;
	}




	@Override
	public boolean onLocationAccuracyBad(NaviController arg0) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}




	@Override
	public boolean onLocationChanged(NaviController arg0) {
		// TODO 自動生成されたメソッド・スタブ

        //目的地までの残りの距離
        double rema_dist = naviController.getTotalDistance();

        //目的地までの残りの時間
        double rema_time = naviController.getTotalTime();

        //出発地から目的地までの距離
        double total_dist = naviController.getDistanceOfRemainder();

        //出発地から目的地までの時間
        double total_time = naviController.getTimeOfRemainder();

        //現在位置
        Location location = naviController.getLocation();

		return false;
	}




	@Override
	public boolean onLocationTimeOver(NaviController arg0) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}




	@Override
	public boolean onRouteOut(NaviController arg0) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void errorUpdateWeather(WeatherOverlay arg0, int arg1) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void finishUpdateWeather(WeatherOverlay arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onProviderDisabled(String provider){

	}

	@Override
	public void onProviderEnabled(String provider){

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras){

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO 自動生成されたメソッド・スタブ
		setContentView(R.layout.activity_main);
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();

		mMapView = new MapView(this, "dj0zaiZpPTdhZ1hERlB4QU01ViZzPWNvbnN1bWVyc2VjcmV0Jng9Mjg-");

		mMapView.setBuiltInZoomControls(true);

		mMapView.setScalebar(true);

		double lat = 35.658516;
		double lon = 139.701773;
		GeoPoint gp = new GeoPoint((int)(lat * 1000000), (int)(lon * 1000000));

		MapController c = mMapView.getMapController();

		c.setCenter(gp);

		c.setZoom(3);

		setContentView(mMapView);


		mLocationManager =
				(LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();

		criteria.setAccuracy(Criteria.ACCURACY_COARSE);

		criteria.setPowerRequirement(Criteria.POWER_LOW);

		String provider = mLocationManager.getBestProvider(criteria, true);


		mLocationManager.requestLocationUpdates(provider, 0, 0, this);

		mWeatherOverlay = new WeatherOverlay(this);

		mWeatherOverlay.setWeatherOverlayListener(this);

		mWeatherOverlay.startAutoUpdate(1);

		mMapView.getOverlays().add(mWeatherOverlay);

		mMapView.setLongPress(true);

		mMapView.setMapTouchListener(this);

		mPinOverlay = new PinOverlay(PinOverlay.PIN_VIOLET);
		mMapView.getOverlays().add(mPinOverlay);
		mPinOverlay.addPoint(gp,null);

		mStartPos = gp;

		//RouteOverlay作成
		routeOverlay = new RouteOverlay(this,"dj0zaiZpPTdhZ1hERlB4QU01ViZzPWNvbnN1bWVyc2VjcmV0Jng9Mjg-");

		routeOverlay.setRoutePinVisible(false);

	}

	@Override
	public boolean errorRouteSearch(RouteOverlay arg0, int arg1) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean finishRouteSearch(RouteOverlay arg0) {
		// TODO 自動生成されたメソッド・スタブ
        //NaviControllerを作成しRouteOverlayインスタンスを設定
        naviController = new NaviController(this,routeOverlay);

        //MapViewインスタンスを設定
        naviController.setMapView(mMapView);

        //NaviControllerListenerを設定
        naviController.setNaviControlListener(this);

        //案内処理を開始
        naviController.start();
		return false;
	}

}
