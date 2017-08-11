package httpSocial;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FacebookLogin {
	String clientId = "your_client_Id";
	String clientSecret = "your_clientSecret";
	String redirect_uri = "redirect_uri=http://localhost:8888/receiveCode";
	String grant_type = "grant_type=authorization_code";
	String result;
	public String connect(String code) {
		
		
		try {
			String apiURL = "https://www.googleapis.com/oauth2/v4/token";
			URL url = new URL(apiURL);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes("code="+ code + "&" + clientId+"&"+clientSecret+"&"+redirect_uri+"&"+grant_type);
			wr.flush();
			wr.close();
			int responseCode = conn.getResponseCode();
			BufferedReader br;
			if(responseCode == 200) {
				System.out.println("성공!!!");
			} else {
				System.out.println("실패!!!!");	
			}
			
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			while((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			System.out.println(response.toString());
			HashMap<String, Object> rs = new ObjectMapper().readValue(response.toString(), HashMap.class);
			String access_token = (String)rs.get("access_token");
			int expires_in = (int)rs.get("expires_in");
			result = access_token;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void useAPI(String token) {
		//https://www.googleapis.com/calendar/v3/users/me/calendarList?key={YOUR_API_KEY}
		
		try {
			String apiURL = "https://www.googleapis.com/calendar/v3/users/me/calendarList";
			System.out.println(apiURL);
			URL url = new URL(apiURL);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("access_token", token);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.flush();
			wr.close();
			int responseCode = conn.getResponseCode();
			BufferedReader br;
			if(responseCode == 200) {
				System.out.println("성공!!!");
			} else {
				System.out.println("실패!!!!");
			}
			
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			while((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			System.out.println(response.toString());
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
