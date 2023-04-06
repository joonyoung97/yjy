package com.jylove.mpc.mpc.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID(); //랜덤 이미지 이름 생성, 중복이름 방지위해 - 고유한 식별자를 만들어주기 위한 UUID클래스
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString()+extension; //uuid와 원래이름 조합해서 저장할 이름
        String fileUploadFullurl = uploadPath + "/" +savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullurl); //파일 저장 객체
        fos.write(fileData); //파일쓰기
        fos.close();

        return savedFileName; //업로드된 파일 이름 반환
    }

    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath); //파일이 저장된 경로로 파일 객체 생성
        if(deleteFile.exists()){ //경로에 파일이 있다면 삭제
            deleteFile.delete();
            log.info("파일을 삭제 하였습니다.");
        }
        else{
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
