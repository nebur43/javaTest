package es.ocr.pruebas.pruebasocr;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EcontratoUtils {
	
	// pruebas
	public static void main(String[] args) {
		try {
			String imagen64 = "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";
			writeImageFile(stringBase64ToByte(imagen64), new File ("c:\\tmp\\imagen.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static byte[] stringBase64ToByte(String base64Image) {
		return Base64Utils.decodeFromString(base64Image);
	}
	
	public static void writeImageFile(byte[] bytes, File file) throws IOException {
		 OutputStream os = new FileOutputStream(file); 

		 // Starts writing the bytes in it 
		 os.write(bytes); 
     
	     // Close the file 
	     os.close(); 
	}
	

	public static String jsonPrettyPrint(String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Object jsonObject = mapper.readValue(json, Object.class);
			String jsonPrettyprint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
			return jsonPrettyprint;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static String inputStreamToString(InputStream inputStream) throws IOException {
		 
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line);
		}
			 
		return stringBuilder.toString();
	}
	
	public static InputStream stringToInputStream(String string) {
		InputStream stream = new ByteArrayInputStream(string.getBytes());
		return stream;
	}
	
	public static byte[] readImageFile(String path) throws IOException {
		
		File file = new File(path);		
		byte[] array = new byte[(int) file.length()];

		FileInputStream fis = new FileInputStream(file);
		try {
			if (fis.read(array)<=0) {
				throw new IOException("Archivo vac�o: "+path) ;
			}
		} finally {
			fis.close();
		}
		
		
		return array;		
	}
	
	public static byte[] getBytes(BufferedImage originalImage ) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( originalImage, "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}
	
	public static BufferedImage getBufferedImage(byte[] image) throws IOException {
		InputStream in = new ByteArrayInputStream(image);
		BufferedImage bImageFromConvert = ImageIO.read(in);
		return bImageFromConvert;
		
	}
	
}