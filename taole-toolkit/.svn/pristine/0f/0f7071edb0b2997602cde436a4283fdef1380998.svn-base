package com.taole.toolkit.filesystem.manager;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.taole.toolkit.filesystem.domain.FastDFSFile;

@Component
public class FileManager {

	/**
	 * 已fastDFS形式操作文件
	 */
	private static String FILE_UPLOAD_TYPE_FASTDFS = "fastDFS";
	
	@Value(value = "#{configProperties['fdfs.connect_timeout']}")
	private String connectTimeout;
	
	@Value(value = "#{configProperties['fdfs.network_timeout']}")
	private String networkTimeout;
	
	@Value(value = "#{configProperties['fdfs.charset']}")
	private String charset;
	
	@Value(value = "#{configProperties['fdfs.http.tracker_http_port']}")
	private String trackerHttpPort;
	
	@Value(value = "#{configProperties['fdfs.tracker_server']}")
	private String trackerServerUrl;
	
	@Value(value = "#{configProperties['filesystem.upload.type']}")
	private String fileSystemUploadType;
	
    private static TrackerClient trackerClient;
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;
    private static StorageClient storageClient;
    
    @PostConstruct
	public void init() {
    	try {
    		if(isFastDFSForFileSystem()){
    			
    			ClientGlobal.init(Integer.parseInt(connectTimeout), Integer.parseInt(networkTimeout), 
    					charset, Integer.parseInt(trackerHttpPort), trackerServerUrl);

                trackerClient = new TrackerClient();
                trackerServer = trackerClient.getConnection();

                storageClient = new StorageClient(trackerServer, storageServer);
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
	 * 获取是否是采用fastDFS形式操作文件
	 * @return
	 */
	public boolean isFastDFSForFileSystem(){
		if(StringUtils.isNotBlank(fileSystemUploadType)){
			if(fileSystemUploadType.equals(FILE_UPLOAD_TYPE_FASTDFS)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

    /**
     * <strong>方法概要： 文件上传</strong> <br>
     * <strong>创建时间： 2016-9-26 上午10:26:11</strong> <br>
     * 
     * @param FastDFSFile
     *            file
     * @return fileAbsolutePath
     * @author Zhang Qian Long
     */
    public String upload(String groupName, FastDFSFile file,NameValuePair[] valuePairs) {
    	
        String[] uploadResults = null;
        try {
        	ClientGlobal.init(Integer.parseInt(connectTimeout), Integer.parseInt(networkTimeout), 
					charset, Integer.parseInt(trackerHttpPort), trackerServerUrl);

        	TrackerClient trackerClient = new TrackerClient();
        	TrackerServer trackerServer = trackerClient.getConnection();

        	StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        	
            uploadResults = storageClient.upload_file(groupName, file.getContent(),file.getExt(), valuePairs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtils.isEmpty(groupName))
        		groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];

        String fileAbsolutePath = "/" + groupName + "/" + remoteFileName;
        return fileAbsolutePath;
    }
    
    /**
     * 文件下载
     * @param groupName
     * @param remoteFileName
     * @param specFileName
     * @return
     * @throws MyException 
     * @throws IOException 
     */
    public byte[] download(String groupName, String remoteFileName) throws IOException, MyException {
        byte[] content = storageClient.download_file(groupName, remoteFileName);
        
        return content;
//        IOUtils.write(content, new FileOutputStream("D:/"+UUID.generateUUID()+".jpg"));
//        HttpHeaders headers = new HttpHeaders();
//        try {
//            content = storageClient.download_file(groupName, remoteFileName);
//            headers.setContentDispositionFormData("attachment",  new String(specFileName.getBytes("UTF-8"),"iso-8859-1"));
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return new ResponseEntity<byte[]>(content, headers, HttpStatus.CREATED);
    }
    
    public FileInfo getFileInfo(String groupName,
            String remoteFileName){
    	FileInfo fileInfo = null;
    	try {
    		fileInfo =  storageClient.get_file_info(groupName, remoteFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return fileInfo;
    }
}
