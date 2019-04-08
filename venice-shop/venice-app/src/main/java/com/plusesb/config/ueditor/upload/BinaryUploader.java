package com.plusesb.config.ueditor.upload;

import com.plusesb.config.cloud.OSSFactory;
import com.plusesb.config.ueditor.PathFormat;
import com.plusesb.config.ueditor.define.AppInfo;
import com.plusesb.config.ueditor.define.BaseState;
import com.plusesb.config.ueditor.define.FileType;
import com.plusesb.config.ueditor.define.State;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class BinaryUploader {
    static Logger logger = LoggerFactory.getLogger(BinaryUploader.class);
    
	public static final State save(HttpServletRequest request, Map<String, Object> conf) {
		
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}
		
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("file");

			String savePath = (String) conf.get("savePath");
			String localSavePathPrefix = (String) conf.get("localSavePathPrefix");
			String originFileName = file.getOriginalFilename();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
			savePath = savePath + suffix;
			
			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}
			savePath = PathFormat.parse(savePath, originFileName);
			localSavePathPrefix = localSavePathPrefix + savePath; 
//			String physicalPath = localSavePathPrefix;
			logger.info("BinaryUploader physicalPath:{},savePath:{}",localSavePathPrefix,savePath);
			InputStream is = file.getInputStream();
			
			//在此处调用ftp的上传图片的方法将图片上传到文件服务器
//			StorageManager storageManager = new StorageManager();
			String suffixN = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

			String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffixN);
			is.close();
			State state = new BaseState(true);
			state.putInfo( "state", "SUCCESS" );
			state.putInfo( "code", 200 );
			//文件名填入此处
			state.putInfo( "title",  originFileName);
			//所属group填入此处
			state.putInfo( "group", "");
			//文件访问的url填入此处
			state.putInfo( "url",  url);
			state.putInfo("type", suffix);
			state.putInfo("original", originFileName + suffix);

			return state;
		} catch (Exception e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		}
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
