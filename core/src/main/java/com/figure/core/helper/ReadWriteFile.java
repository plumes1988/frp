package com.figure.core.helper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteFile {

	
	public static List<String> read(String fileUrl){
		List<String> data = new ArrayList<String>();
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(fileUrl)), StandardCharsets.UTF_8);
			BufferedReader bufferedReader = new BufferedReader(isr);

			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				data.add(line);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public static List<String> read(InputStream input){
		List<String> data = new ArrayList<String>();
		try {
			InputStreamReader isr = new InputStreamReader(input, StandardCharsets.UTF_8);
			BufferedReader bufferedReader = new BufferedReader(isr);

			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				data.add(line);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	public static void write(String fileUrl,List<String> dataList) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileUrl);
			for (String data : dataList) {
				fileOutputStream.write(data.getBytes());
				fileOutputStream.write("\n".getBytes());
			}
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}
}
