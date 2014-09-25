package github.com.leonzhouwei.java.microservice;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPOutputStream;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GzipController {
	
	private static final String CHARSET_NAME_UTF8 = "utf-8"; 
	
	@RequestMapping("/gzip")
	public String query() throws Exception {
		String json = readJsonFile();
//		System.out.println("oops:     original json size: " + json.length());
//		byte[] output = compress(json.getBytes(CHARSET_NAME_UTF8)); 
//		System.out.println("oops: compressed output size: " + output.length);
		return json;
	}
	
	private String readJsonFile() throws IOException {
		String result = null;
		BufferedReader br = null;
		try {
			InputStream is = new FileInputStream("test.json");
			br = new BufferedReader(new InputStreamReader(is , CHARSET_NAME_UTF8));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line=br.readLine()) != null) {
				line = line.trim();
				sb.append(line);
			}
			result = sb.toString();
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return result;
	}
	
	private byte[] compress(byte[] data) throws Exception {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        // 压缩  
        GZIPOutputStream gos = new GZIPOutputStream(baos);  
        gos.write(data, 0, data.length);  
        gos.finish();  
        byte[] output = baos.toByteArray();  
        baos.flush();  
        baos.close();  
        return output;  
    } 
	
	public static void main(String[] args) throws Exception {
		GzipController gc = new GzipController();
		String json = gc.readJsonFile();
		System.out.println(json);
//		System.out.println("oops:     original json size: " + json.length());
//		byte[] output = gc.compress(json.getBytes(CHARSET_NAME_UTF8)); 
//		System.out.println("oops: compressed output size: " + output.length);
	}

}
