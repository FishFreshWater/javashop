package com.plusesb.config.ueditor.upload;

import com.plusesb.config.cloud.OSSFactory;
import com.plusesb.config.ueditor.define.AppInfo;
import com.plusesb.config.ueditor.define.BaseState;
import com.plusesb.config.ueditor.define.State;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.*;


@Component
public class StorageManager {
	public static final int BUFFER_SIZE = 8192;


	public static State saveBinaryFile(byte[] data, String path) {
		File file = new File(path);

		State state = valid(file);

		if (!state.isSuccess()) {
			return state;
		}

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bos.write(data);
			bos.flush();
			bos.close();
		} catch (IOException ioe) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true, file.getAbsolutePath());
		state.putInfo( "size", data.length );
		state.putInfo( "title", file.getName() );
		return state;
	}

//	public State saveFileByInputStream(HttpServletRequest request, InputStream is, String path, String picName,
//			long maxSize) {
//
//		State state = null;
//		File tmpFile = getTmpFile();
//
//		try {
//
//			//调用DFS的存储服务上传文件
//			/**
//			 * 此处调用文件上传服务，并获取返回结果返回
//			 */
//			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//			String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
//			MiniAttachmentEntity miniAttachment = SpringUtils.getBean(MiniAttachmentService.class).createAttachment(picName,path, is, "ue", null);
//
//			boolean success = true;
//			//如果上传成功
//			if (success) {
//				state = new BaseState(true);
//				state.putInfo( "state", "SUCCESS" );
//				state.putInfo( "size", tmpFile.length() );
//				state.putInfo( "title",  tmpFile.getName());//文件名填入此处
//				state.putInfo( "group", "");//所属group填入此处
//				state.putInfo( "url",  miniAttachment.getFullUrl());//文件访问的url填入此处
//				tmpFile.delete();
//			}else{
//				state = new BaseState(false, 4);
//				tmpFile.delete();
//			}
//
//			return state;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new BaseState(false, AppInfo.IO_ERROR);
//	}
//
//	public State saveFileByInputStream(InputStream is, String path, String picName) {
//		State state = null;
//
//		File tmpFile = getTmpFile();
//
//
//		try {
//			MiniAttachmentEntity miniAttachment = SpringUtils.getBean(MiniAttachmentService.class).createAttachment(picName,
//					path, is, "ue", null);
//			boolean success = true;
//
//			//如果上传成功
//			if (success) {
//				state = new BaseState(true);
//				state.putInfo( "state", "SUCCESS" );
//				state.putInfo( "size", tmpFile.length() );
//				state.putInfo( "title", picName);//文件名填入此处
//				state.putInfo( "url",  miniAttachment.getFullUrl());//文件访问的url填入此处
//				tmpFile.delete();
//			}else{
//				state = new BaseState(false, 4);
//				tmpFile.delete();
//			}
//
//			return state;
//		} catch (Exception e) {
//		}
//		return new BaseState(false, AppInfo.IO_ERROR);
//	}

	private static File getTmpFile() {
		File tmpDir = FileUtils.getTempDirectory();
		String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
		return new File(tmpDir, tmpFileName);
	}

	private static State saveTmpFile(File tmpFile, String path) {
		State state = null;
		File targetFile = new File(path);

		if (targetFile.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}
		try {
			FileUtils.moveFile(tmpFile, targetFile);
		} catch (IOException e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true);
		state.putInfo( "size", targetFile.length() );
		state.putInfo( "title", targetFile.getName() );
		
		return state;
	}

	private static State valid(File file) {
		File parentPath = file.getParentFile();

		if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
			return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
		}

		if (!parentPath.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}

		return new BaseState(true);
	}
}
