import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class BabyNames {

	public static void main(String[] args) {
		//Delete data file if already exists
		File dataFile = new File("data.txt");
		if (dataFile.exists()) {
			dataFile.delete();
		}
		
		//Get input from user
		int year = 0;
		String gender = "";
		String name = "";
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter the year: ");
		year = input.nextInt();
		input.nextLine();
		System.out.print("Enter the gender: ");
		gender = input.nextLine();
		System.out.print("Enter the name: ");
		name = input.nextLine();
		
		//Get file
		try {
			URL url = new URL("http://liveexample.pearsoncmg.com/data/babynamesranking" + year + ".txt");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			int rc = conn.getResponseCode();
			
			if (rc == HttpURLConnection.HTTP_OK) {
	            InputStream is = conn.getInputStream();
	            FileOutputStream os = new FileOutputStream(new File("data.txt"));
	            
	            int ds = is.read();
	            while (ds != -1) {
	                os.write(ds);
	                ds = is.read();
	            }
	 
	            os.close();
	            is.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Open file
		Scanner dataScanner = null;
		
		try {
			dataScanner = new Scanner(new File("data.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("No data file found for the year " + year);
			System.exit(1);
		}
		
		//Read data into ArrayList
		ArrayList<String> data = new ArrayList<>();
		
		while(dataScanner.hasNext()) {
			data.add(dataScanner.next());
		}
		
		//Look for data
		boolean isFound = false;
		
		for(int i = 0; i < data.size(); i++) {
			if (data.get(i).equals(name)) {
				if (gender.equals("M")) {
					if (Integer.parseInt(data.get(i - 1)) == 1) {
						System.out.println(name + " is ranked #" + data.get(i - 1) + " in year " + year);
						isFound = true;
					}else if (Integer.parseInt(data.get(i - 1)) - Integer.parseInt(data.get(i - 6)) == 1) {
						System.out.println(name + " is ranked #" + data.get(i - 1) + " in year " + year);
						isFound = true;
					}
				} else if (gender.equals("F")) {
					if (Integer.parseInt(data.get(i - 3)) == 1) {
						System.out.println(name + " is ranked #" + data.get(i - 3) + " in year " + year);
						isFound = true;
					}else if (Integer.parseInt(data.get(i - 3)) - Integer.parseInt(data.get(i - 8)) == 1) {
						System.out.println(name + " is ranked #" + data.get(i - 3) + " in year " + year);
						isFound = true;
					}
				}
			}
		}
		
		if (!isFound) {
			System.out.println("The name " + name + " is not ranked in year " + year);
		}
	}
}