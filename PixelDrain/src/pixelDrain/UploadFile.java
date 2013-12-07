package pixelDrain;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

public class UploadFile {
	public static String upload(File file, String ext) throws IOException{
		PopupWindow.notify("Uploading...", 3000);
		
		HttpClient httpClient = new DefaultHttpClient();
	    httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
	    ((AbstractHttpClient)httpClient).setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
	    
	    HttpPost httppost = new HttpPost("http://pixeldrain.site40.net/upload.php?ext=" + ext);

	    MultipartEntity mpEntity = new MultipartEntity();
	    ContentBody cbFile = new FileBody(file, "file/file");
	    mpEntity.addPart("capture", cbFile);

	    httppost.setEntity(mpEntity);
	    System.out.println("executing request " + httppost.getRequestLine());
	    HttpResponse response = httpClient.execute(httppost);
	    HttpEntity resEntity = response.getEntity();
	    
	    httpClient.getConnectionManager().shutdown();

	    System.out.println(response.getStatusLine());
	    
	    String responseString = EntityUtils.toString(resEntity);
	    
	    System.out.println("Done, response:");
	    System.out.println(responseString);
	    
	    if (responseString.contains("SUCCESS")) {
	    	PopupWindow.notify("Your file has been copied to your clipboard,<br>Press 'CTRL + V' to paste", 8000);
	    	return responseString.replace("SUCCESS: ", "");
	    }else if(responseString.contains("ERROR")){
	    	PopupWindow.notify(responseString, 10000);
	    	return "";
	    }else{
	    	return responseString;
	    }
	}
}
