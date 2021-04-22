package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        LocalDateTime eight = LocalDateTime.of(2021, 1, 1, 8, 0, 0, 0);
        boolean running = true;
        while(running)



            //if (LocalDateTime.now().getHour() == eight.getHour()) {
            if(LocalDateTime.now().equals(LocalDateTime.now())){

                String urlStringValdosta = "https://pro.openweathermap.org/data/2.5/forecast/hourly?zip=" + "31601,US"+ "&appid=170d75a94abf9978637e3c73a8952d8a&units=imperial";
                String urlStringMoultrie = "https://pro.openweathermap.org/data/2.5/forecast/hourly?zip=" + "31768,US" + "&appid=6b071478ac2009a6ab81f316636fe01f&units=imperial";
                String urlStringThomasville = "https://pro.openweathermap.org/data/2.5/forecast/hourly?zip=" + "31792,US" + "&appid=6b071478ac2009a6ab81f316636fe01f&units=imperial";

                StringBuilder result = new StringBuilder();
                try {

                    URL url = new URL(urlStringValdosta);
                    URLConnection conn = url.openConnection();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }
                    System.out.println(result);
                    rd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //System.out.println(LocalDateTime.now().toEpochSecond(new TimeZone()));
                Map<String, Object> respMap = jsonToMap(result.toString());
                Map<String, Object> mainMap = jsonToMap(respMap.get("list").toString());
                Map<String, Object> windMap = jsonToMap(mainMap.get("main").toString());
                //Map<String, Object> conditionMap = jsonToMap(respMap.get("weather").toString());
                ArrayList<Map<String, Object>> weathers = (ArrayList<Map<String, Object>>) respMap.get("weather");
                Map<String, Object> weatherMap = weathers.get(0);

                String Temperature = "Current Temperature: " + mainMap.get("temp");
                String Humidity = "Current Humidity: " + mainMap.get("humidity");
                String WindSpeeds = "Wind Speeds: " + windMap.get("speed");
                //String Condition = "Condition: " + conditionMap.get("main") + " - " + conditionMap.get("description");
                String Weather = "Weather Condition: " + weatherMap.get("main") + " - " + weatherMap.get("description");

                try{
                    SimpleEmail email = new SimpleEmail();
                    email.setHostName("smtp.office365.com");
                    email.setAuthenticator(new DefaultAuthenticator("alekanandy@hotmail.com", "Andrei82@"));

                    email.addTo("alekanandy@hotmail.com");
                    email.setFrom("alekanandy@hotmail.com", "Weather Alert");
                    email.setSubject("Weather Alert");
                    email.setMsg("Valdosta, Georgia" + "- Current Weather: \n" + Temperature + "\n" + Humidity + "\n" + WindSpeeds + "\n" + Weather);
                    email.setSmtpPort(587);
                    email.setStartTLSEnabled(true);
                    email.send();

                }catch(Exception e){
                    e.printStackTrace();
                }

                try {

                    URL url = new URL(urlStringValdosta);
                    URLConnection conn = url.openConnection();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }
                    rd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Map<String, Object> respMapThoamsville = jsonToMap(result.toString());
                Map<String, Object> mainMapThomasville = jsonToMap(respMap.get("main").toString());
                Map<String, Object> windMapThomasville = jsonToMap(respMap.get("wind").toString());
                //Map<String, Object> conditionMap = jsonToMap(respMap.get("weather").toString());
                ArrayList<Map<String, Object>> weathersThomasville = (ArrayList<Map<String, Object>>) respMap.get("weather");
                Map<String, Object> weatherMapThoamsville = weathers.get(0);

                String TemperatureThomasville = "Current Temperature: " + mainMap.get("temp");
                String HumidityThomasville = "Current Humidity: " + mainMap.get("humidity");
                String WindSpeedsThomasville = "Wind Speeds: " + windMap.get("speed");
                //String Condition = "Condition: " + conditionMap.get("main") + " - " + conditionMap.get("description");
                String WeatherThomasville = "Weather Condition: " + weatherMap.get("main") + " - " + weatherMap.get("description");

                try{
                    SimpleEmail email = new SimpleEmail();
                    email.setHostName("smtp.office365.com");
                    email.setAuthenticator(new DefaultAuthenticator("alekanandy@hotmail.com", "Andrei82@"));

                    email.addTo("alekanandy@hotmail.com");
                    email.setFrom("alekanandy@hotmail.com", "Weather Alert");
                    email.setSubject("Weather Alert");
                    email.setMsg("Thomasville, Georgia" + "- Current Weather: \n" + TemperatureThomasville + "\n" + HumidityThomasville + "\n" + WindSpeedsThomasville + "\n" + WeatherThomasville);
                    email.setSmtpPort(587);
                    email.setStartTLSEnabled(true);
                    email.send();
                    break;
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
            public static Map<String, Object> jsonToMap (String str){
                Map<String, Object> map = new Gson().fromJson(
                        str, new TypeToken<HashMap<String, Object>>() {
                        }.getType()
                );
                return map;
            }
            public static String parseData(String json){
                LocalDateTime nowTimeLocalDateTime = LocalDateTime.now();
                long nowTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
                LocalDateTime nineOClock = LocalDateTime.of(nowTimeLocalDateTime.getYear(), nowTimeLocalDateTime.getMonthValue(), nowTimeLocalDateTime.getDayOfMonth(), 9, 0, 0, 0);
                long nineOClockMillis = nineOClock.toEpochSecond(ZoneOffset.UTC);
                int index = json.indexOf((int) nineOClockMillis);
                int endindex = json.indexOf("\"dt\":");
                String data = json.substring(index, endindex);

                return data;
            }
            public static String parseTemp(String data){
                int index = data.indexOf("\"temp\":");
                int indexEnd = data.indexOf(",");

                String temp = data.substring(index, indexEnd);
                return temp;
            }
            public static String parseWindSpeed(String data){
                int index = data.indexOf("\"speed\":");
                String[] split = data.substring(0,index).split(",");
                int endIndex = split[0].indexOf(",");
                String windSpeed =
            }
}


