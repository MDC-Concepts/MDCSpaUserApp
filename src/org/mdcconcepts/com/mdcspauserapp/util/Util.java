package org.mdcconcepts.com.mdcspauserapp.util;

import java.util.ArrayList;

import org.json.JSONArray;

import android.support.v4.app.FragmentManager;


public class Util {

	/**
	 * This is Sign Up User Webservice URL.
	 */
	public static final String Sign_Up_URL = "http://mdcspa.mdcconcepts.com/sign_up__webservice.php";

	/**
	 * This is Login Up User Webservice URL.
	 */
	public static final String Login_URL = "http://mdcspa.mdcconcepts.com/login.php";
	/**
	 * This is NearestSpa Webservice URL.
	 */
	public static final String NearestSpa_URL = "http://mdcspa.mdcconcepts.com/get-top-ten-spa-for-map.php";

	/**
	 * This is Make an Appointment User Webservice URL.
	 */
	public static final String Make_Appointment_URL = "http://mdcspa.mdcconcepts.com/MakeAppointment.php";

	/**
	 * This is Get Appointments User Webservice URL.
	 */
	public static final String Get_Appointments_URL = "http://mdcspa.mdcconcepts.com/getappointments.php";

	/**
	 * This is Get An Single Appointments User Webservice URL.
	 */
	public static final String Get_SingleAppointments_URL = "http://mdcspa.mdcconcepts.com/getsingleappointment.php";

	/**
	 * This is Get An Single Appointments User Webservice URL.
	 */
	public static final String UpdateAppointments_URL = "http://mdcspa.mdcconcepts.com/updateappointment.php";

	/**
	 * This is Get An Single Appointments User Webservice URL.
	 */
	public static final String DeleteAppointments_URL = "http://mdcspa.mdcconcepts.com/deleteappointment.php";

	/**
	 * This is Get An Single Appointments User Webservice URL.
	 */
	public static final String GetUserDetails = "http://mdcspa.mdcconcepts.com/GetUserData.php";

	/**
	 * This is Get An Single Appointments User Webservice URL.
	 */
	public static final String GetAllSpa = "http://mdcspa.mdcconcepts.com/GetAllSpa.php";

	/**
	 * This is Get An Single Appointments User Webservice URL.
	 */
	public static final String GetTherapies = "http://mdcspa.mdcconcepts.com/GetTherapies.php";

	/**
	 * This is Get An Single Appointments User Webservice URL.
	 */
	public static final String GetTherapist = "http://mdcspa.mdcconcepts.com/GetTherapist.php";

	/**
	 * This is Get An Single Appointments User Webservice URL.
	 */
	public static final String GetTherapiesPricing = "http://mdcspa.mdcconcepts.com/GetTherapiesPricing.php";

	/**
	 * This is Get An Single Appointments User Webservice URL.
	 */
	public static final String GetTherapistSchedule = "http://mdcspa.mdcconcepts.com/GetTherapistSchedule.php";

	/**
	 * This is Get An Single Appointments User Webservice URL.
	 */
	public static final String IsTherapistAvailable = "http://mdcspa.mdcconcepts.com/IsTherapistAvailable.php";

	/**
	 * This is Get An Single Appointments User Webservice URL.
	 */
	public static final String send_feedback = "http://mdcspa.mdcconcepts.com/send_feedback.php";

	/**
	 * Get 10 spa's at a time
	 */
	public static final String get_ten_spa="http://mdcspa.mdcconcepts.com/get_ten_spa_webservice.php";
	
	/**
	 * Get Nearest 10 spa's at a time
	 */
	public static final String get_nearest_ten_spa="http://mdcspa.mdcconcepts.com/get_ten_nearest_spa_webservice.php";
	
	/**
	 * Get Nearest 10 spa's at a time
	 */
	public static final String get_ten_therapies="http://mdcspa.mdcconcepts.com/get_ten_therapies_webservice.php";
	
	
	/**
	 * This is Get An Single Appointments User Webservice URL.
	 */
	public static final String sendgift = "http://mdcspa.mdcconcepts.com/sendgift.php";
	

	/**
	 * This is to submit paining area details
	 */
	public static final String sendPainData = "http://mdcspa.mdcconcepts.com/sendPainData.php";
	
	/**
	 * This is to get paining area details
	 */
	public static final String getPainData = "http://mdcspa.mdcconcepts.com/Get_Paining_Areas_webservice.php";
	
	public static final String getDiseaseData = "http://mdcspa.mdcconcepts.com/GetDiseaseWebservice.php";
	
	/**
	 * This is Get An Single Appointments User Webservice URL.
	 */
	public static final String UpdateUserDetails = "http://mdcspa.mdcconcepts.com/UpdateUserProfile_Webservice.php";

	
	public static final String APP_PREFERENCES="MDC_SPA"; 
	
	public static int Uid;
	public static int Appointment_Id;
	public static String Appointment_Time;
	public static String Spa_Name;
	
	public static String User_Name;
	public static String User_Contact_Number;
	public static String User_EmailId;
	public static String User_Address;
	public static String User_DOB;
	public static String User_Anniversary;

	public static String fontPath="Raleway-Light.otf";
	
	public static Boolean isSelected_ImageView_Controller_Activity_BodyPart_Head = false;
	public static Boolean isSelected_ImageView_Controller_Activity_BodyPart_Neck = false;
	public static Boolean isSelected_ImageView_Controller_Activity_BodyPart_Shoulder = false;
	public static Boolean isSelected_ImageView_Controller_Activity_BodyPart_Arm = false;
	public static Boolean isSelected_ImageView_Controller_Activity_BodyPart_Waist = false;
	public static Boolean isSelected_ImageView_Controller_Activity_BodyPart_Back = false;
	public static Boolean isSelected_ImageView_Controller_Activity_BodyPart_Thigh = false;
	public static Boolean isSelected_ImageView_Controller_Activity_BodyPart_Calf = false;
	public static Boolean isSelected_ImageView_Controller_Activity_BodyPart_Sole = false;
	
	public static Boolean isChecked_Heart=false;
	public static Boolean isChecked_Hepatitis=false;
	public static Boolean isChecked_Diabeties=false;
	public static Boolean isChecked_Lung=false;
	public static Boolean isChecked_Hypotension=false;
	public static Boolean isChecked_Skin=false;
	public static Boolean isChecked_BP=false;
	public static Boolean isChecked_Arthritis=false;
	public static Boolean isChecked_Pregnant=false;
	public static Boolean isChecked_Other=false;
	
	
	public static JSONArray jsonPainArray= new JSONArray();
	
	public static JSONArray jsonDiseaseArray= new JSONArray();
	
	
	public static ArrayList<String> PainAreas = new ArrayList<String>();
	public static ArrayList<String> UserDisease = new ArrayList<String>();
 	
	public static FragmentManager fm;
	 public static String DISTANCE="";
}
